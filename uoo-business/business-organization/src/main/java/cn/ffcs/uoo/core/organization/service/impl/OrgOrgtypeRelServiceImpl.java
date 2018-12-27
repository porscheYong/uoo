package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.dao.OrgOrgtypeRelMapper;
import cn.ffcs.uoo.core.organization.entity.OrgOrgtypeRel;
import cn.ffcs.uoo.core.organization.service.OrgOrgtypeRelService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@Service
public class OrgOrgtypeRelServiceImpl extends ServiceImpl<OrgOrgtypeRelMapper, OrgOrgtypeRel> implements OrgOrgtypeRelService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public void delete(OrgOrgtypeRel orgOrgtypeRel){
        orgOrgtypeRel.setStatusCd("1100");
        orgOrgtypeRel.setStatusDate(new Date());
        orgOrgtypeRel.setUpdateDate(new Date());
        orgOrgtypeRel.setUpdateUser(StrUtil.isNullOrEmpty(orgOrgtypeRel.getUpdateUser())?0L:orgOrgtypeRel.getUpdateUser());
        updateById(orgOrgtypeRel);
    }
    /**
     * 新增
     * @param orgOrgtypeRel
     */
    @Override
    public void add(OrgOrgtypeRel orgOrgtypeRel){
        orgOrgtypeRel.setCreateDate(new Date());
        orgOrgtypeRel.setCreateUser(StrUtil.isNullOrEmpty(orgOrgtypeRel.getCreateUser())?0L:orgOrgtypeRel.getCreateUser());
        orgOrgtypeRel.setStatusCd("1000");
        orgOrgtypeRel.setStatusDate(new Date());
        insert(orgOrgtypeRel);
    }

    /**
     *
     * @param orgOrgtypeRel
     */
    @Override
    public void update(OrgOrgtypeRel orgOrgtypeRel){
        orgOrgtypeRel.setUpdateDate(new Date());
        orgOrgtypeRel.setUpdateUser(StrUtil.isNullOrEmpty(orgOrgtypeRel.getUpdateUser())?0L:orgOrgtypeRel.getUpdateUser());
        orgOrgtypeRel.setStatusDate(new Date());
        updateById(orgOrgtypeRel);
    }
}
