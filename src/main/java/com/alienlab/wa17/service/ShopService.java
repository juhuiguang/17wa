package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbShop;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;

import java.util.List;

/**
 * Created by juhuiguang on 2017/2/24.
 */
public interface ShopService {
    //获取当前用户的门店列表
    List<ClientTbShop> getShopes(int account_id) throws Exception;

    //获得当前连接库中所选门店账户信息
    List<ClientTbShopAccount> getShopAccount(int account_id, int shop_id) throws Exception;
}
