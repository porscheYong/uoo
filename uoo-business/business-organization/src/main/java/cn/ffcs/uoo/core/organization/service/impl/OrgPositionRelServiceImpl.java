package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgPositionRel;
import cn.ffcs.uoo.core.organization.dao.OrgPositionRelMapper;
import cn.ffcs.uoo.core.organization.service.OrgPositionRelService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 岗位，不同组织树具有不同的岗位 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-10
 */
@Service
public class OrgPositionRelServiceImpl extends ServiceImpl<OrgPositionRelMapper, OrgPositionRel> implements OrgPositionRelService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }
    @Override
    public void delete(OrgPositionRel orgPositionRel){
        orgPositionRel.setStatusCd("1100");
        orgPositionRel.setStatusDate(new Date());
        orgPositionRel.setUpdateDate(new Date());
        orgPositionRel.setUpdateUser(StrUtil.isNullOrEmpty(orgPositionRel.getUpdateUser())?0L:orgPositionRel.getUpdateUser());
        updateById(orgPositionRel);
    }

    @Override
    public void add(OrgPositionRel orgPositionRel){
        orgPositionRel.setCreateDate(new Date());
        orgPositionRel.setCreateUser(StrUtil.isNullOrEmpty(orgPositionRel.getCreateUser())?0L:orgPositionRel.getCreateUser());
        orgPositionRel.setStatusCd("1000");
        orgPositionRel.setStatusDate(new Date());
        insert(orgPositionRel);
    }


    @Override
    public void update(OrgPositionRel orgPositionRel){
        orgPositionRel.setUpdateDate(new Date());
        orgPositionRel.setUpdateUser(StrUtil.isNullOrEmpty(orgPositionRel.getUpdateUser())?0L:orgPositionRel.getUpdateUser());
        orgPositionRel.setStatusDate(new Date());
        updateById(orgPositionRel);
    }
}
