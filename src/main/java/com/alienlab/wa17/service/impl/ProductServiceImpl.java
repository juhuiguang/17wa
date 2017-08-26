package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbColorCus;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductInclude;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.client.dto.ColorDto;
import com.alienlab.wa17.entity.client.dto.InventoryDetailDto;
import com.alienlab.wa17.entity.client.dto.ProductDto;
import com.alienlab.wa17.entity.client.dto.ProductSkuDto;
import com.alienlab.wa17.entity.main.MainTbColors;
import com.alienlab.wa17.entity.main.MainTbMarket;
import com.alienlab.wa17.entity.main.MainTbProducttype;
import com.alienlab.wa17.entity.main.MainTbSize;
import com.alienlab.wa17.entity.main.dto.MarketDto;
import com.alienlab.wa17.entity.main.dto.ProductTypeDto;
import com.alienlab.wa17.service.ImageService;
import com.alienlab.wa17.service.IncludeService;
import com.alienlab.wa17.service.ProductService;
import com.alienlab.wa17.service.SkuService;
import org.hibernate.annotations.Synchronize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 橘 on 2017/2/6.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    DaoTool daoTool;
    @Autowired
    SkuService skuService;
    @Autowired
    IncludeService includeService;


    @Override
    public ClientTbProduct getProduct(int account_id, long productid) throws Exception {
        return (ClientTbProduct)daoTool.getOne(ClientTbProduct.class,account_id,productid);
    }

    @Override
    public Page<ClientTbProduct> getProducts(int account_id, String keyword, Pageable page) throws Exception {
        String sql="SELECT product_id,product_code,product_code2,account_id,product_name,product_pic,product_price1,product_price2,product_type,product_fabric,product_fabricin,product_sizes " +
                "product_colors,product_status,product_tags FROM tb_product where product_code2 like '%"+keyword+"%' or product_name like '%"+keyword+"%' or product_code like '%"+keyword+"%'";
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
//        String sql="SELECT a.product_id,product_code,product_code2,account_id,product_name,product_pic,product_price1,product_price2,product_type,product_fabric,product_fabricin,product_sizes,product_colors,product_status,product_tags ,lj.amount product_amount,lj2.`status` inventory_status " +
//                " FROM tb_product a LEFT JOIN (SELECT c.`product_id`,SUM(b.`inventory_amount`) amount FROM tb_inventory b,tb_product_sku c WHERE b.shop_id="+shopId+" AND b.`sku_id`=c.`id` ) lj ON lj.product_id=a.`product_id` " +
//                " LEFT JOIN `tb_product_inventory_status` lj2 ON lj2.`product_id`=a.`product_id` AND lj2.`shop_id`="+shopId +
//                " WHERE product_code2 LIKE '%"+keyword+"%' OR product_name LIKE '%"+keyword+"%'";
        String sql="SELECT a.product_id,product_code,product_code2,account_id,product_name,product_pic,product_price1,product_price2,product_type,product_fabric,product_fabricin,product_sizes,product_colors,product_status,product_tags ,lj.product_amount,lj2.`status` inventory_status ,lj3.tempamount " +
                " FROM tb_product a LEFT JOIN (SELECT c.`product_id`,SUM(b.`inventory_amount`) product_amount FROM tb_inventory b,tb_product_sku c WHERE b.shop_id="+shopId+" AND b.`sku_id`=c.`id` ) lj ON lj.product_id=a.`product_id`  " +
                "  LEFT JOIN `tb_product_inventory_status` lj2 ON lj2.`product_id`=a.`product_id` AND lj2.`shop_id`=" +shopId +
                "  LEFT JOIN (SELECT SUM(m.`amount`) tempamount,n.`product_id` FROM `tb_inventory_temp` m,tb_product_sku n WHERE m.`sku_id`=n.`id` AND m.`shop_id`="+shopId+") lj3 ON lj3.product_id=a.`product_id` " +
                " WHERE product_code2 LIKE '%"+keyword+"%' OR product_name LIKE '%"+keyword+"%' "+
                "  ORDER BY FIELD(lj2.`status`,'未盘点','异常','正常'),product_code2";

        Page<ClientTbProduct> results=daoTool.getPageList(sql,page,account_id,ClientTbProduct.class);

        return results;
    }

    @Override
    public ProductSkuDto getProductByCode(int account, String code,long shopId) throws Exception {
        String sql="SELECT a.product_id,product_code,product_code2,account_id,product_name,product_pic,product_price1,product_price2,product_type,product_fabric,product_fabricin,product_sizes,product_colors,product_status,product_tags ,lj.product_amount,lj2.`status` inventory_status ,lj3.tempamount " +
                " FROM tb_product a LEFT JOIN (SELECT c.`product_id`,SUM(b.`inventory_amount`) product_amount FROM tb_inventory b,tb_product_sku c WHERE b.shop_id="+shopId+" AND b.`sku_id`=c.`id` ) lj ON lj.product_id=a.`product_id`  " +
                "  LEFT JOIN `tb_product_inventory_status` lj2 ON lj2.`product_id`=a.`product_id` AND lj2.`shop_id`=" +shopId +
                "  LEFT JOIN (SELECT SUM(m.`amount`) tempamount,n.`product_id` FROM `tb_inventory_temp` m,tb_product_sku n WHERE m.`sku_id`=n.`id` AND m.`shop_id`="+shopId+") lj3 ON lj3.product_id=a.`product_id` " +
                " WHERE product_code2 LIKE '%"+code+"%' OR product_code LIKE '%"+code+"%' "+
                "  ORDER BY FIELD(lj2.`status`,'未盘点','异常','正常'),product_code2";
        ClientTbProduct product=(ClientTbProduct)daoTool.getObject(sql,account,ClientTbProduct.class);
        if(product!=null){
            List<ClientTbProductSku> skus=skuService.loadSku(account,product.getProductId());
            ProductSkuDto p=new ProductSkuDto();
            p.setProduct(product);
            p.setSkus(skus);
            return p;
        }else{
            throw new Exception("查询产品错误");
        }
    }

    @Override
    public Page<ClientTbProduct> getAllProducts(int account_id, long shopId, Pageable page) throws Exception {
//        String sql="SELECT a.product_id,product_code,product_code2,account_id,product_name,product_pic,product_price1,product_price2,product_type,product_fabric,product_fabricin,product_sizes,product_colors,product_status,product_tags ,lj.product_amount,lj2.`status` inventory_status " +
//                " FROM tb_product a LEFT JOIN (SELECT c.`product_id`,SUM(b.`inventory_amount`) product_amount FROM tb_inventory b,tb_product_sku c WHERE b.shop_id="+shopId+" AND b.`sku_id`=c.`id` ) lj ON lj.product_id=a.`product_id` " +
//                " LEFT JOIN `tb_product_inventory_status` lj2 ON lj2.`product_id`=a.`product_id` AND lj2.`shop_id`="+shopId +" ORDER BY FIELD(lj2.`status`,'未盘点','异常','正常'),product_code2";
        String sql="SELECT a.product_id,product_code,product_code2,account_id,product_name,product_pic,product_price1,product_price2,product_type,product_fabric,product_fabricin,product_sizes,product_colors,product_status,product_tags ,lj.product_amount,lj2.`status` inventory_status ,lj3.tempamount " +
                " FROM tb_product a LEFT JOIN (SELECT c.`product_id`,SUM(b.`inventory_amount`) product_amount FROM tb_inventory b,tb_product_sku c WHERE b.shop_id="+shopId+" AND b.`sku_id`=c.`id` ) lj ON lj.product_id=a.`product_id`  " +
                "  LEFT JOIN `tb_product_inventory_status` lj2 ON lj2.`product_id`=a.`product_id` AND lj2.`shop_id`=" +shopId +
                "  LEFT JOIN (SELECT SUM(m.`amount`) tempamount,n.`product_id` FROM `tb_inventory_temp` m,tb_product_sku n WHERE m.`sku_id`=n.`id` AND m.`shop_id`="+shopId+") lj3 ON lj3.product_id=a.`product_id` " +
                "  ORDER BY FIELD(lj2.`status`,'未盘点','异常','正常'),product_code2";
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
    public List<InventoryDetailDto> getOnSaleProducts(int account, long shopId,String keyword) throws Exception {
        if(keyword==null){
            keyword="";
        }
        String sql="select a.*,b.`id` sku_id,b.`color_name`,b.`size_name`,b.`sku_status`,c.`id` inventory_id,c.`inventory_amount`,c.`shop_id` " +
                " from tb_product a,tb_product_sku b,tb_inventory c " +
                " where b.`product_id`=a.`product_id` AND b.`sku_status` ='上架' " +
                " and c.`shop_id`=" +shopId+" "+
                " and b.`id`=c.`sku_id` and c.`inventory_amount`>0 " +
                " and (product_code2 like '%"+keyword+"%' or product_name like '%"+keyword+"%' )";
        List<InventoryDetailDto> results=daoTool.getAllList(sql,account,InventoryDetailDto.class);
        return results;
    }

    @Override
    public List<InventoryDetailDto> getOnSaleByProduct(int account, long shopId,long productId) throws Exception {
        String sql="SELECT a.*,b.`id` sku_id,b.`color_name`,b.`size_name`,b.`sku_status`,c.`id` inventory_id,c.`inventory_amount`,c.`shop_id` " +
                "                FROM tb_product a,tb_product_sku b,tb_inventory c " +
                "                WHERE b.`product_id`=a.`product_id` AND b.`sku_status` ='上架' " +
                "                AND c.`shop_id`= " +shopId+" "+
                "                AND b.`id`=c.`sku_id` AND c.`inventory_amount`>0 " +
                "                AND (a.product_id="+productId+")";
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

            //商品保存成功后，添加默认库存值
            String sql="insert into tb_inventory(sku_id,shop_id,inventory_amount,inventory_count_status) " +
                    "select a.id,b.shop_id,"+product.getProductInitAmount()+",'正常' from tb_product_sku a,tb_shop b where product_id="+productid;
            daoTool.exec(sql,account_id);

        }else{
            throw new Exception("产品保存过程中出错了。");
        }
        return product;
    }

    @Override
    public ClientTbProduct updateProduct(int account_id, ClientTbProduct product,ClientTbProductSku [] clientTbProductSkus) throws Exception {
        product=daoTool.updateOne(account_id,product);
        int productid=(int)product.getProductId();
//        //删除现有sku
//        skuService.delSku(account_id,productid);
        //保存新的sku
        List<ClientTbProductSku> oldSkus=skuService.loadSku(account_id,productid);
        for(ClientTbProductSku sku:clientTbProductSkus){
            sku.setProductId((long)productid);
            boolean isExists=false;
            for (ClientTbProductSku oldSku : oldSkus) {
                //如果更新提交的sku与已有的sku相同
                if(oldSku.getColorName().equals(sku.getColorName())&&sku.getSizeName().equals(oldSku.getSizeName())){
                    isExists=true;
                    break;
                }
            }
            if(isExists){
                continue;//如果已经存在，跳过保存
            }else{//如果有新sku，进行保存
                skuService.addSku(account_id,productid,sku);
            }
        }

        //再找出原有sku里有，更新的sku里被删除的
        for (ClientTbProductSku oldSku : oldSkus) {
            boolean isExists=false;
            for(ClientTbProductSku sku:clientTbProductSkus){
                if(oldSku.getColorName().equals(sku.getColorName())&&sku.getSizeName().equals(oldSku.getSizeName())){
                    isExists=true;
                    break;
                }
            }
            if(isExists){
                continue;//如果已经存在，跳过删除
            }else{//如果旧sku里存在，新sku里不存在，删除旧数据
                skuService.delSkuSingle(account_id,(int)oldSku.getId());
            }
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
        Map colorMap=new HashMap();
        Map sizeMap=new HashMap();
        List<MainTbColors> colors=new ArrayList<>();
        List<MainTbSize> sizes=new ArrayList<>();
        for (ClientTbProductSku clientTbProductSku : skus) {
            String colorName=clientTbProductSku.getColorName();
            if(!colorMap.containsKey(colorName)){
                colorMap.put(colorName,clientTbProductSku);
                MainTbColors c=new MainTbColors();
                c.setColorId(clientTbProductSku.getColorId());
                c.setColorName(clientTbProductSku.getColorName());
                colors.add(c);
            }
            String sizeName=clientTbProductSku.getSizeName();
            if(!sizeMap.containsKey(sizeName)){
                sizeMap.put(sizeName,clientTbProductSku);
                MainTbSize s=new MainTbSize();
                s.setSizeId(clientTbProductSku.getSizeId());
                s.setSizeName(clientTbProductSku.getSizeName());
                sizes.add(s);
            }
        }
        skuDto.setColors(colors);
        skuDto.setSizes(sizes);

        //20170806增加加载详情时
        List<ClientTbProductInclude> includes=includeService.getProductIncluedes(account_id,(int)product_id);
        skuDto.setIncludes(includes);

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
    public boolean  setPics(int account, long productId, String pic, String type) throws Exception {
        ClientTbProduct product=(ClientTbProduct)daoTool.getOne(ClientTbProduct.class,account,productId);
        if(product==null){
            throw new Exception("未找到编号为"+productId+"的产品");
        }
        if(type.equals("产品图")){
            product.setProductPic(pic);
        }else{
            String desc=product.getProductDesc();
            if(type.equals("尺码图")){//增加尺码图的兼容
                if(desc!=null&&desc.length()>0){
                    //判断第一张是否是尺码图
                    String temp=desc.split(",")[0];
                    if(temp.indexOf("_include")>=0){
                        System.out.println("pic desc pictures is not null>>>"+temp+">>>>"+desc);
                        desc=desc.replace(temp,pic);
                        System.out.println("pic desc replaced >>>"+pic+">>>>"+desc);
                    }else{
                        desc=pic+","+desc;
                    }
                }else{
                    desc+=pic;
                }
            }else{
                if(desc!=null&&desc.length()>0){
                    desc+=","+pic;
                }else{
                    desc+=pic;
                }
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
            if(fileName.indexOf("_include")>0){

            }else{
                fileName+="_750";
            }

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
            if(pics.startsWith(",")){
                pics=pics.substring(1);
            }
            product.setProductDesc(pics);
        }
        daoTool.updateOne(account,product);
        return true;
    }

    @Override
    public List<ProductTypeDto> getAllProductType() throws Exception {
        return getSubTypes("0");
    }

    private List<ProductTypeDto> getSubTypes(String pid) throws Exception{
        String sql="select a.*,(select count(1) from tb_producttype b where b.producttype_pid=a.producttype_id) as leaf from tb_producttype a where a.producttype_pid="+pid;
        List<MainTbProducttype> subtypes=daoTool.getAllList(sql,MainTbProducttype.class);
        List<ProductTypeDto> result=new ArrayList<ProductTypeDto>();
        for(MainTbProducttype type:subtypes){
            ProductTypeDto p=new ProductTypeDto();
            p.setProducttypeId(type.getProducttypeId());
            p.setProducttypeLevel(type.getProducttypeLevel());
            p.setProducttypeName(type.getProducttypeName());
            p.setProducttypePid(type.getProducttypePid());
            p.setLeaf(type.getLeaf());
            if(p.getLeaf()>0){
                p.setSubTypes(getSubTypes(String.valueOf(type.getProducttypeId())));
            }
            result.add(p);
        }
        return result;
    }

    @Override
    public MainTbProducttype addType(MainTbProducttype type) throws Exception {
        return daoTool.saveOne(type,0);
    }

    @Override
    public boolean delType(long typeId) throws Exception {
        return daoTool.deleteOne(MainTbProducttype.class,0,typeId);
    }

    @Override
    public MainTbProducttype updateType(MainTbProducttype type) throws Exception{
        return daoTool.updateOne(0,type);
    }

    @Override
    public boolean delProduct(int account, long productId) throws Exception {
        String validatesql="SELECT * FROM tb_product WHERE EXISTS(SELECT 1 FROM tb_order_detail a,tb_product_sku b WHERE a.`sku_id`=b.id AND tb_product.`product_id`=b.`product_id`) " +
                "AND product_id="+productId;
        List r=daoTool.getAllList(validatesql,account);
        if(r!=null&&r.size()>0){
            throw new Exception("此产品已产生订单，不可删除。");
        }
        String delSql="INSERT INTO `tb_product_sku_del` SELECT * FROM tb_product_sku WHERE tb_product_sku.`product_id`="+productId;
        daoTool.exec(delSql,account);
        delSql="delete from tb_product_sku where product_id="+productId;
        daoTool.exec(delSql,account);
        delSql="INSERT INTO `tb_product_del` SELECT * FROM tb_product WHERE tb_product.`product_id`="+productId;
        daoTool.exec(delSql,account);
        delSql="delete from tb_product where product_id="+productId;
        daoTool.exec(delSql,account);
        return true;
    }
}
