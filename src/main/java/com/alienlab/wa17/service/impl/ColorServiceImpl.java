package com.alienlab.wa17.service.impl;

import com.alienlab.db.Dao;
import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbColorCus;
import com.alienlab.wa17.entity.client.dto.ColorDto;
import com.alienlab.wa17.entity.main.MainTbColorSeries;
import com.alienlab.wa17.entity.main.MainTbColors;
import com.alienlab.wa17.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘 on 2017/3/3.
 */
@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    DaoTool daoTool;
    @Override
    public List<MainTbColorSeries> getColorSeries() throws Exception {
        String sql="select * from tb_color_series";
        List<MainTbColorSeries> result=daoTool.getAllList(sql,MainTbColorSeries.class);
        return result;
    }

    @Override
    public MainTbColorSeries addColorSeries(MainTbColorSeries series) throws Exception {
        return daoTool.saveOne(series,0);
    }

    @Override
    public MainTbColorSeries updateColorSeries(MainTbColorSeries series) throws Exception {
        return daoTool.updateOne(0,series);
    }

    @Override
    public boolean delColorSeries(int seriesid) throws Exception {
        return daoTool.deleteOne(MainTbColorSeries.class,0,seriesid);
    }

    @Override
    public List<ColorDto> getMainColors() throws Exception {
        List<MainTbColorSeries> series=getColorSeries();
        String sql="select * from tb_colors";
        List<MainTbColors> colors=daoTool.getAllList(sql,MainTbColors.class);
        List<ColorDto> results=new ArrayList<ColorDto>();
        for(MainTbColorSeries s:series){
            ColorDto colorDto=new ColorDto();
            colorDto.setSeriesId(s.getSeriesId());
            colorDto.setSeriesName(s.getSeriesName());
            colorDto.setSeriesRgb(s.getSeriesRgb());
            List<MainTbColors> subcolors=new ArrayList<>();
            for(MainTbColors color:colors){
                if(color.getColorSeriesId().equals(colorDto.getSeriesId())){
                    subcolors.add(color);
                }
            }
            colorDto.setColors(subcolors);
            results.add(colorDto);
        }
        return results;
    }

    @Override
    public List<ColorDto> getColors(int account_id) throws Exception {
        List<ColorDto> maincolors=getMainColors();
        String sql="select * from tb_color_cus";
        List<ClientTbColorCus> cuscolors=daoTool.getAllList(sql,account_id,ClientTbColorCus.class);
        if(cuscolors!=null&&cuscolors.size()>0){
            for(ClientTbColorCus cuscolor:cuscolors){
                for(ColorDto colordto:maincolors){
                    //自定义颜色根据色系归类
                    if(cuscolor.getColorSeries().equals(colordto.getSeriesName())){
                        MainTbColors maincolor=new MainTbColors();
                        maincolor.setColorId(cuscolor.getColorId());
                        maincolor.setColorName(cuscolor.getColorName());
                        maincolor.setColorRgb(cuscolor.getColorRgb());
                        maincolor.setColorSeries(cuscolor.getColorSeries());
                        maincolor.setColorSeriesId(colordto.getSeriesId());
                        maincolor.colorType="自定义";
                        List<MainTbColors> list=colordto.getColors();
                        list.add(maincolor);
                        colordto.setColors(list);
                    }
                }
            }
        }
        return maincolors;
    }

    @Override
    public ClientTbColorCus addColor(int account_id, ClientTbColorCus color) throws Exception {
        color=daoTool.saveOne(color,account_id);
        if(color.getColorId()>0){
            return color;
        }else{
            throw new Exception("保存自定义颜色失败了。");
        }
    }

    @Override
    public boolean delColor(int account_id, int color_id) throws Exception {
       return daoTool.deleteOne(ClientTbColorCus.class,account_id,color_id);
    }

    @Override
    public MainTbColors addMainColor(MainTbColors color) throws Exception {
        return daoTool.saveOne(color,0);
    }

    @Override
    public boolean delMainColor(int color_id) throws Exception {
        return daoTool.deleteOne(MainTbColors.class,0,color_id);
    }
}
