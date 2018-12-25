package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgPostRel;
import cn.ffcs.uoo.core.organization.dao.OrgPostRelMapper;
import cn.ffcs.uoo.core.organization.service.OrgPostRelService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 职位，不同组织树具有不同的职位 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-25
 */
@Service
public class OrgPostRelServiceImpl extends ServiceImpl<OrgPostRelMapper, OrgPostRel> implements OrgPostRelService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }
    @Override
    public void delete(OrgPostRel orgPostRel){
        orgPostRel.setStatusCd("1100");
        orgPostRel.setStatusDate(new Date());
        orgPostRel.setUpdateDate(new Date());
        orgPostRel.setUpdateUser(StrUtil.isNullOrEmpty(orgPostRel.getUpdateUser())?0L:orgPostRel.getUpdateUser());
        updateById(orgPostRel);
    }

    @Override
    public void add(OrgPostRel orgPostRel){
        orgPostRel.setCreateDate(new Date());
        orgPostRel.setCreateUser(StrUtil.isNullOrEmpty(orgPostRel.getCreateUser())?0L:orgPostRel.getCreateUser());
        orgPostRel.setStatusCd("1000");
        orgPostRel.setStatusDate(new Date());
        insert(orgPostRel);
    }


    @Override
    public void update(OrgPostRel orgPostRel){
        orgPostRel.setUpdateDate(new Date());
        orgPostRel.setUpdateUser(StrUtil.isNullOrEmpty(orgPostRel.getUpdateUser())?0L:orgPostRel.getUpdateUser());
        orgPostRel.setStatusDate(new Date());
        updateById(orgPostRel);
    }
}
