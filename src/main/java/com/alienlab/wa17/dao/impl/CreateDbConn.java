package com.alienlab.wa17.dao.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.alienlab.wa17.dao.DaoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 橘 on 2017/8/4.
 */
@Service
public class CreateDbConn {
    @Autowired
    DaoTool daoTool;
    public Connection getConnection() throws Exception {
        String sql="SELECT * FROM `tb_database_server` WHERE dbserver_status=1";
        List<Map<String,Object>> dblist=daoTool.getAllList(sql);
        if(dblist!=null&&dblist.size()>0){
            Random r=new Random();
            Map<String,Object> connInfo=dblist.get(r.nextInt(dblist.size()));
            if(connInfo!=null){
                String ip= TypeUtils.castToString(connInfo.get("dbserver_IP".toUpperCase()));
                String port=TypeUtils.castToString(connInfo.get("dbserver_port".toUpperCase()));
                String url="jdbc:mysql://"+ip+":"+port+"/17wa_main";
                String username= TypeUtils.castToString(connInfo.get("dbserver_user".toUpperCase()));
                String password=TypeUtils.castToString(connInfo.get("dbserver_pwd".toUpperCase()));
                Class.forName("com.mysql.jdbc.Driver");
                return DriverManager.getConnection(url, username, password);
            }
        }
        return null;
    }
}
