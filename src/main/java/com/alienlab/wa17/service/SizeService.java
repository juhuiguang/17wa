package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbSizeCus;
import com.alienlab.wa17.entity.client.dto.SizeDto;
import com.alienlab.wa17.entity.main.MainTbSize;
import com.alienlab.wa17.entity.main.MainTbSizetype;

import java.util.List;

/**
 * Created by 橘 on 2017/3/2.
 */
public interface SizeService {
    List<MainTbSizetype> getSizeType() throws Exception;
    List<SizeDto> getMainSizes()throws Exception;
    List<SizeDto> getSizes(int account_id)throws Exception;

    ClientTbSizeCus addSize(int account_id, ClientTbSizeCus size) throws Exception;
    boolean delSize(int account_id,int size_id) throws Exception;

    MainTbSize addMainSize(MainTbSize size) throws Exception;
    boolean delMainSize(int sizeId) throws Exception;
}
