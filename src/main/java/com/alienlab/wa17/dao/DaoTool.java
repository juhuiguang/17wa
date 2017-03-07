package com.alienlab.wa17.dao;

import com.alienlab.db.AlienEntity;
import com.alienlab.db.ConnectorBean;
import com.alienlab.wa17.entity.main.MainTbDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by 橘 on 2017/2/26.
 */
public interface DaoTool {

    //根据账户id，获得连接
     ConnectorBean getConnectByAccount(int account_id) throws Exception;
     //根据账户id，获得db对象
    MainTbDatabase getDatabaseByAccount(int account_id);

    List getAllList(String sql)throws Exception;
    List getAllList(String sql,Class entityclass) throws Exception;
    List getAllList(String sql,int account_id) throws Exception;

    Page getPageList(String pagesql,String totalsql, Pageable page, int account_id) throws Exception;

    List getAllList(String sql,int account_id,Class entityclass) throws Exception;
    Page getPageList(String pagesql,String totalsql, Pageable page, int account_id,Class entityclass) throws Exception;

    Map getMap(String sql, int account_id) throws Exception;
    Object getObject(String sql,int account_id,Class entityclass) throws Exception;

    Object getOne(Class entityClass,int account_id,long id) throws Exception;

    <T> T saveOne(T entity, int account_id) throws Exception;

    <T> T updateOne(int account_id,T entity) throws Exception;

    boolean deleteOne(Class entityClass,int account_id,long id) throws Exception;
    boolean exec(String sql,int account_id) throws Exception;
    Long execInsert(String sql,int account_id) throws Exception;
}
