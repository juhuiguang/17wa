package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.AlienEntity;
import com.alienlab.db.Dao;
import com.alienlab.utils.DesUtil;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.entity.main.MainTbAccount;
import com.alienlab.wa17.entity.main.MainTbAccountSetting;
import com.alienlab.wa17.entity.main.MainTbDatabase;
import com.alienlab.wa17.service.AccountService;
import com.alienlab.wa17.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Autowired
    DaoTool daoTool;

    @Autowired
    SqlFileExcutor sqlFileExcutor;

    @Override
    public MainTbAccount getAccount(String account_phone) throws Exception {
        String sql="select * from tb_account where account_phone='"+account_phone+"'";
        Map accountmap=maindao.getMap(sql);
        if(accountmap==null){
            throw new Exception("未找到登录名为"+account_phone+"的账户。");
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
        //创建账户
        try {
            MainTbAccount getac=getAccount(account.getAccountPhone());
            if(getac!=null){
                throw new Exception("账户名已存在。");
            }
            account=daoTool.saveOne(account,0);
            String dbname="17wa_client_"+account.getAccountId();
            JSONObject connectJson=sqlFileExcutor.createNewAccountDb(dbname);
            MainTbDatabase db=new MainTbDatabase();
            db.setAccountId(account.getAccountId());
            db.setDbMaintype("mysql");
            db.setDbPwd(connectJson.getString("password"));
            db.setDbUrl(connectJson.getString("url"));
            db.setDbStatus("1");
            db.setDbSchema(dbname);
            db.setDbUser(connectJson.getString("username"));
            daoTool.saveOne(db,0);
            return account;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //导入脚本

        //写入账户连接

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

    @Override
    public MainTbAccountSetting saveSetting(int account, String setting) throws Exception {
        MainTbAccountSetting accountSetting=getSetting(account);
        if(accountSetting==null){
            accountSetting=new MainTbAccountSetting();
            accountSetting.setAccountId(account);
            accountSetting.setSetting(setting);
            return daoTool.saveOne(accountSetting,0);
        }else{
            accountSetting.setAccountId(account);
            accountSetting.setSetting(setting);
            return daoTool.updateOne(0,accountSetting);
        }

    }

    @Override
    public MainTbAccountSetting getSetting(int account) throws Exception {
        String sql="select * from tb_account_settings where account_id="+account;
        try{
            MainTbAccountSetting result=(MainTbAccountSetting)daoTool.getObject(sql,0,MainTbAccountSetting.class);
            if(result!=null){
                return result;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }


}
