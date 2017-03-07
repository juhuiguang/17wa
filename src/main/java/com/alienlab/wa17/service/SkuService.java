package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbProductSku;

import java.util.List;

/**
 * Created by juhuiguang on 2017/2/24.
 */
public interface SkuService {
    //加载商品sku
    List<ClientTbProductSku> loadSku(int account_id, long product_id) throws Exception;

    //创建sku
    ClientTbProductSku addSku(int account_id, long product_id, ClientTbProductSku sku)throws Exception;

    //删除sku
    boolean delSkuSingle(int account_id,int sku_id)throws Exception;

    boolean delSku(int account_id,int product_id) throws Exception;
}
