package com.madiot.enterprise.controller;

import com.madiot.enterprise.common.AuthUtil;
import com.madiot.enterprise.common.CommonConstant;
import com.madiot.enterprise.common.FileUpDownUtils;
import com.madiot.enterprise.common.exception.RestException;
import com.madiot.enterprise.model.*;
import com.madiot.enterprise.service.IAdmtreeService;
import com.madiot.enterprise.service.IAttachmentService;
import com.madiot.enterprise.service.IDataCollectService;
import com.madiot.enterprise.service.IEnttreeService;
import com.madiot.enterprise.thread.CollectDataThread;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by DELL on 2016/6/26.
 */
@Controller
@RequestMapping("/data-collect")
public class DataCollectController {

    @Autowired
    private IDataCollectService dataCollectService;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private IAdmtreeService admtreeService;

    @Autowired
    private IEnttreeService enttreeService;

    @RequestMapping("/attachmentList")
    public String attachmentList() {
        return "attachment/attachmentList";
    }

    @RequestMapping("/collectData")
    @ResponseBody
        public String collectData(String enterpriseName, Integer localadm, Integer localent, int startRows, int endRows) {
        CollectDataThread collectDataThread = new CollectDataThread(enterpriseName, localadm, localent, startRows, endRows, dataCollectService);
        taskExecutor.execute(collectDataThread);
        try {
            return collectDataThread.getResult();
        } catch (ExecutionException |InterruptedException e) {
            return "执行异常，请重新执行";
        }
    }

    @RequestMapping("/getAttachmentJson")
    @ResponseBody
    public ResponseList<Attachment> getAttachmentJson(HttpSession session, int rows, int page) {
        ResponseList<Attachment> responseList = new ResponseList<Attachment>();
        User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
        if (!AuthUtil.isAdmin(user)) {
            responseList.setRows(new ArrayList<Attachment>());
            responseList.setTotal(0);
            return responseList;
        }
        List<Attachment> attachmentList = attachmentService.selectByParam(rows, page);
        int total = attachmentService.getAllCount();
        responseList.setRows(attachmentList);
        responseList.setTotal(total);
        return responseList;
    }

    @RequestMapping("/deleteAttachment")
    @ResponseBody
    public ResponseVo deleteAttachment(int id) {
        ResponseVo responseVo = new ResponseVo();
        attachmentService.deleteAttachment(id);
        responseVo.setSuccess(true);
        responseVo.setMessage("删除成功");
        return responseVo;
    }

    @RequestMapping("/getFile")
    public void getFile(HttpServletResponse response, int id) {
        Attachment attachment = attachmentService.getAttachment(id);
        byte[] content = (byte[])attachment.getFileContent();
        try {
            FileUpDownUtils.exportExcel(response, attachment.getFileName(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/admtree")
    @ResponseBody
    public List<AdmtreeVo> admtree(Integer id) throws RestException {
        return admtreeService.getList(id);
    }

    @RequestMapping("/enttree")
    @ResponseBody
    public List<EnttreeVo> enttree(Integer id) throws RestException {
        return enttreeService.getList(id);
    }
}
