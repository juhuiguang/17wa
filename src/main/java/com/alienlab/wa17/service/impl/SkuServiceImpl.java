package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbProduct;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.entity.client.dto.InventoryDto;
import com.alienlab.wa17.service.ProductService;
import com.alienlab.wa17.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 橘 on 2017/3/6.
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    DaoTool daoTool;
    @Autowired
    ProductService productService;
    @Override
    public List<ClientTbProductSku> loadSku(int account_id, long product_id,long shopId) throws Exception {
        String sql="";
        if(shopId==0){
            sql="select * from tb_product_sku where product_id="+product_id;
        }else{
            sql="SELECT " +
                    " a.*,c.inventory_amount amount, b.amount tempamount " +
                    "FROM " +
                    " tb_product_sku a " +
                    "LEFT JOIN tb_inventory c ON a.id = c.sku_id and c.shop_id="+shopId+" " +
                    "LEFT JOIN tb_inventory_temp b ON a.id = b.sku_id and b.shop_id="+shopId+" " +
                    "WHERE " +
                    " a.product_id = "+product_id;
        }

        return daoTool.getAllList(sql,account_id,ClientTbProductSku.class);
    }

    public List<InventoryDto> getAllSku(int account, long shopId, String code) throws Exception {
        String sql="SELECT tb1.*,lj.id inventory_id,lj.`inventory_amount` FROM ( " +
                "SELECT a.*,b.`product_code`,b.`product_code2`,b.`product_name` FROM `tb_product_sku` a,`tb_product` b " +
                "WHERE a.`product_id`=b.`product_id` AND (b.`product_code` LIKE '%"+code+"%' OR b.`product_code2` LIKE '%"+code+"%') " +
                ") tb1 " +
                "LEFT JOIN `tb_inventory` lj ON lj.`sku_id`=tb1.id AND lj.`shop_id`= "+shopId;
        return daoTool.getAllList(sql,account,InventoryDto.class);
    }

    @Override
    public ClientTbProductSku addSku(int account_id, long product_id, ClientTbProductSku sku)throws Exception {
        sku.setProductId(product_id);
        return daoTool.saveOne(sku,account_id);
    }

    @Override
    public boolean delSkuSingle(int account_id, int sku_id) throws Exception{
        daoTool.deleteOne(ClientTbProductSku.class,account_id,sku_id);
        return false;
    }

    @Override
    public boolean delSku(int account_id, int product_id) throws Exception {
        String sql="delete from tb_product_sku where product_id="+product_id;
        return daoTool.exec(sql,account_id);
    }

    @Override
    public ClientTbProductSku setStatus(int account_id, long sku_id, String status) throws Exception {
        if(status.indexOf("上架")<0&&status.indexOf("下架")<0&&status.indexOf("缺货")<0){
            throw new Exception("单品状态参数传入错误，允许的参数：[上架、下架、缺货]");
        }
        ClientTbProductSku sku=(ClientTbProductSku)daoTool.getOne(ClientTbProductSku.class,account_id,sku_id);
        if(sku==null){
            throw new Exception("未找到编码为"+sku_id+"的Sku");
        }

        sku.setSkuStatus(status);
        sku=daoTool.updateOne(account_id,sku);
        //更新产品主状态
        productService.refreshStatus(account_id,sku.getProductId());
        return sku;
    }

}
