package com.madiot.enterprise.service;

import com.madiot.enterprise.common.exception.RestException;
import com.madiot.enterprise.dao.AdmtreeDao;
import com.madiot.enterprise.dao.EnttreeDao;
import com.madiot.enterprise.model.Admtree;
import com.madiot.enterprise.model.AdmtreeVo;
import com.madiot.enterprise.model.Enttree;
import com.madiot.enterprise.model.EnttreeVo;
import com.madiot.enterprise.rest.IDataCollectRest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 2017/7/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EnttreeService implements IEnttreeService {

    @Resource
    private EnttreeDao enttreeDao;

    @Resource
    private IDataCollectRest dataCollectRest;

    /**
     * 获取所属辖区数据
     *
     * @param parentId 所属辖区父级id
     * @return 所属辖区列表
     */
    public List<EnttreeVo> getList(Integer parentId) throws RestException {
        if (parentId == null) {
            List<Enttree> enttrees = enttreeDao.selectByParentId(0);
            if (CollectionUtils.isNotEmpty(enttrees)){
                return convert(enttrees);
            } else {
                return convert(init(null));
            }
        }
        Enttree enttree = enttreeDao.selectById(parentId);
        if (enttree == null || enttree.getIsInit() == 0) {
            return convert(init(parentId));
        } else if (enttree.getState() == 0) {
            return new ArrayList<>();
        }
        return convert(enttreeDao.selectByParentId(parentId));
    }

    private List<EnttreeVo> convert(List<Enttree> list) {
        List<EnttreeVo> enttreeVos = new ArrayList<>();
        for (Enttree enttree : list) {
            EnttreeVo enttreeVo = new EnttreeVo();
            enttreeVo.setId(enttree.getId());
            enttreeVo.setParent_id(enttree.getParentId());
            if (enttree.getState() == 1) {
                enttreeVo.setState("closed");
            } else {
                enttreeVo.setState("open");
            }
            enttreeVo.setText(enttree.getName());
            enttreeVos.add(enttreeVo);
        }
        return enttreeVos;
    }

    /**
     * 获取所属辖区
     *
     * @param parentId
     * @return List<Admtree> 下级辖区列表
     */
    public List<Enttree> init(Integer parentId) throws RestException {
        List<Enttree> enttrees = dataCollectRest.getEntTrees(parentId);
        enttreeDao.insertEntByBatch(enttrees);
        if (parentId != null) {
            enttreeDao.init(parentId);
        }
        return enttrees;
    }
}
