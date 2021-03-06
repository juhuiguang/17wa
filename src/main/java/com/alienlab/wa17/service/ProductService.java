package com.alienlab.wa17.service;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.ProductDto;
import com.alienlab.wa17.entity.client.dto.ProductSkuDto;
import com.alienlab.wa17.entity.main.MainTbProducttype;
import com.alienlab.wa17.entity.main.dto.ProductTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by juhuiguang on 2017/2/24.
 */
public interface ProductService {
    ClientTbProduct getProduct(int account_id,long productid) throws Exception;

    Page<ClientTbProduct> getProducts(int account_id,String keyword,Pageable page) throws Exception;

    Page<ClientTbProduct> getAllProducts(int account_id,Pageable page) throws Exception;

    Page<ClientTbProduct> getProducts(int account_id,String keyword,long shopId,Pageable page) throws Exception;

    ProductSkuDto getProductByCode(int account,String code,long shopId) throws Exception;

    Page<ClientTbProduct> getAllProducts(int account_id,long shopId,Pageable page) throws Exception;

    Page<ClientTbProduct> getErrorProducts(int account_id,long shopId,Pageable page) throws Exception;

    List<InventoryDetailDto> getOnSaleProducts(int account, long shopId,String keyword) throws Exception;

    List<InventoryDetailDto> getOnSaleByProduct(int account, long shopId,long productId) throws Exception;

    //添加商品
    ClientTbProduct addProduct(int account_id,ClientTbProduct product,ClientTbProductSku [] clientTbProductSkus) throws Exception;

    //修改商品
    ClientTbProduct updateProduct(int account_id,ClientTbProduct product,ClientTbProductSku [] clientTbProductSkus) throws Exception;

    //下架、售卖商品
    ClientTbProduct changeProductStatus(int account_id,long product_id,String status) throws Exception;

    ProductSkuDto loadProduct(int account_id, long product_id,long shopId) throws Exception;

    ClientTbProduct refreshStatus(int account,long productId) throws Exception;

    boolean setPics(int account,long productId,String pic,String type) throws Exception;

    List<String> getPics(int account,long productId)throws Exception;

    boolean delPic(int account,long productId,String pic,String type) throws Exception;


    List<ProductTypeDto> getAllProductType()throws Exception;

    MainTbProducttype addType(MainTbProducttype type)throws Exception;

    boolean delType(long typeId)throws Exception;

    MainTbProducttype updateType(MainTbProducttype type)throws Exception;

    boolean delProduct(int account,long productId) throws Exception;


}
