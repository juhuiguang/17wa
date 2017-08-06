package com.alienlab.wa17.service;

import com.alibaba.fastjson.JSONArray;
import com.alienlab.wa17.entity.client.ClientTbProductInclude;
import com.alienlab.wa17.entity.main.MainTbInclude;

import java.util.List;

/**
 * Created by 橘 on 2017/5/20.
 */
public interface IncludeService {
    List getIncludeType()throws Exception;
    List<MainTbInclude> getAllInclude()throws Exception;

    MainTbInclude addInclude(String includeName,String includeType) throws Exception;
    boolean delInclude(int includeId) throws Exception;

    List<ClientTbProductInclude> getProductIncluedes(int account,int productId) throws Exception;
    List<ClientTbProductInclude> addProductIncludes(int account, int productId, JSONArray includes) throws Exception;
    boolean delProductInclude(int account,Long productId) throws Exception;



}
