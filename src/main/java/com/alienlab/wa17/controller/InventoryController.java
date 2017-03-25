package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbInventory;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.InventoryDto;
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

    @ApiOperation(value="获取全部库存明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="inventoryId",value="库存记录编码",paramType = "path"),
            @ApiImplicitParam(name="startDate",value="开始日期(2017-03-05)",paramType = "query"),
            @ApiImplicitParam(name="endDate",value="截止日期(2017-03-25)",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码(0开始)",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页长度",paramType = "query")
    })
    @GetMapping("/17wa-inventory/detail/{account}/{inventoryId}")
    public ResponseEntity getInventoryDetails(@PathVariable int account,@PathVariable long inventoryId,
                                              @RequestParam String startDate, @RequestParam String endDate,
                                              @RequestParam int index,@RequestParam int size){
        try {
            Page<InventoryDetailDto> details=inventoryService.loadDetails(account,inventoryId,startDate,endDate,new PageRequest(index,size));
            return ResponseEntity.ok().body(details);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
    @ApiOperation(value="按状态查询库存明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="inventoryId",value="库存记录编码",paramType = "path"),
            @ApiImplicitParam(name="startDate",value="开始日期(2017-03-05)",paramType = "query"),
            @ApiImplicitParam(name="endDate",value="截止日期(2017-03-25)",paramType = "query"),
            @ApiImplicitParam(name="status",value="库存状态",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码(0开始)",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页长度",paramType = "query")
    })
    @GetMapping("/17wa-inventory/detail/status/{account}/{inventoryId}")
    public ResponseEntity getInventoryDetailsByStatus(@PathVariable int account,@PathVariable long inventoryId,
                                              @RequestParam String startDate, @RequestParam String endDate,
                                              @RequestParam String status,
                                              @RequestParam int index,@RequestParam int size){
        try {
            Page<InventoryDetailDto> details=inventoryService.loadDetailsByStatus(account,inventoryId,startDate,endDate,status,new PageRequest(index,size));
            return ResponseEntity.ok().body(details);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

}
