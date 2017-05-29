package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.main.MainTbTags;
import com.alienlab.wa17.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<MainTbTags> getTags(Pageable page) throws Exception {
        String sql="select * from tb_tags";
        return daoTool.getPageList(sql,page,0,MainTbTags.class);
    }

    @Override
    public List<MainTbTags> getTags(String typeName) throws Exception {
       String sql="select * from tb_tags where tag_type='"+typeName+"'";
       return daoTool.getAllList(sql,MainTbTags.class);
    }

    @Override
    public MainTbTags addTag(MainTbTags tag) throws Exception {
        MainTbTags result=daoTool.saveOne(tag,0);
        return result;
    }

    @Override
    public boolean delTag(int tagId) throws Exception {
        return daoTool.deleteOne(MainTbTags.class,0,tagId);
    }
}
