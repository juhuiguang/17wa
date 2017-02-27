package com.alienlab.wa17.dao.impl;

import com.alienlab.db.AlienEntity;
import com.alienlab.db.ConnectorBean;
import com.alienlab.db.Dao;
import com.alienlab.db.DataSourceUtil;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.main.MainTbDatabase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 橘 on 2017/2/26.
 */
@Service
public class DaoToolImpl implements DaoTool {
    Logger logger = Logger.getLogger(DaoTool.class);
    private Map<Integer,MainTbDatabase> databases=new HashMap<Integer,MainTbDatabase>();
    @Autowired
    private Dao maindao;
    @Autowired
    DataSourceUtil dataSourceUtil;
    @Autowired
    AlienEntity alienEntity;

    @Override
    public ConnectorBean getConnectByAccount(int account_id) throws Exception {
        MainTbDatabase dbproperties=getDatabaseByAccount(account_id);
        if(dbproperties==null){
            throw new Exception("id为"+account_id+"的账户不存在。");
        }
        return dataSourceUtil.getConnector(dbproperties.getDbType(),dbproperties.getDbUrl(),dbproperties.getDbUser(),dbproperties.getDbPwd());
    }

    @Override
    public MainTbDatabase getDatabaseByAccount(int account_id) {
        if(databases.containsKey(account_id)){
            return databases.get(account_id);
        }else{
            String sql="select * from tb_database where account_id="+account_id;
            Map<String,Object> db=maindao.getMap(sql);
            return (MainTbDatabase)alienEntity.Obj2T(db,MainTbDatabase.class);
        }
    }

    @Override
    public List getAllList(String sql, int account_id) throws Exception {
        ConnectorBean connect=getConnectByAccount(account_id);
        Dao dao=new Dao(connect);
        return dao.getDataSet(sql);
    }

    @Override
    public Page getPageList(String pagesql, String totalsql, Pageable page, int account_id) throws Exception {
        List pagelist=getAllList(pagesql,account_id);
        List totallist=getAllList(totalsql,account_id);
        return new PageImpl(pagelist,page,totallist.size());
    }

    @Override
    public List getAllList(String sql, int account_id, Class entityclass) throws Exception {
        List list=getAllList(sql,account_id);
        return alienEntity.list2T(list,entityclass);
    }

    @Override
    public Page getPageList(String pagesql, String totalsql, Pageable page, int account_id, Class entityclass) throws Exception {
        List pagelist=getAllList(pagesql,account_id);
        List totallist=getAllList(totalsql,account_id);
        List entitylist=alienEntity.list2T(pagelist,entityclass);
        return new PageImpl(entitylist,page,totallist.size());
    }

    @Override
    public Map getMap(String sql, int account_id) throws Exception {
        ConnectorBean connect=getConnectByAccount(account_id);
        Dao dao=new Dao(connect);
        return dao.getMap(sql);
    }


    @Override
    public Object getObject(String sql, int account_id, Class entityclass) throws Exception {
        Map obj=getMap(sql,account_id);
        return alienEntity.Obj2T(obj,entityclass);
    }


}
