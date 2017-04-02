package com.alienlab.wa17.entity.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/4/2.
 */
@ApiModel(value="调货实体")
public class DispatchDto {
    @ApiModelProperty(value="调货id")
    @Column(name = "dispatch_id")
    private Long dispatchId;
    @ApiModelProperty(value="申请调货时间")
    @Column(name = "dispatch_time1")
    private Timestamp dispatchTime1;
    @ApiModelProperty(value="申请调货数量")
    @Column(name = "dispatch_amount")
    private Integer dispatchAmount;
    @ApiModelProperty(value="单品id")
    @Column(name = "sku_id")
    private Long skuId;
    @ApiModelProperty(value="单品颜色名称")
    @Column(name = "color_name")
    private String colorName;
    @ApiModelProperty(value="单品尺码名称")
    @Column(name = "size_name")
    private String sizeName;
    @ApiModelProperty(value="调出方")
    @Column(name = "dispatch_from_shop")
    private Long dispatchFromShop;
    @ApiModelProperty(value="调入方")
    @Column(name = "dispatch_to_shop")
    private Long dispatchToShop;
    @ApiModelProperty(value="调出是否完成")
    @Column(name = "dispatch_from_isok")
    private String dispatchFromIsok;
    @ApiModelProperty(value="调入是否完成")
    @Column(name = "dispatch_to_isok")
    private String dispatchToIsok;
    @ApiModelProperty(value="调出完成时间")
    @Column(name = "dispatch_from_oktime")
    private Timestamp dispatchFromOktime;
    @ApiModelProperty(value="调入完成时间")
    @Column(name = "dispatch_to_oktime")
    private Timestamp dispatchToOktime;
    @ApiModelProperty(value="调货是否完成")
    @Column(name = "dispatch_isfinished")
    private String dispatchIsfinished;

    @ApiModelProperty(value="产品名称")
    @Column(name = "product_name")
    private String productName;
    @ApiModelProperty(value="产品id")
    @Column(name = "product_id")
    private Long productId;
    @ApiModelProperty(value="产品自动货号")
    @Column(name = "product_code")
    private String productCode;
    @ApiModelProperty(value="产品自编货号")
    @Column(name = "product_code2")
    private String productCode2;
    @ApiModelProperty(value="产品缩略图")
    @Column(name = "product_pic")
    private String productPic;

    public Long getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }

    public Timestamp getDispatchTime1() {
        return dispatchTime1;
    }

    public void setDispatchTime1(Timestamp dispatchTime1) {
        this.dispatchTime1 = dispatchTime1;
    }

    public Integer getDispatchAmount() {
        return dispatchAmount;
    }

    public void setDispatchAmount(Integer dispatchAmount) {
        this.dispatchAmount = dispatchAmount;
    }

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

    public Long getDispatchFromShop() {
        return dispatchFromShop;
    }

    public void setDispatchFromShop(Long dispatchFromShop) {
        this.dispatchFromShop = dispatchFromShop;
    }

    public Long getDispatchToShop() {
        return dispatchToShop;
    }

    public void setDispatchToShop(Long dispatchToShop) {
        this.dispatchToShop = dispatchToShop;
    }

    public String getDispatchFromIsok() {
        return dispatchFromIsok;
    }

    public void setDispatchFromIsok(String dispatchFromIsok) {
        this.dispatchFromIsok = dispatchFromIsok;
    }

    public String getDispatchToIsok() {
        return dispatchToIsok;
    }

    public void setDispatchToIsok(String dispatchToIsok) {
        this.dispatchToIsok = dispatchToIsok;
    }

    public Timestamp getDispatchFromOktime() {
        return dispatchFromOktime;
    }

    public void setDispatchFromOktime(Timestamp dispatchFromOktime) {
        this.dispatchFromOktime = dispatchFromOktime;
    }

    public Timestamp getDispatchToOktime() {
        return dispatchToOktime;
    }

    public void setDispatchToOktime(Timestamp dispatchToOktime) {
        this.dispatchToOktime = dispatchToOktime;
    }

    public String getDispatchIsfinished() {
        return dispatchIsfinished;
    }

    public void setDispatchIsfinished(String dispatchIsfinished) {
        this.dispatchIsfinished = dispatchIsfinished;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }
}
