package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.entity.main.MainTbAccount;
import com.alienlab.wa17.service.AccountService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 橘 on 2017/2/21.
 */
@Api(value = "/api/17wa-account",description = "账户接口")
@RestController
@RequestMapping(value="/api")
public class AccountController {
    @Autowired
    AccountService accountService;

    @ApiOperation(value="获取账户信息",notes="根据当前登录者输入的手机号码，获取该手机号对应的账户信息。")
    @ApiImplicitParam(name="loginname",value="账户登录名，即手机号码",paramType = "path")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = MainTbAccount.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @GetMapping(value="/17wa-account/{loginname}")
    public ResponseEntity getAccount(@PathVariable String loginname){
        try {
            MainTbAccount account=accountService.getAccount(loginname);
            return ResponseEntity.ok().body(account);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="用户登录",notes="输入账户，选择门店后，选择门店用户名，输入登录密码进行登录")
    @ApiImplicitParams({
        @ApiImplicitParam(name="account",value="账户id",paramType = "query"),
            @ApiImplicitParam(name="shop",value="门店id",paramType = "query"),
            @ApiImplicitParam(name="username",value="门店用户",paramType = "query"),
            @ApiImplicitParam(name="pwd",value="登录密码",paramType = "query")
    })
    @PostMapping(value="/17wa-account/")
    public ResponseEntity doShopLogin(@RequestParam int account,@RequestParam int shop,
                                      @RequestParam String username,@RequestParam String pwd){
        try {
            ClientTbShopAccount user=accountService.shopLogin(account,shop,username,pwd);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
