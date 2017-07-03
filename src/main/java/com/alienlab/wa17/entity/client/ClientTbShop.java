package com.alienlab.wa17.entity.client;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_shop", schema = "17wa_client", catalog = "")
public class ClientTbShop {
    @ApiModelProperty(value="店铺ID")
    private long shopId;
    @ApiModelProperty(value="是否默认店铺")
    private String shopIsdefault;
    @ApiModelProperty(value="店铺名称")
    private String shopName;
    @ApiModelProperty(value="店铺地址")
    private String shopAddr;
    @ApiModelProperty(value="联系电话1")
    private String shopPhone1;
    @ApiModelProperty(value="联系电话2")
    private String shopPhone2;
    @ApiModelProperty(value="店铺微信号")
    private String shopWechat;
    @ApiModelProperty(value="QQ号")
    private String shopQq;
    @ApiModelProperty(value="支付方式标签")
    private String payType;
    @ApiModelProperty(value="阿里支付宝昵称")
    private String shopAlipayName;
    @ApiModelProperty(value="支付宝账户")
    private String shopAlipay;
    @ApiModelProperty(value="创建时间")
    private Timestamp shopTime;
    @ApiModelProperty(value="所属市场")
    private String marketName;
    @ApiModelProperty(value="店铺标签")
    private String shopTags;
    @ApiModelProperty(value="银行卡开户行")
    private String shopBank;
    @ApiModelProperty(value="银行卡号")
    private String shopCard;
    @ApiModelProperty(value="银行卡开卡人")
    private String cardName;
    @ApiModelProperty(value="微信支付账号")
    private String shopWechatpay;

    @Id
    @Column(name = "shop_id")
    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "shop_isdefault")
    public String getShopIsdefault() {
        return shopIsdefault;
    }

    public void setShopIsdefault(String shopIsdefault) {
        this.shopIsdefault = shopIsdefault;
    }

    @Basic
    @Column(name = "shop_name")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Basic
    @Column(name = "shop_addr")
    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    @Basic
    @Column(name = "shop_phone1")
    public String getShopPhone1() {
        return shopPhone1;
    }

    public void setShopPhone1(String shopPhone1) {
        this.shopPhone1 = shopPhone1;
    }

    @Basic
    @Column(name = "shop_phone2")
    public String getShopPhone2() {
        return shopPhone2;
    }

    @Basic
    @Column(name = "shop_paytype")
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public void setShopPhone2(String shopPhone2) {
        this.shopPhone2 = shopPhone2;
    }

    @Basic
    @Column(name = "shop_wechat")
    public String getShopWechat() {
        return shopWechat;
    }

    public void setShopWechat(String shopWechat) {
        this.shopWechat = shopWechat;
    }

    @Basic
    @Column(name = "shop_qq")
    public String getShopQq() {
        return shopQq;
    }

    public void setShopQq(String shopQq) {
        this.shopQq = shopQq;
    }

    @Basic
    @Column(name = "shop_alipay_name")
    public String getShopAlipayName() {
        return shopAlipayName;
    }

    public void setShopAlipayName(String shopAlipayName) {
        this.shopAlipayName = shopAlipayName;
    }

    @Basic
    @Column(name = "shop_alipay")
    public String getShopAlipay() {
        return shopAlipay;
    }

    public void setShopAlipay(String shopAlipay) {
        this.shopAlipay = shopAlipay;
    }

    @Basic
    @Column(name = "shop_time")
    public Timestamp getShopTime() {
        return shopTime;
    }

    public void setShopTime(Timestamp shopTime) {
        this.shopTime = shopTime;
    }

    @Basic
    @Column(name = "market_name")
    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    @Basic
    @Column(name = "shop_tags")
    public String getShopTags() {
        return shopTags;
    }

    public void setShopTags(String shopTags) {
        this.shopTags = shopTags;
    }

    @Basic
    @Column(name = "shop_bank")
    public String getShopBank() {
        return shopBank;
    }

    public void setShopBank(String shopBank) {
        this.shopBank = shopBank;
    }
    @Basic
    @Column(name = "shop_card")
    public String getShopCard() {
        return shopCard;
    }

    public void setShopCard(String shopCard) {
        this.shopCard = shopCard;
    }

    @Basic
    @Column(name = "shop_card_name")
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Basic
    @Column(name = "shop_wechatpay")
    public String getShopWechatpay() {
        return shopWechatpay;
    }

    public void setShopWechatpay(String shopWechatpay) {
        this.shopWechatpay = shopWechatpay;
    }
}
