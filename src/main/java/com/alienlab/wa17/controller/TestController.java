package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.AlienEntity;
import com.alienlab.db.Dao;
import com.alienlab.wa17.controller.util.HeaderUtil;
import com.alienlab.wa17.entity.main.MainTbAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by juhuiguang on 2017/2/1.
 */
@RestController
@RequestMapping
public class TestController {
    @GetMapping("/test")
    public String getTest(){
        AlienEntity<MainTbAccount> alienAccount=new AlienEntity<MainTbAccount>();
        Dao dao=new Dao();
        List l=dao.getDataSet("select * from tb_account");
        List result=alienAccount.list2T(l,MainTbAccount.class);
        return JSONObject.toJSONString(result);
    }
}
