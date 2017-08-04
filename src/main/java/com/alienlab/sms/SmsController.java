package com.alienlab.sms;

import com.alienlab.wa17.controller.util.ExecResult;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 橘 on 2017/8/4.
 */
@RestController
@RequestMapping("/api")
public class SmsController {
    @Autowired
    SmsService smsService;
    @PostMapping("/sms/code")
    public ResponseEntity sendCodeSms(String phone){
        String code=SmsCodePool.getRandomCode(6);
        try {
            SendSmsResponse sendSmsResponse=smsService.sendSms(phone,code);
            return ResponseEntity.ok(sendSmsResponse);
        } catch (ClientException e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(500).body(er);
        }
    }
}
