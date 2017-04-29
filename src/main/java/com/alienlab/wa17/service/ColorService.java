package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbColorCus;
import com.alienlab.wa17.entity.client.dto.ColorDto;
import com.alienlab.wa17.entity.main.MainTbColorSeries;
import com.alienlab.wa17.entity.main.MainTbColors;

import java.util.List;

/**
 * Created by 橘 on 2017/3/2.
 */
public interface ColorService {
    List<MainTbColorSeries> getColorSeries() throws Exception;
    List<ColorDto> getMainColors()throws Exception;
    List<ColorDto> getColors(int account_id)throws Exception;

    ClientTbColorCus addColor(int account_id,ClientTbColorCus color) throws Exception;
    boolean delColor(int account_id,int color_id) throws Exception;

    MainTbColors addMainColor(MainTbColors color) throws Exception;
    boolean delMainColor(int color_id) throws Exception;


}

