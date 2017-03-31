package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.client.ClientTbOrderDetail;

/**
 * Created by 橘 on 2017/3/31.
 */
public class OrderDetailDto extends ClientTbOrderDetail {//集成订单明细，增加sku字段，增加库存字段
    private long skuid;
    private Long productId;
    private String colorName;
    private String sizeName;

    private long inventory_id;
    private Long shopId;
    private Integer inventoryAmount;

    public long getSkuid() {
        return skuid;
    }

    public void setSkuid(long skuid) {
        this.skuid = skuid;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public long getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(long inventory_id) {
        this.inventory_id = inventory_id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(Integer inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }
}
