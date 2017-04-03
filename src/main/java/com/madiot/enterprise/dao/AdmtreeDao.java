package com.madiot.enterprise.dao;

import com.madiot.enterprise.model.Admtree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by julian on 2017/4/3.
 */
public interface AdmtreeDao {

    public List<Admtree> selectByParentId(int parentId);

    public Admtree selectById(int id);

    public int insertAdmByBatch(@Param("list") List<Admtree> adms);

    public int init(int id);
}
