package com.alienlab.wa17.entity.client;

import javax.persistence.*;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_size", schema = "17wa_main", catalog = "")
public class ClientTbSize {
    private long sizeId;
    private String sizeName;
    private String sizeTypeName;
    private Long sizeTypeId;
    private String sizeStatus;

    @Id
    @Column(name = "size_id")
    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }

    @Basic
    @Column(name = "size_name")
    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    @Basic
    @Column(name = "size_type_name")
    public String getSizeTypeName() {
        return sizeTypeName;
    }

    public void setSizeTypeName(String sizeTypeName) {
        this.sizeTypeName = sizeTypeName;
    }

    @Basic
    @Column(name = "size_type_id")
    public Long getSizeTypeId() {
        return sizeTypeId;
    }

    public void setSizeTypeId(Long sizeTypeId) {
        this.sizeTypeId = sizeTypeId;
    }

    @Basic
    @Column(name = "size_status")
    public String getSizeStatus() {
        return sizeStatus;
    }

    public void setSizeStatus(String sizeStatus) {
        this.sizeStatus = sizeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbSize that = (ClientTbSize) o;

        if (sizeId != that.sizeId) return false;
        if (sizeName != null ? !sizeName.equals(that.sizeName) : that.sizeName != null) return false;
        if (sizeTypeName != null ? !sizeTypeName.equals(that.sizeTypeName) : that.sizeTypeName != null) return false;
        if (sizeTypeId != null ? !sizeTypeId.equals(that.sizeTypeId) : that.sizeTypeId != null) return false;
        if (sizeStatus != null ? !sizeStatus.equals(that.sizeStatus) : that.sizeStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (sizeId ^ (sizeId >>> 32));
        result = 31 * result + (sizeName != null ? sizeName.hashCode() : 0);
        result = 31 * result + (sizeTypeName != null ? sizeTypeName.hashCode() : 0);
        result = 31 * result + (sizeTypeId != null ? sizeTypeId.hashCode() : 0);
        result = 31 * result + (sizeStatus != null ? sizeStatus.hashCode() : 0);
        return result;
    }
}
