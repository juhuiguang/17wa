package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.*;
import com.alienlab.wa17.entity.client.dto.ColorDto;
import com.alienlab.wa17.entity.client.dto.SizeDto;
import com.alienlab.wa17.entity.main.MainTbColors;
import com.alienlab.wa17.service.ColorService;
import com.alienlab.wa17.service.ProductService;
import com.alienlab.wa17.service.SizeService;
import com.alienlab.wa17.service.SkuService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘 on 2017/2/4.
 */
@Api(value = "/api/17wa-sku",description = "产品sku")
@RestController
@RequestMapping(value="/api")
public class SkuController {
    @Autowired
    ColorService colorService;
    @Autowired
    SizeService sizeService;
    @Autowired
    SkuService skuService;
    @Autowired
    ProductService productService;


    @ApiOperation(value="根据账户获得合并后的颜色清单")
    @ApiImplicitParam(name="account",value="账户id",paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ColorDto.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @GetMapping(value="/17wa-sku/color/{account}")
    public ResponseEntity getColors(@PathVariable int account){
        try {
            List<ColorDto> result=colorService.getColors(account);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="创建账户下新的颜色")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="color",value="需添加的颜色对象",paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbColorCus.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PostMapping(value="/17wa-sku/color/{account}")
    public ResponseEntity addColor(@PathVariable int account,@ApiParam @RequestBody ClientTbColorCus color){
        try {
            color=colorService.addColor(account,color);
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





    @ApiOperation(value="根据账户获得合并后的尺码清单")
    @ApiImplicitParam(name="account",value="账户id",paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = SizeDto.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @GetMapping(value="/17wa-sku/size/{account}")
    public ResponseEntity getSizes(@PathVariable int account){
        try {
            List<SizeDto> result=sizeService.getSizes(account);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="创建账户下新的尺码")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="size",value="需添加的尺码对象",paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbSizeCus.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PostMapping(value="/17wa-sku/size/{account}")
    public ResponseEntity addSize(@PathVariable int account,@ApiParam @RequestBody ClientTbSizeCus size){
        try {
            size=sizeService.addSize(account,size);
            return ResponseEntity.ok().body(size);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="删除用户账户下的尺码")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="sizeid",value="需删除的尺码ID",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @DeleteMapping(value="/17wa-sku/size/{account}/{sizeid}")
    public ResponseEntity deleteSize(@PathVariable int account,@PathVariable int sizeid){
        try {
            boolean result=sizeService.delSize(account,sizeid);
            ExecResult er=new ExecResult(true,"尺码删除成功。");
            return ResponseEntity.ok().body(er);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="设置多个单品状态：上架、下架、缺货")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbProductSku.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PostMapping(value="/17wa-sku/status/{account}")
    public ResponseEntity setSkuStatus(@PathVariable int account,@RequestBody String skusjson){
        try {
            JSONArray skus= JSON.parseArray(skusjson);
            List<ClientTbProductSku> result=new ArrayList<ClientTbProductSku>();
            for(int i=0;i<skus.size();i++){
                JSONObject sku=skus.getJSONObject(i);
                if(sku.containsKey("skuid")&&sku.containsKey("status")){
                    Long sid=sku.getLong("skuid");
                    String status=sku.getString("status");
                    ClientTbProductSku s=skuService.setStatus(account,sid,status);
                    result.add(s);
                }
            }
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="设置单品状态：上架、下架、缺货")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="skuid",value="需指定状态的单品编码（sku_id）",paramType = "path"),
            @ApiImplicitParam(name="status",value="需设置的状态：请直接传入中文状态名称(上架、下架、缺货)",paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbProductSku.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PutMapping(value="/17wa-sku/status/{account}/{skuid}")
    public ResponseEntity setSkuStatus(@PathVariable int account,@PathVariable int skuid,@RequestParam String status){
        try {
            ClientTbProductSku sku=skuService.setStatus(account,skuid,status);
            return ResponseEntity.ok().body(sku);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="批量设置单品状态：上架、下架、缺货")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="productId",value="需指定状态的产品编码（productId）",paramType = "path"),
            @ApiImplicitParam(name="status",value="需设置的状态：请直接传入中文状态名称(上架、下架、缺货)",paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbProduct.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class),
    })
    @PostMapping(value="/17wa-sku/status/all/{account}/{productId}")
    public ResponseEntity setSkuStatusBatch(@PathVariable int account,@PathVariable long productId,@RequestParam String status){
        try {
            ClientTbProduct product=productService.changeProductStatus(account,productId,status);
            return ResponseEntity.ok().body(product);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


}
