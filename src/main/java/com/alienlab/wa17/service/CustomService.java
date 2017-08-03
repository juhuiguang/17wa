package com.alienlab.wa17.service;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbCustom;

import java.util.List;

/**
 * Created by 橘 on 2017/3/27.
 */
public interface CustomService {
    List<ClientTbCustom> findCustom(int account,String keyword) throws Exception;

    ClientTbCustom addCustom(int account,ClientTbCustom custom) throws Exception;

    JSONObject getCustomPaper(int account, Long cusid, String startdate, String enddate) throws Exception;


}
