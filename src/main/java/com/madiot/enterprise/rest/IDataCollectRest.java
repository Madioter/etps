package com.madiot.enterprise.rest;

import com.madiot.enterprise.common.exception.RestException;
import com.madiot.enterprise.model.Admtree;
import com.madiot.enterprise.model.collect.CollectRequest;
import com.madiot.enterprise.model.collect.CollectResponse;

import java.util.List;
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

    /**
     * 通过父id，http请求获取子集数据
     * @param parentId 父id
     * @return 子集id
     */
    List<Admtree> getAdmTrees(Integer parentId) throws RestException;

}
