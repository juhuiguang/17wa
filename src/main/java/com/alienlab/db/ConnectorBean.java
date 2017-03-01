package com.alienlab.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.util.DruidDataSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Created by 橘 on 2017/1/3.
 */
public class ConnectorBean {
    DataSourceUtil dataSourceUtil=new DataSourceUtil();
    private String url;
    private String dbType;
    private String userName;
    private String password;
    private DruidDataSource dataSource;

    public ConnectorBean(){

    }

    public ConnectorBean(String dbType,String userName,String password,String url) throws SQLException {
        this.dbType=dbType;
        this.userName=userName;
        this.password=password;
        this.url=url;
        if(this.dataSource==null){
            this.dataSource=createDataSource();
        }
        dataSourceUtil.addConnector(this);
    }


    private  DruidDataSource createDataSource(){
        DruidDataSource ds=new DruidDataSource();
        ds.setDbType(this.dbType);
        switch(this.dbType){
            case "mysql":{
                ds.setDriverClassName(dataSourceUtil.DRIVER_MYSQL);
                break;
            }
            case "oracle":{
                ds.setDriverClassName(dataSourceUtil.DRIVER_ORACLE);
                break;
            }
            case "mssql":{
                ds.setDriverClassName(dataSourceUtil.DRIVER_MSSQL);
                break;
            }
        }
        ds.setUrl(this.url);
        ds.setUsername(this.userName);
        ds.setPassword(this.password);
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMinIdle(1);
        ds.setTestWhileIdle(true);
        ds.setPoolPreparedStatements(false);
        ds.setDefaultAutoCommit(true);
        ds.setRemoveAbandoned(true);
        ds.setRemoveAbandonedTimeout(1800);
        return ds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DruidDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DruidDataSource dataSource) {
        this.dataSource = dataSource;
    }


}
