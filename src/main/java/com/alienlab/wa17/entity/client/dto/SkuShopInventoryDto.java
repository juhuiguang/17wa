package com.alienlab.wa17.entity.client.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;

/**
 * Created by 橘 on 2017/4/2.
 */
public class SkuShopInventoryDto {
    @ApiModelProperty(value="单品id")
    @Column(name = "sku_id")
    private Long skuId;
    @ApiModelProperty(value="单品颜色名称")
    @Column(name = "color_name")
    private String colorName;
    @ApiModelProperty(value="单品尺码名称")
    @Column(name = "size_name")
    private String sizeName;

    @ApiModelProperty(value="单品库存id")
    @Column(name = "inventory_id")
    private Long inventoryId;
    @ApiModelProperty(value="此店铺的单品库存量")
    @Column(name = "inventory_amount")
    private Integer inventoryAmount;
    @ApiModelProperty(value="此店铺该单品的库存状态")
    @Column(name = "inventory_count_status")
    private String inventoryCountStatus;

    @ApiModelProperty(value="店铺ID")
    @Column(name = "shop_id")
    private long shopId;
    @ApiModelProperty(value="是否默认店铺")
    @Column(name = "shop_isdefault")
    private String shopIsdefault;
    @ApiModelProperty(value="店铺名称")
    @Column(name = "shop_name")
    private String shopName;

    @ApiModelProperty(value="调货状态")
    @Column(name = "dispatch_status")
    private Integer dispatchStatus;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(Integer inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    public String getInventoryCountStatus() {
        return inventoryCountStatus;
    }

    public void setInventoryCountStatus(String inventoryCountStatus) {
        this.inventoryCountStatus = inventoryCountStatus;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopIsdefault() {
        return shopIsdefault;
    }

    public void setShopIsdefault(String shopIsdefault) {
        this.shopIsdefault = shopIsdefault;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getDispatchStatus() {
        return dispatchStatus;
    }

    public void setDispatchStatus(Integer dispatchStatus) {
        this.dispatchStatus = dispatchStatus;
    }
}
