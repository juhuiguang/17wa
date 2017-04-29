package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.main.MainTbColors;
import com.alienlab.wa17.service.ColorService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 橘 on 2017/4/29.
 */
@RestController
public class SystemController {
    @Autowired
    ColorService colorService;
    @PostMapping(value="/17wa-system/color")
    public ResponseEntity addMainColor(@ApiParam @RequestBody MainTbColors color){
        try {
            color=colorService.addMainColor(color);
            return ResponseEntity.ok().body(color);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @DeleteMapping(value="/17wa-system/color/{colorid}")
    public ResponseEntity deleteColor(@PathVariable int colorid){
        try {
            boolean result=colorService.delMainColor(colorid);
            ExecResult er=new ExecResult(true,"颜色删除成功。");
            return ResponseEntity.ok().body(er);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
