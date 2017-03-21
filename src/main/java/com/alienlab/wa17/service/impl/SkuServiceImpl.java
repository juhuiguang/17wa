package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
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
    @Override
    public List<ClientTbProductSku> loadSku(int account_id, long product_id) throws Exception {
        String sql="select * from tb_product_sku where product_id="+product_id;
        return daoTool.getAllList(sql,account_id,ClientTbProductSku.class);
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
        return sku;
    }

    private void refreshProductStatus(int account_id,long product_id){
        String sql="SELECT sku_status,COUNT(sku_status) amount FROM tb_product_sku WHERE product_id="+product_id;
        //List<Map<String,Object>> skuStatus=daoTool.getAllList(sql,account_id);

    }
}
