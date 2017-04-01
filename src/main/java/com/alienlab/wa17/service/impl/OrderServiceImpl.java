package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.entity.client.ClientTbOrder;
import com.alienlab.wa17.entity.client.ClientTbOrderDetail;
import com.alienlab.wa17.entity.client.dto.OrderDto;
import com.alienlab.wa17.service.OrderService;
import javassist.bytecode.ExceptionsAttribute;
import javassist.bytecode.analysis.Type;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    public OrderDto addOrder(int account,long shopId,JSONObject orderinfo) throws Exception {
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
                    odd=detail.getFloat("total_price");
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
            order.setAccountId((long)account);
            order.setCusId(customId);
            order.setCusName(custom.getCustomName());
            order.setCusRemain(custom.getCustomRemainMoney());
            order.setOrderAmount(amount);
            String orderCode=getOrderNo(account,shopId);
            order.setOrderCode(orderCode);
            order.setOrderGradeIn(Math.round(totalPrice));
            order.setOrderGradeOut(orderinfo.getInteger("order_grade_out"));
            //积分换算成金额，如果存在倍率在这里处理
            Float grademoney=orderinfo.getInteger("order_grade_out")*0.1f;
            order.setOrderMemo(orderinfo.getString("order_memo"));
            //订单总额-积分抵扣金额
            totalPrice-=grademoney;
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
                    custom.setCustomGrade(custom.getCustomGrade()+(long)order.getOrderGradeIn());
                    custom.setCustomLatestMoney(totalPrice);
                    custom.setCustomLatestPayment(order.getOrderPayment());
                    custom.setCustomLatestTime(order.getOrderTime());
                    custom.setCustomLatestPaytype(order.getOrderPaytype());
                    custom.setCustomTotalMoney(custom.getCustomTotalMoney()+totalPrice);
                    custom=daoTool.updateOne(account,custom);
                    //刷新库存
                    refreshInventory(successDetails);

                    OrderDto orderDto=new OrderDto();
                    orderDto.setOrderTime(order.getOrderTime());
                    orderDto.setOrderRecharge(order.getOrderRecharge());
                    orderDto.setOrderPaytype(order.getOrderPaytype());
                    orderDto.setOrderPayment(order.getOrderPayment());
                    orderDto.setDetails(successDetails);
                    orderDto.setAccountId((long)account);
                    orderDto.setCusId(order.getCusId());
                    orderDto.setCusName(order.getCusName());
                    orderDto.setOrderCode(order.getOrderCode());
                    orderDto.setOrderGradeIn(order.getOrderGradeIn());
                    orderDto.setOrderGradeOut(order.getOrderGradeOut());
                    orderDto.setOrderMemo(order.getOrderMemo());
                    orderDto.setOrderId(order.getOrderId());
                    orderDto.setOrderMoney(order.getOrderMoney());
                    orderDto.setCusRemain(custom.getCustomRemainMoney());

                    return orderDto;
                }else{//回滚已成功的订单明细

                }
            }else{//订单主表保存失败
                throw new Exception("订单信息保存失败。");
            }
        }
        return null;
    }

    private boolean refreshInventory(List<ClientTbOrderDetail> details){
        return false;
    }

    private String getOrderNo(int account,long shopId) throws Exception {
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        String ordernopre=account+"-"+format.format(new Date())+"-"+shopId;
        String sql="select count(1) num from tb_order where shop_id="+shopId+" and order_code like '"+ordernopre+"%'";
        Map<String,Object> map=daoTool.getMap(sql,account);
        int no=TypeUtils.castToInt(map.get("NUM"));
        String result=ordernopre+"-"+(no+1);
        return result;
    }

    private boolean validateInventory(int account,long shopId,long skuid,int saleAmount) throws Exception {
        String sql="select * from tb_inventory where sku_id="+skuid+" and shop_id="+shopId;
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
}
