package com.alienlab.sms;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.controller.util.ExecResult;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value="向目标手机发送验证码")
    @PostMapping("/sms/code")
    public ResponseEntity sendCodeSms(String phone){
        String code=SmsCodePool.getRandomCode(6);
        try {
            SendSmsResponse sendSmsResponse=smsService.sendSms(phone,code);
            JSONObject result=new JSONObject();
            result.put("smsresponse",sendSmsResponse);
            //result.put("code",code);
            return ResponseEntity.ok(result);
        } catch (ClientException e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(500).body(er);
        }
    }


}
