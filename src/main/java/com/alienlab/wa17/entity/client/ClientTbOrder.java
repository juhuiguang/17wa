package com.alienlab.wa17.entity.client;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 橘 on 2017/2/21.
 */
@Entity
@Table(name = "tb_order", schema = "17wa_client", catalog = "")
public class ClientTbOrder {
    private long orderId;
    private String orderCode;
    private Timestamp orderTime;
    private Long cusId;
    private String cusName;
    private Integer cusRemain;
    private Long accountId;
    private Float orderMoney;
    private Float orderPayment;
    private Float orderAmount;
    private String orderPaytype;
    private Float orderOdd;
    private Float orderRecharge;
    private Integer orderGradeIn;
    private Integer orderGradeOut;
    private String orderMemo;
    private Integer orderSort;

    @Id
    @Column(name = "order_id")
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "order_code")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Basic
    @Column(name = "order_time")
    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Basic
    @Column(name = "cus_id")
    public Long getCusId() {
        return cusId;
    }

    public void setCusId(Long cusId) {
        this.cusId = cusId;
    }

    @Basic
    @Column(name = "cus_name")
    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    @Basic
    @Column(name = "cus_remain")
    public Integer getCusRemain() {
        return cusRemain;
    }

    public void setCusRemain(Integer cusRemain) {
        this.cusRemain = cusRemain;
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
    @Column(name = "order_money")
    public Float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Float orderMoney) {
        this.orderMoney = orderMoney;
    }

    @Basic
    @Column(name = "order_payment")
    public Float getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(Float orderPayment) {
        this.orderPayment = orderPayment;
    }

    @Basic
    @Column(name = "order_amount")
    public Float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Float orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Basic
    @Column(name = "order_paytype")
    public String getOrderPaytype() {
        return orderPaytype;
    }

    public void setOrderPaytype(String orderPaytype) {
        this.orderPaytype = orderPaytype;
    }

    @Basic
    @Column(name = "order_odd")
    public Float getOrderOdd() {
        return orderOdd;
    }

    public void setOrderOdd(Float orderOdd) {
        this.orderOdd = orderOdd;
    }

    @Basic
    @Column(name = "order_recharge")
    public Float getOrderRecharge() {
        return orderRecharge;
    }

    public void setOrderRecharge(Float orderRecharge) {
        this.orderRecharge = orderRecharge;
    }

    @Basic
    @Column(name = "order_grade_in")
    public Integer getOrderGradeIn() {
        return orderGradeIn;
    }

    public void setOrderGradeIn(Integer orderGradeIn) {
        this.orderGradeIn = orderGradeIn;
    }

    @Basic
    @Column(name = "order_grade_out")
    public Integer getOrderGradeOut() {
        return orderGradeOut;
    }

    public void setOrderGradeOut(Integer orderGradeOut) {
        this.orderGradeOut = orderGradeOut;
    }

    @Basic
    @Column(name = "order_memo")
    public String getOrderMemo() {
        return orderMemo;
    }

    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    @Basic
    @Column(name = "order_sort")
    public Integer getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(Integer orderSort) {
        this.orderSort = orderSort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTbOrder that = (ClientTbOrder) o;

        if (orderId != that.orderId) return false;
        if (orderCode != null ? !orderCode.equals(that.orderCode) : that.orderCode != null) return false;
        if (orderTime != null ? !orderTime.equals(that.orderTime) : that.orderTime != null) return false;
        if (cusId != null ? !cusId.equals(that.cusId) : that.cusId != null) return false;
        if (cusName != null ? !cusName.equals(that.cusName) : that.cusName != null) return false;
        if (cusRemain != null ? !cusRemain.equals(that.cusRemain) : that.cusRemain != null) return false;
        if (accountId != null ? !accountId.equals(that.accountId) : that.accountId != null) return false;
        if (orderMoney != null ? !orderMoney.equals(that.orderMoney) : that.orderMoney != null) return false;
        if (orderPayment != null ? !orderPayment.equals(that.orderPayment) : that.orderPayment != null) return false;
        if (orderAmount != null ? !orderAmount.equals(that.orderAmount) : that.orderAmount != null) return false;
        if (orderPaytype != null ? !orderPaytype.equals(that.orderPaytype) : that.orderPaytype != null) return false;
        if (orderOdd != null ? !orderOdd.equals(that.orderOdd) : that.orderOdd != null) return false;
        if (orderRecharge != null ? !orderRecharge.equals(that.orderRecharge) : that.orderRecharge != null)
            return false;
        if (orderGradeIn != null ? !orderGradeIn.equals(that.orderGradeIn) : that.orderGradeIn != null) return false;
        if (orderGradeOut != null ? !orderGradeOut.equals(that.orderGradeOut) : that.orderGradeOut != null)
            return false;
        if (orderMemo != null ? !orderMemo.equals(that.orderMemo) : that.orderMemo != null) return false;
        if (orderSort != null ? !orderSort.equals(that.orderSort) : that.orderSort != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + (orderCode != null ? orderCode.hashCode() : 0);
        result = 31 * result + (orderTime != null ? orderTime.hashCode() : 0);
        result = 31 * result + (cusId != null ? cusId.hashCode() : 0);
        result = 31 * result + (cusName != null ? cusName.hashCode() : 0);
        result = 31 * result + (cusRemain != null ? cusRemain.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (orderMoney != null ? orderMoney.hashCode() : 0);
        result = 31 * result + (orderPayment != null ? orderPayment.hashCode() : 0);
        result = 31 * result + (orderAmount != null ? orderAmount.hashCode() : 0);
        result = 31 * result + (orderPaytype != null ? orderPaytype.hashCode() : 0);
        result = 31 * result + (orderOdd != null ? orderOdd.hashCode() : 0);
        result = 31 * result + (orderRecharge != null ? orderRecharge.hashCode() : 0);
        result = 31 * result + (orderGradeIn != null ? orderGradeIn.hashCode() : 0);
        result = 31 * result + (orderGradeOut != null ? orderGradeOut.hashCode() : 0);
        result = 31 * result + (orderMemo != null ? orderMemo.hashCode() : 0);
        result = 31 * result + (orderSort != null ? orderSort.hashCode() : 0);
        return result;
    }
}
