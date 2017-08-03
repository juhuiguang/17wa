package com.alienlab.wa17.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by juhuiguang on 2017/2/24.
 */
public interface ImageService {
    void convertImageAuto(String path,String fileName, String format,int width) throws Exception;
    String createSizeIncludeImage(int account,int productId,String path,String filename) throws Exception;

    String createCustomShareImage(int account, Long customid,String path,String fileName) throws Exception;

    JSONObject getIncludesPrintObj(int account, int productId)throws Exception;
}
