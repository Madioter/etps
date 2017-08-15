package com.madiot.enterprise.dao;

import com.madiot.enterprise.model.Attachment;
import org.apache.ibatis.annotations.Param;

import java.sql.Blob;
import java.util.List;

/**
 * Created by DELL on 2016/7/5.
 */
public interface AttachmentDao {

    public List<Attachment> selectByParam(@Param("startNum") int startNum,
                                          @Param("pageSize") int pageSize);

    public Attachment getAttachment(int id);

    public void update(@Param("fileContent")Object fileContent,@Param("id")int id);

    public void insert(Attachment attachment);

    public void delete(int id);

    public int getAllCount();

    public void saveError(Attachment attachment);
}
