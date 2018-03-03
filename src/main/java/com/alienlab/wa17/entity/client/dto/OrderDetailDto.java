package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.client.ClientTbOrderDetail;

/**
 * Created by 橘 on 2017/3/31.
 */
public class OrderDetailDto extends ClientTbOrderDetail {//集成订单明细，增加sku字段，增加库存字段
    private long detailId;
    private long skuId;
    private String colorName;
    private String sizeName;
    private String productCode;
    private String productCode2;
    private String productPic;
    private String detailProductName;
    private String orderId;
    private Integer detailAmount;
    private Float detailPrice;
    private Float detailTotalPrice;
    private Integer detailRealAmount;
    private Integer detailReturnamount;

    @Override
    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    @Override
    public Long getSkuId() {
        return skuId;
    }

    @Override
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode2() {
        return productCode2;
    }

    public void setProductCode2(String productCode2) {
        this.productCode2 = productCode2;
    }

    @Override
    public String getDetailProductName() {
        return detailProductName;
    }

    @Override
    public void setDetailProductName(String detailProductName) {
        this.detailProductName = detailProductName;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public Integer getDetailAmount() {
        return detailAmount;
    }

    @Override
    public void setDetailAmount(Integer detailAmount) {
        this.detailAmount = detailAmount;
    }

    @Override
    public Float getDetailPrice() {
        return detailPrice;
    }

    @Override
    public void setDetailPrice(Float detailPrice) {
        this.detailPrice = detailPrice;
    }

    @Override
    public Float getDetailTotalPrice() {
        return detailTotalPrice;
    }

    @Override
    public void setDetailTotalPrice(Float detailTotalPrice) {
        this.detailTotalPrice = detailTotalPrice;
    }

    @Override
    public Integer getDetailReturnamount() {
        return detailReturnamount;
    }

    @Override
    public void setDetailReturnamount(Integer detailReturnamount) {
        this.detailReturnamount = detailReturnamount;
    }

    @Override
    public Integer getDetailRealAmount() {
        if(this.detailReturnamount==null){
            this.detailReturnamount=0;
        }
        this.detailRealAmount=this.detailAmount-this.detailReturnamount;
        return this.detailRealAmount;
    }

    @Override
    public void setDetailRealAmount(Integer detailRealAmount) {
        this.detailRealAmount = detailRealAmount;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }
}
