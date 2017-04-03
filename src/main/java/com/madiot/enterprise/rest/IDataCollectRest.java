package com.madiot.enterprise.rest;

import com.madiot.enterprise.common.exception.RestException;
import com.madiot.enterprise.model.collect.CollectRequest;
import com.madiot.enterprise.model.collect.CollectResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by DELL on 2016/6/26.
 */
public interface IDataCollectRest {

    /**
     * 获取数据
     *
     * @return CollectResponse
     */
    public CollectResponse getCollectData(CollectRequest request, CompletableFuture<Boolean> result) throws RestException;
}
