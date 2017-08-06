package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.*;
import com.alienlab.wa17.entity.client.dto.DispatchDto;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.InventoryDto;
import com.alienlab.wa17.entity.client.dto.SkuShopInventoryDto;
import com.alienlab.wa17.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by 橘 on 2017/2/21.
 */
@Api(value = "/api/17wa-inventory",description = "库存api")
@RestController
@RequestMapping("/api")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;
    @ApiOperation(value="获得指定产品库存sku及库存列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="门店编码",paramType = "path"),
            @ApiImplicitParam(name="productId",value="产品编码",paramType = "path")
    })
    @GetMapping("/17wa-inventory/{account}/{shopId}/{productId}")
    public ResponseEntity getAllSkuInventory(@PathVariable int account, @PathVariable long shopId,@PathVariable Long productId){
        try{
            List<InventoryDto> result= inventoryService.loadInventory(account,shopId,productId);
            return ResponseEntity.ok().body(result);
        }catch(Exception e){
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="设置库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="门店编码",paramType = "path"),
            @ApiImplicitParam(name="skuid",value="单品编码",paramType = "query"),
            @ApiImplicitParam(name="amount",value="数量",paramType = "query"),
            @ApiImplicitParam(name="type",value="库存类型（中文）：入库、出库、销售、退货、盘点、调入、调出",paramType = "query")
    })
    @PostMapping("/17wa-inventory/{account}/{shopId}")
    public ResponseEntity setInventory(@PathVariable int account,
                                       @PathVariable long shopId,
                                       @RequestParam long skuid,
                                       @RequestParam int amount,@RequestParam String type){
        try {
            ClientTbInventory inventory=inventoryService.setInventory(account,shopId,skuid,amount,type);
            return ResponseEntity.ok().body(inventory);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获取单品全部库存明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="skuid",value="单品编码",paramType = "path"),
            @ApiImplicitParam(name="startDate",value="开始日期(2017-03-05)",paramType = "query"),
            @ApiImplicitParam(name="endDate",value="截止日期(2017-03-25)",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码(0开始)",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页长度",paramType = "query")
    })
    @GetMapping("/17wa-inventory/detail/{account}/{skuid}")
    public ResponseEntity getInventoryDetails(@PathVariable int account,@PathVariable long skuid,
                                              @RequestParam String startDate, @RequestParam String endDate,
                                              @RequestParam int index,@RequestParam int size){
        try {
            Page<InventoryDetailDto> details=inventoryService.loadDetails(account,skuid,startDate,endDate,new PageRequest(index,size));
            JSONArray stat=inventoryService.getInventoryStat(account,skuid,startDate,endDate);
            JSONObject result=new JSONObject();
            result.put("details",details);
            result.put("stat",stat);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @ApiOperation(value="按状态查询单品库存明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="skuid",value="单品编码",paramType = "path"),
            @ApiImplicitParam(name="startDate",value="开始日期(2017-03-05)",paramType = "query"),
            @ApiImplicitParam(name="endDate",value="截止日期(2017-03-25)",paramType = "query"),
            @ApiImplicitParam(name="status",value="库存状态",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码(0开始)",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页长度",paramType = "query")
    })
    @GetMapping("/17wa-inventory/detail/status/{account}/{skuid}")
    public ResponseEntity getInventoryDetailsByStatus(@PathVariable int account,@PathVariable long skuid,
                                              @RequestParam String startDate, @RequestParam String endDate,
                                              @RequestParam String status,
                                              @RequestParam int index,@RequestParam int size){
        try {
            Page<InventoryDetailDto> details=inventoryService.loadDetailsByStatus(account,skuid,startDate,endDate,status,new PageRequest(index,size));
            JSONArray stat=inventoryService.getInventoryStatByStatus(account,skuid,startDate,endDate,status);
            JSONObject result=new JSONObject();
            result.put("details",details);
            result.put("stat",stat);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获取指定产品的全部库存明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="productId",value="产品编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="门店id",paramType = "path"),
            @ApiImplicitParam(name="startDate",value="开始日期(2017-03-05)",paramType = "query"),
            @ApiImplicitParam(name="endDate",value="截止日期(2017-03-25)",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码(0开始)",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页长度",paramType = "query")
    })
    @GetMapping("/17wa-inventory/product/{account}/{productId}/{shopId}")
    public ResponseEntity getInventoryDetails(@PathVariable int account,@PathVariable long productId,@PathVariable long shopId,
                                              @RequestParam String startDate, @RequestParam String endDate,
                                              @RequestParam int index,@RequestParam int size){
        try {
            Page<InventoryDetailDto> details=inventoryService.loadDetailsByProduct(account,productId,shopId,startDate,endDate,new PageRequest(index,size));
            JSONArray stat=inventoryService.getInventoryStatByProd(account,productId,shopId,startDate,endDate);
            JSONObject result=new JSONObject();
            result.put("details",details);
            result.put("stat",stat);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="按状态查询产品全部库存明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="productId",value="产品编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="门店id",paramType = "path"),
            @ApiImplicitParam(name="startDate",value="开始日期(2017-03-05)",paramType = "query"),
            @ApiImplicitParam(name="endDate",value="截止日期(2017-03-25)",paramType = "query"),
            @ApiImplicitParam(name="status",value="库存状态",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码(0开始)",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页长度",paramType = "query")
    })
    @GetMapping("/17wa-inventory/product/status/{account}/{productId}/{shopId}")
    public ResponseEntity getInventoryDetailsByStatus(@PathVariable int account,@PathVariable long productId,@PathVariable long shopId,
                                                      @RequestParam String startDate, @RequestParam String endDate,
                                                      @RequestParam String status,
                                                      @RequestParam int index,@RequestParam int size){
        try {
            Page<InventoryDetailDto> details=inventoryService.loadDetailsByProductAndStatus(account,productId,shopId,startDate,endDate,status,new PageRequest(index,size));
            JSONArray stat=inventoryService.getInventoryStatByStatusAndProd(account,productId,shopId,startDate,endDate,status);
            JSONObject result=new JSONObject();
            result.put("details",details);
            result.put("stat",stat);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


    @ApiOperation(value="获取门店的全部库存记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="门店id",paramType = "path"),
            @ApiImplicitParam(name="status",value="库存类型",paramType = "query"),
            @ApiImplicitParam(name="startDate",value="开始日期(2017-03-05)",paramType = "query"),
            @ApiImplicitParam(name="endDate",value="截止日期(2017-03-25)",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码(0开始)",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页长度",paramType = "query"),
            @ApiImplicitParam(name="keyword",value="查询关键字",paramType = "query")
    })
    @GetMapping("/17wa-inventory/product/{account}/{shopId}")
    public ResponseEntity getInventoryDetails(@PathVariable int account,@PathVariable long shopId,
                                              @RequestParam String status,
                                              @RequestParam String startDate, @RequestParam String endDate,
                                              @RequestParam int index,@RequestParam int size,@RequestParam(required = false) String keyword){
        try {
            String isall="0";
            if(status.equals("全部"))isall="1";
            Page<InventoryDetailDto> details=inventoryService.loadDetails(account,shopId,status,isall,startDate,endDate,new PageRequest(index,size),keyword);
            JSONArray stat=inventoryService.getInventoryStat(account,shopId,status,isall,startDate,endDate,keyword);
            JSONObject result=new JSONObject();
            result.put("details",details);
            result.put("stat",stat);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


    @ApiOperation(value="设置产品库存状态:正常，异常,未盘点")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="productid",value="产品编码",paramType = "query"),
            @ApiImplicitParam(name="shopid",value="店铺编码",paramType = "query"),
            @ApiImplicitParam(name="status",value="状态值：正常、异常、未盘点",paramType = "query")
    })
    @PostMapping("/17wa-inventory/error/{account}")
    public ResponseEntity setProductInventoryStatus(@PathVariable int account,@RequestParam long productid,@RequestParam long shopid,@RequestParam String status){
        try {
            ClientTbProductInventoryStatus inventoryStatus=inventoryService.setProductInventoryStatus(account,productid,shopid,status);
            return ResponseEntity.ok().body(inventoryStatus);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @ApiOperation(value="发起门店之间调货")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="fromShopId",value="调出店铺",paramType = "query"),
            @ApiImplicitParam(name="toShopId",value="调入店铺",paramType = "query"),
            @ApiImplicitParam(name="skuId",value="调货单品",paramType = "query"),
            @ApiImplicitParam(name="amount",value="调货数量",paramType = "query")
    })
    @PostMapping("/17wa-inventory/dispatch/{account}")
    public ResponseEntity addDispatch(@PathVariable int account, @RequestParam long fromShopId,
                                      @RequestParam long toShopId, @RequestParam long skuId, @RequestParam int amount){

        try {
            ClientTbDispatch dispatch=inventoryService.addDispatch(account,fromShopId,toShopId,skuId,amount);
            return ResponseEntity.ok().body(dispatch);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="确认调货完成")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="dispatchId",value="调货编码",paramType = "query"),
            @ApiImplicitParam(name="shopId",value="当前店铺编码",paramType = "query")
    })
    @PutMapping("/17wa-inventory/dispatch/{account}")
    public ResponseEntity confirmDispatch(@PathVariable int account,@RequestParam long dispatchId,@RequestParam long shopId){
        try{
            ClientTbDispatch dispatch=inventoryService.confirmDispatch(account,dispatchId,shopId);
            return ResponseEntity.ok().body(dispatch);
        }catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获取当前门店的调货记录表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="当前店铺编码",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页长度",paramType = "query")
    })
    @GetMapping("/17wa-inventory/dispatch/{account}")
    public ResponseEntity getDispatch(@PathVariable int account,@RequestParam long shopId,@RequestParam int index,@RequestParam int size){
        try{
            Page<DispatchDto> result=inventoryService.getDispatch(account,shopId,index,size);
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获取指定产品的跨门店库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="productId",value="产品编码",paramType = "path")
    })
    @GetMapping("/17wa-inventory/shop/{account}/{productId}")
    public ResponseEntity getSkuShopList(@PathVariable int account, @PathVariable long productId){
        try{
            List<SkuShopInventoryDto> result=inventoryService.getSkuShopList(account,productId);
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @ApiOperation(value="重置门店盘点状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="门店编码",paramType = "path")
    })
    @GetMapping("/17wa-inventory/resetcheck/{account}/{shopId}")
    public ResponseEntity resetInventoryCheck(@PathVariable int account, @PathVariable long shopId){
        try{
            boolean result=inventoryService.resetShopInventoryStatus(account,shopId);
            if(result){
                ExecResult er=new ExecResult(result,"盘点状态已重置");
                return ResponseEntity.ok().body(er);
            }else{
                ExecResult er=new ExecResult(result,"盘点状态重置错误");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        }catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

//    @ApiOperation(value="手动盘点（老版本，整体提交）")
//    @PostMapping("/17wa-inventory/check")
//    public ResponseEntity checkInventory(@RequestBody String body){
//        JSONObject param=JSONObject.parseObject(body);
//        try{
//            if(param.containsKey("account")){
//                if(param.containsKey("shopId")){
//                    int account=param.getInteger("account");
//                    long shopId=param.getLong("shopId");
//                    if(param.containsKey("details")){
//                        JSONArray array=param.getJSONArray("details");
//                        List<ClientTbProduct> products=inventoryService.checkShopInventory(account,shopId,array);
//                        JSONObject result=new JSONObject();
//                        JSONArray normal=new JSONArray();
//                        JSONArray error=new JSONArray();
//                        for(ClientTbProduct p:products){
//                            if(p.getInventroyStatus().equals("正常")){
//                                normal.add(p);
//                            }
//                        }
//                        for(ClientTbProduct p:products){
//                            if(p.getInventroyStatus().equals("异常")){
//                                error.add(p);
//                            }
//                        }
//                        result.put("normal",normal);
//                        result.put("error",error);
//                        return ResponseEntity.ok().body(result);
//                    }else{
//                        ExecResult er=new ExecResult(false,"未正确指定details参数");
//                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
//                    }
//
//                }else{
//                    ExecResult er=new ExecResult(false,"未正确指定shopId参数");
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
//                }
//            }else{
//                ExecResult er=new ExecResult(false,"未正确指定account参数");
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
//            }
//
//        }catch(Exception e){
//            ExecResult er=new ExecResult(false,e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
//        }
//
//    }


    @ApiOperation(value="手动盘点（新版本，单个产品提交）")
    @PostMapping("/17wa-inventory/singlecheck")
    public ResponseEntity checkSingleInventory(@RequestBody String body){
        JSONObject param=JSONObject.parseObject(body);
        try{
            if(param.containsKey("account")){
                if(param.containsKey("shopId")){
                    int account=param.getInteger("account");
                    long shopId=param.getLong("shopId");
                    if(param.containsKey("details")){
                        JSONArray array=param.getJSONArray("details");
                        boolean result=inventoryService.checkSingleShopInventory(account,shopId,array);
                        ExecResult er=new ExecResult(result,result?"保存成功":"保存失败");
                        return ResponseEntity.ok().body(er);
                    }else{
                        ExecResult er=new ExecResult(false,"未正确指定details参数");
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
                    }

                }else{
                    ExecResult er=new ExecResult(false,"未正确指定shopId参数");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
                }
            }else{
                ExecResult er=new ExecResult(false,"未正确指定account参数");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }

        }catch(Exception e){
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }

    }

    @ApiOperation(value="扫码盘点提交数据")
    @PostMapping("/17wa-inventory/scan")
    public ResponseEntity scanInventory(@RequestBody String body){
        JSONObject param=JSONObject.parseObject(body);
        try {
            if (param.containsKey("account")&&param.containsKey("shopId")&&param.containsKey("skuId")&&param.containsKey("amount")) {
                int account=param.getInteger("account");
                long shopId=param.getLong("shopId");
                long skuId=param.getLong("skuId");
                int amount=param.getInteger("amount");
                ClientTbInventoryTemp temp=inventoryService.addTempInventoryAmount(account,shopId, skuId,amount);
                return ResponseEntity.ok(temp);
            } else {
                ExecResult er = new ExecResult(false, "需指定参数：account,shopId,skuId,amount");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        }catch(Exception e){
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }



    @ApiOperation(value="扫码盘点,完成盘点")
    @GetMapping("/17wa-inventory/scan")
    public ResponseEntity scanFinish(int account,Long shopId){
        List<ClientTbProduct> products= null;
        try {
            products = inventoryService.getCheckResult(account,shopId);
            JSONObject result=new JSONObject();
            JSONArray normal=new JSONArray();
            JSONArray error=new JSONArray();
            for(ClientTbProduct p:products){
                if(p.getInventroyStatus().equals("正常")){
                    normal.add(p);
                }
            }
            for(ClientTbProduct p:products){
                if(p.getInventroyStatus().equals("异常")){
                    error.add(p);
                }
            }
            result.put("normal",normal);
            result.put("error",error);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }

    }

    @ApiOperation(value="库存核对一键处理")
    @PostMapping("/17wa-inventory/allconfirm")
    public ResponseEntity confirmAllInventory(@RequestParam int account,@RequestParam Long shopId){
        try{
            boolean flag=inventoryService.confirmAllProduct(account,shopId);
            ExecResult er=new ExecResult(flag,flag?"处理成功":"处理失败");
            return ResponseEntity.ok(er);
        }catch (Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="单个商品库存核对")
    @PostMapping("/17wa-inventory/singleconfirm")
    public ResponseEntity confirmSingleInventory(@RequestBody String body){
        JSONObject param=JSONObject.parseObject(body);
        try{
            if(param.containsKey("account")){
                if(param.containsKey("shopId")){
                    int account=param.getInteger("account");
                    long shopId=param.getLong("shopId");
                    if(param.containsKey("details")){
                        JSONArray array=param.getJSONArray("details");
                        boolean flag=inventoryService.confirmSingleProduct(account,shopId,array);
                        ExecResult er=new ExecResult(flag,flag?"处理成功":"处理失败");
                        return ResponseEntity.ok(er);
                    }else{
                        ExecResult er=new ExecResult(false,"未正确指定details参数");
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
                    }

                }else{
                    ExecResult er=new ExecResult(false,"未正确指定shopId参数");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
                }
            }else{
                ExecResult er=new ExecResult(false,"未正确指定account参数");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        }catch(Exception e){
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="加载单个产品的库存详情")
    @GetMapping("/17wa-inventory/single/{account}/{shopId}/{productId}")
    public ResponseEntity loadProductInventory(@PathVariable int account,@PathVariable Long shopId,@PathVariable Long productId){
        try {
            List<InventoryDto> results=inventoryService.loadInventory(account,shopId,productId);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }




}
