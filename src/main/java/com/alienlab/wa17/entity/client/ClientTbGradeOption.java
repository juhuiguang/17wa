package com.alienlab.wa17.entity.client;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by 橘 on 2017/6/19.
 */
@Entity
@Table(name = "tb_grade_option", schema = "17wa_client", catalog = "")
public class ClientTbGradeOption {
    private Long id;
    private int gradeValue;
    private int gradeMoney;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "grade_value")
    @ApiModelProperty("积分分值")
    public int getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(int gradeValue) {
        this.gradeValue = gradeValue;
    }

    @Column(name = "grade_money")
    @ApiModelProperty("积分抵扣金额")
    public int getGradeMoney() {
        return gradeMoney;
    }

    public void setGradeMoney(int gradeMoney) {
        this.gradeMoney = gradeMoney;
    }
}
