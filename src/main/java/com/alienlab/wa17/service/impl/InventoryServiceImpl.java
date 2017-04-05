package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbDispatch;
import com.alienlab.wa17.entity.client.ClientTbInventory;
import com.alienlab.wa17.entity.client.ClientTbInventoryDetail;
import com.alienlab.wa17.entity.client.ClientTbProductInventoryStatus;
import com.alienlab.wa17.entity.client.dto.DispatchDto;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.InventoryDto;
import com.alienlab.wa17.entity.client.dto.SkuShopInventoryDto;
import com.alienlab.wa17.service.InventoryService;
import com.alienlab.wa17.service.OrderService;
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

/**
 * Created by 橘 on 2017/3/23.
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    DaoTool daoTool;

    @Override
    public List<InventoryDto> loadInventory(int account, long shopId, long productId) throws Exception {
        String sql="select a.*,lj.id inventory_id,lj.sku_id,lj.shop_id,lj.inventory_amount,lj.inventory_count_status,lj.inventory_count_time from tb_product_sku a left join tb_inventory lj on a.`id`=lj.`sku_id` and lj.`shop_id`=" +shopId+" "+
                "where a.`product_id`="+productId;
        return daoTool.getAllList(sql,account,InventoryDto.class);
    }

    @Override
    public ClientTbInventory setInventory(int account,long shopId, long skuid, int amount,String type) throws Exception {
        if("入库、出库、销售、退货、盘点加、盘点减、调入、调出".indexOf(type)<0){
            throw new Exception("type参数传递错误，参考值：入库、出库、销售、退货、盘点加、盘点减、调入、调出");
        }
        String sql="select * from tb_inventory where shop_id="+shopId+" and sku_id="+skuid;
        ClientTbInventory inventory=(ClientTbInventory)daoTool.getObject(sql,account,ClientTbInventory.class);
        if(inventory==null){
            inventory=new ClientTbInventory();
            inventory.setInventoryAmount(0);
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
                {
                    amount=amount;
                    break;
                }
                default:{
                    amount=-amount;
                }
            }
            detail.setDetailAmount(amount);
            detail.setDetailTime(new Timestamp(new Date().getTime()));
            detail.setDetailType(type);
            detail.setInventoryId(inventory.getId());
            detail=daoTool.saveOne(detail,account);
            if(detail.getDetailId()>0){
                inventory.setInventoryAmount(inventory.getInventoryAmount()+amount);
                inventory=daoTool.updateOne(account,inventory);
                return inventory;
            }else{
                throw  new Exception("保存库存明细记录失败。");
            }
        }else{
            throw new Exception("库存主记录保存失败。");
        }
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
    public ClientTbProductInventoryStatus setProductInventoryStatus(int account, long productid, long shopid, String status) throws Exception {
        if("异常，正常".indexOf(status)<0){
            throw new Exception("状态值传入错误。请传入[正常、异常]");
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
        //查询调货提供方的库存量是否满足调货要求
        boolean flag=orderService.validateInventory(account,fromShopId,skuId,amount);
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
    public Page<DispatchDto> getDispatch(int account, long shopId,int index,int size) throws Exception {
        String sql="SELECT a.*,b.`color_name`,b.`product_id`,b.`size_name`,c.`product_code2`,c.`product_code`,c.`product_name`,c.`product_pic` FROM tb_dispatch a,tb_product_sku b,tb_product c " +
                " WHERE a.`sku_id`=b.`id` AND b.`product_id`=c.`product_id` AND (a.`dispatch_to_shop`='1' OR a.`dispatch_from_shop`='1')";
        Page<DispatchDto> result=daoTool.getPageList(sql,new PageRequest(index,size),account,DispatchDto.class);
        return result;
    }

    @Override
    public List<SkuShopInventoryDto> getSkuShopList(int account, long skuId) throws Exception {
        String sql="SELECT a.*,c.color_name,c.size_name,b.shop_id,b.shop_name,b.shop_isdefault FROM tb_inventory a,tb_shop b,tb_product_sku c " +
                "WHERE a.`sku_id`=c.id AND c.id="+skuId+" AND a.shop_id=b.shop_id";
        List<SkuShopInventoryDto> result=daoTool.getAllList(sql,account,SkuShopInventoryDto.class);
        return result;
    }


}
