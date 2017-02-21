package com.alienlab.wa17.entity.client;

import javax.persistence.*;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_product_sku", schema = "17wa_client", catalog = "")
public class ClientTbProductSku {
    private long id;
    private Long productId;
    private Long colorId;
    private String colorName;
    private String colorType;
    private Long sizeId;
    private String sizeName;
    private String sizeType;
    private String skuStatus;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product_id")
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "color_id")
    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    @Basic
    @Column(name = "color_name")
    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Basic
    @Column(name = "color_type")
    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    @Basic
    @Column(name = "size_id")
    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    @Basic
    @Column(name = "size_name")
    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    @Basic
    @Column(name = "size_type")
    public String getSizeType() {
        return sizeType;
    }

    public void setSizeType(String sizeType) {
        this.sizeType = sizeType;
    }

    @Basic
    @Column(name = "sku_status")
    public String getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(String skuStatus) {
        this.skuStatus = skuStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbProductSku that = (ClientTbProductSku) o;

        if (id != that.id) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (colorId != null ? !colorId.equals(that.colorId) : that.colorId != null) return false;
        if (colorName != null ? !colorName.equals(that.colorName) : that.colorName != null) return false;
        if (colorType != null ? !colorType.equals(that.colorType) : that.colorType != null) return false;
        if (sizeId != null ? !sizeId.equals(that.sizeId) : that.sizeId != null) return false;
        if (sizeName != null ? !sizeName.equals(that.sizeName) : that.sizeName != null) return false;
        if (sizeType != null ? !sizeType.equals(that.sizeType) : that.sizeType != null) return false;
        if (skuStatus != null ? !skuStatus.equals(that.skuStatus) : that.skuStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (colorId != null ? colorId.hashCode() : 0);
        result = 31 * result + (colorName != null ? colorName.hashCode() : 0);
        result = 31 * result + (colorType != null ? colorType.hashCode() : 0);
        result = 31 * result + (sizeId != null ? sizeId.hashCode() : 0);
        result = 31 * result + (sizeName != null ? sizeName.hashCode() : 0);
        result = 31 * result + (sizeType != null ? sizeType.hashCode() : 0);
        result = 31 * result + (skuStatus != null ? skuStatus.hashCode() : 0);
        return result;
    }
}
