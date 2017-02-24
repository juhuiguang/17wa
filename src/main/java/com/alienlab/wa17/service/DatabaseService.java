package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.main.MainTbDatabase;

/**
 * Created by juhuiguang on 2017/2/24.
 */
public interface DatabaseService {
    //根据登录账户查找数据源连接
    MainTbDatabase getDatabase(int account_id) throws Exception;

}
