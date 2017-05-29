package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.dto.ColorDto;
import com.alienlab.wa17.entity.main.MainTbTags;
import com.alienlab.wa17.service.TagService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value="获取全部系统标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name="index",value="页码",paramType = "query"),
            @ApiImplicitParam(name="size",value="长度",paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbTags.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @GetMapping(value="/17wa-tag/all")
    public ResponseEntity getTags(int index,int size){
        try {
            Page<MainTbTags> result=tagService.getTags(new PageRequest(index,size));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

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

    @ApiOperation(value="添加系统标签")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbTags.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PostMapping(value="/17wa-tag")
    public ResponseEntity addMainTags(@ApiParam @RequestBody MainTbTags tag){
        try{
            tag=tagService.addTag(tag);
            return ResponseEntity.ok().body(tag);
        }catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @ApiOperation(value="删除系统标签")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @DeleteMapping(value="/17wa-tag/{tagid}")
    public ResponseEntity delMainTags(@PathVariable int tagid){
        try {
            boolean result=tagService.delTag(tagid);
            ExecResult er=new ExecResult(true,"删除成功");
            return ResponseEntity.ok().body(er);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
