package com.madiot.enterprise.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.madiot.enterprise.common.CommonConstant;
import com.madiot.enterprise.common.exception.RestException;
import com.madiot.enterprise.common.util.StringUtil;
import com.madiot.enterprise.model.Attachment;
import com.madiot.enterprise.model.EnterpriseVo;
import com.madiot.enterprise.model.collect.CollectRequest;
import com.madiot.enterprise.model.collect.CollectResponse;
import com.madiot.enterprise.model.collect.EnterpriseCollect;
import com.madiot.enterprise.rest.IDataCollectRest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by DELL on 2016/6/26.
 */
@Service
@Transactional
public class DataCollectService implements IDataCollectService {

    @Autowired
    private IDataCollectRest dataCollectRest;

    @Autowired
    private IEnterpriseService enterpriseService;

    @Autowired
    private IAttachmentService attachmentService;

    @Override
    public void collectData(String enterpriseName, Integer localadm, Integer localent, int startRows, int endRows, CompletableFuture<Boolean> result) throws RestException {
        Attachment attachment = null;
        try {
            CollectRequest collectRequest = new CollectRequest();
            collectRequest.setEntname(enterpriseName);
            collectRequest.setLocaladm(localadm);
            collectRequest.setEnttype(localent);
            if (endRows == 0) {
                endRows = startRows + 500;
            }

            int startPage;
            if (startRows % collectRequest.getRows() == 0) {
                startPage = startRows / collectRequest.getRows();
            } else {
                startPage = startRows / collectRequest.getRows() + 1;
            }

            int pageEnd;
            if (endRows % collectRequest.getRows() == 0) {
                pageEnd = endRows / collectRequest.getRows();
            } else {
                pageEnd = endRows / collectRequest.getRows() + 1;
            }

            attachment = new Attachment();
            attachment.setFileName(getFileName(enterpriseName, startRows, endRows));
            attachment.setParam(JSONUtils.toJSONString(collectRequest));
            attachmentService.insert(attachment);

            int total = 0;
            List<EnterpriseCollect> enterpriseCollectList = new ArrayList<EnterpriseCollect>();

            while (startPage <= pageEnd && (startPage * collectRequest.getRows() < total || total == 0)) {
                collectRequest.setPage(startPage);
                CollectResponse response = dataCollectRest.getCollectData(collectRequest, result);
                total = response.getTotal();
                if (CollectionUtils.isEmpty(response.getRows())) {
                    break;
                }
                for (EnterpriseCollect enterprise : response.getRows()) {
                    enterprise.setIndex((startPage - 1) * collectRequest.getRows() + 1);
                    if (enterprise.getIndex() >= startRows && enterprise.getIndex() <= endRows) {
                        enterpriseCollectList.add(enterprise);
                    }
                }
                startPage++;
            }

            List<EnterpriseVo> enterpriseVoList = enterpriseConvent(enterpriseCollectList);
            HSSFWorkbook hssfWorkbook = enterpriseService.createHSSFWorkbook(enterpriseVoList);

            writeFileAndSaveRecord(hssfWorkbook, attachment.getId());
        } catch (Exception e) {
            if (attachment != null && attachment.getId() != null) {
                attachmentService.saveError(e, attachment.getId());
            }
            throw e;
        }
    }

    // 把excel文件写到服务器，并且存档日志
    private void writeFileAndSaveRecord(HSSFWorkbook hssfWorkbook, int attachementId) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] bytes = new byte[0];
        try {
            hssfWorkbook.write(os);
            bytes = os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        attachmentService.saveAttachment(bytes, attachementId);
    }

    private String getFileName(String enterpriseName, int startRows, int endRows) {
        StringBuilder fileName = new StringBuilder();
        if (!StringUtil.isEmpty(enterpriseName)) {
            fileName.append(enterpriseName);
        }
        fileName.append(CommonConstant.CONNECT_TAG).append(startRows).append(CommonConstant.CONNECT_TAG).append(endRows);
        fileName.append(CommonConstant.CONNECT_TAG).append(System.currentTimeMillis());
        return fileName.toString();
    }

    private List<EnterpriseVo> enterpriseConvent(List<EnterpriseCollect> enterpriseCollectList) {
        List<EnterpriseVo> enterpriseVoList = new ArrayList<EnterpriseVo>();
        for (EnterpriseCollect enterprise : enterpriseCollectList) {
            EnterpriseVo enterpriseVo = new EnterpriseVo();
            enterpriseVo.setBusinessScope(enterprise.getBusscope());
            enterpriseVo.setName(enterprise.getEntname());
            enterpriseVo.setPhone(enterprise.getTel());
            enterpriseVo.setRegAddress(enterprise.getDom());
            enterpriseVo.setIndustry(enterprise.getIndustryco_name());
            enterpriseVo.setRegDate(enterprise.getOpfrom());
            enterpriseVo.setLegalPerson(enterprise.getLerep());
            enterpriseVo.setRegAuth(enterprise.getLocaladm_name());
            enterpriseVoList.add(enterpriseVo);
        }
        return enterpriseVoList;
    }
}
