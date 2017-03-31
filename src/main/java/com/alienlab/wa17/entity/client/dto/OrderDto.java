package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.client.ClientTbOrder;
import com.alienlab.wa17.entity.client.ClientTbOrderDetail;

import java.util.List;

/**
 * Created by 橘 on 2017/3/31.
 */
public class OrderDto extends ClientTbOrder {
    List<ClientTbOrderDetail> details;

    public List<ClientTbOrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ClientTbOrderDetail> details) {
        this.details = details;
    }
}
