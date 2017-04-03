package com.madiot.enterprise.service;

import com.madiot.enterprise.common.exception.RestException;
import com.madiot.enterprise.model.Admtree;
import com.madiot.enterprise.model.AdmtreeVo;

import java.util.List;

/**
 * Created by julian on 2017/4/3.
 */
public interface IAdmtreeService {

    public List<AdmtreeVo> getList(Integer parentId) throws RestException;
}
