package com.alienlab.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.sql.SQLException;

/**
 * Created by 橘 on 2017/1/3.
 */
@Component
class ConnectorBean {
    @Autowired
    DataSourceUtil dataSourceUtil;
    private String url;
    private String dbType;
    private String userName;
    private String password;
    private DruidDataSource dataSource;
    private DruidPooledConnection connPool;

    public ConnectorBean(String dbType,String userName,String password,String url) throws SQLException {
        this.dbType=dbType;
        this.userName=userName;
        this.password=password;
        this.url=url;
        if(this.dataSource==null){
            this.dataSource=createDataSource();
        }
        if(this.connPool==null){
            this.connPool=this.dataSource.getConnection();
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
        ds.setPoolPreparedStatements(false);
        ds.setDefaultAutoCommit(true);
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

    public DruidPooledConnection getConnPool() {
        return connPool;
    }

    public void setConnPool(DruidPooledConnection connPool) {
        this.connPool = connPool;
    }

}
