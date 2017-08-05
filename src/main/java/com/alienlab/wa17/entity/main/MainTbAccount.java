package com.alienlab.wa17.entity.main;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_account", schema = "17wa_main", catalog = "")
public class MainTbAccount {
    @ApiModelProperty(value="账户id")
    private long accountId;
    @ApiModelProperty(value="账户编码")
    private String accountCode;
    @ApiModelProperty(value="账户关联手机号码")
    private String accountPhone;
    @ApiModelProperty(value="账户创建时间")
    private Timestamp accountTime;
    @ApiModelProperty(value="账户状态")
    private String accountStatus;

    @Basic
    @Column(name = "account_loginname")
    public String getAccountLoginname() {
        return accountLoginname;
    }

    public void setAccountLoginname(String accountLoginname) {
        this.accountLoginname = accountLoginname;
    }

    @ApiModelProperty(value="登录名=手机号码")
    private String accountLoginname;


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
    @Column(name = "account_status")
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }


}
