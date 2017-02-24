package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbShop;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.entity.main.MainTbAccount;
import com.alienlab.wa17.entity.main.MainTbDatabase;

import java.util.List;

/**
 * Created by juhuiguang on 2017/1/24.
 */
public interface AccountService{
    //根据登录名查找账户信息
    MainTbAccount getAccount(String account_loginname) throws Exception ;



}
