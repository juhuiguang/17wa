package com.alienlab.wa17.service.impl;

import com.alienlab.utils.ImageHelper;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.service.ImageService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by 橘 on 2017/4/16.
 */
@Service
public class ImageServiceImpl implements ImageService {
    Logger logger = Logger.getLogger(ImageService.class);
    @Override
    @Async
    public void convertImageAuto(String path,String fileName, String format,int width) throws Exception {
        String filePath=path+fileName+"."+format;
        String f1=path+fileName+"_"+width+"."+format;
        logger.info("convert image>>"+filePath+",to>>>"+f1);
        ImageHelper.scaleImageWithParams(filePath,f1,width,width,true,format);
    }
}
