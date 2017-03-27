package com.alienlab.wa17.entity.client;

import javax.persistence.*;

/**
 * Created by 橘 on 2017/3/27.
 */
@Entity
@Table(name = "tb_product_inventory_status", schema = "17wa_client")
public class ClientTbProductInventoryStatus {
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name="product_id")
    private Long productId;
    @Basic
    @Column(name="shop_id")
    private Long shopId;
    @Basic
    @Column(name="status")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
