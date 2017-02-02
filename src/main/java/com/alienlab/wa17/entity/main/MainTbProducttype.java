package com.alienlab.wa17.entity.main;

import javax.persistence.*;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_producttype", schema = "17wa_main", catalog = "")
public class MainTbProducttype {
    private long producttypeId;
    private String producttypeName;
    private Integer producttypeLevel;
    private Long producttypePid;

    @Id
    @Column(name = "producttype_id")
    public long getProducttypeId() {
        return producttypeId;
    }

    public void setProducttypeId(long producttypeId) {
        this.producttypeId = producttypeId;
    }

    @Basic
    @Column(name = "producttype_name")
    public String getProducttypeName() {
        return producttypeName;
    }

    public void setProducttypeName(String producttypeName) {
        this.producttypeName = producttypeName;
    }

    @Basic
    @Column(name = "producttype_level")
    public Integer getProducttypeLevel() {
        return producttypeLevel;
    }

    public void setProducttypeLevel(Integer producttypeLevel) {
        this.producttypeLevel = producttypeLevel;
    }

    @Basic
    @Column(name = "producttype_pid")
    public Long getProducttypePid() {
        return producttypePid;
    }

    public void setProducttypePid(Long producttypePid) {
        this.producttypePid = producttypePid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainTbProducttype that = (MainTbProducttype) o;

        if (producttypeId != that.producttypeId) return false;
        if (producttypeName != null ? !producttypeName.equals(that.producttypeName) : that.producttypeName != null)
            return false;
        if (producttypeLevel != null ? !producttypeLevel.equals(that.producttypeLevel) : that.producttypeLevel != null)
            return false;
        if (producttypePid != null ? !producttypePid.equals(that.producttypePid) : that.producttypePid != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (producttypeId ^ (producttypeId >>> 32));
        result = 31 * result + (producttypeName != null ? producttypeName.hashCode() : 0);
        result = 31 * result + (producttypeLevel != null ? producttypeLevel.hashCode() : 0);
        result = 31 * result + (producttypePid != null ? producttypePid.hashCode() : 0);
        return result;
    }
}
