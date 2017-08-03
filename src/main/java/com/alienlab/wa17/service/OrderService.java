package com.alienlab.wa17.service;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.entity.client.ClientTbOrder;
import com.alienlab.wa17.entity.client.dto.OrderDto;
import com.alienlab.wa17.entity.client.dto.OrderPrintDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by 橘 on 2017/3/27.
 */
public interface OrderService {
    OrderPrintDto addOrder(int account,long shopId,JSONObject orderinfo) throws Exception;
    boolean validateInventory(int account,long shopId,long skuid,int saleAmount) throws Exception;
    OrderPrintDto doPrint(int account, Long shopId, OrderDto order, ClientTbCustom custom) throws Exception;
    OrderPrintDto doPrint(int account,Long orderId) throws Exception;

    Page<ClientTbOrder> getOrders(int account,Long shopId, String startdate, String enddate, Pageable page) throws Exception;
    Page<ClientTbOrder> getCustomOrders(int account,Long shopId,int custom,Pageable page) throws Exception;

    ClientTbOrder turnbackOrder(int account,String orderno,Long skuid,int amount) throws Exception;
}
