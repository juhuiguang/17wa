package com.alienlab.wa17.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbProductInclude;
import com.alienlab.wa17.entity.main.MainTbInclude;
import com.alienlab.wa17.service.IncludeService;
import com.sun.xml.internal.rngom.ast.builder.Include;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘 on 2017/5/20.
 */
@Service
public class IncludeServiceImpl implements IncludeService {

    @Autowired
    DaoTool daoTool;

    @Override
    public List getIncludeType() throws Exception {
        String sql="select distinct include_type from tb_size_include";
        return daoTool.getAllList(sql);
    }

    @Override
    public List<MainTbInclude> getAllInclude() throws Exception {
        String sql="select * from tb_size_include";
        return daoTool.getAllList(sql,MainTbInclude.class);
    }

    @Override
    public MainTbInclude addInclude(String includeName, String includeType) throws Exception {
        MainTbInclude mainTbInclude=new MainTbInclude();
        mainTbInclude.setIncludeName(includeName);
        mainTbInclude.setIncludeType(includeType);
        return daoTool.saveOne(mainTbInclude,0);
    }

    @Override
    public boolean delInclude(int includeId) throws Exception {
        return daoTool.deleteOne(MainTbInclude.class,0,includeId);
    }

    @Override
    public List<ClientTbProductInclude> getProductIncluedes(int account, int productId) throws Exception {
        String sql="select * from tb_product_include where product_id="+productId+" order by size_name,include_name";
        return daoTool.getAllList(sql,account,ClientTbProductInclude.class);
    }

    @Override
    public List<ClientTbProductInclude> addProductIncludes(int account, int productId, JSONArray includes) {
        String sql="delete from tb_product_include where product_id="+productId;
        try {
            daoTool.exec(sql,account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ClientTbProductInclude> result=new ArrayList<>();
        if(includes!=null&&includes.size()>0){
            for(int i=0;i<includes.size();i++){
                JSONObject include=includes.getJSONObject(i);
                ClientTbProductInclude ci=new ClientTbProductInclude();
                ci.setIncludeName(include.getString("includeName"));
                ci.setIncludeValue(include.getIntValue("includeValue"));
                ci.setProductId(productId);
                ci.setSizeName(include.getString("sizeName"));
                try {
                    ci=daoTool.saveOne(ci,account);
                    if(ci.getIncludeId()>0){
                        result.add(ci);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

    @Override
    public boolean delProductInclude(int account, int includeId) throws Exception {
        return daoTool.deleteOne(ClientTbProductInclude.class,account,includeId);
    }
}
