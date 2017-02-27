package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbShop;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 橘 on 2017/2/27.
 */
@Api(value = "/api/17wa-shop",description = "门店接口")
@RestController
@RequestMapping(value="/api")
public class ShopController {
    @Autowired
    ShopService shopService;


    @ApiOperation(value="根据账户获得门店列表")
    @ApiImplicitParam(name="account",value="账户id",paramType = "path")
    @GetMapping(value="/17wa-shop/{account}")
    public ResponseEntity getShopList(@PathVariable int account){
        try{
            List<ClientTbShop> shopes=shopService.getShopes(account);
            return ResponseEntity.ok().body(shopes);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="根据账户获得门店列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
        @ApiImplicitParam(name="shop",value="门店id",paramType = "path")
    })
    @GetMapping(value="/17wa-shop/{account}/{shop}")
    public ResponseEntity getShopAccount(@PathVariable int account,@PathVariable int shop){
        try{
            List<ClientTbShopAccount> shopAccounts=shopService.getShopAccountList(account,shop);
            return ResponseEntity.ok().body(shopAccounts);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

}
