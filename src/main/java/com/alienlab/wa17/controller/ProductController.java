package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbColorCus;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.client.ClientTbSizeCus;
import com.alienlab.wa17.entity.client.dto.ColorDto;
import com.alienlab.wa17.entity.client.dto.ProductDto;
import com.alienlab.wa17.entity.client.dto.ProductSkuDto;
import com.alienlab.wa17.entity.client.dto.SizeDto;
import com.alienlab.wa17.entity.main.MainTbTags;
import com.alienlab.wa17.service.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;


/**
 * Created by 橘 on 2017/2/21.
 */
@Api(value = "/api/17wa-product",description = "产品api")
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation(value="新增产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户id",paramType = "path"),
            @ApiImplicitParam(name="product",value="产品json",paramType = "body",dataType = "String",example = "{" +
                        "productCode:productCode," +
                        "productColors:productColors," +
                        "productSizes:productSizes," +
                        "productFabric:productFabric," +
                        "productFabricin:productFabricin," +
                        "productName:productName," +
                        "productPrice1:productPrice1," +
                        "productPrice2:productPrice2," +
                        "productType:productType," +
                        "productTags:productTags,"+
                        "colors:[{" +
                            "colorId:colorId,"+
                            "colorName:colorName,"+
                            "colorType:colorType"+
                        "},{" +
                            "colorId:colorId,"+
                            "colorName:colorName,"+
                            "colorType:colorType"+
                        "}]," +
                        "sizes:[{" +
                            "colorId:colorId,"+
                            "colorName:colorName,"+
                            "colorType:colorType"+
                        "},{" +
                            "sizeId:sizeId,"+
                            "sizeName:sizeName,"+
                            "sizeType:sizeType"+
                        "}]" +
                    "}")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ClientTbProduct.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @PostMapping("/17wa-product/{account}")
    public ResponseEntity addProduct(@PathVariable int account,@RequestBody String product){
        JSONObject productbase=JSONObject.parseObject(product);
        JSONArray colors=productbase.getJSONArray("colors");
        JSONArray sizes=productbase.getJSONArray("sizes");
        ClientTbProduct p=new ClientTbProduct();
        p.setAccountId((long)account);
        p.setProductCode2(productbase.getString("productCode2"));
        p.setProductCode(productbase.getString("productCode"));
        p.setProductColors(productbase.getString("productColors"));
        p.setProductSizes(productbase.getString("productSizes"));
        p.setProductFabric(productbase.getString("productFabric"));
        p.setProductFabricin(productbase.getString("productFabricin"));
        p.setProductName(productbase.getString("productName"));
        p.setProductPrice1(productbase.getInteger("productPrice1"));
        p.setProductPrice2(productbase.getInteger("productPrice2"));
        p.setProductType(productbase.getString("productType"));
        p.setProductStatus("上架");
        p.setProductTags(productbase.getString("productTags"));
        ClientTbProductSku [] skus=null;
        if(colors!=null&&colors.size()>0&&sizes!=null&&sizes.size()>0){
            int n=colors.size()*sizes.size();
            skus=new ClientTbProductSku[n];
            int pos=0;
            for(int i=0;i<colors.size();i++){
                JSONObject color=colors.getJSONObject(i);
                for(int j=0;j<sizes.size();j++){
                    JSONObject size=sizes.getJSONObject(j);
                    skus[pos]=new ClientTbProductSku();
                    skus[pos].setColorId(color.getLong("colorId"));
                    skus[pos].setColorName(color.getString("colorName"));
                    skus[pos].setColorType(color.getString("colorType"));
                    skus[pos].setSizeId(size.getLong("sizeId"));
                    skus[pos].setSizeName(size.getString("sizeName"));
                    skus[pos].setSizeType(size.getString("sizeType"));
                    skus[pos].setSkuStatus("上架");
                    pos++;
                }
            }
        }
        try {
            p=productService.addProduct(account,p,skus);
            return ResponseEntity.ok().body(p);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="按关键字查询账户下商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="keyword",value="查询关键字",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页页长",paramType = "query")
    })
    @GetMapping("/17wa-product/{account}")
    public ResponseEntity getShopList(@PathVariable int account,@RequestParam String keyword,@RequestParam int index,@RequestParam int size){
        try {
            Page<ClientTbProduct> results = productService.getProducts(account,keyword,new PageRequest(index, size));
            return ResponseEntity.ok().body(results);
        }catch(Exception ex){
            ex.printStackTrace();
            ExecResult er=new ExecResult(false,ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获取账户下全部商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="index",value="分页页码",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页页长",paramType = "query")
    })
    @GetMapping("/17wa-product/{account}/all")
    public ResponseEntity getShopList(@PathVariable int account,@RequestParam int index,@RequestParam int size){
        try {
            Page<ClientTbProduct> results = productService.getAllProducts(account,new PageRequest(index, size));
            return ResponseEntity.ok().body(results);
        }catch(Exception ex){
            ex.printStackTrace();
            ExecResult er=new ExecResult(false,ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @GetMapping("/17wa-product/{account}/product/{productid}")
    @ApiOperation(value="获取商品详细信息，包含sku数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="productid",value="商品id",paramType = "path")
    })
    public ResponseEntity loadProduct(@PathVariable int account,@PathVariable long productid){
        try{
            ProductSkuDto productSkuDto=productService.loadProduct(account,productid);
            return ResponseEntity.ok().body(productSkuDto);
        }catch(Exception ex){
            ExecResult er=new ExecResult(false,ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
