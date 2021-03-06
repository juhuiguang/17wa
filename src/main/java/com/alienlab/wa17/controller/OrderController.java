package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.entity.client.ClientTbOrder;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.OrderDto;
import com.alienlab.wa17.entity.client.dto.OrderPrintDto;
import com.alienlab.wa17.service.CustomService;
import com.alienlab.wa17.service.OrderService;
import com.alienlab.wa17.service.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity getOnSaleProduct(@PathVariable int account,@PathVariable long shopId,@RequestParam(required = false) String keyword){
        try {
            List<InventoryDetailDto> results=productService.getOnSaleProducts(account,shopId,keyword);
            Map<Long,List<ClientTbProductSku>> hashProduct=new HashMap<>();
            for (InventoryDetailDto result : results) {
                Long productId=result.getProductId();
                ClientTbProductSku sku=new ClientTbProductSku();
                sku.setSizeName(result.getSizeName());
                sku.setColorName(result.getColorName());
                sku.setSkuStatus(result.getSkuStatus());
                sku.setColorId(result.getColorId());
                sku.setSizeId(result.getSizeId());
                sku.setSizeType(result.getSizeType());
                sku.setColorType(result.getColorType());
                sku.setAmount(result.getAmount());
                sku.setId(result.getId());
                if(hashProduct.containsKey(productId)){
                    List<ClientTbProductSku> skuslist=hashProduct.get(productId);
                    skuslist.add(sku);
                    hashProduct.put(productId,skuslist);
                }else{
                    List<ClientTbProductSku> skuslist=new ArrayList<>();
                    skuslist.add(sku);
                    hashProduct.put(productId,skuslist);
                }

            }
            JSONArray array= JSONArray.parseArray(JSON.toJSONString(results));
            for(int i=0;i<array.size();i++){
                JSONObject item=array.getJSONObject(i);
                item.put("skuslist",hashProduct.get(item.getLong("productId")));
            }
            return ResponseEntity.ok().body(array);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获取当前门店的单品可售明细列表用于下单功能中的加号下单的模式")
    @GetMapping("/17wa-order/onsale/{account}/{shopId}/{productId}")
    public ResponseEntity getOnSaleProduct(@PathVariable int account,@PathVariable long shopId,@PathVariable long productId){
        try {
            List<InventoryDetailDto> results=productService.getOnSaleByProduct(account,shopId,productId);
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
            if(keyword.equalsIgnoreCase("all")){
                keyword="";
            }
            List<ClientTbCustom> results=customService.findCustom(account,keyword);
            return ResponseEntity.ok().body(results);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="客户查询分页接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="keyword",value="查询关键字",paramType = "query"),
            @ApiImplicitParam(name="page",value="当前页码",paramType = "query"),
            @ApiImplicitParam(name="size",value="页长",paramType = "query")
    })
    @GetMapping("/17wa-order/custom/{account}")
    public ResponseEntity getCustomPage(@PathVariable int account,@RequestParam String keyword,@RequestParam int page,@RequestParam int size){
        try {
            if(keyword.equalsIgnoreCase("all")){
                keyword="";
            }
            Page<ClientTbCustom> results=customService.findCustomPage(account,keyword,page,size);
            return ResponseEntity.ok().body(results);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="客户下单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="店铺id",paramType = "path"),
            @ApiImplicitParam(name="order",value="订单json",paramType = "body")

    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = OrderPrintDto.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @PostMapping("/17wa-order/{account}/{shopId}")
    public ResponseEntity addOrder(@PathVariable int account,@PathVariable long shopId,@RequestBody String order){
        JSONObject orderjson= JSONObject.parseObject(order);
        try {
            OrderPrintDto orderDto=orderService.addOrder(account,shopId,orderjson);
            return ResponseEntity.ok().body(orderDto);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="订单打印")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="orderId",value="订单Id",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = OrderPrintDto.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @GetMapping("/17wa-order/{account}/{orderId}")
    public ResponseEntity printOrder(@PathVariable int account,@PathVariable long orderId){
        try {
            OrderPrintDto orderDto=orderService.doPrint(account,orderId);
            return ResponseEntity.ok().body(orderDto);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="查询订单记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="店铺id",paramType = "query"),
            @ApiImplicitParam(name="sdate",value="开始日期 格式:2017-04-01",paramType = "query"),
            @ApiImplicitParam(name="edate",value="结束日期 格式:2017-04-01",paramType = "query"),
            @ApiImplicitParam(name="keyword",value="关键字(商品code1,code2,商品名称)",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页长度",paramType = "query")
    })
    @GetMapping("/17wa-order/{account}")
    public ResponseEntity getOrders(@PathVariable int account,@RequestParam Long shopId,
                                    @RequestParam String sdate,@RequestParam String edate,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam int index,@RequestParam int size){
        try {
            Page<ClientTbOrder> orders=orderService.getOrders(account,shopId,sdate,edate,keyword,new PageRequest(index,size));
            return ResponseEntity.ok().body(orders);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }

    }

    @ApiOperation(value="查询指定客户的订单记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="店铺id",paramType = "query"),
            @ApiImplicitParam(name="custom",value="客户id",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页长度",paramType = "query")
    })
    @GetMapping("/17wa-order/{account}/custom")
    public ResponseEntity getCustomOrders(@PathVariable int account,@RequestParam Long shopId,
                                    @RequestParam int custom,
                                    @RequestParam int index,@RequestParam int size){
        try {
            Page<ClientTbOrder> orders=orderService.getCustomOrders(account,shopId,custom,new PageRequest(index,size));
            return ResponseEntity.ok().body(orders);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }

    }

    @ApiOperation(value="订单打印")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="orderId",value="订单Id",paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = OrderPrintDto.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @GetMapping("/17wa-order/detail/{account}/{orderId}")
    public ResponseEntity getOrderDetail(@PathVariable int account,@PathVariable long orderId){
        try {
            OrderPrintDto orderDto=orderService.doPrint(account,orderId);
            return ResponseEntity.ok().body(orderDto);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


    @ApiOperation(value="订单退货")
    @PostMapping("/17wa-order/turnback")
    public ResponseEntity turnbackOrder(@RequestBody Map turnBack){
        if(turnBack!=null&&turnBack.containsKey("account")&&turnBack.containsKey("skuid")
                &&turnBack.containsKey("orderno")&&turnBack.containsKey("amount")){
            int account= TypeUtils.castToInt(turnBack.get("account"));
            Long skuid=TypeUtils.castToLong(turnBack.get("skuid"));
            String orderno=TypeUtils.castToString(turnBack.get("orderno"));
            int amount=TypeUtils.castToInt(turnBack.get("amount"));
            try {
                ClientTbOrder order=orderService.turnbackOrder(account,orderno,skuid,amount);
                return ResponseEntity.ok(order);
            } catch (Exception e) {
                e.printStackTrace();
                ExecResult er=new ExecResult(false,e.getMessage());
                return ResponseEntity.status(500).body(er);
            }
        }else{
            ExecResult er=new ExecResult(false,"传入参数有误，参数列表：account,skuid,orderno,amount");
            return ResponseEntity.status(500).body(er);
        }
    }




}
