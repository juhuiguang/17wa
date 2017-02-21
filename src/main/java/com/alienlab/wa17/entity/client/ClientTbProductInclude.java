package com.alienlab.wa17.entity.client;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_product_include", schema = "17wa_client", catalog = "")
public class ClientTbProductInclude {
    private Long includeId;
    private Integer productId;
    private String sizeName;
    private String includeName;
    private Integer includeValue;

    @Basic
    @Column(name = "include_id")
    public Long getIncludeId() {
        return includeId;
    }

    public void setIncludeId(Long includeId) {
        this.includeId = includeId;
    }

    @Basic
    @Column(name = "product_id")
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
    @Column(name = "include_name")
    public String getIncludeName() {
        return includeName;
    }

    public void setIncludeName(String includeName) {
        this.includeName = includeName;
    }

    @Basic
    @Column(name = "include_value")
    public Integer getIncludeValue() {
        return includeValue;
    }

    public void setIncludeValue(Integer includeValue) {
        this.includeValue = includeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbProductInclude that = (ClientTbProductInclude) o;

        if (includeId != null ? !includeId.equals(that.includeId) : that.includeId != null) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (sizeName != null ? !sizeName.equals(that.sizeName) : that.sizeName != null) return false;
        if (includeName != null ? !includeName.equals(that.includeName) : that.includeName != null) return false;
        if (includeValue != null ? !includeValue.equals(that.includeValue) : that.includeValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = includeId != null ? includeId.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (sizeName != null ? sizeName.hashCode() : 0);
        result = 31 * result + (includeName != null ? includeName.hashCode() : 0);
        result = 31 * result + (includeValue != null ? includeValue.hashCode() : 0);
        return result;
    }
}
