package com.alienlab.wa17.entity.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/3/22.
 */
@ApiModel(value="单品库存数据模型")
public class InventoryDto {
    @ApiModelProperty(value="单品id")
    private Long id;
    @ApiModelProperty(value="产品id")
    private Long productId;

    private String productName;

    private String productCode;

    private String productCode2;

    private Integer productPrice1;
    private Integer productPrice2;

    @ApiModelProperty(value="颜色编码")
    private Long colorId;
    @ApiModelProperty(value="颜色名称")
    private String colorName;
    @ApiModelProperty(value="颜色类型:系统颜色，自定义颜色")
    private String colorType;
    @ApiModelProperty(value="尺码id")
    private Long sizeId;
    @ApiModelProperty(value="尺码名称")
    private String sizeName;
    @ApiModelProperty(value="尺码类型：系统尺码，自定义尺码")
    private String sizeType;
    @ApiModelProperty(value="单品状态")
    private String skuStatus;


    @ApiModelProperty(value="单品库存id")
    private Long inventoryId;
    @ApiModelProperty(value="店铺id")
    private Long shopId;
    @ApiModelProperty(value="此店铺的单品库存量")
    private Integer inventoryAmount;
    @ApiModelProperty(value="此店铺该单品的库存状态")
    private String inventoryCountStatus;
    @ApiModelProperty(value="此店铺该单品的库存清点时间")
    private Timestamp inventoryCountTime;
    @ApiModelProperty(value="此店铺的单品当前清点数量")
    private Integer inventoryTempAmount;
    @ApiModelProperty(value = "产品图片")
    private String productPic;

    @Column(name = "temp_amount")
    public Integer getInventoryTempAmount() {
        return inventoryTempAmount;
    }

    public void setInventoryTempAmount(Integer inventoryTempAmount) {
        this.inventoryTempAmount = inventoryTempAmount;
    }

    @Column(name = "inventory_id")
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Basic
    @Column(name = "inventory_amount")
    public Integer getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(Integer inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
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

    public String getProductName() {
        return productName;
    }

    @Basic
    @Column(name = "product_name")
    public void setProductName(String productName) {
        this.productName = productName;
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
    @Column(name = "product_pic")
    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }
}
