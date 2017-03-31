package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.entity.client.dto.OrderDto;
import com.alienlab.wa17.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
                }else{//一般商品的请求,验证库存是否可以满足下单

                }
            }
        }
        return null;
    }

    private String getOrderNo(int account,long shopId) throws Exception {
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        String ordernopre=account+"-"+format.format(new Date())+"-"+shopId;
        String sql="select count(1) num from tb_order where shop_id="+shopId+" and order_code like '"+ordernopre+"%'";
        Map<String,Object> map=daoTool.getMap(sql,account);
        int no=TypeUtils.castToInt(map.get("num"));
        String result=ordernopre+"-"+(no+1);
        return result;
    }

    private boolean validateInventory(int account,long shopId,long skuid,Float saleAmount) throws Exception {
        String sql="select * from tb_inventory where sku_id="+skuid+" and shop_id="+shopId;
        Map<String,Object> map=daoTool.getMap(sql,account);
        return false;
    }
}
