package com.alienlab.wa17.entity.client;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/7/4.
 */
@Entity
@Table(name = "tb_inventory_temp", schema = "17wa_client")
public class ClientTbInventoryTemp {
    private long id;
    private Long skuId;
    private Long shopId;
    private Integer inventoryAmount;
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
    @Column(name = "amount")
    public Integer getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(Integer inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    @Basic
    @Column(name = "temp_time")
    public Timestamp getInventoryCountTime() {
        return inventoryCountTime;
    }

    public void setInventoryCountTime(Timestamp inventoryCountTime) {
        this.inventoryCountTime = inventoryCountTime;
    }

}
