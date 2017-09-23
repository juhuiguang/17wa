package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.api.AppInfo;
import com.alienlab.wa17.entity.api.AppVersion;
import com.alienlab.wa17.entity.client.dto.ColorDto;
import com.alienlab.wa17.entity.client.dto.SizeDto;
import com.alienlab.wa17.entity.main.*;
import com.alienlab.wa17.service.ColorService;
import com.alienlab.wa17.service.SizeService;
import com.alienlab.wa17.service.SystemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    SystemService systemService;

    @Value("${17wa.pgy.apikey}")
    String pgyapikey;
    @Value("${17wa.pgy.appid}")
    String pgyappid;

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
    @GetMapping(value="/17wa-system/color")
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
    @ApiOperation(value="获取系统尺码")
    @GetMapping(value="/17wa-system/size")
    public ResponseEntity getSizes(){
        try {
            List<SizeDto> result=sizeService.getMainSizes();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
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

    @GetMapping(value="/17wa-system/imageserver")
    public ResponseEntity getEnableImageServer(){
        try {
            MainTbImageserver result=systemService.getEnableImageServer();
            if(result==null){
                ExecResult er=new ExecResult(false,"无可用的图片服务器");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @GetMapping(value="/17wa-system/dbserver")
    public ResponseEntity getEnableDBServer(){
        try {
            MainTbDatabaseServer result=systemService.getEnableDatabaseServer();
            if(result==null){
                ExecResult er=new ExecResult(false,"无可用的数据库服务器");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


    @GetMapping(value="/17wa-system/version/{v}")
    public ResponseEntity checkNewVersion(@PathVariable  Integer v){
        String url="http://www.pgyer.com/apiv1/app/builds";

        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("aId", pgyappid);
        requestEntity.add("_api_key", pgyapikey);

        RestTemplate template=new RestTemplate();
        ResponseEntity result=template.postForEntity(url,requestEntity, JSONObject.class);
        if(result.getStatusCode().is2xxSuccessful()){
            JSONObject jo=(JSONObject)result.getBody();
            JSONObject data=jo.getJSONObject("data");
            JSONArray list=data.getJSONArray("list");
            List<AppVersion> versions=JSON.parseArray(list.toJSONString(), AppVersion.class);

            if(versions!=null&&versions.size()>0){
                AppVersion latestVersion=versions.get(0);
                if(Integer.valueOf(latestVersion.getAppBuildVersion())>v){
                    MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
                    param.add("aKey",latestVersion.getAppKey());
                    param.add("_api_key",pgyapikey);
                    url="http://www.pgyer.com/apiv1/app/view";
                    ResponseEntity newVersion=template.postForEntity(url,param,JSONObject.class);
                    if(newVersion.getStatusCode().is2xxSuccessful()){
                        JSONObject versionJson=(JSONObject)newVersion.getBody();
                        JSONObject versionData=versionJson.getJSONObject("data");
                        AppInfo app=JSON.parseObject(versionData.toJSONString(),AppInfo.class);
                        app.setAppShortcutUrl("http://www.pgyer.com/"+app.getAppShortcutUrl());
                        return ResponseEntity.ok(app);

                    }
                }
            }
            ExecResult er=new ExecResult(false,"您已是最新本版");
            return ResponseEntity.status(500).body(er);
        }else{
            ExecResult er=new ExecResult(false,"获取版本信息错误");
            return ResponseEntity.status(500).body(er);
        }

    }




}
