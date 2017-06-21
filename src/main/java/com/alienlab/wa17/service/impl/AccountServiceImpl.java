package com.alienlab.wa17.service.impl;

import com.alienlab.db.AlienEntity;
import com.alienlab.db.Dao;
import com.alienlab.utils.DesUtil;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.entity.main.MainTbAccount;
import com.alienlab.wa17.service.AccountService;
import com.alienlab.wa17.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by juhuiguang on 2017/2/25.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    Dao maindao;
    @Autowired
    AlienEntity<MainTbAccount> accountAlienEntity;

    @Override
    public MainTbAccount getAccount(String account_loginname) throws Exception {
        String sql="select * from tb_account where account_loginname='"+account_loginname+"'";
        Map accountmap=maindao.getMap(sql);
        if(accountmap==null){
            throw new Exception("未找到登录名为"+account_loginname+"的账户。");
        }
        MainTbAccount result=accountAlienEntity.Obj2T(accountmap,MainTbAccount.class);
        return result;
    }

    @Autowired
    DesUtil desUtil;
    @Autowired
    ShopService shopService;
    @Override
    public ClientTbShopAccount shopLogin(int account_id, int shop_id, String username, String pwd) throws Exception {
        ClientTbShopAccount account=shopService.getShopAccount(account_id,shop_id,username);
        if(account==null){
            throw new Exception("门店用户"+username+"不存在。");
        }
        String orignpwd=account.getAccountPwd();
        String loginpwd=desUtil.encrypt(pwd);
        if(orignpwd.equals(loginpwd)){
            return account;
        }else{
            throw new Exception("用户"+username+"密码不正确。");
        }
    }

    @Override
    public MainTbAccount addAccount(MainTbAccount account) {
        return null;
    }

    @Override
    public MainTbAccount stopAccount(int account) {
        return null;
    }

    @Override
    public MainTbAccount changePwd(String phone, String newPwd) {
        return null;
    }

}
