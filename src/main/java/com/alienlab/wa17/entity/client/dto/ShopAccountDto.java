package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.client.ClientTbShop;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * Created by juhuiguang on 2017/3/9.
 */
public class ShopAccountDto  {
    @ApiModelProperty(value="店铺账户编码")
    private long accountId;
    @ApiModelProperty(value="店铺编码")
    private Long shopId;
    @ApiModelProperty(value="账户类型")
    private String accountType;
    @ApiModelProperty(value="账户名称")
    private String accountName;
    @ApiModelProperty(value="账户密码")
    private String accountPwd;
    @ApiModelProperty(value="账户状态，正常，停用")
    private String accountStatus;
    private Timestamp accountTime;
    @ApiModelProperty(value="店铺名称")
    private String shopName;

    @Column(name = "shop_name")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    @Column(name = "account_id")
    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Column(name = "shop_id")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "account_type")
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Column(name = "account_name")
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    @Column(name = "account_pwd")
    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }
    @Column(name = "account_status")
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
    @Column(name = "account_time")
    public Timestamp getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(Timestamp accountTime) {
        this.accountTime = accountTime;
    }
}
