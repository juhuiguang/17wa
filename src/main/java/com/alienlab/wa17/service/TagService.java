package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.main.MainTbTags;

import java.util.List;

/**
 * Created by 橘 on 2017/3/6.
 */
public interface TagService {
    List<MainTbTags> getTags(String typeName) throws Exception;
}
