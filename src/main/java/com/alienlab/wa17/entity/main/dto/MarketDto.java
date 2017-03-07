package com.alienlab.wa17.entity.main.dto;

import com.alienlab.wa17.entity.main.MainTbMarket;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘 on 2017/2/4.
 */
public class MarketDto extends MainTbMarket{
    @ApiModelProperty(value="子节点集合")
    List<MarketDto> subMarkets=new ArrayList<>();



    public List<MarketDto> getSubMarkets() {
        return subMarkets;
    }

    public void setSubMarkets(List<MarketDto> subMarkets) {
        this.subMarkets = subMarkets;
    }
}
