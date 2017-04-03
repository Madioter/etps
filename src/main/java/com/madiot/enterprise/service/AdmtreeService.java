package com.madiot.enterprise.service;

import com.madiot.enterprise.common.exception.RestException;
import com.madiot.enterprise.dao.AdmtreeDao;
import com.madiot.enterprise.model.Admtree;
import com.madiot.enterprise.model.AdmtreeVo;
import com.madiot.enterprise.rest.IDataCollectRest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 2017/4/3.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AdmtreeService implements IAdmtreeService {

    @Resource
    private AdmtreeDao admtreeDao;

    @Resource
    private IDataCollectRest dataCollectRest;

    /**
     * 获取所属辖区数据
     * @param parentId 所属辖区父级id
     * @return 所属辖区列表
     */
    public List<AdmtreeVo> getList(Integer parentId) throws RestException {
        if (parentId == null){
            List<AdmtreeVo> admtreeVos = new ArrayList<>();
            AdmtreeVo admtreeVo = new AdmtreeVo();
            admtreeVo.setText("山东省工商行政管理局");
            admtreeVo.setId(370000);
            admtreeVo.setState("closed");
            admtreeVos.add(admtreeVo);
            return admtreeVos;
        }
        Admtree admtree = admtreeDao.selectById(parentId);
        if (admtree == null || admtree.getIsInit() == 0) {
            return convert(init(parentId));
        } else if (admtree.getState() == 0){
            return new ArrayList<>();
        }
        return convert(admtreeDao.selectByParentId(parentId));
    }

    private List<AdmtreeVo> convert(List<Admtree> list) {
        List<AdmtreeVo> admtreeVos = new ArrayList<>();
        for (Admtree admtree:list){
            AdmtreeVo admtreeVo = new AdmtreeVo();
            admtreeVo.setId(admtree.getId());
            admtreeVo.setParent_id(admtree.getParentId());
            if (admtree.getState() == 1) {
                admtreeVo.setState("closed");
            } else {
                admtreeVo.setState("open");
            }
            admtreeVo.setText(admtree.getName());
            admtreeVos.add(admtreeVo);
        }
        return admtreeVos;
    }

    /**
     * 获取所属辖区
     * @param parentId
     * @return List<Admtree> 下级辖区列表
     */
    public List<Admtree> init(int parentId) throws RestException {
        List<Admtree> admtrees = dataCollectRest.getAdmTrees(parentId);
        admtreeDao.insertAdmByBatch(admtrees);
        admtreeDao.init(parentId);
        return admtrees;
    }
}
