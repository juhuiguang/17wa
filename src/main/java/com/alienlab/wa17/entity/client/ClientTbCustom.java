package com.alienlab.wa17.entity.client;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_custom", schema = "17wa_client", catalog = "")
public class ClientTbCustom {
    private long customId;
    private String customName;
    private Timestamp customLatestTime;
    private Integer customLatestMoney;
    private Integer customLatestPayment;
    private Integer customTotalMoney;
    private String customLatestPaytype;
    private Integer customRemainMoney;
    private String customPhone;
    private Long customGrade;
    private Date customBirth;
    private String customCard;

    @Id
    @Column(name = "custom_id")
    public long getCustomId() {
        return customId;
    }

    public void setCustomId(long customId) {
        this.customId = customId;
    }

    @Basic
    @Column(name = "custom_name")
    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    @Basic
    @Column(name = "custom_latest_time")
    public Timestamp getCustomLatestTime() {
        return customLatestTime;
    }

    public void setCustomLatestTime(Timestamp customLatestTime) {
        this.customLatestTime = customLatestTime;
    }

    @Basic
    @Column(name = "custom_latest_money")
    public Integer getCustomLatestMoney() {
        return customLatestMoney;
    }

    public void setCustomLatestMoney(Integer customLatestMoney) {
        this.customLatestMoney = customLatestMoney;
    }

    @Basic
    @Column(name = "custom_latest_payment")
    public Integer getCustomLatestPayment() {
        return customLatestPayment;
    }

    public void setCustomLatestPayment(Integer customLatestPayment) {
        this.customLatestPayment = customLatestPayment;
    }

    @Basic
    @Column(name = "custom_total_money")
    public Integer getCustomTotalMoney() {
        return customTotalMoney;
    }

    public void setCustomTotalMoney(Integer customTotalMoney) {
        this.customTotalMoney = customTotalMoney;
    }

    @Basic
    @Column(name = "custom_latest_paytype")
    public String getCustomLatestPaytype() {
        return customLatestPaytype;
    }

    public void setCustomLatestPaytype(String customLatestPaytype) {
        this.customLatestPaytype = customLatestPaytype;
    }

    @Basic
    @Column(name = "custom_remain_money")
    public Integer getCustomRemainMoney() {
        return customRemainMoney;
    }

    public void setCustomRemainMoney(Integer customRemainMoney) {
        this.customRemainMoney = customRemainMoney;
    }

    @Basic
    @Column(name = "custom_phone")
    public String getCustomPhone() {
        return customPhone;
    }

    public void setCustomPhone(String customPhone) {
        this.customPhone = customPhone;
    }

    @Basic
    @Column(name = "custom_grade")
    public Long getCustomGrade() {
        return customGrade;
    }

    public void setCustomGrade(Long customGrade) {
        this.customGrade = customGrade;
    }

    @Basic
    @Column(name = "custom_birth")
    public Date getCustomBirth() {
        return customBirth;
    }

    public void setCustomBirth(Date customBirth) {
        this.customBirth = customBirth;
    }

    @Basic
    @Column(name = "custom_card")
    public String getCustomCard() {
        return customCard;
    }

    public void setCustomCard(String customCard) {
        this.customCard = customCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbCustom that = (ClientTbCustom) o;

        if (customId != that.customId) return false;
        if (customName != null ? !customName.equals(that.customName) : that.customName != null) return false;
        if (customLatestTime != null ? !customLatestTime.equals(that.customLatestTime) : that.customLatestTime != null)
            return false;
        if (customLatestMoney != null ? !customLatestMoney.equals(that.customLatestMoney) : that.customLatestMoney != null)
            return false;
        if (customLatestPayment != null ? !customLatestPayment.equals(that.customLatestPayment) : that.customLatestPayment != null)
            return false;
        if (customTotalMoney != null ? !customTotalMoney.equals(that.customTotalMoney) : that.customTotalMoney != null)
            return false;
        if (customLatestPaytype != null ? !customLatestPaytype.equals(that.customLatestPaytype) : that.customLatestPaytype != null)
            return false;
        if (customRemainMoney != null ? !customRemainMoney.equals(that.customRemainMoney) : that.customRemainMoney != null)
            return false;
        if (customPhone != null ? !customPhone.equals(that.customPhone) : that.customPhone != null) return false;
        if (customGrade != null ? !customGrade.equals(that.customGrade) : that.customGrade != null) return false;
        if (customBirth != null ? !customBirth.equals(that.customBirth) : that.customBirth != null) return false;
        if (customCard != null ? !customCard.equals(that.customCard) : that.customCard != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (customId ^ (customId >>> 32));
        result = 31 * result + (customName != null ? customName.hashCode() : 0);
        result = 31 * result + (customLatestTime != null ? customLatestTime.hashCode() : 0);
        result = 31 * result + (customLatestMoney != null ? customLatestMoney.hashCode() : 0);
        result = 31 * result + (customLatestPayment != null ? customLatestPayment.hashCode() : 0);
        result = 31 * result + (customTotalMoney != null ? customTotalMoney.hashCode() : 0);
        result = 31 * result + (customLatestPaytype != null ? customLatestPaytype.hashCode() : 0);
        result = 31 * result + (customRemainMoney != null ? customRemainMoney.hashCode() : 0);
        result = 31 * result + (customPhone != null ? customPhone.hashCode() : 0);
        result = 31 * result + (customGrade != null ? customGrade.hashCode() : 0);
        result = 31 * result + (customBirth != null ? customBirth.hashCode() : 0);
        result = 31 * result + (customCard != null ? customCard.hashCode() : 0);
        return result;
    }
}
