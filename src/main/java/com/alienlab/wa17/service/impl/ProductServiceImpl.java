package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbColorCus;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.ProductDto;
import com.alienlab.wa17.entity.client.dto.ProductSkuDto;
import com.alienlab.wa17.service.ProductService;
import com.alienlab.wa17.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public Page<ClientTbProduct> getProducts(int account_id, String keyword, Pageable page) throws Exception {
        String sql="SELECT product_id,product_code,product_code2,account_id,product_name,product_pic,product_price1,product_price2,product_type,product_fabric,product_fabricin,product_sizes " +
                "product_colors,product_status,product_tags FROM tb_product where product_code2 like '%"+keyword+"%' or product_name like '%"+keyword+"%'";
        Page<ClientTbProduct> results=daoTool.getPageList(sql,page,account_id,ClientTbProduct.class);

        return results;
    }

    @Override
    public Page<ClientTbProduct> getAllProducts(int account_id, Pageable page) throws Exception {
        String sql="SELECT a.product_id,a.product_code,a.product_code2,a.account_id,a.product_name,a.product_pic,a.product_price1,a.product_price2,a.product_type,a.product_fabric,a.product_fabricin,a.product_sizes," +
                "a.product_colors,a.product_status,a.product_tags,lj.product_amount FROM tb_product a" +
                " LEFT JOIN (SELECT c.`product_id`,SUM(b.`inventory_amount`) product_amount FROM tb_inventory b,tb_product_sku c WHERE b.`sku_id`=c.`id` ) lj ON lj.product_id=a.`product_id` ";
        Page<ClientTbProduct> results=daoTool.getPageList(sql,page,account_id,ClientTbProduct.class);

        return results;
    }

    @Override
    public Page<ClientTbProduct> getProducts(int account_id, String keyword, long shopId, Pageable page) throws Exception {
        String sql="SELECT a.product_id,product_code,product_code2,account_id,product_name,product_pic,product_price1,product_price2,product_type,product_fabric,product_fabricin,product_sizes,product_colors,product_status,product_tags ,lj.amount product_amount,lj2.`status` inventory_status " +
                " FROM tb_product a LEFT JOIN (SELECT c.`product_id`,SUM(b.`inventory_amount`) amount FROM tb_inventory b,tb_product_sku c WHERE b.shop_id="+shopId+" AND b.`sku_id`=c.`id` ) lj ON lj.product_id=a.`product_id` " +
                " LEFT JOIN `tb_product_inventory_status` lj2 ON lj2.`product_id`=a.`product_id` AND lj2.`shop_id`="+shopId +
                " WHERE product_code2 LIKE '%"+keyword+"%' OR product_name LIKE '%"+keyword+"%'";
        Page<ClientTbProduct> results=daoTool.getPageList(sql,page,account_id,ClientTbProduct.class);

        return results;
    }

    @Override
    public Page<ClientTbProduct> getAllProducts(int account_id, long shopId, Pageable page) throws Exception {
        String sql="SELECT a.product_id,product_code,product_code2,account_id,product_name,product_pic,product_price1,product_price2,product_type,product_fabric,product_fabricin,product_sizes,product_colors,product_status,product_tags ,lj.product_amount,lj2.`status` inventory_status " +
                " FROM tb_product a LEFT JOIN (SELECT c.`product_id`,SUM(b.`inventory_amount`) product_amount FROM tb_inventory b,tb_product_sku c WHERE b.shop_id="+shopId+" AND b.`sku_id`=c.`id` ) lj ON lj.product_id=a.`product_id` " +
                " LEFT JOIN `tb_product_inventory_status` lj2 ON lj2.`product_id`=a.`product_id` AND lj2.`shop_id`="+shopId ;
        Page<ClientTbProduct> results=daoTool.getPageList(sql,page,account_id,ClientTbProduct.class);

        return results;
    }

    @Override
    public Page<ClientTbProduct> getErrorProducts(int account_id, long shopId, Pageable page) throws Exception {
        String sql="SELECT a.*,lj.amount FROM tb_product a " +
                " LEFT JOIN (SELECT d.`product_id`,SUM(c.`inventory_amount`) amount FROM tb_inventory c,tb_product_sku d WHERE c.shop_id="+shopId+" AND c.`sku_id`=d.`id` ) lj ON lj.product_id=a.`product_id` " +
                " WHERE a.product_id in (SELECT product_id FROM `tb_product_inventory_status` WHERE STATUS='异常')";
        Page<ClientTbProduct> results=daoTool.getPageList(sql,page,account_id,ClientTbProduct.class);
        return results;
    }

    @Override
    public List<InventoryDetailDto> getOnSaleProducts(int account, long shopId) throws Exception {
        String sql="select a.*,b.`id` sku_id,b.`color_name`,b.`size_name`,b.`sku_status`,c.`id` inventory_id,c.`inventory_amount`,c.`shop_id` " +
                " from tb_product a,tb_product_sku b,tb_inventory c " +
                " where b.`product_id`=a.`product_id` AND b.`sku_status` ='上架' " +
                " and c.`shop_id`=1 " +
                " and b.`id`=c.`sku_id` and c.`inventory_amount`>0 " +
                " and (product_code2 like '%%' or product_name like '%%' )";
        List<InventoryDetailDto> results=daoTool.getAllList(sql,account,InventoryDetailDto.class);
        return results;
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
        SimpleDateFormat format2=new SimpleDateFormat("ddyyMM");
        String result=format2.format(new Date())+account_id+"-"+num;
        return result;
    }

    @Override
    public ClientTbProduct changeProductStatus(int account_id, long product_id, String status) throws Exception {
        if(status.indexOf("上架")<0&&status.indexOf("下架")<0&&status.indexOf("缺货")<0){
            throw new Exception("单品状态参数传入错误，允许的参数：[上架、下架、缺货]");
        }
        String sql="UPDATE `tb_product_sku` SET `sku_status`='"+status+"' WHERE `product_id`="+product_id;
        boolean result=daoTool.exec(sql,account_id);
        if(result){
            return refreshStatus(account_id,product_id);
        }else{
            throw new Exception("更新产品状态失败。");
        }
    }

    @Override
    public ProductSkuDto loadProduct(int account_id, long product_id) throws Exception {
        String sql="select * from tb_product where product_id="+product_id;
        ClientTbProduct product=(ClientTbProduct)daoTool.getOne(ClientTbProduct.class,account_id,product_id);
        if(product==null){
            throw new Exception("未找到编码为"+product_id+"的产品");
        }
        List<ClientTbProductSku> skus=skuService.loadSku(account_id,product_id);
        ProductSkuDto skuDto=new ProductSkuDto();
        skuDto.setProduct(product);
        skuDto.setSkus(skus);
        return skuDto;

    }

    @Override
    public ClientTbProduct refreshStatus(int account, long productId) throws Exception {
        ClientTbProduct product=(ClientTbProduct)daoTool.getOne(ClientTbProduct.class,account,productId);
        if(product==null){
            throw new Exception("未找到编码为"+productId+"的产品");
        }
        String sql="SELECT sku_status,COUNT(sku_status) statuamount, lj.skuamount FROM tb_product_sku a " +
                "LEFT JOIN (SELECT COUNT(1) skuamount FROM tb_product_sku b WHERE product_id="+productId+") lj ON 1=1 " +
                "WHERE a.product_id="+productId+" GROUP BY a.sku_status";
        List<Map<String,Object>> statusCount=daoTool.getAllList(sql,account);
        for(Map<String,Object> status:statusCount){
            if(status.get("SKU_STATUS").equals("缺货")){
                int skuamount=TypeUtils.castToInt(status.get("SKUAMOUNT"));
                int statusamount=TypeUtils.castToInt(status.get("STATUAMOUNT"));
                if(skuamount==statusamount){
                    product.setProductStatus("缺货");
                }else{
                    product.setProductStatus("部分缺货");
                }
            }
            else if(status.get("SKU_STATUS").equals("下架")){
                int skuamount=TypeUtils.castToInt(status.get("SKUAMOUNT"));
                int statusamount=TypeUtils.castToInt(status.get("STATUAMOUNT"));
                if(skuamount==statusamount){
                    product.setProductStatus("下架");
                }else{
                    product.setProductStatus("部分上架");
                }
            }
            else if(status.get("SKU_STATUS").equals("上架")){
                int skuamount=TypeUtils.castToInt(status.get("SKUAMOUNT"));
                int statusamount=TypeUtils.castToInt(status.get("STATUAMOUNT"));
                if(skuamount==statusamount){
                    product.setProductStatus("上架");
                }else{
                    product.setProductStatus("部分上架");
                }
            }
            product=daoTool.updateOne(account,product);
        }
        return product;
    }

    @Override

    public boolean setPics(int account, long productId, String pic, String type) throws Exception {
        ClientTbProduct product=(ClientTbProduct)daoTool.getOne(ClientTbProduct.class,account,productId);
        if(product==null){
            throw new Exception("未找到编号为"+productId+"的产品");
        }
        if(type.equals("产品图")){
            product.setProductPic(pic);
        }else{
            String desc=product.getProductDesc();
            if(desc!=null&&desc.length()>0){
                desc+=","+pic;
            }else{
                desc+=pic;
            }
            product.setProductDesc(desc);
        }
        daoTool.updateOne(account,product);
        return true;
    }

    @Override
    public List<String> getPics(int account, long productId) throws Exception {
        ClientTbProduct product=(ClientTbProduct)daoTool.getOne(ClientTbProduct.class,account,productId);
        if(product==null){
            throw new Exception("未找到编号为"+productId+"的产品");
        }
        List<String> result=new ArrayList<String>();
        String pics=product.getProductDesc();
        String [] picsArray=pics.split(",");
        for(int i=0;i<picsArray.length&&i<7;i++){
            String pic=picsArray[i];
            if(pic.equals("")){
                continue;
            }
            int pos=pic.lastIndexOf(".");
            String fileName=pic.substring(0,pos);
            String ext=pic.substring(pos+1);
            fileName+="_750";
            result.add(fileName+"."+ext);
        }
        return result;
    }

    @Override
    public boolean delPic(int account, long productId, String pic, String type) throws Exception {
        ClientTbProduct product=(ClientTbProduct)daoTool.getOne(ClientTbProduct.class,account,productId);
        if(product==null){
            throw new Exception("未找到编号为"+productId+"的产品");
        }
        if(type.equals("产品图")){
            product.setProductPic("");
        }else{
            String pics=product.getProductDesc();
            pics=pics.replaceAll(pic,"");
            product.setProductDesc(pics);
        }
        daoTool.updateOne(account,product);
        return true;
    }
}
