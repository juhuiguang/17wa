package com.alienlab.wa17.entity.client;

import com.alienlab.utils.SpringUtil;
import com.alienlab.wa17.WaImageProp;
import com.alienlab.wa17.entity.LogicField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@ApiModel(value="产品对象")
@Entity
@Table(name = "tb_product", schema = "17wa_client", catalog = "")
public class ClientTbProduct {

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

    @ApiModelProperty(value="产品备注")
    @Column(name="product_memo")
    private String memo;
    @ApiModelProperty(value="初始库存")
    @Column(name="product_initamount")
    private Integer productInitAmount;

    @Column(name="iswebsite")
    @ApiModelProperty(value="是否用于网站")
    private String iswebsite;

    @ApiModelProperty(value="产品库存总量")
    @LogicField
    private Integer amount;

    @ApiModelProperty(value="压缩后的图片")
    @LogicField
    private String productDescExpress;

    @ApiModelProperty(value="产品临时清点库存总量")
    private Integer tempAmount;

    @ApiModelProperty(value="产品库存状态")
    @LogicField
    private String inventroyStatus;

    public String getProductDescExpress() {
        return productDescExpress;
    }

    public void setProductDescExpress(String productDescExpress) {
        this.productDescExpress = productDescExpress;
    }

    @Basic
    @Column(name="inventory_status")
    public String getInventroyStatus() {
        return inventroyStatus;
    }

    public void setInventroyStatus(String inventroyStatus) {
        this.inventroyStatus = inventroyStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Basic
    @Column(name = "product_amount")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

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
        if(productPic!=null&&!productPic.equals("")){
            int pos=productPic.lastIndexOf(".");
            String s=productPic.substring(0,pos)+"_750"+productPic.substring(pos);
            return s;
        }else{
            WaImageProp waImageProp=SpringUtil.getBean(WaImageProp.class);
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

    @LogicField
    @Column(name = "tempamount")
    public Integer getTempAmount() {
        return tempAmount;
    }

    public void setTempAmount(Integer tempAmount) {
        this.tempAmount = tempAmount;
    }

    public Integer getProductInitAmount() {
        return productInitAmount;
    }

    public void setProductInitAmount(Integer productInitAmount) {
        this.productInitAmount = productInitAmount;
    }


    public String getIswebsite() {
        return iswebsite;
    }

    public void setIswebsite(String iswebsite) {
        this.iswebsite = iswebsite;
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
