package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.entity.client.ClientTbColorCus;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.client.ClientTbSizeCus;
import com.alienlab.wa17.entity.client.dto.*;
import com.alienlab.wa17.entity.main.MainTbProducttype;
import com.alienlab.wa17.entity.main.MainTbTags;
import com.alienlab.wa17.entity.main.dto.ProductTypeDto;
import com.alienlab.wa17.service.ImageService;
import com.alienlab.wa17.service.ProductService;
import com.alienlab.wa17.service.SkuService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;


/**
 * Created by 橘 on 2017/2/21.
 */
@Api(value = "/api/17wa-product",description = "产品api")
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ImageService imageService;

    @Autowired
    SkuService skuService;

    @Value("${alienlab.upload.path}")
    String upload_path;

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
    public ResponseEntity addProduct(@PathVariable int account, @RequestBody String product, HttpServletRequest request){
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
            String path=request.getSession().getServletContext().getRealPath(upload_path);
            String exName="jpg";
            String fileName= UUID.randomUUID().toString();
//            try {
//                String s=imageService.createSizeIncludeImage(account,(int)p.getProductId(),(path+ File.separator+fileName+"_include"+"."+exName),(fileName+"_include"+"."+exName));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return ResponseEntity.ok().body(p);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="按关键字查询账户下商品列表,包含当前门店的库存总量")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="店铺编码",paramType = "path"),
            @ApiImplicitParam(name="keyword",value="查询关键字",paramType = "query"),
            @ApiImplicitParam(name="index",value="分页页码",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页页长",paramType = "query")
    })
    @GetMapping("/17wa-product/{account}/{shopId}")
    public ResponseEntity getProductList(@PathVariable int account,@PathVariable long shopId,@RequestParam String keyword,@RequestParam int index,@RequestParam int size){
        try {
            Page<ClientTbProduct> results = productService.getProducts(account,keyword,shopId,new PageRequest(index, size));
            return ResponseEntity.ok().body(results);
        }catch(Exception ex){
            ex.printStackTrace();
            ExecResult er=new ExecResult(false,ex.getMessage());
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
    public ResponseEntity getProductList(@PathVariable int account,@RequestParam String keyword,@RequestParam int index,@RequestParam int size){
        try {
            Page<ClientTbProduct> results = productService.getProducts(account,keyword,new PageRequest(index, size));
            return ResponseEntity.ok().body(results);
        }catch(Exception ex){
            ex.printStackTrace();
            ExecResult er=new ExecResult(false,ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获取账户下全部商品列表,包含当前门店的库存总量")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="店铺编码",paramType = "path"),
            @ApiImplicitParam(name="index",value="分页页码",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页页长",paramType = "query")
    })
    @GetMapping("/17wa-product/{account}/{shopId}/all")
    public ResponseEntity getAllProductList(@PathVariable int account,@PathVariable long shopId,@RequestParam int index,@RequestParam int size){
        try {
            Page<ClientTbProduct> results = productService.getAllProducts(account,shopId,new PageRequest(index, size));
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
    public ResponseEntity getAllProductList(@PathVariable int account,@RequestParam int index,@RequestParam int size){
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
    @ApiOperation(value="获取当前门店异常库存的产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="shopId",value="店铺编码",paramType = "path"),
            @ApiImplicitParam(name="index",value="分页页码",paramType = "query"),
            @ApiImplicitParam(name="size",value="分页页长",paramType = "query")
    })
    @GetMapping("/17wa-product/error/{account}/{shopId}")
    public ResponseEntity getErrorProduct(@PathVariable int account,@PathVariable long shopId,@RequestParam int index,@RequestParam int size){
        try {
            Page<ClientTbProduct> results=productService.getErrorProducts(account,shopId,new PageRequest(index,size));
            return ResponseEntity.ok().body(results);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="删除商品图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="productId",value="产品编码",paramType = "query"),
            @ApiImplicitParam(name="pic",value="图片地址",paramType = "query"),
            @ApiImplicitParam(name="type",value="图片类型:产品图，介绍图",paramType = "query")
    })
    @DeleteMapping("/17wa-produt/pic/{account}")
    public ResponseEntity delProductPic(@PathVariable int account, @RequestParam long productId, @RequestParam String pic,@RequestParam String type){
        try {
            boolean result=productService.delPic(account,productId,pic,type);
            ExecResult er=new ExecResult(true,"success");
            return ResponseEntity.ok().body(er);
        } catch (Exception e) {
            ExecResult er=new ExecResult(false,e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获得商品图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "path"),
            @ApiImplicitParam(name="productId",value="产品编码",paramType = "path")
    })
    @GetMapping("/17wa-produt/pic/{account}/{productId}")
    public ResponseEntity getPics(@PathVariable int account, @PathVariable long productId){
        try {
            List<String> pics=productService.getPics(account,productId);
            JSONArray array= new JSONArray();
            for(String pic :pics){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("url",pic);
                array.add(jsonObject);
            }
            return ResponseEntity.ok().body(array);
        } catch (Exception e) {
            ExecResult er=new ExecResult(false,e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="获得全部产品分类")
    @GetMapping("/17wa-produt/types")
    public ResponseEntity loadTypes(){
        try {
            List<ProductTypeDto> types=productService.getAllProductType();
            return ResponseEntity.ok().body(types);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="新增或修改产品类型")
    @PostMapping("/17wa-produt/types")
    public ResponseEntity addOrUpdateTypes(@RequestBody MainTbProducttype type){
        try{
            if(type.getProducttypeId()>0){
                type=productService.updateType(type);
            }else{
                type=productService.addType(type);
            }
            return ResponseEntity.ok().body(type);
        }catch (Exception e){
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="删除产品类型")
    @ApiResponses({
            @ApiResponse(code = 200, message = "", response = ExecResult.class),
            @ApiResponse(code = 500, message = "", response = ExecResult.class)
    })
    @DeleteMapping(value="/17wa-produt/types/{typeId}")
    public ResponseEntity delType(@PathVariable long typeId){
        try {
            boolean result=productService.delType(typeId);
            if(result){
                ExecResult er=new ExecResult(true,"类型删除成功。");
                return ResponseEntity.ok().body(er);
            }else{
                ExecResult er=new ExecResult(false,"类型删除失败了。");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation(value="根据货号查询产品信息")
    @GetMapping(value="/17wa-produt/code/{account}/{shopId}/{code}")
    public ResponseEntity getProductByCode(@PathVariable int account,@PathVariable long shopId,@PathVariable String code){
        try {
            ProductSkuDto product=productService.getProductByCode(account,code,shopId);
            return ResponseEntity.ok().body(product);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }


    @ApiOperation(value="获取所有SKU数据（用于门店调货）")
    @GetMapping(value="/17wa-produt/allsku")
    public ResponseEntity getAllSkusByCode(@RequestParam int account,@RequestParam long shopId,@RequestParam(required = false) String code){
        try {
            if(code==null){
                code="";
            }
            List<InventoryDto> skus=skuService.getAllSku(account,shopId,code);
            return ResponseEntity.ok().body(skus);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }
}
