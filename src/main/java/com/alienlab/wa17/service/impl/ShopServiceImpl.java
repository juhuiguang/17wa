package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbShop;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 橘 on 2017/1/26.
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
        ClientTbShop s=daoTool.saveOne(shop,account_id);
        return s;
    }

    @Override
    public ClientTbShop updateShop(int account_id, ClientTbShop shop) throws Exception {
        ClientTbShop s=daoTool.updateOne(account_id,shop);
        return s;
    }

    @Override
    public ClientTbShop setDefaultShop(int account_id, int shop, boolean isdefault) throws Exception {
        ClientTbShop clientshop=(ClientTbShop)daoTool.getOne(ClientTbShop.class,account_id,(long)shop);
        if(clientshop==null){
            throw new Exception("未找到编号为"+shop+"的店铺。");
        }
        String sql="update tb_shop set shop_isdefault=0 where 1=1";
        boolean result=daoTool.exec(sql,account_id);//将其他店铺设置为非默认
        if(result){
            clientshop.setShopIsdefault("1");
            daoTool.updateOne(account_id,clientshop);
            return clientshop;
        }else{
            throw new Exception("更新店铺状态过程中发生错误。");
        }

    }

    @Override
    public boolean delShop(int account_id, int shop) throws Exception {
        return daoTool.deleteOne(ClientTbShop.class,account_id,(long)shop);
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
        account=daoTool.saveOne(account,account_id);
        return account;
    }

    @Override
    public ClientTbShopAccount setDenied(int account_id, int shopaccount) throws Exception {
        ClientTbShopAccount shopat=(ClientTbShopAccount)daoTool.getOne(ClientTbShopAccount.class,account_id,(long)shopaccount);
        if(shopat==null){
            throw new Exception("未找到编号为"+shopaccount+"的账号。");
        }
        shopat.setAccountStatus("0");
        daoTool.updateOne(account_id,shopat);
        return shopat;
    }

    @Override
    public ClientTbShopAccount setActive(int account_id, int shopaccount) throws Exception {
        ClientTbShopAccount shopat=(ClientTbShopAccount)daoTool.getOne(ClientTbShopAccount.class,account_id,(long)shopaccount);
        if(shopat==null){
            throw new Exception("未找到编号为"+shopaccount+"的账号。");
        }
        shopat.setAccountStatus("1");
        daoTool.updateOne(account_id,shopat);
        return shopat;
    }

    @Override
    public boolean delAccount(int account_id, int shopaccount) throws Exception {
        return daoTool.deleteOne(ClientTbShopAccount.class,account_id,(long)shopaccount);
    }
}
