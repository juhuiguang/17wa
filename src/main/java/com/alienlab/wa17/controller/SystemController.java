package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.dto.ColorDto;
import com.alienlab.wa17.entity.main.MainTbColorSeries;
import com.alienlab.wa17.entity.main.MainTbColors;
import com.alienlab.wa17.entity.main.MainTbSize;
import com.alienlab.wa17.entity.main.MainTbSizetype;
import com.alienlab.wa17.service.ColorService;
import com.alienlab.wa17.service.SizeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by 橘 on 2017/4/29.
 */
@RestController
@RequestMapping(value="/api")
public class SystemController {
    @Autowired
    ColorService colorService;
    @Autowired
    SizeService sizeService;

    @ApiOperation(value="获取系统色系")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbColorSeries.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @GetMapping(value="/17wa-system/color/series")
    public ResponseEntity getColorSeries(){
        try {
            List<MainTbColorSeries> result=colorService.getColorSeries();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="更新系统色系")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbColorSeries.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @PostMapping(value="/17wa-system/color/series")
    public ResponseEntity addColorSeries(@ApiParam @RequestBody MainTbColorSeries series){
        try{
            series=colorService.addColorSeries(series);
            return ResponseEntity.ok().body(series);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="更新系统色系")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbColorSeries.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @PutMapping(value="/17wa-system/color/series")
    public ResponseEntity updateColorSeries(@ApiParam @RequestBody MainTbColorSeries series){
        try{
            series=colorService.updateColorSeries(series);
            return ResponseEntity.ok().body(series);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="删除系统色系")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @DeleteMapping(value="/17wa-system/color/series/{seriesid}")
    public ResponseEntity delColorSeries(@PathVariable int seriesid){
        try{
            boolean result=colorService.delColorSeries(seriesid);
            ExecResult er=new ExecResult(true,"删除色系成功");
            return ResponseEntity.ok().body(er);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @ApiOperation(value="获取系统颜色列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ColorDto.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @PostMapping(value="/17wa-system/color")
    public ResponseEntity getColors(){
        try {
            List<ColorDto> result=colorService.getMainColors();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }

    }

    @ApiOperation(value="添加系统颜色")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbColors.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @PostMapping(value="/17wa-system/color")
    public ResponseEntity addMainColor(@ApiParam @RequestBody MainTbColors color){
        try {
            color.setColorType("系统");
            color=colorService.addMainColor(color);
            return ResponseEntity.ok().body(color);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="删除系统颜色")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
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

    @ApiOperation(value="获取系统尺码类型")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbSizetype.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @GetMapping(value="/17wa-system/size/type")
    public ResponseEntity getSizetypes(){
        try {
            List<MainTbSizetype> result=sizeService.getSizeType();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="新增系统尺码类型")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbSizetype.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @PostMapping(value="/17wa-system/size/type")
    public ResponseEntity addSizeType(@ApiParam @RequestBody MainTbSizetype type){
        try{

            type=sizeService.addSizeType(type);
            return ResponseEntity.ok().body(type);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="更新系统尺码类型")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbSizetype.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @PutMapping(value="/17wa-system/size/type")
    public ResponseEntity updateSizeType(@ApiParam @RequestBody MainTbSizetype type){
        try{
            type=sizeService.updateSizeType(type);
            return ResponseEntity.ok().body(type);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="删除系统尺码类型")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @DeleteMapping(value="/17wa-system/size/type/{typeid}")
    public ResponseEntity delSizeType(@PathVariable int typeid){
        try{
            boolean result=sizeService.delSizeType(typeid);
            ExecResult er=new ExecResult(true,"删除尺码类型成功");
            return ResponseEntity.ok().body(er);
        }catch(Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="添加系统尺码")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = MainTbSize.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @PostMapping(value="/17wa-system/size")
    public ResponseEntity addMainSize(@ApiParam @RequestBody MainTbSize size){
        try {
            size=sizeService.addMainSize(size);
            return ResponseEntity.ok().body(size);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


    @ApiOperation(value="删除系统尺码")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @DeleteMapping(value="/17wa-system/size/{sizeid}")
    public ResponseEntity deleteMainSize(@PathVariable int sizeid){
        try {
            boolean result=sizeService.delMainSize(sizeid);
            ExecResult er=new ExecResult(true,"尺码删除成功。");
            return ResponseEntity.ok().body(er);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


}
