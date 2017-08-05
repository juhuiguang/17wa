package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.AlienEntity;
import com.alienlab.db.Dao;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.dao.impl.CreateDbConn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 橘 on 2017/8/4.
 */
@Service
public class SqlFileExcutor {
    @Autowired
    CreateDbConn createDbConn;
    @Value("$(alienlab.db.default.username)")
    String username;
    @Value("$(alienlab.db.default.password)")
    String password;
    @Value("${17wa.sqlpath}")
    String sqlpath;
    /**
     * 读取 SQL 文件，获取 SQL 语句
     * @param sqlFile SQL 脚本文件
     * @return List<sql> 返回所有 SQL 语句的 List
     * @throws Exception
     */
    private List<String> loadSql(String sqlFile,String dbname) throws Exception {
        List<String> sqlList = new ArrayList<String>();

        try {
            InputStream sqlFileIn = new FileInputStream(sqlFile);

            StringBuffer sqlSb = new StringBuffer();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = sqlFileIn.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead,"utf8"));
            }
            if(dbname==null||dbname.equalsIgnoreCase("")){
                dbname="17wa_client";
            }
            String sqlscript= sqlSb.toString().replace("$(17wa_client)","17wa_client_"+dbname);
            sqlscript=sqlscript.replaceAll("\\r|\\n","");
            // Windows 下换行是 /r/n, Linux 下是 /n
            String[] sqlArr = sqlscript.split(";");
            for (int i = 0; i < sqlArr.length; i++) {
                String sql = sqlArr[i].replaceAll("--.*", "").trim();
                if (!sql.equals("")) {
                    sqlList.add(sql);
                }
            }
            return sqlList;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 传入连接来执行 SQL 脚本文件，这样可与其外的数据库操作同处一个事物中
     * @param conn 传入数据库连接
     * @param sqlFile SQL 脚本文件
     * @throws Exception
     */
    public void execute(Connection conn, String sqlFile) throws Exception {
        Statement stmt = null;
        List<String> sqlList = loadSql(sqlFile,"");
        stmt = conn.createStatement();
        for (String sql : sqlList) {
            stmt.addBatch(sql);
        }
        int[] rows = stmt.executeBatch();
        System.out.println("Row count:" + Arrays.toString(rows));
    }

    /**
     * 自建连接，独立事物中执行 SQL 文件
     * @param sqlFile SQL 脚本文件
     * @throws Exception
     */
    public JSONObject execute(String sqlFile, String dbname) throws Exception {
        Connection conn =createDbConn.getConnection();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("url",conn.getMetaData().getURL());
        jsonObject.put("user",username);
        jsonObject.put("password",password);
        Statement stmt = null;
        List<String> sqlList = loadSql(sqlFile,dbname);
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            for (String sql : sqlList) {
                stmt.addBatch(sql);
            }
            int[] rows = stmt.executeBatch();
            System.out.println("Row count:" + Arrays.toString(rows));
            conn.commit();
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.close();
        }
        return jsonObject;
    }

    public JSONObject createNewAccountDb(String dbname) throws Exception{
        return execute(sqlpath,dbname);
    }
//
//    public static void main(String[] args) throws Exception {
////        List<String> sqlList = new SqlFileExcutor().loadSql("d:\\17wa.sql","temp1");
////        System.out.println("size:" + sqlList.size());
////        for (String sql : sqlList) {
////            System.out.println(sql);
////        }
//        new SqlFileExcutor().createNewAccountDb("temp1");
//    }
}
