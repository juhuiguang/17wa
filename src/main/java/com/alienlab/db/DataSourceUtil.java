package com.alienlab.db;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by juhuiguang on 2017/1/2.
 */
@Component
@Scope("singleton")
public class DataSourceUtil {
    public final String DRIVER_MYSQL="com.mysql.jdbc.Driver";
    public final String DRIVER_ORACLE="oracle.jdbc.driver.OracleDriver";
    public final String DRIVER_MSSQL="com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public Map<String,ConnectorBean> connectorMap=new HashMap<String,ConnectorBean>();

    @Autowired
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
            return null;
        }
    }
}
