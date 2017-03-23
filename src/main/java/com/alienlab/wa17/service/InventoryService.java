package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbInventory;
import com.alienlab.wa17.entity.client.ClientTbInventoryDetail;
import com.alienlab.wa17.entity.client.dto.InventoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by 橘 on 2017/3/22.
 */
public interface InventoryService {
    List<InventoryDto> loadInventory(int account, long shopId, long productId) throws Exception;
    ClientTbInventory addInventory(int account,long shopId,long productId,int amount,String type) throws Exception;
    ClientTbInventory updateInventory(int account,long inventoryId,int amount) throws Exception;
    Page<ClientTbInventoryDetail> loadDetails(int account, long inventoryId, Pageable page) throws Exception;
    Page<ClientTbInventoryDetail> loadDetailsByStatus(int account,long inventoryId,String status, Pageable page) throws Exception;
    ClientTbInventoryDetail addInventoryLog(int account,long inventoryId,int amount,String type) throws Exception;
}
