package com.alienlab.wa17.controller;

import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductInclude;
import com.alienlab.wa17.service.CustomService;
import com.alienlab.wa17.service.ImageService;
import com.alienlab.wa17.service.IncludeService;
import com.alienlab.wa17.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 橘 on 2017/7/15.
 */
@Controller
public class PageController {
    @Autowired
    IncludeService includeService;
    @Autowired
    ProductService productService;

    @Autowired
    CustomService customService;

    @Autowired
    ImageService imageService;

    @GetMapping("/custable/{account}/{cusid}")
    public String custable(@PathVariable int account,@PathVariable Long cusid, @RequestParam(required = false) String sdate,
                           @RequestParam(required = false) String edate, Model model){
        if(sdate==null){
            sdate="2017-01-01";
        }
        if(edate==null){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            edate=format.format(new Date());
        }
        try {
            JSONObject result=customService.getCustomPaper(account,cusid,sdate,edate);
            model.addAttribute("custom",result.get("custom"));
            model.addAttribute("orders",result.get("orders"));
            model.addAttribute("total",result.get("total"));
            model.addAttribute("startdate",sdate);
            model.addAttribute("enddate",edate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "customTable";
    }

    @GetMapping("/includetable/{account}/{productid}")
    public String includetable(@PathVariable int account,@PathVariable Long productid, Model model){
        try {
            JSONObject includes=imageService.getIncludesPrintObj(account,productid.intValue());
            List<String> columnsName=new ArrayList<String>();
            columnsName.add("尺码");
            model.addAttribute("includes",includes);
            if(includes!=null&&includes.size()>0){
                for (String s : includes.keySet()) {
                    JSONObject detail=includes.getJSONObject(s);
                    for (String s1 : detail.keySet()) {
                        columnsName.add(s1);
                    }
                    model.addAttribute("fieldLength",columnsName.size());
                    break;
                }
                String [][] rows=new String [includes.size()][columnsName.size()+1];
                int m=0,n=0;
                for (String s : includes.keySet()) {
                    JSONObject detail=includes.getJSONObject(s);
                    String [] r=new String [detail.size()+1];
                    r[0]=s;
                    n=1;
                    for (String s1 : detail.keySet()) {
                        r[n]=detail.getString(s1);
                        n++;
                    }
                    rows[m]=r;
                    m++;
                }
                model.addAttribute("columns",columnsName);
                model.addAttribute("rows",rows);

            }
            ClientTbProduct product=productService.getProduct(account,productid);
            model.addAttribute("product",product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "includeTable";
    }
}
