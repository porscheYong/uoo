package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgtreeOrgpersonRel;
import cn.ffcs.uoo.core.organization.dao.OrgtreeOrgpersonRelMapper;
import cn.ffcs.uoo.core.organization.service.OrgtreeOrgpersonRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 描述该员工挂着到哪些专业树上 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-28
 */
@Service
public class OrgtreeOrgpersonRelServiceImpl extends ServiceImpl<OrgtreeOrgpersonRelMapper, OrgtreeOrgpersonRel> implements OrgtreeOrgpersonRelService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public void delete(OrgtreeOrgpersonRel orgtreeOrgpersonRel){
        orgtreeOrgpersonRel.setStatusCd("1100");
        orgtreeOrgpersonRel.setStatusDate(new Date());
        orgtreeOrgpersonRel.setUpdateDate(new Date());
        orgtreeOrgpersonRel.setUpdateUser(0L);
        updateById(orgtreeOrgpersonRel);
    }

    @Override
    public void add(OrgtreeOrgpersonRel orgtreeOrgpersonRel){
        orgtreeOrgpersonRel.setCreateDate(new Date());
        orgtreeOrgpersonRel.setCreateUser(0L);
        orgtreeOrgpersonRel.setStatusCd("1000");
        orgtreeOrgpersonRel.setStatusDate(new Date());
        insert(orgtreeOrgpersonRel);
    }


    @Override
    public void update(OrgtreeOrgpersonRel orgtreeOrgpersonRel){
        orgtreeOrgpersonRel.setUpdateDate(new Date());
        orgtreeOrgpersonRel.setUpdateUser(0L);
        orgtreeOrgpersonRel.setStatusDate(new Date());
        updateById(orgtreeOrgpersonRel);
    }
}
