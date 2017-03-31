package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.service.CustomService;
import com.alienlab.wa17.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by 橘 on 2017/2/21.
 */
@Api(value = "/api/17wa-order",description = "订单,下单api")
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    ProductService productService;
    @Autowired
    CustomService customService;

    @ApiOperation(value="获取当前门店的可售产品明细列表，用于下单功能中的产品查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="店铺编码",paramType = "path")
    })
    @GetMapping("/17wa-order/onsale/{account}/{shopId}")
    public ResponseEntity getOnSaleProduct(@PathVariable int account,@PathVariable long shopId){
        try {
            List<InventoryDetailDto> results=productService.getOnSaleProducts(account,shopId);
            return ResponseEntity.ok().body(results);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获取当前账户的客户列表，用于下单功能中的客户查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="keyword",value="查询关键字",paramType = "path")
    })
    @GetMapping("/17wa-order/custom/{account}/{keyword}")
    public ResponseEntity getSaleCustom(@PathVariable int account,@PathVariable String keyword){
        try {
            List<ClientTbCustom> results=customService.findCustom(account,keyword);
            return ResponseEntity.ok().body(results);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
