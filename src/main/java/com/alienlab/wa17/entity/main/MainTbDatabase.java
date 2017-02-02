package com.alienlab.wa17.entity.main;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_database", schema = "17wa_main", catalog = "")
public class MainTbDatabase {
    private long dbId;
    private Timestamp dbTime;
    private Long accountId;
    private String dbMaintype;
    private String dbStatus;
    private String dbIp;
    private String dbType;
    private String dbPort;
    private String dbUser;
    private String dbPwd;
    private String dbSchema;

    @Id
    @Column(name = "db_id")
    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    @Basic
    @Column(name = "db_time")
    public Timestamp getDbTime() {
        return dbTime;
    }

    public void setDbTime(Timestamp dbTime) {
        this.dbTime = dbTime;
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
    @Column(name = "db_maintype")
    public String getDbMaintype() {
        return dbMaintype;
    }

    public void setDbMaintype(String dbMaintype) {
        this.dbMaintype = dbMaintype;
    }

    @Basic
    @Column(name = "db_status")
    public String getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(String dbStatus) {
        this.dbStatus = dbStatus;
    }

    @Basic
    @Column(name = "db_ip")
    public String getDbIp() {
        return dbIp;
    }

    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

    @Basic
    @Column(name = "db_type")
    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Basic
    @Column(name = "db_port")
    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    @Basic
    @Column(name = "db_user")
    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    @Basic
    @Column(name = "db_pwd")
    public String getDbPwd() {
        return dbPwd;
    }

    public void setDbPwd(String dbPwd) {
        this.dbPwd = dbPwd;
    }

    @Basic
    @Column(name = "db_schema")
    public String getDbSchema() {
        return dbSchema;
    }

    public void setDbSchema(String dbSchema) {
        this.dbSchema = dbSchema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainTbDatabase that = (MainTbDatabase) o;

        if (dbId != that.dbId) return false;
        if (dbTime != null ? !dbTime.equals(that.dbTime) : that.dbTime != null) return false;
        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (dbMaintype != null ? !dbMaintype.equals(that.dbMaintype) : that.dbMaintype != null) return false;
        if (dbStatus != null ? !dbStatus.equals(that.dbStatus) : that.dbStatus != null) return false;
        if (dbIp != null ? !dbIp.equals(that.dbIp) : that.dbIp != null) return false;
        if (dbType != null ? !dbType.equals(that.dbType) : that.dbType != null) return false;
        if (dbPort != null ? !dbPort.equals(that.dbPort) : that.dbPort != null) return false;
        if (dbUser != null ? !dbUser.equals(that.dbUser) : that.dbUser != null) return false;
        if (dbPwd != null ? !dbPwd.equals(that.dbPwd) : that.dbPwd != null) return false;
        if (dbSchema != null ? !dbSchema.equals(that.dbSchema) : that.dbSchema != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (dbTime != null ? dbTime.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (dbMaintype != null ? dbMaintype.hashCode() : 0);
        result = 31 * result + (dbStatus != null ? dbStatus.hashCode() : 0);
        result = 31 * result + (dbIp != null ? dbIp.hashCode() : 0);
        result = 31 * result + (dbType != null ? dbType.hashCode() : 0);
        result = 31 * result + (dbPort != null ? dbPort.hashCode() : 0);
        result = 31 * result + (dbUser != null ? dbUser.hashCode() : 0);
        result = 31 * result + (dbPwd != null ? dbPwd.hashCode() : 0);
        result = 31 * result + (dbSchema != null ? dbSchema.hashCode() : 0);
        return result;
    }
}
