package com.alienlab.wa17.entity.main;

import javax.persistence.*;

/**
 * Created by juhuiguang on 2017/2/1.
 */
@Entity
@Table(name = "tb_market", schema = "17wa_main", catalog = "")
public class MainTbMarket {
    private long mkId;
    private String mkName;
    private String mkType;
    private Integer mkLevel;
    private Long mkPid;
    private Integer mkSort;

    @Id
    @Column(name = "mk_id")
    public long getMkId() {
        return mkId;
    }

    public void setMkId(long mkId) {
        this.mkId = mkId;
    }

    @Basic
    @Column(name = "mk_name")
    public String getMkName() {
        return mkName;
    }

    public void setMkName(String mkName) {
        this.mkName = mkName;
    }

    @Basic
    @Column(name = "mk_type")
    public String getMkType() {
        return mkType;
    }

    public void setMkType(String mkType) {
        this.mkType = mkType;
    }

    @Basic
    @Column(name = "mk_level")
    public Integer getMkLevel() {
        return mkLevel;
    }

    public void setMkLevel(Integer mkLevel) {
        this.mkLevel = mkLevel;
    }

    @Basic
    @Column(name = "mk_pid")
    public Long getMkPid() {
        return mkPid;
    }

    public void setMkPid(Long mkPid) {
        this.mkPid = mkPid;
    }

    @Basic
    @Column(name = "mk_sort")
    public Integer getMkSort() {
        return mkSort;
    }

    public void setMkSort(Integer mkSort) {
        this.mkSort = mkSort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainTbMarket that = (MainTbMarket) o;

        if (mkId != that.mkId) return false;
        if (mkName != null ? !mkName.equals(that.mkName) : that.mkName != null) return false;
        if (mkType != null ? !mkType.equals(that.mkType) : that.mkType != null) return false;
        if (mkLevel != null ? !mkLevel.equals(that.mkLevel) : that.mkLevel != null) return false;
        if (mkPid != null ? !mkPid.equals(that.mkPid) : that.mkPid != null) return false;
        if (mkSort != null ? !mkSort.equals(that.mkSort) : that.mkSort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (mkId ^ (mkId >>> 32));
        result = 31 * result + (mkName != null ? mkName.hashCode() : 0);
        result = 31 * result + (mkType != null ? mkType.hashCode() : 0);
        result = 31 * result + (mkLevel != null ? mkLevel.hashCode() : 0);
        result = 31 * result + (mkPid != null ? mkPid.hashCode() : 0);
        result = 31 * result + (mkSort != null ? mkSort.hashCode() : 0);
        return result;
    }
}
