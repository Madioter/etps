package com.madiot.enterprise.rest;

import com.madiot.enterprise.common.AuthUtil;
import com.madiot.enterprise.common.exception.RestException;
import com.madiot.enterprise.common.http.ConnectInfo;
import com.madiot.enterprise.common.util.HttpUtil;
import com.madiot.enterprise.model.Admtree;
import com.madiot.enterprise.model.collect.CollectRequest;
import com.madiot.enterprise.model.collect.CollectResponse;
import com.madiot.enterprise.model.collect.EnterpriseCollect;
import net.sf.json.JSONArray;
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

import java.util.*;
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
        if (AuthUtil.checkLogin(responseStr)) {
            result.complete(true);
        } else {
            result.complete(false);
            throw new RestException("请重新登陆");
        }
        JSONObject jsonObject = JSONObject.fromObject(responseStr);
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("rows", EnterpriseCollect.class);
        return (CollectResponse) JSONObject.toBean(jsonObject, CollectResponse.class, classMap);
    }

    @Override
    public List<Admtree> getAdmTrees(Integer parentId) throws RestException {
        String responseStr = HttpUtil.getJson(connectInfo.getAdmtreeUrl(), "id=" + parentId);
        if (!AuthUtil.checkLogin(responseStr)) {
            throw new RestException("请先登陆监管平台");
        }
        JSONArray jsonArray = JSONArray.fromObject(responseStr);
        Iterator<JSONObject> iterator = jsonArray.iterator();
        List<Admtree> admtrees = new ArrayList<>();
        while (iterator.hasNext()) {
            JSONObject jsonObject = iterator.next();
            Admtree admtree = new Admtree();
            admtree.setId(Integer.valueOf((String) jsonObject.get("id")));
            admtree.setName((String) jsonObject.get("text"));
            admtree.setParentId(Integer.valueOf((String) jsonObject.get("parent_id")));
            if (jsonObject.get("state").equals("open")) {
                admtree.setState(0);
                admtree.setIsInit(1);
            } else {
                admtree.setState(1);
                admtree.setIsInit(0);
            }
            admtrees.add(admtree);
        }
        return admtrees;
    }

}
