package com.madiot.enterprise.service;

import com.madiot.enterprise.dao.AttachmentDao;
import com.madiot.enterprise.model.Attachment;
import com.madiot.enterprise.model.AttachmentState;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

/**
 * Created by DELL on 2016/7/6.
 */
@Service
@Transactional
public class AttachmentService implements IAttachmentService {

    @Resource
    private AttachmentDao attachmentDao;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int insert(Attachment attachment) {
        return attachmentDao.insert(attachment);
    }

    public int update(InputStream stream, int id) {
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(stream);
            return update(bytes, id);
        } catch (IOException e) {
            saveError(e, id);
            return 0;
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int saveError(Throwable e, int id) {
        Attachment attachment = new Attachment();
        attachment.setId(id);
        String error = e.getMessage();
        if (e.getCause() != null) {
            error += ", cause:" + e.getCause().toString();
        }
        if (error.length() > 2000) {
            error = error.substring(0, 2000);
        }
        attachment.setError(error);
        attachment.setState(AttachmentState.ERROR_STATE);
        return attachmentDao.saveError(attachment);
    }


    public int update(byte[] bytes, int id) {
        return attachmentDao.update(bytes, id);
    }


    public int saveAttachment(byte[] bytes, int attachmentId) {
        return update(bytes, attachmentId);
    }

    public Attachment getAttachment(int id) {
        return attachmentDao.getAttachment(id);
    }

    public List<Attachment> selectByParam(int rows, int page) {
        return attachmentDao.selectByParam((page - 1) * rows, rows);
    }

    public int getAllCount() {
        return attachmentDao.getAllCount();
    }

    public int deleteAttachment(int id) {
        return attachmentDao.delete(id);
    }
}
