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
    public void insert(Attachment attachment) {
        attachmentDao.insert(attachment);
    }

    public void update(InputStream stream, int id) {
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(stream);
            update(bytes, id);
        } catch (IOException e) {
            saveError(e, id);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveError(Throwable e, int id) {
        Attachment attachment = new Attachment();
        attachment.setId(id);
        String error = e.getCause().toString();
        if (error.length() > 2000) {
            error = error.substring(0, 2000);
        }
        attachment.setError(error);
        attachment.setState(AttachmentState.ERROR_STATE);
        attachmentDao.saveError(attachment);
    }


    public void update(byte[] bytes, int id) {
        attachmentDao.update(bytes, id);
    }


    public void saveAttachment(byte[] bytes, int attachmentId) {
        update(bytes, attachmentId);
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

    public void deleteAttachment(int id) {
        attachmentDao.delete(id);
    }
}
