package com.alienlab.db;

import java.awt.print.Pageable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.alibaba.fastjson.JSON;

import com.alienlab.utils.DateUtils;
import com.alienlab.utils.TypeUtils;
import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidPooledConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Dao {
    Logger logger = Logger.getLogger(Dao.class);
    @Autowired
    DataSourceUtil dataSourceUtil;
    //当前操作所需的链接，每次执行都需要指定。
    private ConnectorBean connectorBean=null;


    //未指定连接的构造函数，获取默认连接
    public Dao() {
//        this.connectorBean=dataSourceUtil.getDefaultDataSource();
    }

    //指定特定连接的构造函数
    public Dao(ConnectorBean connectorBean){
        this.connectorBean=connectorBean;
    }


    /**
     * 获取返回数据总行�?
     *
     * @param sql
     * @return
     */
    public int getDataCount(String sql) {
        int count = 0;
        ResultSet resSet = null;
        DruidPooledConnection conn = null;
        Statement stmt = null;
        try {
            // sql=sql.toUpperCase();
            if(this.connectorBean==null){
                this.connectorBean=dataSourceUtil.getDefaultDataSource();
            }
            conn = this.connectorBean.getConnPool();
            if (!TypeUtils.isEmpty(sql) && !TypeUtils.isEmpty(conn)) {
                stmt = conn.createStatement();
                resSet = stmt.executeQuery(sql);//
                if(resSet.next()) {
                    count=resSet.getInt("totalCount");
                }
            } else {
                logger.info("SQL 输入语法错误或没有可用的连接");
                return 0;
            }

        } catch (Exception e) {
            // TODO: handle exception
            logger.error("SQL 输入语法错误或没有可用的连接---" + sql);
        }finally {
            closeResultSet(resSet);
            closeStatement(stmt);
            closeConnection(conn);
        }
        return count;
    }

    /**
     * 获取返回数据
     *
     * @param sql
     * @return
     */
    public List<Map<String, Object>> getDataSet(String sql) {
        List<Map<String, Object>> datas = null;
        // PreparedStatement pstmt=null; //使用子类报错，不知道为啥�??
        ResultSet resSet = null;
        DruidPooledConnection conn = null;
        Statement stmt = null;
        try {
            // sql=sql.toUpperCase();
            if(this.connectorBean==null){
                this.connectorBean=dataSourceUtil.getDefaultDataSource();
            }
            conn = this.connectorBean.getConnPool();
            if (!TypeUtils.isEmpty(sql) && !TypeUtils.isEmpty(conn)) {
                // pstmt=conn.prepareStatement(sql);
                stmt = conn.createStatement();
                resSet = stmt.executeQuery(sql);//
                ResultSetMetaData rsmd = resSet.getMetaData();
                // 取得结果集列�??
                int columnCount = rsmd.getColumnCount();
                // 构�?�泛型结果集
                datas = new ArrayList<Map<String, Object>>();
                Map<String, Object> data = null;
                // 循环结果�??
                while (resSet.next()) {
                    data = new HashMap<String, Object>();
                    // 每循环一条将列名和列值存入Map
                    for (int i = 1; i <= columnCount; i++) {
                        data.put(rsmd.getColumnLabel(i).toUpperCase(),
                                TypeUtils.getString(resSet.getObject(rsmd.getColumnLabel(i))));
                    }
                    // 将整条数据的Map存入到List�??
                    datas.add(data);
                }
                return datas;
            } else {
                logger.info("SQL 输入语法错误或没有可用的连接");
                return new ArrayList<Map<String, Object>>();
            }
        } catch (Exception e) {
            logger.error("SQL 输入语法错误或没有可用的连接---" + sql);
        } finally {
            closeResultSet(resSet);
            closeStatement(stmt);
            closeConnection(conn);
        }
        return datas;
    }

    /**
     * 删除、更新语�?
     *
     * @param sql
     * @return
     */
    public boolean execCommand(String sql) {
        boolean bool = false;
        Statement pstmt = null;
        DruidPooledConnection conn = null;
        try {
            if(this.connectorBean==null){
                this.connectorBean=dataSourceUtil.getDefaultDataSource();
            }
            conn = this.connectorBean.getConnPool();
            if (!TypeUtils.isEmpty(sql) && !TypeUtils.isEmpty(conn)) {
                pstmt = conn.createStatement();
                int row = pstmt.executeUpdate(sql);
                if (row > 0) {
                    bool = true;
                }
            }
        } catch (Exception e) {
            logger.error("SQL===" + sql + "\n" + e.getMessage());
        } finally {
            closeStatement(pstmt);
            closeConnection(conn);
        }
        return bool;
    }

    public Object execInsertId(String sql) {
        Statement pstmt = null;
        DruidPooledConnection conn = null;
        try {
            if(this.connectorBean==null){
                this.connectorBean=dataSourceUtil.getDefaultDataSource();
            }
            conn = this.connectorBean.getConnPool();
            if (!TypeUtils.isEmpty(sql) && !TypeUtils.isEmpty(conn)) {
                pstmt = conn.createStatement();
                if (pstmt.executeUpdate(sql) > 0) {
                    sql = "SELECT @@IDENTITY AS id";
                    ResultSet rs = pstmt.executeQuery(sql);
                    if (rs.next()) {
                        return rs.getObject(1);
                    }
                    return null;
                } else {
                    return null;
                }

            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("SQL===" + sql + "\n" + e.getMessage());
            return null;
        } finally {
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * 删除、更新�?�增�?? 防止sql 依赖注入
     *
     * @param sql
     * @return
     */
    public boolean execCommandPrepared(String sql, Map<String, Object> map) {
        boolean bool = false;
        PreparedStatement pstmt = null;
        DruidPooledConnection conn = null;
        try {
            conn = this.connectorBean.getConnPool();
            if (!TypeUtils.isEmpty(sql) && !TypeUtils.isEmpty(map) && !TypeUtils.isEmpty(conn)) {
                pstmt = conn.prepareStatement(sql);
                for (String key : map.keySet()) {
                    pstmt.setObject(TypeUtils.getInt(key), map.get(key));
                }
                int row = pstmt.executeUpdate();
                if (row > 0) {
                    bool = true;
                }
            }
        } catch (Exception e) {
            logger.error("SQL===" + sql + "\n" + e.getMessage());
        } finally {
            closeStatement(pstmt);
            closeConnection(conn);
        }
        return bool;
    }

    /**
     * 批量插入
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public boolean executeBatch(List<String> sql) throws SQLException {
        boolean bool = false;
        // 生产connection
        Connection conn = this.connectorBean.getConnPool();
        Statement sm = null;
        int[] result = null;
        sm = conn.createStatement();
        try {
            // 获得当前Connection的提交模�??
            boolean autoCommit = conn.getAutoCommit();
            // 关闭自动提交模式
            conn.setAutoCommit(false);
            sm = conn.createStatement();
            // 遍历sql
            for (String s : sql) {
                sm.addBatch(s);
            }
            // 执行批量更新
            result = sm.executeBatch();
            for (int v : result) {
                if (v >= 0) {
                    bool = true;
                } else {
                    return bool;
                }
            }
            // 提交事务
            conn.commit();
            // 还原提交模式
            conn.setAutoCommit(autoCommit);
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            closeStatement(sm);
            closeConnection(conn);
        }
        return bool;
    }

    public  void closeStatement(Statement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("close PreparedStatement error", e);
        }
    }

    public  void closeConnection(Connection dbConnection) {
        try {
            if (dbConnection != null && (!dbConnection.isClosed())) {
                dbConnection.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("close connection error", e);
        }
    }

    public  void closeResultSet(ResultSet res) {
        try {
            if (res != null) {
                res.close();
                res = null;
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("close ResultSet error", e);
        }
    }



}
