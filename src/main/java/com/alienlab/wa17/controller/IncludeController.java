package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSONArray;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbProductInclude;
import com.alienlab.wa17.entity.main.MainTbInclude;
import com.alienlab.wa17.service.IncludeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 橘 on 2017/5/21.
 */
@Api(value = "/api/17wa-include",description = "尺码明细API")
@RestController
@RequestMapping("/api")
public class IncludeController {
    @Autowired
    IncludeService includeService;
    @ApiOperation("获取系统尺码标签类型")
    @GetMapping("/17wa-include/type")
    public ResponseEntity getIncludeType(){
        try {
            List result=includeService.getIncludeType();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @ApiOperation("获取系统尺码标签")
    @GetMapping("/17wa-include")
    public ResponseEntity getAllInclude(){
        try {
            List result=includeService.getAllInclude();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @ApiOperation("新增系统尺码标签")
    @ApiImplicitParams({
        @ApiImplicitParam(name="includeName",value="标签名称",paramType = "query"),
        @ApiImplicitParam(name="includeType",value="标签类型",paramType = "query")
    })
    @PostMapping("/17wa-include")
    public ResponseEntity addInclude(@RequestParam String includeName,@RequestParam String includeType){
        try {
            MainTbInclude include=includeService.addInclude(includeName,includeType);
            return ResponseEntity.ok().body(include);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @ApiOperation("删除系统尺码标签类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name="includeId",value="标签编号",paramType = "query")
    })
    @DeleteMapping("/17wa-include")
    public ResponseEntity delInclude(int includeId){
        try {
            boolean flag=includeService.delInclude(includeId);
            if(flag){
                ExecResult er=new ExecResult(true,"删除尺码明细成功");
                return ResponseEntity.ok().body(er);
            }else{
                ExecResult er=new ExecResult(false,"删除尺码明细失败");
                return ResponseEntity.ok().body(er);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation("获取产品尺码详细")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id号",paramType = "path"),
            @ApiImplicitParam(name="productId",value="产品id号",paramType = "path")
    })
    @GetMapping("/17wa-include/product/{account}/{productId}")
    public ResponseEntity getProductIncluedes(@PathVariable int account,@PathVariable int productId){
        try {
            List<ClientTbProductInclude>result =includeService.getProductIncluedes(account,productId);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation("新增产品尺码详细，一组同时提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id号",paramType = "query"),
            @ApiImplicitParam(name="productId",value="产品id号",paramType = "query"),
            @ApiImplicitParam(name="includes",value="尺码详细表单[{sizeName:'M',includeName:'胸围',includeValue:'90A'}]")
    })
    @PostMapping("/17wa-include/product")
    public ResponseEntity addProductIncludes(
            @RequestParam int account,@RequestParam int productId,@RequestParam String includes){
        JSONArray array=JSONArray.parseArray(includes);
        try {
            List<ClientTbProductInclude> result=includeService.addProductIncludes(account,productId,array);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @ApiOperation("删除某条产品尺码标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id号",paramType = "query"),
            @ApiImplicitParam(name="includeId",value="产品尺码标签id",paramType = "query")
    })
    @DeleteMapping("/17wa-include/product")
    public ResponseEntity delProductInclude(@RequestParam int account,@RequestParam int includeId){
        try {
            boolean flag=includeService.delProductInclude(account,includeId);
            if(flag){
                ExecResult er=new ExecResult(true,"删除尺码明细成功");
                return ResponseEntity.ok().body(er);
            }else{
                ExecResult er=new ExecResult(false,"删除尺码明细失败");
                return ResponseEntity.ok().body(er);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
