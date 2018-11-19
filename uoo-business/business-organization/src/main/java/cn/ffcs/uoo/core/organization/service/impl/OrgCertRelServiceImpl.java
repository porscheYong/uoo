package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgCertRel;
import cn.ffcs.uoo.core.organization.dao.OrgCertRelMapper;
import cn.ffcs.uoo.core.organization.service.OrgCertRelService;
import cn.ffcs.uoo.core.organization.vo.OrgCertVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 解决智慧BSS组织机构接入过程中的法人信息问题 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@Service
public class OrgCertRelServiceImpl extends ServiceImpl<OrgCertRelMapper, OrgCertRel> implements OrgCertRelService {
    /**
     * 获取seq
     * @return
     */
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    /**
     * 失效状态
     * @param orgCertRel
     */
    @Override
    public void delete(OrgCertRel orgCertRel){
        orgCertRel.setStatusCd("1100");
        orgCertRel.setStatusDate(new Date());
        orgCertRel.setUpdateDate(new Date());
        orgCertRel.setUpdateUser(0L);
        updateById(orgCertRel);
    }



    /**
     * 新增
     */
    @Override
    public void add(OrgCertRel orgCertRel){
        orgCertRel.setCreateDate(new Date());
        orgCertRel.setCreateUser(0L);
        orgCertRel.setStatusCd("1000");
        orgCertRel.setStatusDate(new Date());
        insert(orgCertRel);
    }

    /**
     * 更新
     */
    @Override
    public void update(OrgCertRel orgCertRel){
        orgCertRel.setUpdateDate(new Date());
        orgCertRel.setUpdateUser(0L);
        orgCertRel.setStatusDate(new Date());
        updateById(orgCertRel);
    }


    /**
     * 获取组织信息
     */
    @Override
    public List<OrgCertVo> getOrgCerRelByOrgId(String orgId){
        return baseMapper.getOrgCerRelByOrgId(orgId);
    }

}
