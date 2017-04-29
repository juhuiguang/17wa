package com.alienlab.wa17.controller.system;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbColorCus;
import com.alienlab.wa17.entity.main.MainTbColors;
import com.alienlab.wa17.service.ColorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 橘 on 2017/4/27.
 */
@RestController
public class MainColorController {
    @Autowired
    ColorService colorService;

    @ApiOperation(value="创建新的颜色")
    @ApiImplicitParams({
            @ApiImplicitParam(name="color",value="需添加的颜色对象",paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbColors.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PostMapping(value="/17wa-sku/color/{account}")
    public ResponseEntity addColor(@PathVariable int account, @ApiParam @RequestBody MainTbColors color){
        try {
            color=colorService.addMainColor(color);
            return ResponseEntity.ok().body(color);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="删除用户账户下的颜色")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="colorid",value="需删除的颜色ID",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @DeleteMapping(value="/17wa-sku/color/{account}/{colorid}")
    public ResponseEntity deleteColor(@PathVariable int account,@PathVariable int colorid){
        try {
            boolean result=colorService.delColor(account,colorid);
            ExecResult er=new ExecResult(true,"颜色删除成功。");
            return ResponseEntity.ok().body(er);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
