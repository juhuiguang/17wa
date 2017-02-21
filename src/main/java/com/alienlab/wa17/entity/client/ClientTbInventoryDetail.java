package com.alienlab.wa17.entity.client;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_inventory_detail", schema = "17wa_client", catalog = "")
public class ClientTbInventoryDetail {
    private long detailId;
    private Long inventoryId;
    private Integer detailAmount;
    private Timestamp detailTime;
    private String detailType;

    @Id
    @Column(name = "detail_id")
    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    @Basic
    @Column(name = "inventory_id")
    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Basic
    @Column(name = "detail_amount")
    public Integer getDetailAmount() {
        return detailAmount;
    }

    public void setDetailAmount(Integer detailAmount) {
        this.detailAmount = detailAmount;
    }

    @Basic
    @Column(name = "detail_time")
    public Timestamp getDetailTime() {
        return detailTime;
    }

    public void setDetailTime(Timestamp detailTime) {
        this.detailTime = detailTime;
    }

    @Basic
    @Column(name = "detail_type")
    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbInventoryDetail that = (ClientTbInventoryDetail) o;

        if (detailId != that.detailId) return false;
        if (inventoryId != null ? !inventoryId.equals(that.inventoryId) : that.inventoryId != null) return false;
        if (detailAmount != null ? !detailAmount.equals(that.detailAmount) : that.detailAmount != null) return false;
        if (detailTime != null ? !detailTime.equals(that.detailTime) : that.detailTime != null) return false;
        if (detailType != null ? !detailType.equals(that.detailType) : that.detailType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (detailId ^ (detailId >>> 32));
        result = 31 * result + (inventoryId != null ? inventoryId.hashCode() : 0);
        result = 31 * result + (detailAmount != null ? detailAmount.hashCode() : 0);
        result = 31 * result + (detailTime != null ? detailTime.hashCode() : 0);
        result = 31 * result + (detailType != null ? detailType.hashCode() : 0);
        return result;
    }
}
