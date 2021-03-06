package com.alienlab.db;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by juhuiguang on 2017/1/2.
 */
@Component
public class DataSourceUtil {
    public final String DRIVER_MYSQL="com.mysql.jdbc.Driver";
    public final String DRIVER_ORACLE="oracle.jdbc.driver.OracleDriver";
    public final String DRIVER_MSSQL="com.microsoft.sqlserver.jdbc.SQLServerDriver";

    @Singleton
    public static Map<String,ConnectorBean> connectorMap=new HashMap<String,ConnectorBean>();

    Logger logger = Logger.getLogger(DataSourceUtil.class);

    public void addConnector(ConnectorBean conn){
        String key=conn.getDbType()+"$"+conn.getUrl()+"$"+conn.getUserName()+"$"+conn.getPassword();
        connectorMap.put(key,conn);
    }

    public ConnectorBean getConnector(String dbType,String url,String userName,String password){
        String key=dbType+"$"+url+"$"+userName+"$"+password;
        if(connectorMap.containsKey(key)){
            return connectorMap.get(key);
        }else{
            logger.error("get connector wrong!! the key was not fond:"+key);
            try {
                logger.info("create new ConnectorBean:"+key);
                return new ConnectorBean(dbType,userName,password,url);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("create new ConnectorBean fail:"+key);
                return null;
            }
        }
    }

    @Value("${alienlab.db.default.dbType}")
    String default_dbType;
    @Value("${alienlab.db.default.url}")
    String default_url;
    @Value("${alienlab.db.default.username}")
    String default_username;
    @Value("${alienlab.db.default.password}")
    String default_password;
    public ConnectorBean getDefaultDataSource(){
        return getConnector(default_dbType,default_url,default_username,default_password);
    }
}
