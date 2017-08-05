package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.AlienEntity;
import com.alienlab.db.Dao;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.controller.util.HeaderUtil;
import com.alienlab.wa17.entity.main.MainTbAccount;
import com.alienlab.wa17.service.impl.SqlFileExcutor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;


/**
 * Created by juhuiguang on 2017/2/1.
 */
@RestController
@RequestMapping(value="/api")
@Api(value = "/api/test", description = "群组的相关操作")
public class TestController {
    @Autowired
    Dao dao;

    @ApiOperation(value="测试文档",notes="这是一个文档提示的测试")
    @GetMapping("/test")
    public ResponseEntity getTest(){
        AlienEntity<MainTbAccount> alienAccount=new AlienEntity<MainTbAccount>();
        List l=dao.getDataSet("select * from tb_account");
        List result=alienAccount.list2T(l,MainTbAccount.class);
        return ResponseEntity.ok().body(alienAccount.UpdateSql((MainTbAccount)result.get(0)));
    }

    @Autowired
    SqlFileExcutor sqlFileExcutor;

    @ApiOperation(value="获取账户配置")
    @GetMapping(value="/17wa-test/ctdb")
    public ResponseEntity createDb(){
        try {
            sqlFileExcutor.createNewAccountDb("test1");
            return ResponseEntity.ok("test1");
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(500).body(er);
        }
    }
}
