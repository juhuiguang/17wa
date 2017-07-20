package com.alienlab.wa17.controller;

import com.alienlab.wa17.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 橘 on 2017/2/21.
 */
@Controller
public class CustomController {
    @Autowired
    ImageService imageService;



    @GetMapping(value="/api/custom/{account}/{cusid}")
    @ResponseBody
    public ResponseEntity getSharePic(@PathVariable int account,@PathVariable Long cusid){
        try {
            String result=imageService.createCustomShareImage(account,cusid);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
