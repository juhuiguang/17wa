package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.main.MainTbDatabase;
import com.alienlab.wa17.entity.main.MainTbDatabaseServer;
import com.alienlab.wa17.entity.main.MainTbImageserver;
import com.alienlab.wa17.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 橘 on 2017/6/20.
 */
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
    public MainTbDatabase addDatabase(MainTbDatabase database) {
        return null;
    }
}
