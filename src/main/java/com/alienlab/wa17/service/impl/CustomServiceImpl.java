package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.utils.TypeUtils;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 橘 on 2017/3/31.
 */
@Service
public class CustomServiceImpl implements CustomService {
    @Autowired
    DaoTool daoTool;
    @Override
    public List<ClientTbCustom> findCustom(int account, String keyword) throws Exception {
        String sql="select * from tb_custom where (custom_name like '%"+keyword+"%' or custom_phone like '%"+keyword+"%')";
        List<ClientTbCustom> customs=daoTool.getAllList(sql,account,ClientTbCustom.class);
        return customs;
    }

    @Override
    public ClientTbCustom addCustom(int account, ClientTbCustom custom) throws Exception {
        return daoTool.saveOne(custom,account);
    }
    @Override
    public JSONObject getCustomPaper(int account, Long cusid, String startdate, String enddate) throws Exception {
        JSONObject result=new JSONObject();
        ClientTbCustom custom=(ClientTbCustom)daoTool.getOne(ClientTbCustom.class,account,cusid);
        if(custom==null){
            throw new Exception("未找到客户信息，客户编码："+cusid);
        }
        result.put("custom",custom);
        String sql="SELECT COUNT(1) ordercount,SUM(order_amount)amount,SUM(order_recharge) recharge,SUM(order_money) money,SUM(order_odd) odd " +
                " FROM tb_order WHERE cus_id="+cusid+" AND order_time>='"+startdate+"' AND order_time<='"+enddate+"'";
        Map<String,Object> totalResult=daoTool.getMap(sql,account);
        if(totalResult!=null&&totalResult.containsKey("ordercount".toUpperCase())){
            result.put("total",totalResult);
        }
        sql="SELECT * FROM  tb_order WHERE cus_id="+cusid+" AND order_time>='"+startdate+"' AND order_time<='"+enddate+"' order by order_time desc";
        List orderList=daoTool.getAllList(sql,account);
        if(orderList!=null&&orderList.size()>0){
            result.put("orders",orderList);
        }
        return result;
    }
}
