package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbMsgInfo;
import com.alienlab.wa17.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    DaoTool daoTool;

    @Override
    public Page<ClientTbMsgInfo> getMessages(Long account, Long shopid, int index, int size) {
        String sql="select * from tb_msg_info where msg_to_shop="+shopid+" order by msg_id desc";
        try {
            Page<ClientTbMsgInfo> result=daoTool.getPageList(sql,new PageRequest(index,size),account.intValue(),ClientTbMsgInfo.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ClientTbMsgInfo readOne(Long account, Long msgId) {
        return null;
    }

    @Override
    public Page<ClientTbMsgInfo> readAll(Long account, Long shopid) {
        return null;
    }

    @Override
    public ClientTbMsgInfo heartBeat(Long account, Long shopid) {
        String sql="select * from tb_msg_info where msg_to_shop="+shopid+" order by msg_id desc";
        try {
            Page<ClientTbMsgInfo> result=daoTool.getPageList(sql,new PageRequest(0,5),account.intValue(),ClientTbMsgInfo.class);
            if(result!=null&&result.getTotalElements()>0){
                ClientTbMsgInfo item=result.getContent().get(0);
                return item;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ClientTbMsgInfo sendMsg(Long account, ClientTbMsgInfo msg) {
        try {
            return daoTool.saveOne(msg,account.intValue());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
