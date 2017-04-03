package com.madiot.enterprise.service;

import com.madiot.enterprise.dao.AttachmentDao;
import com.madiot.enterprise.model.Attachment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
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

    public Attachment insert(String fileName) {
        Attachment attachment = new Attachment();
        attachment.setFileName(fileName);
        attachmentDao.insert(attachment);
        return attachment;
    }

    public void update(InputStream stream, int id) {
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(stream);
            update(bytes, id);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(byte[] bytes, int id) {
        attachmentDao.update(bytes, id);
    }

    public void saveAttachment(InputStream stream, String fileName) {
        Attachment attachment = this.insert(fileName);
        update(stream, attachment.getId());
    }

    public void saveAttachment(byte[] bytes, String fileName) {
        Attachment attachment = this.insert(fileName);
        update(bytes, attachment.getId());
    }

    public void delete(int id) {
        attachmentDao.delete(id);
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
