package com.madiot.enterprise.common;

import java.io.*;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.madiot.enterprise.common.util.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 文件上传下载工具类
 *
 * @author
 */
public class FileUpDownUtils {

    private static Logger log = Logger.getLogger(FileUpDownUtils.class);

    /**
     * Excel文件导出
     *
     * @param response HttpServletResponse
     */
    public static void download(HttpServletResponse response, InputStream inputStream)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            String fileName = DateUtils.simpleDateFormat(new Date(), "yyyy-MM-hh HH:mm:ss");

            // 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
            String rtn = "filename=\"" + fileName + "\"";
            response.setHeader("Content-disposition", "attachment;" + rtn);
            response.setContentType("application/x-msdownload;");
            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[1024];
            int bytesRead;
            int fileLength = 0;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                fileLength += buff.length;
                bos.write(buff, 0, bytesRead);
            }
            response.setHeader("Content-Length", String.valueOf(fileLength));

        } catch (Exception e) {
            log.error("Excel导出错误", e);
            throw e;
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public static void exportExcel(HttpServletResponse response, HSSFWorkbook workbook) throws IOException {
        String fileName = DateUtils.simpleDateFormat(new Date(), "yyyyMMhhHHmmss");
        fileName = fileName + ".xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public static void exportExcel(HttpServletResponse response, String fileName, byte[] bytes) throws IOException {
        fileName = fileName + ".xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
