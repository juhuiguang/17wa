package com.alienlab.wa17.entity.client;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_msg_info", schema = "17wa_client", catalog = "")
public class ClientTbMsgInfo {
    private Long msgId;
    private String msgType;
    private String msgContent;
    private Timestamp msgTime;
    private String msgStatus;

    @Basic
    @Column(name = "msg_id")
    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    @Basic
    @Column(name = "msg_type")
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Basic
    @Column(name = "msg_content")
    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    @Basic
    @Column(name = "msg_time")
    public Timestamp getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Timestamp msgTime) {
        this.msgTime = msgTime;
    }

    @Basic
    @Column(name = "msg_status")
    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbMsgInfo that = (ClientTbMsgInfo) o;

        if (msgId != null ? !msgId.equals(that.msgId) : that.msgId != null) return false;
        if (msgType != null ? !msgType.equals(that.msgType) : that.msgType != null) return false;
        if (msgContent != null ? !msgContent.equals(that.msgContent) : that.msgContent != null) return false;
        if (msgTime != null ? !msgTime.equals(that.msgTime) : that.msgTime != null) return false;
        if (msgStatus != null ? !msgStatus.equals(that.msgStatus) : that.msgStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = msgId != null ? msgId.hashCode() : 0;
        result = 31 * result + (msgType != null ? msgType.hashCode() : 0);
        result = 31 * result + (msgContent != null ? msgContent.hashCode() : 0);
        result = 31 * result + (msgTime != null ? msgTime.hashCode() : 0);
        result = 31 * result + (msgStatus != null ? msgStatus.hashCode() : 0);
        return result;
    }
}
