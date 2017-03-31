package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.entity.client.dto.OrderDto;
import com.alienlab.wa17.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 橘 on 2017/3/31.
 */
public class OrderServiceImpl implements OrderService {
    @Autowired
    DaoTool daoTool;
    @Override
    public OrderDto addOrder(int account,long shopId,JSONObject orderinfo) throws Exception {
        JSONArray details=orderinfo.getJSONArray("details");
        long customId=orderinfo.getLong("cus_id");
        ClientTbCustom custom=(ClientTbCustom)daoTool.getOne(ClientTbCustom.class,account,customId);
        if(custom==null){
            throw new Exception("客户不存在，客户编码"+customId);
        }
        Float amount=0f;
        Float totalPrice=0f;
        Float recharge=0f;
        Float odd=0f;
        //遍历明细
        if(details!=null&&details.size()>0){
            for(int i=0;i<details.size();i++){
                JSONObject detail=details.getJSONObject(i);
                String productName=detail.getString("detail_name");
                //处理抹零请求
                if(productName.equals("抹零")&&detail.getFloat("total_price")>0){
                    odd=detail.getFloat("total_price");
                }else if(productName.equals("核销")&&detail.getFloat("total_price")>0){//处理核销
                    recharge=detail.getFloat("total_price");
                }else{//一般商品的请求

                }
            }
        }
        return null;
    }
}
