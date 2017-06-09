package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbShop;
import com.alienlab.wa17.entity.client.ClientTbShopAccount;
import com.alienlab.wa17.entity.client.dto.ShopAccountDto;
import com.alienlab.wa17.entity.main.MainTbAccount;
import com.alienlab.wa17.entity.main.MainTbMarket;
import com.alienlab.wa17.entity.main.dto.MarketDto;
import com.alienlab.wa17.service.AccountService;
import com.alienlab.wa17.service.MarketService;
import com.alienlab.wa17.service.ShopService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    AccountService accountService;
    @Autowired
    MarketService marketService;

    @ApiOperation(value="获取市场列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MarketDto.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @GetMapping(value="/17wa-shop/market")
    public ResponseEntity getMarkets(){
        try{
            List<MarketDto> mks=marketService.getMarkets();
            return ResponseEntity.ok().body(mks);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="添加或更新市场")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbMarket.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PostMapping(value="/17wa-shop/market")
    public ResponseEntity addMarkets(@RequestBody MainTbMarket market){
        if(market==null){
            ExecResult er=new ExecResult(false,"市场参数传入错误");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
        if(market.getMkId()>0){
            try {
                market=marketService.updateMarket(market);
            } catch (Exception e) {
                e.printStackTrace();
                ExecResult er=new ExecResult(false,e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        }else{
            try {
                market=marketService.addMarket(market);
            } catch (Exception e) {
                e.printStackTrace();
                ExecResult er=new ExecResult(false,e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        }
        return ResponseEntity.ok().body(market);
    }
    @ApiOperation(value="删除市场")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @DeleteMapping(value="/17wa-shop/market/{marketId}")
    public ResponseEntity delMarket(@PathVariable int marketId){
        try {
            boolean result=marketService.delMarket(marketId);
            if(result){
                ExecResult er=new ExecResult(true,"市场删除成功。");
                return ResponseEntity.ok().body(er);
            }else{
                ExecResult er=new ExecResult(false,"市场删除失败了。");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


    @ApiOperation(value="根据账户获得门店列表")
    @ApiImplicitParam(name="account",value="账户id",paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbShop.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
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

    @ApiOperation(value="根据账户登录名（手机号）码获得门店列表")
    @ApiImplicitParam(name="loginname",value="登录名（手机号）",paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbShop.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @GetMapping(value="/17wa-shop/list/{loginname}")
    public ResponseEntity getShopList(@PathVariable String loginname){
        try{
            MainTbAccount account=accountService.getAccount(loginname);
            if(account==null){
                throw new Exception("未找到登录名为"+loginname+"的账户。");
            }
            List<ClientTbShop> shopes=shopService.getShopes((int)account.getAccountId());
            return ResponseEntity.ok().body(shopes);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @GetMapping(value="/17wa-shop/detail/{account}/{shopId}")
    public ResponseEntity getShop(int account,int shopId){
        try {
            ClientTbShop shop=shopService.getShop(account,shopId);
            if(shop==null){
                ExecResult er=new ExecResult(false,"未查询到门店编码为"+shopId+"的门店");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
            return ResponseEntity.ok().body(shop);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }

    }

    @ApiOperation(value="根据账户获得所有门店账户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbShopAccount.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @GetMapping(value="/17wa-shop/{account}/all")
    public ResponseEntity getAllShopAccount(@PathVariable int account){
        try{
            List<ShopAccountDto> shopAccounts=shopService.getShopAccountList(account);
            return ResponseEntity.ok().body(shopAccounts);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


    @ApiOperation(value="根据门店获得门店账户列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
        @ApiImplicitParam(name="shop",value="门店id",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbShopAccount.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
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

    @ApiOperation(value="增加/创建门店")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="shop",value="门店对象",paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbShop.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PostMapping(value="/17wa-shop/{account}")
    public ResponseEntity addShop(@PathVariable int account,@ApiParam @RequestBody ClientTbShop shop){
        try {
            shop=shopService.addShop(account,shop);
            if(shop.getShopId()>0){
                return ResponseEntity.ok().body(shop);
            }else{
                ExecResult er=new ExecResult(false,"门店信息保存失败了。");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="更新门店")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="shop",value="门店对象",paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbShop.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PutMapping(value="/17wa-shop/{account}")
    public ResponseEntity updateShop(@PathVariable int account,@ApiParam @RequestBody ClientTbShop shop){
        try {
            shop=shopService.updateShop(account,shop);
            if(shop.getShopId()>0){
                return ResponseEntity.ok().body(shop);
            }else{
                ExecResult er=new ExecResult(false,"门店信息保存失败了。");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="删除门店")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="shop",value="门店id",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @DeleteMapping(value="/17wa-shop/{account}/{shop}")
    public ResponseEntity delShop(@PathVariable int account,@PathVariable int shop){
        try {
            boolean result=shopService.delShop(account,shop);
            if(result){
                ExecResult er=new ExecResult(true,"门店删除成功。");
                return ResponseEntity.ok().body(er);
            }else{
                ExecResult er=new ExecResult(false,"门店删除失败了。");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="增加/创建门店账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="shopaccount",value="门店账户对象",paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbShopAccount.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PostMapping(value="/17wa-shop/account/{account}")
    public ResponseEntity addShop(@PathVariable int account,@ApiParam @RequestBody ClientTbShopAccount shopaccount){
        try {
            shopaccount=shopService.addAccount(account,shopaccount);
            if(shopaccount.getAccountId()>0){
                return ResponseEntity.ok().body(shopaccount);
            }else{
                ExecResult er=new ExecResult(false,"门店账户信息保存失败了。");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="更新门店账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="shopaccount",value="门店账户对象",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @DeleteMapping(value="/17wa-shop/account/{account}/{shopaccount}")
    public ResponseEntity delShopAccount(@PathVariable int account,@PathVariable int shopaccount){
        try {
            boolean result=shopService.delAccount(account,shopaccount);
            if(result){
                ExecResult er=new ExecResult(true,"门店账户删除成功。");
                return ResponseEntity.ok().body(er);
            }else{
                ExecResult er=new ExecResult(false,"门店账户删除出错了。");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="启用/停用账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="shopaccount",value="门店账户对象",paramType = "path"),
            @ApiImplicitParam(name="status",value="1启用/0停用状态",paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbShopAccount.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PutMapping(value="/17wa-shop/account/{account}/{shopaccount}")
    public ResponseEntity delShopAccount(@PathVariable int account,@PathVariable int shopaccount,@RequestParam String status){
        try {
            if(status.equals("0")){
                ClientTbShopAccount cta=shopService.setDenied(account,shopaccount);
                return ResponseEntity.ok().body(cta);
            }else{
                ClientTbShopAccount cta=shopService.setActive(account,shopaccount);
                return ResponseEntity.ok().body(cta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }




}
