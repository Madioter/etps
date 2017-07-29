package com.madiot.enterprise.service;

import com.madiot.enterprise.common.exception.RestException;
import com.madiot.enterprise.model.EnttreeVo;

import java.util.List;

/**
 * Created by julian on 2017/7/29.
 */
public interface IEnttreeService {
    List<EnttreeVo> getList(Integer parentId) throws RestException;
}
