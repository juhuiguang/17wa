package com.alienlab.wa17.entity.client;

import javax.persistence.*;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_order_detail", schema = "17wa_client", catalog = "")
public class ClientTbOrderDetail {
    private long detailId;
    private String detailProductName;
    private Long orderId;
    private Long skuId;
    private Integer detailAmount;
    private Integer detailPrice;
    private Integer detailTotalPrice;

    @Id
    @Column(name = "detail_id")
    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    @Basic
    @Column(name = "detail_product_name")
    public String getDetailProductName() {
        return detailProductName;
    }

    public void setDetailProductName(String detailProductName) {
        this.detailProductName = detailProductName;
    }

    @Basic
    @Column(name = "order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    @Column(name = "detail_amount")
    public Integer getDetailAmount() {
        return detailAmount;
    }

    public void setDetailAmount(Integer detailAmount) {
        this.detailAmount = detailAmount;
    }

    @Basic
    @Column(name = "detail_price")
    public Integer getDetailPrice() {
        return detailPrice;
    }

    public void setDetailPrice(Integer detailPrice) {
        this.detailPrice = detailPrice;
    }

    @Basic
    @Column(name = "detail_total_price")
    public Integer getDetailTotalPrice() {
        return detailTotalPrice;
    }

    public void setDetailTotalPrice(Integer detailTotalPrice) {
        this.detailTotalPrice = detailTotalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbOrderDetail that = (ClientTbOrderDetail) o;

        if (detailId != that.detailId) return false;
        if (detailProductName != null ? !detailProductName.equals(that.detailProductName) : that.detailProductName != null)
            return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) return false;
        if (detailAmount != null ? !detailAmount.equals(that.detailAmount) : that.detailAmount != null) return false;
        if (detailPrice != null ? !detailPrice.equals(that.detailPrice) : that.detailPrice != null) return false;
        if (detailTotalPrice != null ? !detailTotalPrice.equals(that.detailTotalPrice) : that.detailTotalPrice != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (detailId ^ (detailId >>> 32));
        result = 31 * result + (detailProductName != null ? detailProductName.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (detailAmount != null ? detailAmount.hashCode() : 0);
        result = 31 * result + (detailPrice != null ? detailPrice.hashCode() : 0);
        result = 31 * result + (detailTotalPrice != null ? detailTotalPrice.hashCode() : 0);
        return result;
    }
}
