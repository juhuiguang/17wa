package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductInclude;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.main.MainTbColors;
import com.alienlab.wa17.entity.main.MainTbSize;

import java.util.List;

/**
 * Created by 橘 on 2017/3/19.
 */
public class ProductSkuDto {
    ClientTbProduct product;
    List<ClientTbProductSku> skus;
    List<ClientTbProductInclude> includes;
    List<MainTbColors> colors;
    List<MainTbSize> sizes;

    public List<MainTbColors> getColors() {
        return colors;
    }

    public void setColors(List<MainTbColors> colors) {
        this.colors = colors;
    }

    public List<MainTbSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<MainTbSize> sizes) {
        this.sizes = sizes;
    }

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
