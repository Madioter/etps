package com.madiot.enterprise.service;

import com.madiot.enterprise.common.exception.ErrorMessage;
import com.madiot.enterprise.dao.EnterpriseDao;
import com.madiot.enterprise.model.EnterpriseVo;
import com.madiot.enterprise.model.Industry;
import com.madiot.enterprise.model.RegAuth;
import com.madiot.enterprise.model.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
@Service
@Transactional
public class EnterpriseService implements IEnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private ICodeService codeService;

    public int importEnterprise(List<EnterpriseVo> enterpriseList, ErrorMessage errorMessage, User loginUser) {
        Date addTime = new Date();
        int successCount = 0;
        for (EnterpriseVo model : enterpriseList) {
            try {
                List<EnterpriseVo> enterpriseVoList = enterpriseDao.getEnterpriseByNameAndRegDate(model.getName(), null, null, 0, 1);
                if (enterpriseVoList.size() > 0) {
                    EnterpriseVo oldModel = enterpriseVoList.get(0);
                    if (oldModel.sameWith(model)) {
                        continue;
                    } else {
                        model.setId(oldModel.getId());
                    }
                }

                model.setUpdateUserId(loginUser.getId());
                model.setUpdateTime(new Date());

                Industry industry = codeService.getIndustryByName(model.getIndustry());
                if (industry != null) {
                    model.setIndustryCode(industry.getCode());
                } else {
                    industry = new Industry();
                    industry.setName(model.getIndustry());
                    int industryCode = codeService.saveIndustry(industry);
                    model.setIndustryCode(industryCode);
                }

                RegAuth regAuth = codeService.getRegAuthName(model.getRegAuth());
                if (regAuth != null) {
                    model.setRegAuthCode(regAuth.getCode());
                } else {
                    regAuth = new RegAuth();
                    regAuth.setName(model.getRegAuth());
                    int regAuthCode = codeService.saveRegAuth(regAuth);
                    model.setRegAuthCode(regAuthCode);
                }

                if (model.getId() == null) {
                    model.setAddTime(addTime);
                    enterpriseDao.save(model);
                } else {
                    enterpriseDao.update(model);
                }
                successCount++;
            } catch (Exception e) {
                errorMessage.addError(model.getName() + "数据保存失败：" + e.getMessage());
            }

        }
        return successCount;
    }

    @Override
    public List<EnterpriseVo> queryEnterprisePageByCondition(String name, Date beginDate, Date endDate, int rows, int page) {
        return enterpriseDao.getEnterpriseByNameAndRegDate(name, beginDate, endDate, (page - 1) * rows, rows);
    }

    @Override
    public int countEnterpriseByCondition(String name, Date beginDate, Date endDate) {
        return enterpriseDao.countEnterpriseByNameAndRegDate(name, beginDate, endDate);
    }

    @Override
    public HSSFWorkbook exportEnterprise(String name, Date beginDate, Date endDate) {

        //查询结果集
        //List<EnterpriseVo> enterpriseVoList = queryEnterprisePageByCondition(name, beginDate, endDate, -1, -1);

        //只导出最后一次导入的数据
        Date lastAddTime = enterpriseDao.getLastAddTime();
        List<EnterpriseVo> enterpriseVoList = enterpriseDao.queryEnterpriseByLastAdd(lastAddTime);

        return createHSSFWorkbook(enterpriseVoList);
    }

    public HSSFWorkbook  createHSSFWorkbook(List<EnterpriseVo> enterpriseVoList){
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet("sheet1");
        //产生表格标题行
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        HSSFRichTextString text = new HSSFRichTextString("序号");
        cell.setCellValue(text);
        for (int i = 0; i < EnterpriseVo.EXCEL_TITLE.length; i++) {
            cell = row.createCell(i + 1);
            text = new HSSFRichTextString(EnterpriseVo.EXCEL_TITLE[i]);
            cell.setCellValue(text);
        }

        //遍历集合数据，产生数据行
        Iterator<EnterpriseVo> it = enterpriseVoList.iterator();
        int index = 0;

        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            EnterpriseVo t = it.next();
            cell = row.createCell(0);
            cell.setCellValue(index);

            cell = row.createCell(1);
            cell.setCellValue(t.getName());

            cell = row.createCell(2);
            cell.setCellValue(t.getIndustry());

            cell = row.createCell(3);
            cell.setCellValue(t.getLegalPerson());

            cell = row.createCell(4);
            cell.setCellValue(t.getRegAddress());

            cell = row.createCell(5);
            cell.setCellValue(t.getBusinessScope());

            cell = row.createCell(6);
            cell.setCellValue(t.getRegAuth());

            cell = row.createCell(7);
            cell.setCellValue(t.getRegDate());

            cell = row.createCell(8);
            cell.setCellValue(t.getPhone());
        }
        return workbook;
    }
}
