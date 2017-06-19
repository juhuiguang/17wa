package com.alienlab.wa17.service;

import com.alienlab.wa17.entity.client.ClientTbGradeOption;

import java.util.List;

/**
 * Created by 橘 on 2017/6/19.
 */
public interface GradeOptionService {
    List<ClientTbGradeOption> getOptions(int account) throws Exception;

    ClientTbGradeOption addOption(ClientTbGradeOption option,int account) throws Exception;
    boolean delOption(int id,int account) throws Exception;
}
