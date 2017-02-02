package com.alienlab.wa17.entity.main;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_account", schema = "17wa_main", catalog = "")
public class MainTbAccount {
    private long accountId;
    private String accountCode;
    private String accountPhone;
    private Timestamp accountTime;
    private String accountType;
    private String accountStatus;
    private String accountLoginname;
    private String accountPwd;

    @Id
    @Column(name = "account_id")
    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "account_code")
    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    @Basic
    @Column(name = "account_phone")
    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    @Basic
    @Column(name = "account_time")
    public Timestamp getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(Timestamp accountTime) {
        this.accountTime = accountTime;
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
    @Column(name = "account_status")
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Basic
    @Column(name = "account_loginname")
    public String getAccountLoginname() {
        return accountLoginname;
    }

    public void setAccountLoginname(String accountLoginname) {
        this.accountLoginname = accountLoginname;
    }

    @Basic
    @Column(name = "account_pwd")
    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainTbAccount that = (MainTbAccount) o;

        if (accountId != that.accountId) return false;
        if (accountCode != null ? !accountCode.equals(that.accountCode) : that.accountCode != null) return false;
        if (accountPhone != null ? !accountPhone.equals(that.accountPhone) : that.accountPhone != null) return false;
        if (accountTime != null ? !accountTime.equals(that.accountTime) : that.accountTime != null) return false;
        if (accountType != null ? !accountType.equals(that.accountType) : that.accountType != null) return false;
        if (accountStatus != null ? !accountStatus.equals(that.accountStatus) : that.accountStatus != null)
            return false;
        if (accountLoginname != null ? !accountLoginname.equals(that.accountLoginname) : that.accountLoginname != null)
            return false;
        if (accountPwd != null ? !accountPwd.equals(that.accountPwd) : that.accountPwd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (accountCode != null ? accountCode.hashCode() : 0);
        result = 31 * result + (accountPhone != null ? accountPhone.hashCode() : 0);
        result = 31 * result + (accountTime != null ? accountTime.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (accountStatus != null ? accountStatus.hashCode() : 0);
        result = 31 * result + (accountLoginname != null ? accountLoginname.hashCode() : 0);
        result = 31 * result + (accountPwd != null ? accountPwd.hashCode() : 0);
        return result;
    }
}
