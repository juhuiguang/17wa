package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.main.MainTbSize;
import com.alienlab.wa17.entity.main.MainTbSizetype;

import java.util.List;

/**
 * Created by 橘 on 2017/3/2.
 */
public class SizeDto extends MainTbSizetype {
    List<MainTbSize> sizes;

    public List<MainTbSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<MainTbSize> sizes) {
        this.sizes = sizes;
    }
}

