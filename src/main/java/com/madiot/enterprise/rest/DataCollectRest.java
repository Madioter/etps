package com.madiot.enterprise.rest;

import com.madiot.enterprise.common.exception.RestException;
import com.madiot.enterprise.common.http.ConnectInfo;
import com.madiot.enterprise.common.util.HttpUtil;
import com.madiot.enterprise.model.collect.CollectRequest;
import com.madiot.enterprise.model.collect.CollectResponse;
import com.madiot.enterprise.model.collect.EnterpriseCollect;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * Created by DELL on 2016/6/26.
 */
@Service
public class DataCollectRest implements IDataCollectRest {

    @Autowired
    private ConnectInfo connectInfo;

    /**
     * 获取数据
     *
     * @return CollectResponse
     */
    public CollectResponse getCollectData(CollectRequest request, CompletableFuture<Boolean> result) throws RestException {
        String responseStr = HttpUtil.getJson(connectInfo.getCollectUrl(), request.toString());
        if (responseStr.startsWith("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML")) {
            result.complete(false);
        }
        JSONObject jsonObject = JSONObject.fromObject(responseStr);
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("rows", EnterpriseCollect.class);
        return (CollectResponse) JSONObject.toBean(jsonObject, CollectResponse.class, classMap);
    }

    private String getPostResponse(String url, String params) throws RestException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 60秒超时
        httpClientBuilder.setConnectionTimeToLive(60, TimeUnit.SECONDS);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(params, HTTP.UTF_8));
        CloseableHttpClient httpClient = httpClientBuilder.build();
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity);
            return body;
        } catch (Exception e) {
            throw new RestException(e.getMessage(), e.getCause());
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
            }
        }
    }

}
