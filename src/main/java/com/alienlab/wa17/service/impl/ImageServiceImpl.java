package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.utils.ImageHelper;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbProductInclude;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.service.ImageService;
import com.alienlab.wa17.service.IncludeService;
import com.alienlab.wa17.service.ProductService;
import com.alienlab.wa17.service.SkuService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘 on 2017/4/16.
 */
@Service
public class ImageServiceImpl implements ImageService {
    Logger logger = Logger.getLogger(ImageService.class);
    @Autowired
    IncludeService includeService;
    @Autowired
    SkuService skuService;
    @Autowired
    ProductService productService;


    @Value("${17wa.basepath}")
    String base_path;

    @Value("${17wa.image.path}")
    String image_path;

    @Value("${alienlab.upload.path}")
    String upload_path;

    @Override
    @Async
    public void convertImageAuto(String path,String fileName, String format,int width) throws Exception {
        String filePath=path+fileName+"."+format;
        String f1=path+fileName+"_"+width+"."+format;
        logger.info("convert image>>"+filePath+",to>>>"+f1);
        ImageHelper.scaleImageWithParams(filePath,f1,width,width,true,format);
    }
    @Override
    public JSONObject getIncludesPrintObj(int account, int productId)throws Exception{
        List<ClientTbProductInclude> includes=includeService.getProductIncluedes(account,productId);
        if(includes==null||includes.size()==0){
            throw new Exception("产品没有设置尺码明细");
        }
        JSONObject includesPrintObj=new JSONObject();
        for(ClientTbProductInclude include:includes){
            String sizeName=include.getSizeName();
            if(includesPrintObj.containsKey(sizeName)){
                JSONObject detail=(JSONObject)includesPrintObj.get(sizeName);
                detail.put(include.getIncludeName(),include.getIncludeValue());
                includesPrintObj.put(sizeName,detail);
            }else{
                JSONObject detail=new JSONObject();
                detail.put(include.getIncludeName(),include.getIncludeValue());
                includesPrintObj.put(sizeName,detail);
            }
        }
        return includesPrintObj;
    }

    @Override
    public String createSizeIncludeImage(int account, int productId,String path,String fileName) throws Exception {
//        JSONObject includesPrintObj=getIncludesPrintObj(account,productId);
//        int imageWidth = 750;// 图片的宽度
//        int imageHeight = includesPrintObj.size()*50+50;// 图片的高度
//        BufferedImage image = new BufferedImage(imageWidth, imageHeight,
//                BufferedImage.TYPE_INT_RGB);
//        Graphics graphics = image.getGraphics();
//        graphics.setColor(Color.white);
//        graphics.fillRect(0, 0, imageWidth, imageHeight);
//        graphics.setColor(Color.black);
//        graphics.setFont(new Font("宋体", Font.BOLD, 20));
//        //绘制外框
//        graphics.drawLine(0,0,imageWidth,0);
//        graphics.drawLine(0,0,0,imageHeight);
//        graphics.drawLine(imageWidth,0,imageWidth,imageHeight);
//        graphics.drawLine(imageWidth,0,imageWidth,imageHeight);
//
//        int rowHeight=50;
//        List<String> sizeTitles=new ArrayList<String>();
//        for(String sizeTitle:includesPrintObj.keySet()){
//            if(sizeTitles.size()==0){
//                sizeTitles.add(sizeTitle);
//                sizeTitles.add(sizeTitle);
//            }else{
//                sizeTitles.add(sizeTitle);
//            }
//
//        }
//        //画每行
//        for(int i=0;i<sizeTitles.size();i++){
//            String sizeName=sizeTitles.get(i);
//
//            JSONObject printObj=includesPrintObj.getJSONObject(sizeName);
//            int columnSize=printObj.keySet().size()+1;
//            int columnWidth=imageWidth/columnSize;
//
//            List<String> titles=new ArrayList<String>();
//            titles.add("尺码");
//            for(String title:printObj.keySet()){
//                titles.add(title);
//            }
//
//            for(int j=0;j<columnSize;j++){
//                //画竖线
//                graphics.drawLine(columnWidth*(j),0,columnWidth*(j),rowHeight*(i+1));
//                if(i>0&&j==0){
//                    String sizecolumn=sizeTitles.get(i);
//                    int sizeWidth=sizecolumn.length()*20;
//                    int sizex=(columnWidth-sizeWidth)/2;
//                    int sizey=(rowHeight-20);
//                    graphics.drawString(sizecolumn,sizex,(rowHeight*i+sizey));
//                }else{
//                    String text=titles.get(j);
//                    //填文字
//                    if(i>0){
//                        text=printObj.getString(text);
//                    }
//                    int wordWidth=text.length()*20;
//                    int wordx=(columnWidth-wordWidth)/2;
//                    int wordy=(rowHeight-20);
//                    graphics.drawString(text,(columnWidth*j+wordx),(rowHeight*i+wordy));
//                }
//            }
////            //分割线
////            graphics.drawLine(0,rowHeight*(i+1),imageWidth,rowHeight*(i+1));
//            //行线
//            graphics.drawLine(0,rowHeight*(i+1),imageWidth,rowHeight*(i+1));
//
//        }
//        createImage(path, image);
//        productService.setPics(account,productId,(image_path+fileName),"尺码图");

        html2Img(base_path+"includetable/"+account+"/"+productId,path);
        productService.setPics(account,productId,(image_path+fileName),"尺码图");
        return image_path+fileName;
    }

    private String html2Img(String urlStr,String path){
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.setSize(new Dimension(1000,1000));
        try {
            URL url = new URL(urlStr);
            InputStream in =url.openStream();
            InputStreamReader isr = new InputStreamReader(in,"UTF8");
            BufferedReader bufr = new BufferedReader(isr);
            String str;
            String html="";

            while ((str = bufr.readLine()) != null) {
                html+=str;
            }
            bufr.close();
            isr.close();
            in.close();

            imageGenerator.loadHtml(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageGenerator.getBufferedImage();
        imageGenerator.saveAsImage(path);
        return path;
    }

    @Override
    public String createCustomShareImage(int account, Long customid,String path,String fileName) throws Exception {
        String url=base_path+"custable/"+account+"/"+customid;
        html2Img(url,path);
        return image_path+fileName;
    }

    private static void createImage(String fileLocation, BufferedImage image) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
