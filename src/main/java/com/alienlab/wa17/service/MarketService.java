package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.main.MainTbMarket;
import com.alienlab.wa17.entity.main.dto.MarketDto;

import java.util.List;

/**
 * Created by 橘 on 2017/3/5.
 */
public interface MarketService {
    List<MarketDto> getMarkets() throws Exception ;

    MarketDto addMarket(MarketDto market) throws Exception;
    MarketDto updateMarket(MarketDto market) throws Exception;
    boolean delMarket(int marketId) throws Exception;
}
