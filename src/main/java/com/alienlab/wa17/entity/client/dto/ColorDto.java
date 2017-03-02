package com.alienlab.wa17.entity.client.dto;

import com.alienlab.wa17.entity.main.MainTbColorSeries;
import com.alienlab.wa17.entity.main.MainTbColors;

import java.util.List;

/**
 * Created by 橘 on 2017/3/2.
 */
public class ColorDto extends MainTbColorSeries {
    List<MainTbColors> colors;

    public List<MainTbColors> getColors() {
        return colors;
    }

    public void setColors(List<MainTbColors> colors) {
        this.colors = colors;
    }
}
