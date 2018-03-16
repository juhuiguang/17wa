package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.client.dto.InventoryDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by juhuiguang on 2017/2/24.
 */
public interface SkuService {
    //加载商品sku
    List<ClientTbProductSku> loadSku(int account_id, long product_id,long shopId) throws Exception;

    //创建sku
    ClientTbProductSku addSku(int account_id, long product_id, ClientTbProductSku sku)throws Exception;

    //删除sku
    boolean delSkuSingle(int account_id,int sku_id)throws Exception;

    boolean delSku(int account_id,int product_id) throws Exception;

    ClientTbProductSku setStatus(int account_id,long sku_id,String status) throws Exception;

    Page<InventoryDto> getAllSku(int account, long shopId, String code, int index, int size) throws Exception;
}
