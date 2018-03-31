package com.alienlab.wa17.service;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.entity.client.ClientTbMsgInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService {
    /**
     * 获取门店所有消息
     * @param account 账户Id
     * @param shopid 门店Id
     * @param index 页码
     * @param size 页长
     * @return 分页的消息列表
     */
    Page<ClientTbMsgInfo> getMessages(Long account, Long shopid,int index,int size);

    /**
     * 设置一条消息已读
     * @param account 账户Id
     * @param msgId 消息id
     * @return 设置成功后的消息体
     */
    ClientTbMsgInfo readOne(Long account,Long msgId);

    /**
     * 设置门店消息全部已读
     * @param account 账户Id
     * @param shopid 门店Id
     * @return 返回第一页消息体，同getMessages
     */
    Page<ClientTbMsgInfo> readAll(Long account,Long shopid);

    /**
     * 消息心跳检测，返回最新一条消息的Id
     * @param account 账户Id
     * @param shopid 门店id
     * @return 返回最新一条消息
     */
    ClientTbMsgInfo heartBeat(Long account, Long shopid);

    /**
     * 发送消息
     * @param account 账户Id
     * @param msg 消息体
     * @return 发送后的消息体
     */
    ClientTbMsgInfo sendMsg(Long account,ClientTbMsgInfo msg);
}
