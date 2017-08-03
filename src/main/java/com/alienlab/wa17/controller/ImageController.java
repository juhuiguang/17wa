package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.controller.util.ExecResult;
import com.alienlab.wa17.service.ImageService;
import com.alienlab.wa17.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by 橘 on 2017/3/14.
 */
@Api(value = "/api/17wa-image",description = "图片API")
@RestController
@RequestMapping(value="/api")
public class ImageController {
    @Value("${alienlab.upload.path}")
    String upload_path;
    @Value("${17wa.image.path}")
    String image_path;
    @Autowired
    ImageService imageService;
    @Autowired
    ProductService productService;

    @ApiOperation(value="图片上传接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account",value="账户编码",paramType = "query"),
            @ApiImplicitParam(name="productId",value="产品编码",paramType = "query"),
            @ApiImplicitParam(name="sort",value="图片排序值",paramType = "query"),
            @ApiImplicitParam(name="type",value="类型：产品图，介绍图",paramType = "query")
    })
    @PostMapping("/17wa-image")
    public ResponseEntity uploadImage(@RequestPart("file") MultipartFile file,@RequestParam int account,@RequestParam int sort,@RequestParam Long productId,@RequestParam String type, HttpServletRequest request){
//        System.out.println("file"+JSONObject.toJSONString(file));
        String path=request.getSession().getServletContext().getRealPath(upload_path);
//        List<MultipartFile> files =((MultipartHttpServletRequest)request).getFiles("file");
//        MultipartFile file = null;
        BufferedOutputStream stream = null;
        if (!file.isEmpty()) {
            String fName=file.getOriginalFilename();
            String exName=fName.substring(fName.indexOf('.')+1);
            String fileName= UUID.randomUUID().toString();
            try {
                byte[] bytes = file.getBytes();
                stream = new BufferedOutputStream(new FileOutputStream(new File(path+File.separator+fileName+"."+exName)));
                stream.write(bytes);
                stream.close();

            } catch (Exception e) {
                stream =  null;
                ExecResult er=new ExecResult(false,e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
            try{
                String fnpath=path+File.separator;
                imageService.convertImageAuto(fnpath,fileName,exName,110);
                imageService.convertImageAuto(fnpath,fileName,exName,320);
                imageService.convertImageAuto(fnpath,fileName,exName,750);
                imageService.convertImageAuto(fnpath,fileName,exName,1200);
            }catch(Exception e) {
                e.printStackTrace();
            }
            try {
                productService.setPics(account,productId,(image_path+fileName+"."+exName),type);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ExecResult er=new ExecResult(true,image_path+fileName+"."+exName);
            return ResponseEntity.ok().body(er);
        } else {
            ExecResult er=new ExecResult(false,"没有上传文件.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }


    }
    @GetMapping("/17wa-image/include")
    public ResponseEntity getIncludeImage(@RequestParam int account,@RequestParam int product,HttpServletRequest request){
        String path=request.getSession().getServletContext().getRealPath(upload_path);
        String exName="png";
        String fileName= UUID.randomUUID().toString();
        try {
            String s=imageService.createSizeIncludeImage(account,product,(path+File.separator+fileName+"_include"+"."+exName),(fileName+"_include"+"."+exName));
            return ResponseEntity.ok().body(s);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/17wa-image/custom")
    public ResponseEntity getIncludeImage(@RequestParam int account,@RequestParam Long cusid,HttpServletRequest request){
        String path=request.getSession().getServletContext().getRealPath(upload_path);
        String exName="png";
        String fileName= UUID.randomUUID().toString();
        try {
            String s=imageService.createCustomShareImage(account,cusid,(path+File.separator+fileName+"."+exName),(fileName+"."+exName));
            return ResponseEntity.ok().body(s);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }



}
