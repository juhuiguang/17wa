package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.entity.client.ClientTbOrder;
import com.alienlab.wa17.entity.client.ClientTbOrderDetail;
import com.alienlab.wa17.entity.client.ClientTbShop;

import java.util.List;

/**
 * Created by 橘 on 2017/4/22.
 */
public class OrderPrintDto {
    private ClientTbOrder order;
    private List<ClientTbOrderDetail> details;
    private ClientTbCustom custom;
    private ClientTbShop shop;

    public ClientTbOrder getOrder() {
        return order;
    }

    public void setOrder(ClientTbOrder order) {
        this.order = order;
    }

    public List<ClientTbOrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ClientTbOrderDetail> details) {
        this.details = details;
    }

    public ClientTbCustom getCustom() {
        return custom;
    }

    public void setCustom(ClientTbCustom custom) {
        this.custom = custom;
    }

    public ClientTbShop getShop() {
        return shop;
    }

    public void setShop(ClientTbShop shop) {
        this.shop = shop;
    }
}
