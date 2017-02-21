package com.alienlab.wa17.entity.client;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_product", schema = "17wa_client", catalog = "")
public class ClientTbProduct {
    private long productId;
    private String productCode;
    private String productCode2;
    private Long accountId;
    private String productName;
    private String productPic;
    private Integer productPrice1;
    private Integer productPrice2;
    private String productDesc;
    private Long productType;
    private String productFabric;
    private String productFabricin;
    private String productSizes;
    private String productColors;
    private String productStatus;
    private Timestamp productTime;
    private String productTags;
    private Timestamp updatetime;

    @Id
    @Column(name = "product_id")
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "product_code")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Basic
    @Column(name = "product_code2")
    public String getProductCode2() {
        return productCode2;
    }

    public void setProductCode2(String productCode2) {
        this.productCode2 = productCode2;
    }

    @Basic
    @Column(name = "account_id")
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "product_pic")
    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    @Basic
    @Column(name = "product_price1")
    public Integer getProductPrice1() {
        return productPrice1;
    }

    public void setProductPrice1(Integer productPrice1) {
        this.productPrice1 = productPrice1;
    }

    @Basic
    @Column(name = "product_price2")
    public Integer getProductPrice2() {
        return productPrice2;
    }

    public void setProductPrice2(Integer productPrice2) {
        this.productPrice2 = productPrice2;
    }

    @Basic
    @Column(name = "product_desc")
    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    @Basic
    @Column(name = "product_type")
    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    @Basic
    @Column(name = "product_fabric")
    public String getProductFabric() {
        return productFabric;
    }

    public void setProductFabric(String productFabric) {
        this.productFabric = productFabric;
    }

    @Basic
    @Column(name = "product_fabricin")
    public String getProductFabricin() {
        return productFabricin;
    }

    public void setProductFabricin(String productFabricin) {
        this.productFabricin = productFabricin;
    }

    @Basic
    @Column(name = "product_sizes")
    public String getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(String productSizes) {
        this.productSizes = productSizes;
    }

    @Basic
    @Column(name = "product_colors")
    public String getProductColors() {
        return productColors;
    }

    public void setProductColors(String productColors) {
        this.productColors = productColors;
    }

    @Basic
    @Column(name = "product_status")
    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    @Basic
    @Column(name = "product_time")
    public Timestamp getProductTime() {
        return productTime;
    }

    public void setProductTime(Timestamp productTime) {
        this.productTime = productTime;
    }

    @Basic
    @Column(name = "product_tags")
    public String getProductTags() {
        return productTags;
    }

    public void setProductTags(String productTags) {
        this.productTags = productTags;
    }

    @Basic
    @Column(name = "updatetime")
    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbProduct that = (ClientTbProduct) o;

        if (productId != that.productId) return false;
        if (productCode != null ? !productCode.equals(that.productCode) : that.productCode != null) return false;
        if (productCode2 != null ? !productCode2.equals(that.productCode2) : that.productCode2 != null) return false;
        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (productPic != null ? !productPic.equals(that.productPic) : that.productPic != null) return false;
        if (productPrice1 != null ? !productPrice1.equals(that.productPrice1) : that.productPrice1 != null)
            return false;
        if (productPrice2 != null ? !productPrice2.equals(that.productPrice2) : that.productPrice2 != null)
            return false;
        if (productDesc != null ? !productDesc.equals(that.productDesc) : that.productDesc != null) return false;
        if (productType != null ? !productType.equals(that.productType) : that.productType != null) return false;
        if (productFabric != null ? !productFabric.equals(that.productFabric) : that.productFabric != null)
            return false;
        if (productFabricin != null ? !productFabricin.equals(that.productFabricin) : that.productFabricin != null)
            return false;
        if (productSizes != null ? !productSizes.equals(that.productSizes) : that.productSizes != null) return false;
        if (productColors != null ? !productColors.equals(that.productColors) : that.productColors != null)
            return false;
        if (productStatus != null ? !productStatus.equals(that.productStatus) : that.productStatus != null)
            return false;
        if (productTime != null ? !productTime.equals(that.productTime) : that.productTime != null) return false;
        if (productTags != null ? !productTags.equals(that.productTags) : that.productTags != null) return false;
        if (updatetime != null ? !updatetime.equals(that.updatetime) : that.updatetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (productId ^ (productId >>> 32));
        result = 31 * result + (productCode != null ? productCode.hashCode() : 0);
        result = 31 * result + (productCode2 != null ? productCode2.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (productPic != null ? productPic.hashCode() : 0);
        result = 31 * result + (productPrice1 != null ? productPrice1.hashCode() : 0);
        result = 31 * result + (productPrice2 != null ? productPrice2.hashCode() : 0);
        result = 31 * result + (productDesc != null ? productDesc.hashCode() : 0);
        result = 31 * result + (productType != null ? productType.hashCode() : 0);
        result = 31 * result + (productFabric != null ? productFabric.hashCode() : 0);
        result = 31 * result + (productFabricin != null ? productFabricin.hashCode() : 0);
        result = 31 * result + (productSizes != null ? productSizes.hashCode() : 0);
        result = 31 * result + (productColors != null ? productColors.hashCode() : 0);
        result = 31 * result + (productStatus != null ? productStatus.hashCode() : 0);
        result = 31 * result + (productTime != null ? productTime.hashCode() : 0);
        result = 31 * result + (productTags != null ? productTags.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        return result;
    }
}
