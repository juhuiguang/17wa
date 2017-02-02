package com.alienlab.wa17.entity.main;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_imageserver", schema = "17wa_main", catalog = "")
public class MainTbImageserver {
    private long serverId;
    private String serverIp;
    private String serverPort;
    private String serverUser;
    private String serverPwd;
    private String serverUrl;
    private Timestamp serverTime;
    private String serverStatus;

    @Id
    @Column(name = "server_id")
    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    @Basic
    @Column(name = "server_ip")
    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    @Basic
    @Column(name = "server_port")
    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    @Basic
    @Column(name = "server_user")
    public String getServerUser() {
        return serverUser;
    }

    public void setServerUser(String serverUser) {
        this.serverUser = serverUser;
    }

    @Basic
    @Column(name = "server_pwd")
    public String getServerPwd() {
        return serverPwd;
    }

    public void setServerPwd(String serverPwd) {
        this.serverPwd = serverPwd;
    }

    @Basic
    @Column(name = "server_url")
    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Basic
    @Column(name = "server_time")
    public Timestamp getServerTime() {
        return serverTime;
    }

    public void setServerTime(Timestamp serverTime) {
        this.serverTime = serverTime;
    }

    @Basic
    @Column(name = "server_status")
    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainTbImageserver that = (MainTbImageserver) o;

        if (serverId != that.serverId) return false;
        if (serverIp != null ? !serverIp.equals(that.serverIp) : that.serverIp != null) return false;
        if (serverPort != null ? !serverPort.equals(that.serverPort) : that.serverPort != null) return false;
        if (serverUser != null ? !serverUser.equals(that.serverUser) : that.serverUser != null) return false;
        if (serverPwd != null ? !serverPwd.equals(that.serverPwd) : that.serverPwd != null) return false;
        if (serverUrl != null ? !serverUrl.equals(that.serverUrl) : that.serverUrl != null) return false;
        if (serverTime != null ? !serverTime.equals(that.serverTime) : that.serverTime != null) return false;
        if (serverStatus != null ? !serverStatus.equals(that.serverStatus) : that.serverStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (serverId ^ (serverId >>> 32));
        result = 31 * result + (serverIp != null ? serverIp.hashCode() : 0);
        result = 31 * result + (serverPort != null ? serverPort.hashCode() : 0);
        result = 31 * result + (serverUser != null ? serverUser.hashCode() : 0);
        result = 31 * result + (serverPwd != null ? serverPwd.hashCode() : 0);
        result = 31 * result + (serverUrl != null ? serverUrl.hashCode() : 0);
        result = 31 * result + (serverTime != null ? serverTime.hashCode() : 0);
        result = 31 * result + (serverStatus != null ? serverStatus.hashCode() : 0);
        return result;
    }
}
