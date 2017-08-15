package com.madiot.enterprise.service;

import com.madiot.enterprise.model.Attachment;

import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

/**
 * Created by DELL on 2016/7/6.
 */
public interface IAttachmentService {

    void insert(Attachment attachment);

    void saveAttachment(byte[] bytes, int attachmentId);

    Attachment getAttachment(int id);

    List<Attachment> selectByParam(int rows, int page);

    int getAllCount();

    void deleteAttachment(int id);

    void saveError(Throwable e, int id);
}
