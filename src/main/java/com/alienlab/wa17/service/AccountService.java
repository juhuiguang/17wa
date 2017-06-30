package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbShop;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.entity.main.MainTbAccount;
import com.alienlab.wa17.entity.main.MainTbAccountSetting;
import com.alienlab.wa17.entity.main.MainTbDatabase;

import java.util.List;

/**
 * Created by juhuiguang on 2017/1/24.
 */
public interface AccountService{
    //根据登录名查找账户信息
    MainTbAccount getAccount(String account_loginname) throws Exception ;
    ClientTbShopAccount shopLogin(int account_id,int shop_id,String username,String pwd) throws Exception;
    MainTbAccount addAccount(MainTbAccount account);
    MainTbAccount stopAccount(int account);

    MainTbAccount changePwd(String phone,String newPwd);

    MainTbAccountSetting saveSetting(int account,String setting) throws Exception;

    MainTbAccountSetting getSetting(int account) throws Exception;

}
