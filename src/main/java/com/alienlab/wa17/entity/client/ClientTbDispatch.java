package com.alienlab.wa17.entity.client;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_dispatch", schema = "17wa_client", catalog = "")
public class ClientTbDispatch {
    private Long dispatchId;
    private Timestamp dispatchTime1;
    private Integer dispatchAmount;
    private Long skuId;
    private Long dispatchFromShop;
    private Long dispatchToShop;
    private String dispatchFromIsok;
    private String dispatchToIsok;
    private Timestamp dispatchFromOktime;
    private Timestamp dispatchToOktime;
    private String dispatchIsfinished;

    @Id
    @Column(name = "dispatch_id")
    public Long getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }

    @Basic
    @Column(name = "dispatch_time1")
    public Timestamp getDispatchTime1() {
        return dispatchTime1;
    }

    public void setDispatchTime1(Timestamp dispatchTime1) {
        this.dispatchTime1 = dispatchTime1;
    }

    @Basic
    @Column(name = "dispatch_amount")
    public Integer getDispatchAmount() {
        return dispatchAmount;
    }

    public void setDispatchAmount(Integer dispatchAmount) {
        this.dispatchAmount = dispatchAmount;
    }

    @Basic
    @Column(name = "sku_id")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Basic
    @Column(name = "dispatch_from_shop")
    public Long getDispatchFromShop() {
        return dispatchFromShop;
    }

    public void setDispatchFromShop(Long dispatchFromShop) {
        this.dispatchFromShop = dispatchFromShop;
    }

    @Basic
    @Column(name = "dispatch_to_shop")
    public Long getDispatchToShop() {
        return dispatchToShop;
    }

    public void setDispatchToShop(Long dispatchToShop) {
        this.dispatchToShop = dispatchToShop;
    }

    @Basic
    @Column(name = "dispatch_from_isok")
    public String getDispatchFromIsok() {
        return dispatchFromIsok;
    }

    public void setDispatchFromIsok(String dispatchFromIsok) {
        this.dispatchFromIsok = dispatchFromIsok;
    }

    @Basic
    @Column(name = "dispatch_to_isok")
    public String getDispatchToIsok() {
        return dispatchToIsok;
    }

    public void setDispatchToIsok(String dispatchToIsok) {
        this.dispatchToIsok = dispatchToIsok;
    }

    @Basic
    @Column(name = "dispatch_from_oktime")
    public Timestamp getDispatchFromOktime() {
        return dispatchFromOktime;
    }

    public void setDispatchFromOktime(Timestamp dispatchFromOktime) {
        this.dispatchFromOktime = dispatchFromOktime;
    }

    @Basic
    @Column(name = "dispatch_to_oktime")
    public Timestamp getDispatchToOktime() {
        return dispatchToOktime;
    }

    public void setDispatchToOktime(Timestamp dispatchToOktime) {
        this.dispatchToOktime = dispatchToOktime;
    }

    @Basic
    @Column(name = "dispatch_isfinished")
    public String getDispatchIsfinished() {
        return dispatchIsfinished;
    }

    public void setDispatchIsfinished(String dispatchIsfinished) {
        this.dispatchIsfinished = dispatchIsfinished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbDispatch that = (ClientTbDispatch) o;

        if (dispatchId != null ? !dispatchId.equals(that.dispatchId) : that.dispatchId != null) return false;
        if (dispatchTime1 != null ? !dispatchTime1.equals(that.dispatchTime1) : that.dispatchTime1 != null)
            return false;
        if (dispatchAmount != null ? !dispatchAmount.equals(that.dispatchAmount) : that.dispatchAmount != null)
            return false;
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) return false;
        if (dispatchFromShop != null ? !dispatchFromShop.equals(that.dispatchFromShop) : that.dispatchFromShop != null)
            return false;
        if (dispatchToShop != null ? !dispatchToShop.equals(that.dispatchToShop) : that.dispatchToShop != null)
            return false;
        if (dispatchFromIsok != null ? !dispatchFromIsok.equals(that.dispatchFromIsok) : that.dispatchFromIsok != null)
            return false;
        if (dispatchToIsok != null ? !dispatchToIsok.equals(that.dispatchToIsok) : that.dispatchToIsok != null)
            return false;
        if (dispatchFromOktime != null ? !dispatchFromOktime.equals(that.dispatchFromOktime) : that.dispatchFromOktime != null)
            return false;
        if (dispatchToOktime != null ? !dispatchToOktime.equals(that.dispatchToOktime) : that.dispatchToOktime != null)
            return false;
        if (dispatchIsfinished != null ? !dispatchIsfinished.equals(that.dispatchIsfinished) : that.dispatchIsfinished != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dispatchId != null ? dispatchId.hashCode() : 0;
        result = 31 * result + (dispatchTime1 != null ? dispatchTime1.hashCode() : 0);
        result = 31 * result + (dispatchAmount != null ? dispatchAmount.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (dispatchFromShop != null ? dispatchFromShop.hashCode() : 0);
        result = 31 * result + (dispatchToShop != null ? dispatchToShop.hashCode() : 0);
        result = 31 * result + (dispatchFromIsok != null ? dispatchFromIsok.hashCode() : 0);
        result = 31 * result + (dispatchToIsok != null ? dispatchToIsok.hashCode() : 0);
        result = 31 * result + (dispatchFromOktime != null ? dispatchFromOktime.hashCode() : 0);
        result = 31 * result + (dispatchToOktime != null ? dispatchToOktime.hashCode() : 0);
        result = 31 * result + (dispatchIsfinished != null ? dispatchIsfinished.hashCode() : 0);
        return result;
    }
}
