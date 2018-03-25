package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.entity.client.ClientTbOrder;
import com.alienlab.wa17.entity.client.ClientTbOrderDetail;
import com.alienlab.wa17.entity.client.ClientTbShop;
import com.alienlab.wa17.entity.client.dto.OrderDetailDto;
import com.alienlab.wa17.entity.client.dto.OrderDto;
import com.alienlab.wa17.entity.client.dto.OrderPrintDto;
import com.alienlab.wa17.service.CustomService;
import com.alienlab.wa17.service.InventoryService;
import com.alienlab.wa17.service.OrderService;
import javassist.bytecode.ExceptionsAttribute;
import javassist.bytecode.analysis.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 橘 on 2017/3/31.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    DaoTool daoTool;

    @Autowired
    CustomService customService;

    @Override
    public OrderPrintDto addOrder(int account,long shopId,JSONObject orderinfo) throws Exception {
        JSONArray details=orderinfo.getJSONArray("details");
        long customId=orderinfo.getLong("cus_id");
        ClientTbCustom custom=(ClientTbCustom)daoTool.getOne(ClientTbCustom.class,account,customId);
        if(custom==null){
            throw new Exception("客户不存在，客户编码"+customId);
        }
        Float amount=0f;
        Float totalPrice=0f;
        Float recharge=0f;
        Float odd=0f;
        //遍历明细
        if(details!=null&&details.size()>0){
            for(int i=0;i<details.size();i++){
                JSONObject detail=details.getJSONObject(i);
                String productName=detail.getString("detail_name");
                //处理抹零请求
                if(productName.equals("抹零")){
                    //odd=detail.getFloat("total_price");
                }else if(productName.equals("核销")){//处理核销
                    recharge=detail.getFloat("total_price");
                }else{//一般商品的请求,验证库存是否可以满足下单
                    boolean validate=validateInventory(account,shopId,detail.getLong("sku_id"),detail.getInteger("detail_amount"));
                    if(!validate){
                        throw new Exception("产品："+detail.getString("detail_name")+" 库存不足,无法下单。");
                    }
                    int detailAmount=detail.getInteger("detail_amount");
                    amount+=detailAmount;
                    Float detailTotalPrice=detail.getFloat("total_price");
                    totalPrice+=detailTotalPrice;
                }
            }
            List<ClientTbOrderDetail> successDetails=new ArrayList<ClientTbOrderDetail>();
            //主对象
            ClientTbOrder order=new ClientTbOrder();
            order.setAccountId(orderinfo.getLong("account"));
            order.setCusId(customId);
            odd=orderinfo.getFloat("order_odd");
            totalPrice-=odd;
            //积分换算成金额，如果存在倍率在这里处理
            Float grademoney=orderinfo.getInteger("order_grade_out")*0.1f;
            //订单总额-积分抵扣金额
            totalPrice-=grademoney;

            order.setCusName(custom.getCustomName());
            order.setCusRemain(custom.getCustomRemainMoney());
            order.setOrderAmount(amount);
            String orderCode=getOrderNo(account,shopId);
            order.setOrderCode(orderCode);
            order.setOrderGradeIn(Math.round(totalPrice));
            order.setOrderGradeOut(orderinfo.getInteger("order_grade_out"));
            order.setOrderMemo(orderinfo.getString("order_memo"));
            order.setOrderMoney(totalPrice);
            order.setOrderOdd(odd);
            order.setOrderPayment(orderinfo.getFloat("order_payment"));
            order.setOrderPaytype(orderinfo.getString("order_paytype"));
            order.setOrderRecharge(recharge);
            order.setOrderTime(Timestamp.from(Instant.now()));
            order.setShopId(shopId);

            order=daoTool.saveOne(order,account);
            if(order.getOrderId()>0){
                //单个保存
                for(int i=0;i<details.size();i++){
                    JSONObject detail=details.getJSONObject(i);
                    ClientTbOrderDetail orderDetail=new ClientTbOrderDetail();
                    int detailAmount=detail.getInteger("detail_amount");
                    Float detailTotalPrice=detail.getFloat("total_price");
                    orderDetail.setDetailAmount(detailAmount);
                    orderDetail.setDetailPrice(detail.getFloat("detail_price"));
                    orderDetail.setDetailProductName(detail.getString("detail_name"));
                    orderDetail.setDetailTotalPrice(detailTotalPrice);
                    orderDetail.setSkuId(detail.getLong("sku_id"));
                    orderDetail.setOrderId(order.getOrderCode());
                    orderDetail=daoTool.saveOne(orderDetail,account);
                    if(orderDetail.getDetailId()>0){//明细保存成功，记录成功
                        successDetails.add(orderDetail);
                    }else{
                        throw new Exception("产品明细保存失败："+detail.getString("detail_name"));
                    }
                }
                //订单保存成功后，更新下单关联的客户信息与库存信息
                if(successDetails.size()==details.size()){
                    //更新客户信息
                    custom.setCustomRemainMoney(custom.getCustomRemainMoney()-(order.getOrderMoney()-order.getOrderPayment()));
                    if(custom.getCustomGrade()==null){
                        custom.setCustomGrade(0L);
                    }
                    custom.setCustomGrade(custom.getCustomGrade()+(long)order.getOrderGradeIn());
                    custom.setCustomLatestMoney(totalPrice);
                    custom.setCustomLatestPayment(order.getOrderPayment());
                    custom.setCustomLatestTime(order.getOrderTime());
                    custom.setCustomLatestPaytype(order.getOrderPaytype());
                    custom.setCustomTotalMoney(custom.getCustomTotalMoney()+totalPrice);
                    custom=daoTool.updateOne(account,custom);
                    try{
                        //刷新库存
                        refreshInventory(account,shopId,successDetails);
                    }catch (Exception e){
                        for(ClientTbOrderDetail detail:successDetails){
                            daoTool.deleteOne(ClientTbOrderDetail.class,account,detail.getDetailId());
                        }
                        throw new Exception("更新库存失败。");
                    }

                    OrderDto orderDto=new OrderDto();
                    orderDto.setOrderTime(order.getOrderTime());
                    orderDto.setAccountId(order.getAccountId());
                    orderDto.setOrderAmount(order.getOrderAmount());
                    orderDto.setOrderOdd(order.getOrderOdd());
                    orderDto.setOrderRecharge(order.getOrderRecharge());
                    orderDto.setOrderPaytype(order.getOrderPaytype());
                    orderDto.setShopId(shopId);
                    orderDto.setOrderPayment(order.getOrderPayment());
                    orderDto.setDetails(successDetails);
                    orderDto.setCusId(order.getCusId());
                    orderDto.setCusName(order.getCusName());
                    orderDto.setOrderCode(order.getOrderCode());
                    orderDto.setOrderGradeIn(order.getOrderGradeIn());
                    orderDto.setOrderGradeOut(order.getOrderGradeOut());
                    orderDto.setOrderMemo(order.getOrderMemo());
                    orderDto.setOrderId(order.getOrderId());
                    orderDto.setOrderMoney(order.getOrderMoney());
                    orderDto.setCusRemain(custom.getCustomRemainMoney());
                    try{
                        return doPrint(account,shopId,orderDto,custom);
                    }catch(Exception e){
                        e.printStackTrace();
                        throw e;
                    }
                }else{//回滚已成功的订单明细
                    for(ClientTbOrderDetail detail:successDetails){
                        daoTool.deleteOne(ClientTbOrderDetail.class,account,detail.getDetailId());
                    }
                    daoTool.deleteOne(ClientTbOrder.class,account,order.getOrderId());

                }
            }else{//订单主表保存失败
                throw new Exception("订单信息保存失败。");
            }
        }
        return null;
    }
    @Autowired
    InventoryService inventoryService;

    private boolean refreshInventory(int account,long shopId,List<ClientTbOrderDetail> details) throws Exception{
        for(ClientTbOrderDetail detail:details){
            if(detail.getSkuId()>0){
                inventoryService.setInventory(account,shopId,detail.getSkuId(),detail.getDetailAmount(),"销售");
            }

        }
        return true;
    }

    private String getOrderNo(int account,long shopId) throws Exception {
        SimpleDateFormat format=new SimpleDateFormat("yyMMdd");
        String ordernopre=account+format.format(new Date())+shopId;
        String sql="select count(1) num from tb_order where shop_id="+shopId+" and order_code like '"+ordernopre+"%'";
        Map<String,Object> map=daoTool.getMap(sql,account);
        int no=TypeUtils.castToInt(map.get("NUM"));
        String result=ordernopre+(no+1);
        return result;
    }

    public boolean validateInventory(int account,long fromshopId,long skuid,int saleAmount) throws Exception {
        //校验当前剩余库存
        String sql="select * from tb_inventory where sku_id="+skuid+" and shop_id="+fromshopId;
        Map<String,Object> map=daoTool.getMap(sql,account);
        if(map==null){
            return false;
        }
        String status= TypeUtils.castToString(map.get("INVENTORY_COUNT_STATUS"));
        Float amount=TypeUtils.castToFloat(map.get("inventory_amount".toUpperCase()));
        if(amount==null){
            return false;
        }
        if(saleAmount>amount){
            return false;
        }
        return true;
    }

    @Override
    public boolean canDispatch(int account, long fromshopid, long toshopid, long skuid) throws Exception {
        //检查相同调入调出方是否已经存在未完成调货记录
        String sql="select * from `tb_dispatch` where sku_id="+skuid+" and dispatch_from_shop="+fromshopid+" and dispatch_to_shop="+toshopid +" and dispatch_isfinished=0 ";
        Map<String,Object> map=daoTool.getMap(sql,account);
        if(map==null){
            //检验调出方是否被其他店申请调出且未完成
            sql="select * from `tb_dispatch` where sku_id="+skuid+" and dispatch_from_shop="+fromshopid+" and dispatch_isfinished=0 ";
            map=daoTool.getMap(sql,account);
            if(map==null){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }


    @Override
    public OrderPrintDto doPrint(int account,Long orderId) throws Exception {
        ClientTbOrder order=(ClientTbOrder)daoTool.getOne(ClientTbOrder.class,account,orderId);
        if(order==null){
            throw new Exception("未找到订单编号为："+orderId+"的订单");
        }
        Long shopId=order.getShopId();
        Long customId=order.getCusId();
        ClientTbCustom custom=(ClientTbCustom)daoTool.getOne(ClientTbCustom.class,account,customId);
        ClientTbShop shop=(ClientTbShop)daoTool.getOne(ClientTbShop.class,account,shopId);
        OrderPrintDto printDto=new OrderPrintDto();
        printDto.setOrder(order);
        printDto.setShop(shop);
        printDto.setCustom(custom);
        printDto.setDetails(loadOrderDetails(account,orderId));
        return printDto;

    }

    @Override
    public Page<ClientTbOrder> getOrders(int account, Long shopId,String startdate, String enddate, String keyword,Pageable page) throws Exception {
        if(keyword==null){
            keyword="";
        }
        if(keyword.indexOf("'")>=0){
            keyword=keyword.replace("'","''");
        }
        String sql="select * from tb_order where order_time>='"+startdate+" 00:00:00' and order_time<='"+enddate+" 23:59:59' and (cus_name like '%"+keyword+"%' or order_code like '%"+keyword+"%') and  shop_id="+shopId +" order by order_time desc";
        return daoTool.getPageList(sql,page,account,ClientTbOrder.class);
    }
    @Override
    public Page<ClientTbOrder> getCustomOrders(int account,Long shopId,int custom,Pageable page) throws Exception{
        String sql="select * from tb_order where  shop_id="+shopId+" and cus_id="+custom+" order by order_time desc";
        return daoTool.getPageList(sql,page,account,ClientTbOrder.class);
    }

    @Override
    public ClientTbOrder turnbackOrder(int account, String orderno, Long skuid, int amount) throws Exception {
        String sql="select * from tb_order_detail where order_id=(select order_code from tb_order where order_id="+orderno+") and sku_id="+skuid;
        ClientTbOrderDetail detail=(ClientTbOrderDetail)daoTool.getObject(sql,account,ClientTbOrderDetail.class);
        sql="select * from tb_order where order_id='"+orderno+"'";
        ClientTbOrder order=(ClientTbOrder)daoTool.getObject(sql,account,ClientTbOrder.class);
        if(detail!=null){
            if(detail.getDetailReturnamount()==null)detail.setDetailReturnamount(0);
            Float returnmoney=amount*detail.getDetailPrice();
            //初始化明细退货金额，如果没有发生过退货，则退货金额起始为0
            if(returnmoney==null)returnmoney=0f;

            //初始化当前订单退货金额
            Float rtn=order.getOrderTurnback();
            if(rtn==null)rtn=0f;

            //验证退货数量。此处未考虑到连续退货
            if(amount>detail.getDetailRealAmount()){
                throw new Exception("退货数量大于订单有效数量！");
            }
            //更新退货总数
            detail.setDetailReturnamount(detail.getDetailReturnamount()+amount);
            detail.setDetailReturntime(Timestamp.from(new Date().toInstant()));


            detail=daoTool.updateOne(account,detail);

            if(order!=null){//更新订单主表
                order.setOrderTurnback(rtn+returnmoney);
                daoTool.updateOne(account,order);
                inventoryService.setInventory(account,order.getShopId(),skuid,amount,"退货");
                ClientTbCustom custom=(ClientTbCustom)daoTool.getOne(ClientTbCustom.class,account,order.getCusId());
                if(custom!=null){//更新客户余款
                    Float remainMoney=custom.getCustomRemainMoney();
                    if(remainMoney==null)remainMoney=0f;
                    remainMoney+=returnmoney;
                    custom.setCustomRemainMoney(remainMoney);
                    daoTool.updateOne(account,custom);
                }
            }
        }
        return order;
    }

    @Override
    public OrderPrintDto doPrint(int account, Long shopId, OrderDto order, ClientTbCustom custom) throws Exception {
        ClientTbShop shop=(ClientTbShop)daoTool.getOne(ClientTbShop.class,account,shopId);
        ClientTbOrder orderInfo=new ClientTbOrder();
        OrderPrintDto printDto=new OrderPrintDto();
        orderInfo.setCusId(custom.getCustomId());
        orderInfo.setCusName(custom.getCustomName());
        orderInfo.setCusRemain(custom.getCustomRemainMoney());
        orderInfo.setOrderAmount(order.getOrderAmount());
        orderInfo.setOrderCode(order.getOrderCode());
        orderInfo.setOrderGradeIn(order.getOrderGradeIn());
        orderInfo.setOrderGradeOut(order.getOrderGradeOut());
        orderInfo.setOrderPaytype(order.getOrderPaytype());
        orderInfo.setOrderId(order.getOrderId());
        orderInfo.setOrderMoney(order.getOrderMoney());
        orderInfo.setOrderPayment(order.getOrderPayment());
        orderInfo.setOrderOdd(order.getOrderOdd());
        orderInfo.setOrderRecharge(order.getOrderRecharge());
        orderInfo.setOrderTime(Timestamp.from(Instant.now()));
        orderInfo.setAccountId(order.getAccountId());
        printDto.setOrder(orderInfo);
        printDto.setCustom(custom);
        printDto.setDetails(loadOrderDetails(account,order.getOrderId()));
        printDto.setShop(shop);
        return printDto;
    }

    private List<OrderDetailDto> loadOrderDetails(int account, long orderId) throws Exception{
        ClientTbOrder order=(ClientTbOrder)daoTool.getOne(ClientTbOrder.class,account,orderId);
        if(order==null){
            throw new Exception("未找到订单编号为："+orderId+"的订单");
        }
        String sql="SELECT a.*,b.`color_name`,b.`size_name`,c.`product_code2`,c.`product_code`,c.product_pic FROM `tb_order_detail` a " +
                "LEFT JOIN tb_product_sku b ON a.`sku_id`=b.`id` " +
                "LEFT JOIN tb_product c ON c.`product_id`=b.`product_id` " +
                " WHERE a.`order_id`='"+order.getOrderCode()+"'";
        List<Map<String,Object>> details=daoTool.getAllList(sql,account);
        List<OrderDetailDto> result=new ArrayList<OrderDetailDto>();
        for(Map<String,Object> detail:details){
            OrderDetailDto orderDetail=new OrderDetailDto();
            orderDetail.setColorName(TypeUtils.castToString(detail.get("COLOR_NAME")));
            orderDetail.setDetailAmount(TypeUtils.castToInt(detail.get("DETAIL_AMOUNT")));
            orderDetail.setDetailId(TypeUtils.castToInt(detail.get("DETAIL_ID")));
            orderDetail.setDetailPrice(TypeUtils.castToFloat(detail.get("DETAIL_PRICE")));
            orderDetail.setDetailProductName(TypeUtils.castToString(detail.get("DETAIL_PRODUCT_NAME")));
            orderDetail.setDetailTotalPrice(TypeUtils.castToFloat(detail.get("DETAIL_TOTAL_PRICE")));
            orderDetail.setOrderId(TypeUtils.castToString(detail.get("ORDER_ID")));
            orderDetail.setProductCode(TypeUtils.castToString(detail.get("PRODUCT_CODE")));
            orderDetail.setProductCode2(TypeUtils.castToString(detail.get("PRODUCT_CODE2")));
            orderDetail.setProductPic(TypeUtils.castToString(detail.get("PRODUCT_PIC")));
            orderDetail.setSizeName(TypeUtils.castToString(detail.get("SIZE_NAME")));
            orderDetail.setSkuId(TypeUtils.castToLong(detail.get("SKU_ID")));
            orderDetail.setDetailReturnamount(TypeUtils.castToInt(detail.get("DETAIL_RETURNAMOUNT")));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.0");
            if(detail.get("DETAIL_RETURN_TIME")!=null){
                orderDetail.setDetailReturntime(TypeUtils.castToTimestamp(sdf.parse(TypeUtils.castToString(detail.get("DETAIL_RETURN_TIME"))).getTime()));
            }
            result.add(orderDetail);
        }
        return result;
    }






}
