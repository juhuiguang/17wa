package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.client.ClientTbShop;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * Created by juhuiguang on 2017/3/9.
 */
public class ShopAccountDto extends ClientTbShop {
    @ApiModelProperty(value="店铺名称")
    private String shopName;

    @Override
    @Column(name = "shop_name")
    public String getShopName() {
        return shopName;
    }

    @Override
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
