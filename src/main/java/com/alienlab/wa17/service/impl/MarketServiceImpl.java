package com.alienlab.wa17.service.impl;

import com.alienlab.wa17.dao.DaoTool;
import com.alienlab.wa17.entity.main.MainTbMarket;
import com.alienlab.wa17.entity.main.dto.MarketDto;
import com.alienlab.wa17.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 橘 on 2017/3/5.
 */
@Service
public class MarketServiceImpl implements MarketService {
    @Autowired
    DaoTool daoTool;
    @Override
    public List<MarketDto> getMarkets() throws Exception {
        return getSubMarkets("0");
    }

    @Override
    public MarketDto addMarket(MarketDto market) throws Exception {
        return null;
    }

    @Override
    public MarketDto updateMarket(MarketDto market) throws Exception {
        return null;
    }

    @Override
    public boolean delMarket(int marketId) throws Exception {
        return false;
    }

    private List<MarketDto> getSubMarkets(String pid) throws Exception{
        String sql="select a.*,(select count(1) from tb_market b where b.mk_pid=a.mk_id) as leaf from tb_market a where a.mk_pid="+pid+" order by mk_sort";
        List<MainTbMarket> submks=daoTool.getAllList(sql,MainTbMarket.class);
        List<MarketDto> result=new ArrayList<MarketDto>();
        for(MainTbMarket mk:submks){
            MarketDto m=new MarketDto();
            m.setMkId(mk.getMkId());
            m.setMkLevel(mk.getMkLevel());
            m.setMkName(mk.getMkName());
            m.setLeaf(mk.getLeaf());
            m.setMkPid(mk.getMkPid());
            m.setMkSort(mk.getMkSort());
            m.setMkType(mk.getMkType());
            if(m.getLeaf()>0){
                m.setSubMarkets(getSubMarkets(String.valueOf(mk.getMkId())));
            }
            result.add(m);
        }
        return result;
    }


}
