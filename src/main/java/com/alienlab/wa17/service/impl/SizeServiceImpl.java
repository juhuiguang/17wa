package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.client.ClientTbColorCus;
import com.alienlab.wa17.entity.client.ClientTbSizeCus;
import com.alienlab.wa17.entity.client.dto.ColorDto;
import com.alienlab.wa17.entity.client.dto.SizeDto;
import com.alienlab.wa17.entity.main.MainTbColorSeries;
import com.alienlab.wa17.entity.main.MainTbColors;
import com.alienlab.wa17.entity.main.MainTbSize;
import com.alienlab.wa17.entity.main.MainTbSizetype;
import com.alienlab.wa17.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 橘 on 2017/3/3.
 */
@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    DaoTool daoTool;
    @Override
    public List<MainTbSizetype> getSizeType() throws Exception {
        String sql="select * from tb_sizetype";
        List<MainTbSizetype> result=daoTool.getAllList(sql,MainTbSizetype.class);
        return result;
    }

    @Override
    public List<SizeDto> getMainSizes() throws Exception {
        List<MainTbSizetype> sizetypes=getSizeType();
        String sql="select * from tb_size";
        List<MainTbSize> sizes=daoTool.getAllList(sql,MainTbSize.class);
        List<SizeDto> results=new ArrayList<SizeDto>();
        for(MainTbSizetype s:sizetypes){
            SizeDto sizeDto=new SizeDto();
            sizeDto.setSizetypeId(s.getSizetypeId());
            sizeDto.setSizetypeName(s.getSizetypeName());
            List<MainTbSize> subsizes=new ArrayList<>();
            for(MainTbSize size:sizes){
                if(size.getSizeTypeId().equals(sizeDto.getSizetypeId())){
                    subsizes.add(size);
                }
            }
            sizeDto.setSizes(subsizes);
            results.add(sizeDto);
        }
        return results;
    }

    @Override
    public List<SizeDto> getSizes(int account_id) throws Exception {
        List<SizeDto> mainSizes=getMainSizes();
        String sql="select * from tb_size_cus";
        List<ClientTbSizeCus> cussizes=daoTool.getAllList(sql,account_id,ClientTbSizeCus.class);
        if(cussizes!=null&&cussizes.size()>0) {
            for (ClientTbSizeCus cussize : cussizes) {
                for (SizeDto sizeDto : mainSizes) {
                    //自定义颜色根据色系归类
                    if (cussize.getSizeTypeName().equals(sizeDto.getSizetypeName())) {
                        MainTbSize mainTbSize = new MainTbSize();
                        mainTbSize.setSizeId(cussize.getSizeId());
                        mainTbSize.setSizeName(cussize.getSizeName());
                        mainTbSize.setSizeTypeId(sizeDto.getSizetypeId());
                        mainTbSize.setSizeTypeName(cussize.getSizeTypeName());
                        mainTbSize.sizeType="自定义";
                        List<MainTbSize> list = sizeDto.getSizes();
                        list.add(mainTbSize);
                        sizeDto.setSizes(list);
                    }
                }
            }
        }
        return mainSizes;
    }

    @Override
    public ClientTbSizeCus addSize(int account_id, ClientTbSizeCus size) throws Exception {
        size=daoTool.saveOne(size,account_id);
        if(size.getSizeId()>0){
            return size;
        }else{
            throw new Exception("保存自定义尺码失败了。");
        }
    }

    @Override
    public boolean delSize(int account_id, int size_id) throws Exception {
        return daoTool.deleteOne(ClientTbSizeCus.class,account_id,size_id);
    }
}
