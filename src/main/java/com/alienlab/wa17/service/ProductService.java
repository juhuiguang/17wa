package com.alienlab.wa17.service;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.entity.client.dto.ProductDto;
import com.alienlab.wa17.entity.client.dto.ProductSkuDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by juhuiguang on 2017/2/24.
 */
public interface ProductService {
    Page<ClientTbProduct> getProducts(int account_id,String keyword,Pageable page) throws Exception;

    Page<ClientTbProduct> getAllProducts(int account_id,Pageable page) throws Exception;

    Page<ClientTbProduct> getProducts(int account_id,String keyword,long shopId,Pageable page) throws Exception;

    Page<ClientTbProduct> getAllProducts(int account_id,long shopId,Pageable page) throws Exception;

    //添加商品
    ClientTbProduct addProduct(int account_id,ClientTbProduct product,ClientTbProductSku [] clientTbProductSkus) throws Exception;

    //修改商品
    ClientTbProduct updateProduct(int account_id,ClientTbProduct product,ClientTbProductSku [] clientTbProductSkus) throws Exception;

    //下架、售卖商品
    ClientTbProduct changeProductStatus(int account_id,long product_id,String status) throws Exception;

    ProductSkuDto loadProduct(int account_id, long product_id) throws Exception;

    ClientTbProduct refreshStatus(int account,long productId) throws Exception;





}
