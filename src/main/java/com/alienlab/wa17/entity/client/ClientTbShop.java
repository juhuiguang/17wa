package com.alienlab.wa17.entity.client;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_shop", schema = "17wa_client", catalog = "")
public class ClientTbShop {
    private long shopId;
    private String shopIsdefault;
    private String shopName;
    private String shopAddr;
    private String shopPhone1;
    private String shopPhone2;
    private String shopWechat;
    private String shopQq;
    private String shopAlipayName;
    private String shopAlipay;
    private Timestamp shopTime;
    private Long marketId;
    private String shopTags;

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
    @Column(name = "market_id")
    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    @Basic
    @Column(name = "shop_tags")
    public String getShopTags() {
        return shopTags;
    }

    public void setShopTags(String shopTags) {
        this.shopTags = shopTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbShop that = (ClientTbShop) o;

        if (shopId != that.shopId) return false;
        if (shopIsdefault != null ? !shopIsdefault.equals(that.shopIsdefault) : that.shopIsdefault != null)
            return false;
        if (shopName != null ? !shopName.equals(that.shopName) : that.shopName != null) return false;
        if (shopAddr != null ? !shopAddr.equals(that.shopAddr) : that.shopAddr != null) return false;
        if (shopPhone1 != null ? !shopPhone1.equals(that.shopPhone1) : that.shopPhone1 != null) return false;
        if (shopPhone2 != null ? !shopPhone2.equals(that.shopPhone2) : that.shopPhone2 != null) return false;
        if (shopWechat != null ? !shopWechat.equals(that.shopWechat) : that.shopWechat != null) return false;
        if (shopQq != null ? !shopQq.equals(that.shopQq) : that.shopQq != null) return false;
        if (shopAlipayName != null ? !shopAlipayName.equals(that.shopAlipayName) : that.shopAlipayName != null)
            return false;
        if (shopAlipay != null ? !shopAlipay.equals(that.shopAlipay) : that.shopAlipay != null) return false;
        if (shopTime != null ? !shopTime.equals(that.shopTime) : that.shopTime != null) return false;
        if (marketId != null ? !marketId.equals(that.marketId) : that.marketId != null) return false;
        if (shopTags != null ? !shopTags.equals(that.shopTags) : that.shopTags != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (shopId ^ (shopId >>> 32));
        result = 31 * result + (shopIsdefault != null ? shopIsdefault.hashCode() : 0);
        result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
        result = 31 * result + (shopAddr != null ? shopAddr.hashCode() : 0);
        result = 31 * result + (shopPhone1 != null ? shopPhone1.hashCode() : 0);
        result = 31 * result + (shopPhone2 != null ? shopPhone2.hashCode() : 0);
        result = 31 * result + (shopWechat != null ? shopWechat.hashCode() : 0);
        result = 31 * result + (shopQq != null ? shopQq.hashCode() : 0);
        result = 31 * result + (shopAlipayName != null ? shopAlipayName.hashCode() : 0);
        result = 31 * result + (shopAlipay != null ? shopAlipay.hashCode() : 0);
        result = 31 * result + (shopTime != null ? shopTime.hashCode() : 0);
        result = 31 * result + (marketId != null ? marketId.hashCode() : 0);
        result = 31 * result + (shopTags != null ? shopTags.hashCode() : 0);
        return result;
    }
}
