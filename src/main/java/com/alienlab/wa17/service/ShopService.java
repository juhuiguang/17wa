package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbShop;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.entity.client.dto.ShopAccountDto;

import java.util.List;

/**
 * Created by juhuiguang on 2017/2/24.
 */
public interface ShopService {
    //获取当前用户的门店列表
    List<ClientTbShop> getShopes(int account_id) throws Exception;

    ClientTbShop getShop(int account,int shop) throws Exception;;

    ClientTbShop addShop(int account_id,ClientTbShop shop) throws Exception;

    ClientTbShop updateShop(int account_id,ClientTbShop shop) throws Exception;

    ClientTbShop setDefaultShop(int account_id,int shop,boolean isdefault) throws Exception;

    boolean delShop(int account_id,int shop) throws Exception;

    List<ShopAccountDto> getShopAccountList(int account_id) throws Exception;
    //获得当前连接库中所选门店账户信息
    List<ClientTbShopAccount> getShopAccountList(int account_id, int shop_id) throws Exception;

    ClientTbShopAccount getShopAccount(int account_id,int shop_id,String username) throws Exception;

    ClientTbShopAccount addAccount(int account_id,ClientTbShopAccount account)throws Exception;

    ClientTbShopAccount changeAccountPwd(int account_id,long shopaccount_id,String old_pwd,String pwd) throws Exception;

    ClientTbShopAccount setDenied(int account_id,int shopaccount) throws Exception;

    boolean delAccount(int account_id,int shopaccount) throws Exception;

    ClientTbShopAccount setActive(int account_id, int shopaccount) throws Exception;
}
