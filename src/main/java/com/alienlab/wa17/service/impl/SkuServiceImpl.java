package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbProductSku;
import com.alienlab.wa17.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
