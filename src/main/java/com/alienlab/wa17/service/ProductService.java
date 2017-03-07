package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by juhuiguang on 2017/2/24.
 */
public interface ProductService {
    //获得指定门店的商品信息
    Page<ClientTbProduct> getProducts(int account_id,int shop_id,String keyword,Pageable page) throws Exception;

    //添加商品
    ClientTbProduct addProduct(int account_id,ClientTbProduct product,ClientTbProductSku [] clientTbProductSkus) throws Exception;

    //修改商品
    ClientTbProduct updateProduct(int account_id,ClientTbProduct product,ClientTbProductSku [] clientTbProductSkus) throws Exception;

    //下架、售卖商品
    ClientTbProduct changeProductStatus(int account_id,int product_id,String status) throws Exception;





}
