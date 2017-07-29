package com.madiot.enterprise.service;

import com.madiot.enterprise.common.exception.RestException;

import java.util.concurrent.CompletableFuture;


/**
 * Created by DELL on 2016/6/26.
 */
public interface IDataCollectService {
    void collectData(String enterpriseName, Integer localadm, Integer localent, int startRows, int endRows, CompletableFuture<Boolean> result) throws RestException;

}
