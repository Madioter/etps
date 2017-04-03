package com.madiot.enterprise.thread;

import com.madiot.enterprise.service.IDataCollectService;
import org.apache.http.concurrent.BasicFuture;

import java.util.concurrent.*;

/**
 * Created by DELL on 2016/6/26.
 */
public class CollectDataThread implements Runnable {

    private String enterpriseName;

    private int localadm;

    private int startRows;

    private int endRows;

    private IDataCollectService dataCollectService;

    private CompletableFuture<Boolean> result;

    public CollectDataThread(String enterpriseName, int localadm, int startRows, int endRows, IDataCollectService dataCollectService) {
        this.enterpriseName = enterpriseName;
        this.localadm = localadm;
        this.startRows = startRows;
        this.endRows = endRows;
        this.dataCollectService = dataCollectService;
        this.result = new CompletableFuture<>();
    }

    @Override
    public void run() {
        try {
            dataCollectService.collectData(enterpriseName, localadm, startRows, endRows, result);
        } catch (Exception e) {
            result.complete(false);
        }
    }

    public String getResult() throws ExecutionException, InterruptedException {
        try {
            if (result.get(5, TimeUnit.SECONDS)) {
                return "已经开始执行，稍后请刷新文件列表并下载EXCEL";
            } else {
                return "登陆失败，需要重新登陆";
            }
        } catch (TimeoutException e){
            return "已经开始执行，稍后请刷新文件列表并下载EXCEL";
        }
    }
}
