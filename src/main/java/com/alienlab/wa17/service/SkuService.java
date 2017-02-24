package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.sun.deploy.util.SessionState;

import java.util.List;

/**
 * Created by juhuiguang on 2017/2/24.
 */
public interface SkuService {
    //加载商品sku
    List<ClientTbProductSku> loadSku(int account_id, int product_id) throws Exception;

    //创建sku
    ClientTbProductSku addSku(int account_id, int product_id, ClientTbProductSku sku);

    //删除sku
    boolean delSku(int account_id,int sku_id);
}
