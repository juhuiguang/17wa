package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.main.MainTbTags;
import com.alienlab.wa17.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 橘 on 2017/3/6.
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    DaoTool daoTool;
    @Override
    public List<MainTbTags> getTags(String typeName) throws Exception {
       String sql="select * from tb_tags where tag_type='"+typeName+"'";
       return daoTool.getAllList(sql,MainTbTags.class);
    }
}
