package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.main.MainTbTags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by 橘 on 2017/3/6.
 */
public interface TagService {
    Page<MainTbTags> getTags(Pageable page) throws Exception;
    List<MainTbTags> getTags(String typeName) throws Exception;
    MainTbTags addTag(MainTbTags tag) throws Exception;
    boolean delTag(int tagId) throws Exception;
}
