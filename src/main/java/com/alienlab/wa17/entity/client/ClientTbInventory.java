package com.alienlab.wa17.entity.client;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_inventory", schema = "17wa_client", catalog = "")
public class ClientTbInventory {
    private long id;
    private Long skuId;
    private Long shopId;
    private Integer inventoryAmount;
    private String inventoryCountStatus;
    private Timestamp inventoryCountTime;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sku_id")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Basic
    @Column(name = "shop_id")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "inventory_amount")
    public Integer getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(Integer inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    @Basic
    @Column(name = "inventory_count_status")
    public String getInventoryCountStatus() {
        return inventoryCountStatus;
    }

    public void setInventoryCountStatus(String inventoryCountStatus) {
        this.inventoryCountStatus = inventoryCountStatus;
    }

    @Basic
    @Column(name = "inventory_count_time")
    public Timestamp getInventoryCountTime() {
        return inventoryCountTime;
    }

    public void setInventoryCountTime(Timestamp inventoryCountTime) {
        this.inventoryCountTime = inventoryCountTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbInventory that = (ClientTbInventory) o;

        if (id != that.id) return false;
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (inventoryAmount != null ? !inventoryAmount.equals(that.inventoryAmount) : that.inventoryAmount != null)
            return false;
        if (inventoryCountStatus != null ? !inventoryCountStatus.equals(that.inventoryCountStatus) : that.inventoryCountStatus != null)
            return false;
        if (inventoryCountTime != null ? !inventoryCountTime.equals(that.inventoryCountTime) : that.inventoryCountTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (inventoryAmount != null ? inventoryAmount.hashCode() : 0);
        result = 31 * result + (inventoryCountStatus != null ? inventoryCountStatus.hashCode() : 0);
        result = 31 * result + (inventoryCountTime != null ? inventoryCountTime.hashCode() : 0);
        return result;
    }
}
