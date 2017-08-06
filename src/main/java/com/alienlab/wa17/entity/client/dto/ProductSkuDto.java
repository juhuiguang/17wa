package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductInclude;
import com.alienlab.wa17.entity.client.ClientTbProductSku;

import java.util.List;

/**
 * Created by 橘 on 2017/3/19.
 */
public class ProductSkuDto {
    ClientTbProduct product;
    List<ClientTbProductSku> skus;
    List<ClientTbProductInclude> includes;

    public ClientTbProduct getProduct() {
        return product;
    }

    public void setProduct(ClientTbProduct product) {
        this.product = product;
    }

    public List<ClientTbProductSku> getSkus() {
        return skus;
    }

    public void setSkus(List<ClientTbProductSku> skus) {
        this.skus = skus;
    }

    public List<ClientTbProductInclude> getIncludes() {
        return includes;
    }

    public void setIncludes(List<ClientTbProductInclude> includes) {
        this.includes = includes;
    }
}
