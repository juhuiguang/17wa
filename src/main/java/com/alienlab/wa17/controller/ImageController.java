package com.alienlab.wa17.controller;

import com.alienlab.wa17.controller.util.ExecResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value="图片上传接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sort",value="图片排序值",paramType = "query"),
            @ApiImplicitParam(name="productId",value="图片排序值",paramType = "query"),
            @ApiImplicitParam(name="type",value="类型：产品图，介绍图",paramType = "query")
    })
    @PostMapping("/17wa-image")
    public ResponseEntity uploadImage(@RequestPart("file") MultipartFile file,@RequestParam int sort,@RequestParam int productId,@RequestParam String type, HttpServletRequest request){
        String path=request.getSession().getServletContext().getRealPath(upload_path);
//        List<MultipartFile> files =((MultipartHttpServletRequest)request).getFiles("file");
//        MultipartFile file = null;
        BufferedOutputStream stream = null;
        if (!file.isEmpty()) {
            String fName=file.getOriginalFilename();
            String exName=fName.substring(fName.indexOf('.')+1);
            String fileName= UUID.randomUUID().toString()+"."+exName;
            try {
                byte[] bytes = file.getBytes();
                stream = new BufferedOutputStream(new FileOutputStream(new File(path+File.separator+fileName)));
                stream.write(bytes);
                stream.close();

            } catch (Exception e) {
                stream =  null;
                ExecResult er=new ExecResult(false,e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
            }
            return ResponseEntity.ok().body(fileName);
        } else {
            ExecResult er=new ExecResult(false,"没有上传文件.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }


    }
}
