package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.main.MainTbAccount;
import com.alienlab.wa17.entity.main.MainTbDatabase;
import com.alienlab.wa17.entity.main.MainTbDatabaseServer;
import com.alienlab.wa17.entity.main.MainTbImageserver;

/**
 * Created by 橘 on 2017/6/20.
 */
public interface SystemService {
    MainTbImageserver addImageServer(MainTbImageserver server) throws Exception;
    MainTbImageserver updateImageServer(MainTbImageserver server) throws Exception;
    boolean deleteImageServer(long serverId) throws Exception;

    MainTbDatabaseServer addDatabaseServer(MainTbDatabaseServer server) throws Exception;
    MainTbDatabaseServer updateDatabaseServer(MainTbDatabaseServer server) throws Exception;
    boolean deleteDatabaseServer(long serverId) throws Exception;


    MainTbDatabase addDatabase(MainTbDatabase database);




}
