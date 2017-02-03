package com.alienlab.wa17.db;

import com.alibaba.druid.pool.DruidDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by juhuiguang on 2017/2/2.
 */
public class DataSourceUtil {
    public static Map<String,DruidDataSource> dataSourceMap=new HashMap<String,DruidDataSource>();

    private  DruidDataSource createDataSource(String dbtype,String username,String pwd,String url){
        DruidDataSource ds=new DruidDataSource();
        ds.setDbType(dbtype);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(pwd);
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMinIdle(1);
        ds.setPoolPreparedStatements(false);
        return ds;
    }
}
