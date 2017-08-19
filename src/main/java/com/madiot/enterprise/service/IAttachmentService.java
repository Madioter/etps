package com.madiot.enterprise.service;

import com.madiot.enterprise.model.Attachment;

import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

/**
 * Created by DELL on 2016/7/6.
 */
public interface IAttachmentService {

    int insert(Attachment attachment);

    int saveAttachment(byte[] bytes, int attachmentId);

    Attachment getAttachment(int id);

    List<Attachment> selectByParam(int rows, int page);

    int getAllCount();

    int deleteAttachment(int id);

    int saveError(Throwable e, int id);
}
