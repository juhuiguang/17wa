package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbShop;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 橘 on 2017/2/26.
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    DaoTool daoTool;

    @Override
    public List<ClientTbShop> getShopes(int account_id) throws Exception {
        String sql="select * from tb_shop";
        return daoTool.getAllList(sql,account_id,ClientTbShop.class);
    }

    @Override
    public ClientTbShop addShop(int account_id, ClientTbShop shop) throws Exception {
        return null;
    }

    @Override
    public ClientTbShop updateShop(int account_id, ClientTbShop shop) throws Exception {
        return null;
    }

    @Override
    public ClientTbShop setDefaultShop(int account_id, int shop, boolean isdefault) throws Exception {
        return null;
    }

    @Override
    public boolean delShop(int account_id, int shop) throws Exception {
        return false;
    }

    @Override
    public List<ClientTbShopAccount> getShopAccountList(int account_id, int shop_id) throws Exception {
        String sql="select * from tb_shop_account where shop_id="+shop_id;
        return daoTool.getAllList(sql,account_id,ClientTbShopAccount.class);
    }

    @Override
    public ClientTbShopAccount getShopAccount(int account_id, int shop_id, String username) throws Exception {
        String sql="select * from tb_shop_account where shop_id="+shop_id+" and account_name='"+username+"'";
        return (ClientTbShopAccount)daoTool.getObject(sql,account_id,ClientTbShopAccount.class);
    }

    @Override
    public ClientTbShopAccount addAccount(int account_id, ClientTbShopAccount account) throws Exception {
        return null;
    }

    @Override
    public ClientTbShopAccount setDenied(int account_id, int shopaccount) throws Exception {
        return null;
    }

    @Override
    public boolean delAccount(int account_id, int shopaccount) throws Exception {
        return false;
    }
}