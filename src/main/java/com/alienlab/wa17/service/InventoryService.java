package com.alienlab.wa17.service;

import com.alibaba.fastjson.JSONArray;
import com.alienlab.wa17.entity.client.*;
import com.alienlab.wa17.entity.client.dto.DispatchDto;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.InventoryDto;
import com.alienlab.wa17.entity.client.dto.SkuShopInventoryDto;
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

    Page<InventoryDetailDto> loadDetails(int account,long shopId,String status,String isall,String startDate, String endDate, Pageable page,String keyword) throws Exception;

    //加载sku库存明细
    Page<InventoryDetailDto> loadDetails(int account, long skuId,String startDate,String endDate,Pageable page) throws Exception;
    //按类型加载库存明细
    Page<InventoryDetailDto> loadDetailsByStatus(int account,long skuId,String startDate,String endDate,String status, Pageable page) throws Exception;

    Page<InventoryDetailDto> loadDetailsByProduct(int account, long productId,long shopId,String startDate,String endDate,Pageable page) throws Exception;

    Page<InventoryDetailDto> loadDetailsByProductAndStatus(int account,long productId,long shopId,String startDate,String endDate,String status, Pageable page) throws Exception;

    JSONArray getInventoryStat(int account, long skuId, String startDate, String endDate) throws Exception;
    JSONArray getInventoryStatByProd(int account, long productId, long shopId, String startDate, String endDate) throws Exception;

    JSONArray getInventoryStatByStatus(int account, long skuId,String startDate,String endDate,String status) throws Exception;
    JSONArray getInventoryStatByStatusAndProd(int account, long productId, long shopId,String startDate,String endDate,String status) throws Exception;

    JSONArray getInventoryStat(int account,long shopId,String status,String isall,String startDate,String endDate,String keyword) throws Exception;

    ClientTbProductInventoryStatus setProductInventoryStatus(int account,long productid,long shopid,String status)throws Exception;

    ClientTbDispatch addDispatch(int account,long fromShopId,long toShopId,long skuId,int amount) throws Exception;

    boolean delDispatch(int account,long dispatchId) throws Exception;

    ClientTbDispatch confirmDispatch(int account,long dispatchId,long shopId) throws Exception;

    Page<DispatchDto> getDispatch(int account, long shopId,int index,int size) throws Exception;

    List<SkuShopInventoryDto> getSkuShopList(int account,long productId) throws Exception;

    void checkInventoryStatus(int account,long shopid,long skuid,int amount) throws Exception;

    boolean resetShopInventoryStatus(int account,long shopid) throws Exception;

    List<ClientTbProduct> checkShopInventory(int account,long shopid,JSONArray details) throws Exception;
}
