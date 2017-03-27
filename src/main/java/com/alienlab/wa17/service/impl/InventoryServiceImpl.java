package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbInventory;
import com.alienlab.wa17.entity.client.ClientTbInventoryDetail;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.InventoryDto;
import com.alienlab.wa17.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    public Page<InventoryDetailDto> loadDetails(int account, long inventoryId, String startDate, String endDate, Pageable page) throws Exception {
        String sql="SELECT a.*,b.sku_id,b.shop_id,b.`inventory_amount`,b.`inventory_count_status`,b.`inventory_count_time`,c.`color_name`,c.`size_name` " +
                "FROM `tb_inventory_detail` a,`tb_inventory` b,`tb_product_sku` c " +
                "WHERE a.`inventory_id`=b.`id` AND b.`sku_id`=c.`id` " +
                "AND b.`id`=" +inventoryId+
                " AND a.`detail_time`>='"+startDate+"' AND a.`detail_time`<='"+endDate+"'";
        return daoTool.getPageList(sql,page,account,InventoryDetailDto.class);
    }

    @Override
    public Page<InventoryDetailDto> loadDetailsByStatus(int account, long inventoryId, String startDate, String endDate, String status, Pageable page) throws Exception {
        String sql="SELECT a.*,b.sku_id,b.shop_id,b.`inventory_amount`,b.`inventory_count_status`,b.`inventory_count_time`,c.`color_name`,c.`size_name` " +
                "FROM `tb_inventory_detail` a,`tb_inventory` b,`tb_product_sku` c " +
                "WHERE a.`inventory_id`=b.`id` AND b.`sku_id`=c.`id` " +
                "AND b.`id`=" +inventoryId+" and a.detail_type='"+status+"'"+
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


}
