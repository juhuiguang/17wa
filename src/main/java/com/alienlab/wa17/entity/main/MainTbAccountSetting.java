package com.alienlab.wa17.entity.main;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_account_settings", schema = "17wa_main", catalog = "")
public class MainTbAccountSetting {
    @Id
    @Column(name="id")
    private long id;
    @Column(name="account_id")
    private long accountId;
    @Column(name="settings")
    private String setting;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }
}
