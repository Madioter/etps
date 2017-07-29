package com.madiot.enterprise.dao;

import com.madiot.enterprise.model.Admtree;
import com.madiot.enterprise.model.Enttree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by julian on 2017/4/3.
 */
public interface EnttreeDao {

    public List<Enttree> selectByParentId(int parentId);

    public Enttree selectById(int id);

    public int insertEntByBatch(@Param("list") List<Enttree> adms);

    public int init(int id);
}
