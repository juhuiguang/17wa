package com.alienlab.wa17.entity.client;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_database_server", schema = "17wa_main", catalog = "")
public class ClientTbDatabaseServer {
    private Long dbseverId;
    private String dbserverIp;
    private String dbserverType;
    private Integer dbserverPort;
    private String dbserverUser;
    private String dbserverPwd;
    private String dbserverStatus;

    @Basic
    @Column(name = "dbsever_id")
    public Long getDbseverId() {
        return dbseverId;
    }

    public void setDbseverId(Long dbseverId) {
        this.dbseverId = dbseverId;
    }

    @Basic
    @Column(name = "dbserver_IP")
    public String getDbserverIp() {
        return dbserverIp;
    }

    public void setDbserverIp(String dbserverIp) {
        this.dbserverIp = dbserverIp;
    }

    @Basic
    @Column(name = "dbserver_type")
    public String getDbserverType() {
        return dbserverType;
    }

    public void setDbserverType(String dbserverType) {
        this.dbserverType = dbserverType;
    }

    @Basic
    @Column(name = "dbserver_port")
    public Integer getDbserverPort() {
        return dbserverPort;
    }

    public void setDbserverPort(Integer dbserverPort) {
        this.dbserverPort = dbserverPort;
    }

    @Basic
    @Column(name = "dbserver_user")
    public String getDbserverUser() {
        return dbserverUser;
    }

    public void setDbserverUser(String dbserverUser) {
        this.dbserverUser = dbserverUser;
    }

    @Basic
    @Column(name = "dbserver_pwd")
    public String getDbserverPwd() {
        return dbserverPwd;
    }

    public void setDbserverPwd(String dbserverPwd) {
        this.dbserverPwd = dbserverPwd;
    }

    @Basic
    @Column(name = "dbserver_status")
    public String getDbserverStatus() {
        return dbserverStatus;
    }

    public void setDbserverStatus(String dbserverStatus) {
        this.dbserverStatus = dbserverStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbDatabaseServer that = (ClientTbDatabaseServer) o;

        if (dbseverId != null ? !dbseverId.equals(that.dbseverId) : that.dbseverId != null) return false;
        if (dbserverIp != null ? !dbserverIp.equals(that.dbserverIp) : that.dbserverIp != null) return false;
        if (dbserverType != null ? !dbserverType.equals(that.dbserverType) : that.dbserverType != null) return false;
        if (dbserverPort != null ? !dbserverPort.equals(that.dbserverPort) : that.dbserverPort != null) return false;
        if (dbserverUser != null ? !dbserverUser.equals(that.dbserverUser) : that.dbserverUser != null) return false;
        if (dbserverPwd != null ? !dbserverPwd.equals(that.dbserverPwd) : that.dbserverPwd != null) return false;
        if (dbserverStatus != null ? !dbserverStatus.equals(that.dbserverStatus) : that.dbserverStatus != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dbseverId != null ? dbseverId.hashCode() : 0;
        result = 31 * result + (dbserverIp != null ? dbserverIp.hashCode() : 0);
        result = 31 * result + (dbserverType != null ? dbserverType.hashCode() : 0);
        result = 31 * result + (dbserverPort != null ? dbserverPort.hashCode() : 0);
        result = 31 * result + (dbserverUser != null ? dbserverUser.hashCode() : 0);
        result = 31 * result + (dbserverPwd != null ? dbserverPwd.hashCode() : 0);
        result = 31 * result + (dbserverStatus != null ? dbserverStatus.hashCode() : 0);
        return result;
    }
}
