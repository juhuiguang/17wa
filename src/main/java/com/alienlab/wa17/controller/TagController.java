package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.dto.ColorDto;
import com.alienlab.wa17.entity.main.MainTbTags;
import com.alienlab.wa17.service.TagService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 橘 on 2017/3/6.
 */
@Api(value = "/api/17wa-tag",description = "标签API")
@RestController
@RequestMapping(value="/api")
public class TagController {
    @Autowired
    TagService tagService;
    @ApiOperation(value="根据标签类型，获取系统库中标签信息")
    @ApiImplicitParam(name="typeName",value="标签类型",paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbTags.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @GetMapping(value="/17wa-tag/{typeName}")
    public ResponseEntity getTags(@PathVariable String typeName){
        try {
            List<MainTbTags> result=tagService.getTags(typeName);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
