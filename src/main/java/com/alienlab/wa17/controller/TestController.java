package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by juhuiguang on 2017/2/1.
 */
@RestController
@RequestMapping
public class TestController {
    @GetMapping("/test")
    public String getTest(){
        return "abc";
    }
}
