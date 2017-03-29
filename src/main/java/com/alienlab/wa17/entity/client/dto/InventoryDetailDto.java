package com.alienlab.wa17.entity.client.dto;

import com.alienlab.utils.SpringUtil;
import com.alienlab.wa17.WaImageProp;
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


    @ApiModelProperty(value="产品编码")
    private long productId;
    @ApiModelProperty(value="自动货号",notes="表单提交时，可不赋值，规则是：日期+accountid+当日增量")
    private String productCode;
    @ApiModelProperty(value="自编货号")
    private String productCode2;
    @ApiModelProperty(value="所属账户Id",notes = "表单提交时可不赋值")
    private Long accountId;
    @ApiModelProperty(value="产品名")
    private String productName;
    @ApiModelProperty(value="产品缩略图路径",notes="表单提交时可不赋值，先提交产品基本信息，保存后，调用图片接口，发送图片")
    private String productPic;
    @ApiModelProperty(value="厂商指导价格")
    private Integer productPrice1;
    @ApiModelProperty(value="自定义价格",notes="自定义价格优先级最高")
    private Integer productPrice2;
    @ApiModelProperty(value="产品描述",notes="多张图片地址，分号相隔，表单提交时可不提交，当表单基础信息保存完成后，调用图片上传接口，完成详情图片上传")
    private String productDesc;
    @ApiModelProperty(value="产品类型")
    private String productType;
    @ApiModelProperty(value="面料")
    private String productFabric;
    @ApiModelProperty(value="面料成分")
    private String productFabricin;
    @ApiModelProperty(value="尺码概要",notes = "选择的尺码组合成 S|M|L|XL|XXL的形式保存")
    private String productSizes;
    @ApiModelProperty(value="颜色概要",notes = "选择的颜色组合成 红|蓝|绿|的形式保存")
    private String productColors;
    @ApiModelProperty(value="产品状态")
    private String productStatus;
    @ApiModelProperty(value="创建时间",notes = "表单提交时可不赋值，默认为正常状态，在状态改变功能中改变此状态")
    private Timestamp productTime;
    @ApiModelProperty(value="产品标签",notes="用户选择的标签，组合类似颜色，尺码的格式保存")
    private String productTags;
    @ApiModelProperty(value="产品最后更新时间",notes = "表单提交时可不赋值")
    private Timestamp updatetime;
    @ApiModelProperty(value="产品库存总量")
    private Integer amount;
    @ApiModelProperty(value="产品库存状态")
    private String inventroyStatus;

    @Basic
    @Column(name="inventory_status")
    public String getInventroyStatus() {
        return inventroyStatus;
    }

    public void setInventroyStatus(String inventroyStatus) {
        this.inventroyStatus = inventroyStatus;
    }

    @Basic
    @Column(name = "product_amount")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
        if(productPic!=null&&!productPic.equals("")){
            return productPic;
        }else{
            WaImageProp waImageProp= SpringUtil.getBean(WaImageProp.class);
            return waImageProp.getPath()+"/product.png";
        }

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
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
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
