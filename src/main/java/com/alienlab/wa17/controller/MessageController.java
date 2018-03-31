package com.alienlab.wa17.controller;

import com.alienlab.wa17.entity.client.ClientTbMsgInfo;
import com.alienlab.wa17.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by 橘 on 2017/2/21.
 */
@Api(value = "/api/17wa-message",description = "消息api")
@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    MessageService messageService;

    @ApiOperation(value="心跳获取新消息")
    @GetMapping("/17wa-message/heartbeat/{account}/{shopid}")
    public ResponseEntity msgHeartbeat(@PathVariable Long account,@PathVariable Long shopid){
       ClientTbMsgInfo msgInfo= messageService.heartBeat(account,shopid);
       return ResponseEntity.ok(msgInfo);
    }


    @ApiOperation(value="获取所有消息")
    @GetMapping("/17wa-message/message/{account}/{shopid}")
    public ResponseEntity getMessages(@PathVariable Long account,@PathVariable Long shopid,@RequestParam(required = false) Integer index,@RequestParam(required = false) Integer size){
        if(index==null)index=0;
        if(size==null)size=10;
        Page<ClientTbMsgInfo> result= messageService.getMessages(account,shopid,index,size);
        return ResponseEntity.ok(result);
    }

}
