package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.*;
import com.alienlab.wa17.entity.client.dto.DispatchDto;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.InventoryDto;
import com.alienlab.wa17.entity.client.dto.SkuShopInventoryDto;
import com.alienlab.wa17.service.InventoryService;
import com.alienlab.wa17.service.OrderService;
import com.alienlab.wa17.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 橘 on 2017/3/23.
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    DaoTool daoTool;
    @Autowired
    ProductService productService;

    public Page<InventoryDto> loadInventory(int account,long shopId){

        return null;
    }

    @Override
    public List<InventoryDto> loadInventory(int account, long shopId, long productId) throws Exception {
        String sql="SELECT " +
                "  a.*, " +
                "  lj.id inventory_id, " +
                "  lj.sku_id, " +
                "  lj.shop_id, " +
                "  lj.inventory_amount, " +
                "  lj1.amount temp_amount, " +
                "  lj.inventory_count_status, " +
                "  lj.inventory_count_time, " +
                "  lj2.`product_code`, " +
                "  lj2.`product_code2`, " +
                "  lj2.`product_name`, " +
                "  lj2.`product_price1`, " +
                "  lj2.`product_price2` " +
                "FROM " +
                "  tb_product_sku a " +
                "  LEFT JOIN tb_inventory lj " +
                "    ON a.`id` = lj.`sku_id` " +
                "    AND lj.`shop_id` = "+shopId+" " +
                "  LEFT JOIN (select sku_id,shop_id,sum(amount) amount from `tb_inventory_temp` group by sku_id,shop_id) lj1 " +
                "    ON a.`id` = lj1.`sku_id` " +
                "    AND lj1.`shop_id` = "+shopId+" " +
                "  LEFT JOIN tb_product lj2 ON lj2.`product_id`=a.`product_id` " +
                "WHERE a.`product_id` = "+productId;
        return daoTool.getAllList(sql,account,InventoryDto.class);
    }

    @Override
    public ClientTbInventory setInventory(int account,long shopId, long skuid, int amount,String type) throws Exception {
        if("入库、出库、销售、退货、盘点加、盘点减、调入、调出、重置、初始".indexOf(type)<0){
            throw new Exception("type参数传递错误，参考值：入库、出库、销售、退货、盘点加、盘点减、调入、调出、重置、初始");
        }
        String sql="select * from tb_inventory where shop_id="+shopId+" and sku_id="+skuid;
        ClientTbInventory inventory=(ClientTbInventory)daoTool.getObject(sql,account,ClientTbInventory.class);
        if(inventory==null){
            inventory=new ClientTbInventory();
            inventory.setInventoryAmount(0);
            if(type.equalsIgnoreCase("重置")||type.equalsIgnoreCase("初始")){
                inventory.setInventoryAmount(amount);
            }
            inventory.setInventoryCountTime(new Timestamp(new Date().getTime()));
            inventory.setInventoryCountStatus("正常");
            inventory.setShopId(shopId);
            inventory.setSkuId(skuid);
            inventory=daoTool.saveOne(inventory,account);
        }
        if(inventory!=null&&inventory.getId()>0){
            ClientTbInventoryDetail detail=new ClientTbInventoryDetail();
            switch(type){
                case "入库":
                case "退货":
                case "调入":
                case "盘点加":
                case "重置":
                case "初始":
                {
                    amount=amount;
                    break;
                }
                default:{
                    amount=-amount;
                }
            }
            //if(type.equalsIgnoreCase("重置")||type.equalsIgnoreCase("初始")&&amount!=inventory.getInventoryAmount()){
                detail.setDetailAmount(amount);
                detail.setDetailTime(new Timestamp(new Date().getTime()));
                detail.setDetailType(type);
                detail.setInventoryId(inventory.getId());
                detail=daoTool.saveOne(detail,account);
                if(detail.getDetailId()>0){
                    if(type.equalsIgnoreCase("重置")||type.equalsIgnoreCase("初始")){
                        inventory.setInventoryAmount(amount);
                    }else{
                        inventory.setInventoryAmount(inventory.getInventoryAmount()+amount);
                    }
                    inventory=daoTool.updateOne(account,inventory);
                    return inventory;
                }else{
                    throw  new Exception("保存库存明细记录失败。");
                }
//            }
//            else{
//                return inventory;
//            }
        }else{
            throw new Exception("库存主记录保存失败。");
        }
    }


    public Page<InventoryDetailDto> loadDetails(int account,long shopId,String status,String isall,String startDate, String endDate, Pageable page,String keyword) throws Exception{
        String sql="SELECT " +
                "  a.*, " +
                "  b.sku_id, " +
                "  b.shop_id, " +
                "  b.`inventory_amount`, " +
                "  b.`inventory_count_status`, " +
                "  b.`inventory_count_time`, " +
                "  c.`color_name`, " +
                "  c.`size_name`, " +
                "  d.product_id, " +
                "  d.`product_code`, " +
                "  d.`product_code2`, " +
                "  d.`product_name`, " +
                "  d.`product_price1`, " +
                "  d.`product_price2` " +
                "FROM " +
                "  `tb_inventory_detail` a, " +
                "  `tb_inventory` b, " +
                "  `tb_product_sku` c, " +
                "  tb_product d " +
                "WHERE a.`inventory_id` = b.`id` " +
                "  AND b.`sku_id` = c.`id` " +
                "  AND b.`shop_id` = "+shopId+" " +
                "  AND (1="+isall+" OR a.detail_type = '"+status+"') " +
                "  AND d.`product_id`=c.`product_id` " +
                "  AND a.`detail_time` >= '"+startDate+" 00:00:00' " +
                "  AND a.`detail_time` <= '"+endDate+" 23:59:59' ";
        sql+=(keyword==null?"":" AND ( d.product_code like '%"+keyword+"%' or d.product_code2 like '%"+keyword+"%' or d.product_name like '%"+keyword+"%')");
        return daoTool.getPageList(sql,page,account,InventoryDetailDto.class);
    }

    @Override
    public Page<InventoryDetailDto> loadDetails(int account, long skuId, String startDate, String endDate, Pageable page) throws Exception {
        String sql="SELECT a.*,b.sku_id,b.shop_id,b.`inventory_amount`,b.`inventory_count_status`,b.`inventory_count_time`,c.`color_name`,c.`size_name` " +
                "FROM `tb_inventory_detail` a,`tb_inventory` b,`tb_product_sku` c " +
                "WHERE a.`inventory_id`=b.`id` AND b.`sku_id`=c.`id` " +
                "AND b.`sku_id`=" +skuId+
                " AND a.`detail_time`>='"+startDate+"' AND a.`detail_time`<='"+endDate+"'";
        return daoTool.getPageList(sql,page,account,InventoryDetailDto.class);
    }

    @Override
    public Page<InventoryDetailDto> loadDetailsByStatus(int account, long skuId, String startDate, String endDate, String status, Pageable page) throws Exception {
        String sql="SELECT a.*,b.sku_id,b.shop_id,b.`inventory_amount`,b.`inventory_count_status`,b.`inventory_count_time`,c.`color_name`,c.`size_name` " +
                "FROM `tb_inventory_detail` a,`tb_inventory` b,`tb_product_sku` c " +
                "WHERE a.`inventory_id`=b.`id` AND b.`sku_id`=c.`id` " +
                "AND b.`sku_id`=" +skuId+" and a.detail_type='"+status+"'"+
                " AND a.`detail_time`>='"+startDate+"' AND a.`detail_time`<='"+endDate+"'";
        return daoTool.getPageList(sql,page,account,InventoryDetailDto.class);
    }

    @Override
    public Page<InventoryDetailDto> loadDetailsByProduct(int account, long productId, long shopId, String startDate, String endDate, Pageable page) throws Exception {
        String sql="SELECT a.*,b.sku_id,b.shop_id,b.`inventory_amount`,b.`inventory_count_status`,b.`inventory_count_time`,c.`color_name`,c.`size_name` " +
                "FROM `tb_inventory_detail` a,`tb_inventory` b,`tb_product_sku` c " +
                "WHERE a.`inventory_id`=b.`id` AND b.`sku_id`=c.`id` " +
                "AND b.`shop_id`=" +shopId+" AND c.product_id="+productId+
                " AND a.`detail_time`>='"+startDate+"' AND a.`detail_time`<='"+endDate+"'";
        return daoTool.getPageList(sql,page,account,InventoryDetailDto.class);
    }

    @Override
    public Page<InventoryDetailDto> loadDetailsByProductAndStatus(int account, long productId, long shopId, String startDate, String endDate, String status, Pageable page) throws Exception {
        String sql="SELECT a.*,b.sku_id,b.shop_id,b.`inventory_amount`,b.`inventory_count_status`,b.`inventory_count_time`,c.`color_name`,c.`size_name` " +
                "FROM `tb_inventory_detail` a,`tb_inventory` b,`tb_product_sku` c " +
                "WHERE a.`inventory_id`=b.`id` AND b.`sku_id`=c.`id` " +
                "AND b.`shop_id`=" +shopId+" and a.detail_type='"+status+"' AND c.product_id="+productId+
                " AND a.`detail_time`>='"+startDate+"' AND a.`detail_time`<='"+endDate+"'";
        return daoTool.getPageList(sql,page,account,InventoryDetailDto.class);
    }

    @Override
    public JSONArray getInventoryStat(int account, long skuId, String startDate, String endDate) throws Exception {
        String sql="SELECT detail_type,sum(detail_amount) detail_amount " +
                "FROM `tb_inventory_detail` a,`tb_inventory` b,`tb_product_sku` c " +
                "WHERE a.`inventory_id`=b.`id` AND b.`sku_id`=c.`id` " +
                "AND b.`sku_id`=" +skuId+
                " AND a.`detail_time`>='"+startDate+"' AND a.`detail_time`<='"+endDate+"' group by detail_type";
        return JSONArray.parseArray(JSON.toJSONString(daoTool.getAllList(sql,account)));
    }

    @Override
    public JSONArray getInventoryStatByProd(int account, long productId, long shopId,String startDate, String endDate) throws Exception {
        String sql="SELECT detail_type,sum(detail_amount) detail_amount " +
                "FROM `tb_inventory_detail` a,`tb_inventory` b,`tb_product_sku` c " +
                "WHERE a.`inventory_id`=b.`id` AND b.`sku_id`=c.`id` " +
                "AND b.`shop_id`=" +shopId+" AND c.product_id="+productId+
                " AND a.`detail_time`>='"+startDate+"' AND a.`detail_time`<='"+endDate+"' group by detail_type";
        return JSONArray.parseArray(JSON.toJSONString(daoTool.getAllList(sql,account)));
    }

    @Override
    public JSONArray getInventoryStatByStatus(int account, long skuId,String startDate, String endDate,String status) throws Exception {
        String sql="SELECT detail_type,sum(detail_amount) detail_amount " +
                "FROM `tb_inventory_detail` a,`tb_inventory` b,`tb_product_sku` c " +
                "WHERE a.`inventory_id`=b.`id` AND b.`sku_id`=c.`id` " +
                "AND b.`sku_id`=" +skuId+" and a.detail_type='"+status+"'"+
                " AND a.`detail_time`>='"+startDate+"' AND a.`detail_time`<='"+endDate+"' group by detail_type";
        return JSONArray.parseArray(JSON.toJSONString(daoTool.getAllList(sql,account)));
    }

    @Override
    public JSONArray getInventoryStatByStatusAndProd(int account, long productId, long shopId, String startDate, String endDate,String status) throws Exception {
        String sql="detail_type,sum(detail_amount) detail_amount " +
                "FROM `tb_inventory_detail` a,`tb_inventory` b,`tb_product_sku` c " +
                "WHERE a.`inventory_id`=b.`id` AND b.`sku_id`=c.`id` " +
                "AND b.`shop_id`=" +shopId+" and a.detail_type='"+status+"' AND c.product_id="+productId+
                " AND a.`detail_time`>='"+startDate+"' AND a.`detail_time`<='"+endDate+"' group by detail_type";
        return JSONArray.parseArray(JSON.toJSONString(daoTool.getAllList(sql,account)));
    }

    @Override
    public JSONArray getInventoryStat(int account, long shopId, String status, String isall, String startDate, String endDate,String keyword) throws Exception {
        String sql="SELECT " +
                "  detail_type,SUM(detail_amount) detail_amount " +
                "FROM " +
                "  `tb_inventory_detail` a, " +
                "  `tb_inventory` b, " +
                "  `tb_product_sku` c, " +
                "  tb_product d " +
                "WHERE a.`inventory_id` = b.`id` " +
                "  AND b.`sku_id` = c.`id` " +
                "  AND b.`shop_id` = "+shopId+" " +
                "  AND (1="+isall+" OR a.detail_type = '"+status+"') " +
                "  AND d.`product_id`=c.`product_id` " +
                "  AND a.`detail_time` >= '"+startDate+"' " +
                "  AND a.`detail_time` <= '"+endDate+"' " +
                (keyword==null?"":" AND ( d.product_code like '%"+keyword+"%' or d.product_code2 like '%"+keyword+"%' or d.product_name like '%"+keyword+"%')")+
                "   GROUP BY detail_type ";
        return JSONArray.parseArray(JSON.toJSONString(daoTool.getAllList(sql,account)));
    }

    @Override
    public ClientTbProductInventoryStatus setProductInventoryStatus(int account, long productid, long shopid, String status) throws Exception {
        if("异常，正常，未盘点".indexOf(status)<0){
            throw new Exception("状态值传入错误。请传入[正常、异常、未盘点]");
        }
        String sql="select * from tb_product_inventory_status where product_id="+productid+" and shop_id="+shopid;
        ClientTbProductInventoryStatus inventoryStatus=(ClientTbProductInventoryStatus)daoTool.getObject(sql,account,ClientTbProductInventoryStatus.class);
        if(inventoryStatus==null){
            inventoryStatus=new ClientTbProductInventoryStatus();
        }
        inventoryStatus.setShopId(shopid);
        inventoryStatus.setProductId(productid);
        inventoryStatus.setStatus(status);
        inventoryStatus=daoTool.saveOne(inventoryStatus,account);
        return inventoryStatus;
    }

    @Autowired
    OrderService orderService;
    @Override
    public ClientTbDispatch addDispatch(int account, long fromShopId, long toShopId, long skuId, int amount) throws Exception {
        //检验调货操作是否可以进行
        if(amount==0){
            throw new Exception("申请调出数量不可以为0。");
        }
        boolean flag=orderService.canDispatch(account,fromShopId,toShopId,skuId);
        if(!flag){
            throw new Exception("调货不成功！调出方有未完成的请求。");
        }
        //查询调货提供方的库存量是否满足调货要求
        flag=orderService.validateInventory(account,fromShopId,skuId,amount);
        if(!flag){
            throw new Exception("调货方库存不足");
        }
        ClientTbDispatch dispatch=new ClientTbDispatch();
        dispatch.setSkuId(skuId);
        dispatch.setDispatchAmount(amount);
        dispatch.setDispatchFromShop(fromShopId);
        dispatch.setDispatchToShop(toShopId);
        dispatch.setDispatchTime1(Timestamp.from(Instant.now()));
        dispatch.setDispatchIsfinished("0");
        dispatch=daoTool.saveOne(dispatch,account);
        if(dispatch.getDispatchId()>0){
            return dispatch;
        }else{
            throw new Exception("调货请求保存失败");
        }

    }

    @Override
    public boolean delDispatch(int account, long dispatchId) throws Exception {
        ClientTbDispatch dispatch=(ClientTbDispatch)daoTool.getOne(ClientTbDispatch.class,account,dispatchId);
        if(dispatch==null){
            throw new Exception("未找到编码为"+dispatchId+"的调度记录");
        }
        if(dispatch.getDispatchFromIsok().equals("1")||dispatch.getDispatchToIsok().equals("1")||dispatch.getDispatchIsfinished().equals("1")){
            throw new Exception("此调度记录已经进入调拨阶段，不可以删除");
        }

        return daoTool.deleteOne(ClientTbDispatch.class,account,dispatchId);
    }

    @Override
    public ClientTbDispatch confirmDispatch(int account, long dispatchId, long shopId) throws Exception {
        ClientTbDispatch dispatch=(ClientTbDispatch)daoTool.getOne(ClientTbDispatch.class,account,dispatchId);
        if(dispatch==null){
            throw new Exception("未找到编码为"+dispatchId+"的调度记录");
        }
        if(dispatch.getDispatchFromShop()==shopId){//如果是调出方
            if(dispatch.getDispatchFromIsok().equals("1")){
                throw new Exception("该调货申请已经确认调出。");
            }
        }else if(dispatch.getDispatchToShop()==shopId){
            if(dispatch.getDispatchToIsok().equals("1")){
                throw new Exception("该调货申请已经确认调入。");
            }
            if(!dispatch.getDispatchFromIsok().equals("1")){
                throw new Exception("调出方尚未确认调出，不能进行调入确认。");
            }
        }
        long fromShopId=dispatch.getDispatchFromShop();
        long toShopId=dispatch.getDispatchToShop();
        if(shopId==fromShopId){
            dispatch.setDispatchFromIsok("1");
            dispatch.setDispatchFromOktime(Timestamp.from(Instant.now()));
        }else{
            dispatch.setDispatchToIsok("1");
            dispatch.setDispatchToOktime(Timestamp.from(Instant.now()));
        }
        if(dispatch.getDispatchFromIsok().equals("1")&&dispatch.getDispatchToIsok().equals("1")){
            dispatch.setDispatchIsfinished("1");
        }
        dispatch=daoTool.updateOne(account,dispatch);
        //库存操作
        if(shopId==fromShopId){
            setInventory(account,shopId,dispatch.getSkuId(),dispatch.getDispatchAmount(),"调出");
        }else{
            setInventory(account,shopId,dispatch.getSkuId(),dispatch.getDispatchAmount(),"调入");
        }
        return dispatch;
    }

    @Override
    public Page<DispatchDto> getDispatch(int account, long shopId,String keyword,int index,int size) throws Exception {
        if(keyword==null){
            keyword="";
        }
        if(keyword.indexOf("'")>=0){
            keyword=keyword.replace("'","''");
        }
        String sql="SELECT tb1.*,ljfrom.shop_name dispatch_from_shopname,ljto.shop_name dispatch_to_shopname FROM ( " +
                "SELECT a.*,b.`color_name`,b.`product_id`,b.`size_name`,c.`product_code2`,c.`product_code`,c.`product_name`,c.`product_pic` FROM tb_dispatch a,tb_product_sku b,tb_product c " +
                "WHERE a.`sku_id`=b.`id` AND b.`product_id`=c.`product_id` AND (c.product_code like '%"+keyword+"%' or c.product_code2 like '%"+keyword+"%' or c.product_name like '%"+keyword+"%') AND (a.`dispatch_to_shop`="+shopId+" OR a.`dispatch_from_shop`="+shopId+") " +
                ") tb1  " +
                "LEFT JOIN tb_shop ljfrom ON ljfrom.`shop_id`=tb1.dispatch_from_shop " +
                "LEFT JOIN tb_shop ljto ON ljto.`shop_id`=tb1.dispatch_to_shop " +
                " order by tb1.dispatch_time1 desc";
        Page<DispatchDto> result=daoTool.getPageList(sql,new PageRequest(index,size),account,DispatchDto.class);
        return result;
    }

    @Override
    public List<SkuShopInventoryDto> getSkuShopList(int account, long skuId) throws Exception {
        String sql="select tb.*,ifnull(lj.dispatch_id,0) dispatch_status from ( " +
                "SELECT " +
                "a.*, c.color_name, " +
                "c.size_name, " +
                "b.shop_name, " +
                "b.shop_isdefault " +
                "FROM " +
                "tb_inventory a, " +
                "tb_shop b, " +
                "tb_product_sku c " +
                "WHERE " +
                "a.`sku_id` = c.id " +
                "AND c.id = "+skuId+" " +
                "AND a.shop_id = b.shop_id " +
                "AND a.inventory_amount > 0 " +
                ") tb " +
                "left join tb_dispatch lj on lj.sku_id=tb.sku_id and lj.dispatch_from_shop=tb.shop_id and lj.dispatch_isfinished=0 ";
        List<SkuShopInventoryDto> result=daoTool.getAllList(sql,account,SkuShopInventoryDto.class);
        for (SkuShopInventoryDto skuShopInventoryDto : result) {
            if(skuShopInventoryDto.getDispatchStatus()>0){
                skuShopInventoryDto.setDispatchStatus(1);
            }
        }
        return result;
    }

    @Override
    public void checkInventoryStatus(int account, long shopid, long skuid, int amount) throws Exception {
        String sql="SELECT inventory_amount amount FROM `tb_inventory` WHERE sku_id="+skuid+" AND shop_id="+shopid;
        Map amountMap=daoTool.getMap(sql,account);
        if(amountMap==null){//当前sku没有库存;
            sql="insert into tb_inventory(sku_id,shop_id,inventory_amount,inventory_count_status) values("+skuid+","+shopid+","+amount+",'正常')";
            daoTool.exec(sql,account);
        }else{
            String status="";
            Object tmp=amountMap.get("AMOUNT");
            int inv_amount=TypeUtils.castToInt(tmp);
            if(amount==inv_amount){//库存正常
                status="正常";
            }else{//库存异常
                status="异常";
            }
            sql="update tb_inventory set inventory_count_status='"+status+"' WHERE sku_id="+skuid+" AND shop_id="+shopid;
            daoTool.exec(sql,account);
        }

    }

    @Override
    public boolean resetShopInventoryStatus(int account, long shopid) throws Exception {
        String sql="delete from tb_product_inventory_status where shop_id="+shopid;
        daoTool.exec(sql,account);
        sql="update tb_inventory set inventory_count_status='正常' where shop_id="+shopid;
        daoTool.exec(sql,account);
        sql="delete from tb_inventory_temp where shop_id="+shopid;
        return daoTool.exec(sql,account);
    }

//    @Override
//    public List<ClientTbProduct> checkShopInventory(int account, long shopid, JSONArray details) throws Exception {
//        resetShopInventoryStatus(account,shopid);
//        for(int j=0;j<details.size();j++){
//            JSONObject item=details.getJSONObject(j);
//            JSONArray array=item.getJSONArray("skus");
//            for(int i=0;i<array.size();i++){
//                JSONObject jo=array.getJSONObject(i);
//                long skuId=jo.getLong("skuId");
//                int amount=jo.getInteger("amount");
//                //插入临时库存表，用于记录盘点数字
//                saveTempInventory(account,shopid,skuId,amount);
//            }
//        }
//        return getCheckResult(account,shopid);
//    }

    /**
     * 手动盘点单个产品提交
     * @param account
     * @param shopid
     * @param details
     * @return
     * @throws Exception
     */
    @Override
    public boolean checkSingleShopInventory(int account, long shopid, JSONArray details) throws Exception {
        try{
            for(int j=0;j<details.size();j++){
                JSONObject item=details.getJSONObject(j);
                JSONArray array=item.getJSONArray("skus");
                for(int i=0;i<array.size();i++){
                    JSONObject jo=array.getJSONObject(i);
                    long skuId=jo.getLong("skuId");
                    int amount=jo.getInteger("amount");
                    //插入临时库存表，用于记录盘点数字
                    saveTempInventory(account,shopid,skuId,amount);
                }
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ClientTbInventoryTemp saveTempInventory(int account, long shopid, long skuid, int amount) throws Exception {
        String sql="delete from tb_inventory_temp where shopid="+shopid+" and skuid="+skuid;
        boolean delflag=daoTool.exec(sql,account);
        ClientTbInventoryTemp temp=new ClientTbInventoryTemp();
        temp.setInventoryAmount(amount);
        temp.setInventoryCountTime(Timestamp.from(new Date().toInstant()));
        temp.setShopId(shopid);
        temp.setSkuId(skuid);
        temp=daoTool.saveOne(temp,account);
        return temp;
    }

    @Override
    public ClientTbInventoryTemp addTempInventoryAmount(int account, long shopid, long skuid, int amount) throws Exception {
        String existssql="select * from tb_inventory_temp where shop_id="+shopid+" and sku_id="+skuid;
        ClientTbInventoryTemp temp=(ClientTbInventoryTemp)daoTool.getObject(existssql,account,ClientTbInventoryTemp.class);
        if(temp==null){
            temp=new ClientTbInventoryTemp();
            temp.setSkuId(skuid);
            temp.setShopId(shopid);
            temp.setInventoryAmount(amount);
            temp.setInventoryCountTime(Timestamp.from(new Date().toInstant()));
            return daoTool.saveOne(temp,account);
        }else{
            int n=temp.getInventoryAmount();
            n+=amount;
            temp.setInventoryAmount(n);
            temp.setInventoryCountTime(Timestamp.from(new Date().toInstant()));
            return daoTool.updateOne(account,temp);
        }
    }


    /**
     * 进行盘点，将临时库存表中数据与库存表里库存比对
     * @param account
     * @param shopid
     * @return
     * @throws Exception
     */
    @Override
    public List<ClientTbProduct> getCheckResult(int account, long shopid) throws Exception {
        String basesql="select * from tb_inventory_temp where shop_id="+shopid;
        List<ClientTbInventoryTemp> tempList=daoTool.getAllList(basesql,account,ClientTbInventoryTemp.class);
        if(tempList!=null){
            for(ClientTbInventoryTemp temp:tempList){
                checkInventoryStatus(account,temp.getShopId(),temp.getSkuId(),temp.getInventoryAmount());
            }
        }
        String sql="delete from tb_product_inventory_status where shop_id="+shopid;
        daoTool.exec(sql,account);

        sql="insert into tb_product_inventory_status(product_id,shop_id,status) SELECT DISTINCT b.product_id,a.shop_id,inventory_count_status FROM `tb_inventory` a,tb_product_sku b " +
                "WHERE a.shop_id="+shopid+" AND a.`sku_id`=b.`id` AND a.`inventory_count_status`='异常' ";
        daoTool.exec(sql,account);

        sql="insert into tb_product_inventory_status(product_id,shop_id,status) SELECT DISTINCT b.product_id,a.shop_id,inventory_count_status FROM `tb_inventory` a,tb_product_sku b " +
                "WHERE a.shop_id="+shopid+" AND a.`sku_id`=b.`id` AND a.`inventory_count_status`='正常' and not exists(select 1 from tb_product_inventory_status where tb_product_inventory_status.product_id=b.product_id)";
        daoTool.exec(sql,account);
        return productService.getAllProducts(account,shopid,new PageRequest(0,99)).getContent();
    }

    /**
     * 20170805新增，库存清点完成后，核对单个产品库存，覆盖原有库存
     * @param account
     * @param shopid
     * @param details
     * @return
     */
    @Override
    public boolean confirmSingleProduct(int account, long shopid, JSONArray details){
        try{
            for(int j=0;j<details.size();j++){
                JSONObject item=details.getJSONObject(j);
                JSONArray array=item.getJSONArray("skus");
                for(int i=0;i<array.size();i++){
                    JSONObject jo=array.getJSONObject(i);
                    long skuId=jo.getLong("skuId");
                    int amount=jo.getInteger("amount");
                    //进行库存对比
                    setInventory(account,shopid,skuId,amount,"重置");
                }
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<InventoryDto> getInventoryByProduct(int account, long shopid, long productid) throws Exception {
        String sql="select b.*,c.`product_code2`,c.`product_code`,c.`product_name`,c.`product_id`,c.`product_price1`,c.`product_price2`,c.`product_pic`, " +
                "a.`inventory_amount`,a.`inventory_count_status`,a.`inventory_count_time`,a.`shop_id`,d.`amount` temp_amount " +
                "from `tb_product_sku` b " +
                "left join `tb_inventory` a on a.`sku_id`=b.`id` and a.`shop_id`="+shopid+" " +
                "left join `tb_product` c on c.`product_id`=b.`product_id` and c.`product_id`="+productid+" " +
                "left join (select sku_id,shop_id,sum(amount) amount from `tb_inventory_temp` group by sku_id,shop_id) d on d.`sku_id`=b.id and d.`shop_id`="+shopid+" " +
                "where b.`product_id`="+productid;
        List<InventoryDto> result=daoTool.getAllList(sql,account,InventoryDto.class);
        return result;
    }

    /**
     * 一键核对库存
     * @param account
     * @param shopid
     * @return
     * @throws Exception
     */
    @Override
    public boolean confirmAllProduct(int account,long shopid) throws Exception {
        String sql="select * from tb_inventory_temp where shop_id="+shopid;
        List<ClientTbInventoryTemp> tempinvs=daoTool.getAllList(sql,account,ClientTbInventoryTemp.class);
        for (ClientTbInventoryTemp tempinv : tempinvs) {
            //进行库存对比
            setInventory(account,shopid,tempinv.getSkuId(),tempinv.getInventoryAmount(),"重置");
        }
        return true;
    }






}
