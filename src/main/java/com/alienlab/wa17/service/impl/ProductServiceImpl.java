package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbColorCus;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.service.ProductService;
import com.alienlab.wa17.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by 橘 on 2017/2/6.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    DaoTool daoTool;
    @Autowired
    SkuService skuService;
    @Override
    public Page<ClientTbProduct> getProducts(int account_id, int shop_id, String keyword, Pageable page) throws Exception {
        String sql="";

        return null;
    }

    @Override
    public ClientTbProduct addProduct(int account_id, ClientTbProduct product, ClientTbProductSku [] clientTbProductSkus) throws Exception {
        product.setProductCode(getProductCode(account_id));
        product=daoTool.saveOne(product,account_id);
        if(product.getProductId()>0){
            int productid=(int)product.getProductId();
            //保存sku
            for(ClientTbProductSku sku:clientTbProductSkus){
                skuService.addSku(account_id,productid,sku);
            }
        }else{
            throw new Exception("产品保存过程中出错了。");
        }
        return product;
    }

    @Override
    public ClientTbProduct updateProduct(int account_id, ClientTbProduct product,ClientTbProductSku [] clientTbProductSkus) throws Exception {

        product=daoTool.updateOne(account_id,product);
        int productid=(int)product.getProductId();
        //删除现有sku
        skuService.delSku(account_id,productid);
        //保存新的sku
        for(ClientTbProductSku sku:clientTbProductSkus){
            sku.setProductId((long)productid);
            skuService.addSku(account_id,productid,sku);
        }
        return product;
    }

    private String getProductCode(int account_id) throws Exception{
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String date=format.format(new Date());
        String sql="select count(1) num from tb_product where product_time>='"+date+" 00:00:00' and product_time<='"+date+" 23:59:59'";
        Map countResult=daoTool.getMap(sql,account_id);
        int num=1;
        if(countResult!=null&&countResult.get("NUM")!=null){
             num=TypeUtils.castToInt(countResult.get("NUM"))+1;
        }
        SimpleDateFormat format2=new SimpleDateFormat("yyyyMMdd");
        String result=format2.format(new Date())+"-"+account_id+"-"+num;
        return result;
    }

    @Override
    public ClientTbProduct changeProductStatus(int account_id, int product_id, String status) throws Exception {
        return null;
    }
}
