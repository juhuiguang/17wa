package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.OrderDto;
import com.alienlab.wa17.service.CustomService;
import com.alienlab.wa17.service.OrderService;
import com.alienlab.wa17.service.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    OrderService orderService;

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

    @ApiOperation(value="获取当前账户的客户列表，用于下单功能中的客户查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="店铺id",paramType = "path"),
            @ApiImplicitParam(name="order",value="订单json",paramType = "body")

    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = OrderDto.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @PostMapping("/17wa-order/{account}/{shopId}")
    public ResponseEntity addOrder(@PathVariable int account,@PathVariable long shopId,@RequestBody String order){
        JSONObject orderjson= JSONObject.parseObject(order);
        try {
            OrderDto orderDto=orderService.addOrder(account,shopId,orderjson);
            return ResponseEntity.ok().body(orderDto);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
