package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbCustom;
import com.alienlab.wa17.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 橘 on 2017/3/31.
 */
@Service
public class CustomServiceImpl implements CustomService {
    @Autowired
    DaoTool daoTool;
    @Override
    public List<ClientTbCustom> findCustom(int account, String keyword) throws Exception {
        String sql="select * from tb_custom where (custom_name like '%"+keyword+"%' or custom_phone like '%"+keyword+"%')";
        List<ClientTbCustom> customs=daoTool.getAllList(sql,account,ClientTbCustom.class);
        return customs;
    }

    @Override
    public ClientTbCustom addCustom(int account, ClientTbCustom custom) throws Exception {
        return null;
    }
}
