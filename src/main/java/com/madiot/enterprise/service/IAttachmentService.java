package com.madiot.enterprise.service;

import com.madiot.enterprise.model.Attachment;

import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

/**
 * Created by DELL on 2016/7/6.
 */
public interface IAttachmentService {

    public void saveAttachment(InputStream stream, String fileName);

    public void saveAttachment(byte[] bytes, String fileName);

    public Attachment getAttachment(int id);

    public List<Attachment> selectByParam(int rows, int page);

    public int getAllCount();

    public void deleteAttachment(int id);
}
