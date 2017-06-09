package com.alienlab.wa17.entity.main.dto;

import com.alienlab.wa17.entity.main.MainTbProducttype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘 on 2017/6/9.
 */
public class ProductTypeDto extends MainTbProducttype {
    List<ProductTypeDto> subTypes=new ArrayList<ProductTypeDto>();

    public List<ProductTypeDto> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<ProductTypeDto> subTypes) {
        this.subTypes = subTypes;
    }
}
