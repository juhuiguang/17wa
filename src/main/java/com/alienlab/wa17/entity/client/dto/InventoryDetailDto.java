package com.alienlab.wa17.entity.client.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/3/24.
 */
public class InventoryDetailDto {
    @ApiModelProperty(value="单品id")
    private long id;
    @ApiModelProperty(value="产品id")
    private Long productId;
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

    @ApiModelProperty(value="店铺id")
    private Long shopId;
    @ApiModelProperty(value="此店铺的单品库存量")
    private Integer inventoryAmount;
    @ApiModelProperty(value="此店铺该单品的库存状态")
    private String inventoryCountStatus;
    @ApiModelProperty(value="此店铺该单品的库存清点时间")
    private Timestamp inventoryCountTime;

    private Long detailId;
    private Long inventoryId;
    private Integer detailAmount;
    private Timestamp detailTime;
    private String detailType;

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

    @Id
    @Column(name = "detail_id")
    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    @Basic
    @Column(name = "inventory_id")
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
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
    @Column(name = "detail_time")
    public Timestamp getDetailTime() {
        return detailTime;
    }

    public void setDetailTime(Timestamp detailTime) {
        this.detailTime = detailTime;
    }

    @Basic
    @Column(name = "detail_type")
    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }
}