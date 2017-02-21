package com.alienlab.wa17.entity.client;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_shop_account", schema = "17wa_client", catalog = "")
public class ClientTbShopAccount {
    private long accountId;
    private Long shopId;
    private String accountType;
    private String accountName;
    private String accountPwd;
    private String accountStatus;
    private Timestamp accountTime;

    @Id
    @Column(name = "account_id")
    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
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
    @Column(name = "account_type")
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Basic
    @Column(name = "account_name")
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Basic
    @Column(name = "account_pwd")
    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    @Basic
    @Column(name = "account_status")
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Basic
    @Column(name = "account_time")
    public Timestamp getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(Timestamp accountTime) {
        this.accountTime = accountTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbShopAccount that = (ClientTbShopAccount) o;

        if (accountId != that.accountId) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (accountType != null ? !accountType.equals(that.accountType) : that.accountType != null) return false;
        if (accountName != null ? !accountName.equals(that.accountName) : that.accountName != null) return false;
        if (accountPwd != null ? !accountPwd.equals(that.accountPwd) : that.accountPwd != null) return false;
        if (accountStatus != null ? !accountStatus.equals(that.accountStatus) : that.accountStatus != null)
            return false;
        if (accountTime != null ? !accountTime.equals(that.accountTime) : that.accountTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (accountName != null ? accountName.hashCode() : 0);
        result = 31 * result + (accountPwd != null ? accountPwd.hashCode() : 0);
        result = 31 * result + (accountStatus != null ? accountStatus.hashCode() : 0);
        result = 31 * result + (accountTime != null ? accountTime.hashCode() : 0);
        return result;
    }
}
