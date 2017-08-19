package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.sms.SmsCodePool;
import com.alienlab.utils.TypeUtils;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbGradeOption;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.entity.main.MainTbAccount;
import com.alienlab.wa17.entity.main.MainTbAccountSetting;
import com.alienlab.wa17.service.AccountService;
import com.alienlab.wa17.service.GradeOptionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 橘 on 2017/2/21.
 */
@Api(value = "/api/17wa-account",description = "账户接口")
@RestController
@RequestMapping(value="/api")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    GradeOptionService gradeOptionService;

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

    @ApiOperation(value="注册账户")
    @PostMapping("/17wa-account/reg")
    public ResponseEntity addAccount(@RequestBody Map body){
        String phone= com.alibaba.fastjson.util.TypeUtils.castToString(body.get("phone"));
        String code=com.alibaba.fastjson.util.TypeUtils.castToString(body.get("code"));
        if(SmsCodePool.CodePool.containsKey(phone)){
            if(SmsCodePool.CodePool.get(phone).equalsIgnoreCase(code)){
                MainTbAccount account=new MainTbAccount();
                account.setAccountCode(phone);
                account.setAccountLoginname(phone);
                account.setAccountStatus("1");
                account=accountService.addAccount(account);
                return ResponseEntity.ok(account);
            }else{
                ExecResult er=new ExecResult(false,"验证码错误");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        }else{
            ExecResult er=new ExecResult(false,"未查询到您的短信验证码");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }

    }

    @ApiOperation(value="激活账户")
    @PostMapping("/17wa-account/active")
    public ResponseEntity activeAccount(@RequestParam int accountId){
        try {
            MainTbAccount account=accountService.activeAccount(accountId);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(500).body(er);
        }
    }



    @ApiOperation(value="用户登录",notes="输入账户，选择门店后，选择门店用户名，输入登录密码进行登录")
    @ApiImplicitParams({
        @ApiImplicitParam(name="account",value="账户id",paramType = "query"),
            @ApiImplicitParam(name="shop",value="门店id",paramType = "query"),
            @ApiImplicitParam(name="username",value="门店用户",paramType = "query"),
            @ApiImplicitParam(name="pwd",value="登录密码",paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbShopAccount.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
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

    @ApiOperation(value="获取积分规则",notes="获取账户积分规则")
    @GetMapping(value="/17wa-account/grade/{account}")
    public ResponseEntity loadGradeGroup(@PathVariable  int account){
        try {
            List<ClientTbGradeOption> result=gradeOptionService.getOptions(account);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="新增积分规则",notes="新增积分规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="grade",value="抵扣积分",paramType = "query"),
            @ApiImplicitParam(name="money",value="抵扣金额",paramType = "query")
    })
    @PostMapping(value="/17wa-account/grade/{account}")
    public ResponseEntity addGradegroup(@PathVariable int account,@RequestParam int grade,@RequestParam int money){
        try {
            ClientTbGradeOption option=new ClientTbGradeOption();
            option.setGradeMoney(money);
            option.setGradeValue(grade);
            option=gradeOptionService.addOption(option,account);
            return ResponseEntity.ok().body(option);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @DeleteMapping(value="/17wa-account/grade/{account}/{groupId}")
    public ResponseEntity delGradeGroup(@PathVariable int account,@PathVariable int groupId){
        try {
            boolean result=gradeOptionService.delOption(groupId,account);
            if(result){
                ExecResult er=new ExecResult(true,"删除成功");
                return ResponseEntity.ok().body(er);
            }else{
                ExecResult er=new ExecResult(false,"删除发生错误");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="保存账户设置，如果已经存在直接更新，如果不存在，创建设置数据")
    @PostMapping(value="/17wa-account/settings")
    public ResponseEntity saveSettings(@RequestBody String post){
        try{
            JSONObject param=JSONObject.parseObject(post);
            if(param.containsKey("account")){
                try {
                    MainTbAccountSetting settings=accountService.saveSetting(param.getInteger("account"),post);
                    return ResponseEntity.ok().body(settings);
                } catch (Exception e) {
                    e.printStackTrace();
                    ExecResult er=new ExecResult(false,e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
                }
            }else{
                ExecResult er=new ExecResult(false,"请传入account参数");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }

        }catch (Exception e){
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获取账户配置")
    @PostMapping(value="/17wa-account/settings/{account}")
    public ResponseEntity saveSettings(@PathVariable int account){
        try{
            MainTbAccountSetting settings=accountService.getSetting(account);
            return ResponseEntity.ok().body(settings);
        }catch (Exception e){
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


}
