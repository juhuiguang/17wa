package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.main.MainTbDatabase;
import com.alienlab.wa17.entity.main.MainTbDatabaseServer;
import com.alienlab.wa17.entity.main.MainTbImageserver;
import com.alienlab.wa17.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by 橘 on 2017/6/20.
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    DaoTool daoTool;
    @Override
    public MainTbImageserver addImageServer(MainTbImageserver server) throws Exception {
        return daoTool.saveOne(server,0);
    }

    @Override
    public MainTbImageserver updateImageServer(MainTbImageserver server) throws Exception {
        return daoTool.updateOne(0,server);
    }

    @Override
    public boolean deleteImageServer(long serverId) throws Exception {
        return daoTool.deleteOne(MainTbImageserver.class,0,serverId);
    }

    @Override
    public MainTbDatabaseServer addDatabaseServer(MainTbDatabaseServer server) throws Exception {
        return daoTool.saveOne(server,0);
    }

    @Override
    public MainTbDatabaseServer updateDatabaseServer(MainTbDatabaseServer server) throws Exception {
        return daoTool.updateOne(0,server);
    }

    @Override
    public boolean deleteDatabaseServer(long serverId) throws Exception {
        return daoTool.deleteOne(MainTbDatabaseServer.class,0,serverId);
    }

    @Override
    public MainTbDatabase addDatabase(MainTbDatabase database) throws Exception {
        return daoTool.saveOne(database,0);
    }

    @Override
    public MainTbImageserver getEnableImageServer() throws Exception {
        String sql="select * from tb_imageserver where server_status=1";
        List<MainTbImageserver> servers=daoTool.getAllList(sql);
        if(servers==null||servers.size()==0){
            return null;
        }
        if(servers.size()>0&&servers.size()==1){
            return servers.get(0);
        }else{
            Random random = new Random();
            int s = random.nextInt(servers.size()-1)%(servers.size()+1-1) + 0;
            return servers.get(s);
        }
    }

    @Override
    public MainTbDatabaseServer getEnableDatabaseServer() throws Exception {
        String sql="select * from tb_database_server where dbserver_status=1";
        List<MainTbDatabaseServer> servers=daoTool.getAllList(sql);
        if(servers==null||servers.size()==0){
            return null;
        }
        if(servers.size()>0&&servers.size()==1){
            return servers.get(0);
        }else{
            Random random = new Random();
            int s = random.nextInt(servers.size()-1)%(servers.size()+1-1) + 0;
            return servers.get(s);
        }
    }
}
