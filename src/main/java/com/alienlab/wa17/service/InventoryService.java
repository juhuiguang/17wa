package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbInventory;
import com.alienlab.wa17.entity.client.ClientTbInventoryDetail;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.InventoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by 橘 on 2017/3/22.
 */
public interface InventoryService {
    //加载产品sku库存
    List<InventoryDto> loadInventory(int account, long shopId, long productId) throws Exception;
    //设置产品sku库存记录
    ClientTbInventory setInventory(int account,long shopId, long skuid, int amount,String type) throws Exception;
    //加载库存明细
    Page<InventoryDetailDto> loadDetails(int account, long inventoryId,String startDate,String endDate,Pageable page) throws Exception;
    //按类型加载库存明细
    Page<InventoryDetailDto> loadDetailsByStatus(int account,long inventoryId,String startDate,String endDate,String status, Pageable page) throws Exception;

    Page<InventoryDetailDto> loadDetailsByProduct(int account, long productId,long shopId,String startDate,String endDate,Pageable page) throws Exception;

    Page<InventoryDetailDto> loadDetailsByProductAndStatus(int account,long productId,long shopId,String startDate,String endDate,String status, Pageable page) throws Exception;
}
