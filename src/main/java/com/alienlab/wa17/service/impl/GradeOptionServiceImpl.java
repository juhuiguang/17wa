package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbGradeOption;
import com.alienlab.wa17.service.GradeOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 橘 on 2017/6/19.
 */
@Service
public class GradeOptionServiceImpl implements GradeOptionService {
    @Autowired
    DaoTool daoTool;
    @Override
    public List<ClientTbGradeOption> getOptions(int account) throws Exception {
        String sql="select * from tb_grade_option";
        return daoTool.getAllList(sql,account,ClientTbGradeOption.class);
    }

    @Override
    public ClientTbGradeOption addOption(ClientTbGradeOption option,int account) throws Exception {
        return daoTool.saveOne(option,account);
    }

    @Override
    public boolean delOption(int id,int account) throws Exception {
        return daoTool.deleteOne(ClientTbGradeOption.class,account,id);
    }


}
