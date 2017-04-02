package com.alienlab.wa17.service;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.entity.client.dto.OrderDto;

/**
 * Created by 橘 on 2017/3/27.
 */
public interface OrderService {
    OrderDto addOrder(int account,long shopId,JSONObject orderinfo) throws Exception;
    boolean validateInventory(int account,long shopId,long skuid,int saleAmount) throws Exception;
}
