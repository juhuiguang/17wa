package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.service.CustomService;
import com.alienlab.wa17.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 橘 on 2017/2/21.
 */
@RestController
@RequestMapping("/api")
public class CustomController {

    @Autowired
    CustomService customService;

    @ApiOperation(value="新增、修改客户")
    @PostMapping("/17wa-custom/{account}")
    public ResponseEntity addCustom(@PathVariable int account, @RequestBody ClientTbCustom custom){
        try {
            custom=customService.addCustom(account,custom);
            return ResponseEntity.ok(custom);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
