package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbInventory;
import com.alienlab.wa17.entity.client.ClientTbInventoryDetail;
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
        String sql="select * from tb_product_sku a left join tb_inventory lj on a.`id`=lj.`sku_id` and lj.`shop_id`=" +shopId+" "+
                "where a.`product_id`="+productId;
        return daoTool.getAllList(sql,account,InventoryDto.class);
    }

    @Override
    public ClientTbInventory addInventory(int account, long shopId, long productId, int amount, String type) throws Exception {
        return null;
    }

    @Override
    public ClientTbInventory updateInventory(int account, long inventoryId, int amount) throws Exception {
        ClientTbInventory inventory=(ClientTbInventory)daoTool.getOne(ClientTbInventory.class,account,inventoryId);
        if(inventory==null){//不存在的库存需新增
            throw new Exception("未找到库存信息，编码为"+inventoryId);
        }else{
            inventory.setInventoryAmount(amount);
            inventory.setInventoryCountTime(new Timestamp(new Date().getTime()));
            inventory=daoTool.updateOne(account,inventory);
        }

        return inventory;
    }

    @Override
    public Page<ClientTbInventoryDetail> loadDetails(int account, long inventoryId, Pageable page) throws Exception {
        return null;
    }

    @Override
    public Page<ClientTbInventoryDetail> loadDetailsByStatus(int account, long inventoryId, String status, Pageable page) throws Exception {
        return null;
    }


    @Override
    public ClientTbInventoryDetail addInventoryLog(int account, long inventoryId, int amount, String type) throws Exception {

        return null;
    }
}
