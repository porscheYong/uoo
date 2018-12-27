package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgContactRel;
import cn.ffcs.uoo.core.organization.dao.OrgContactRelMapper;
import cn.ffcs.uoo.core.organization.entity.OrgLevel;
import cn.ffcs.uoo.core.organization.service.OrgContactRelService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@Service
public class OrgContactRelServiceImpl extends ServiceImpl<OrgContactRelMapper, OrgContactRel> implements OrgContactRelService {
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
     * @param orgContactRel
     */
    @Override
    public void delete(OrgContactRel orgContactRel){
        orgContactRel.setStatusCd("1100");
        orgContactRel.setStatusDate(new Date());
        orgContactRel.setUpdateDate(new Date());
        orgContactRel.setUpdateUser(StrUtil.isNullOrEmpty(orgContactRel.getUpdateUser())?0L:orgContactRel.getUpdateUser());
        updateById(orgContactRel);
    }

    /**
     * 新增
     */
    @Override
    public void add(OrgContactRel orgContactRel){
        orgContactRel.setCreateDate(new Date());
        orgContactRel.setCreateUser(StrUtil.isNullOrEmpty(orgContactRel.getCreateUser())?0L:orgContactRel.getCreateUser());
        orgContactRel.setStatusCd("1000");
        orgContactRel.setStatusDate(new Date());
        insert(orgContactRel);
    }

    /**
     * 更新
     */
    @Override
    public void update(OrgContactRel orgContactRel){
        orgContactRel.setUpdateDate(new Date());
        orgContactRel.setUpdateUser(StrUtil.isNullOrEmpty(orgContactRel.getUpdateUser())?0L:orgContactRel.getUpdateUser());
        orgContactRel.setStatusDate(new Date());
        updateById(orgContactRel);
    }

    /**
     * 获取组织联系人表
     * @param psonOrgVo
     * @return
     */
    @Override
    public Page<PsonOrgVo> selectOrgContactPage(PsonOrgVo psonOrgVo){
        Page<PsonOrgVo> page = new Page<PsonOrgVo>(psonOrgVo.getPageNo()==0?1:psonOrgVo.getPageNo(),
                psonOrgVo.getPageSize()==0?10:psonOrgVo.getPageSize());
        List<PsonOrgVo> list = baseMapper.selectOrgContactPage(page,psonOrgVo);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<PsonOrgVo> getOrgContact(String orgId){
        PsonOrgVo psnOrg = new PsonOrgVo();
        return baseMapper.getOrgContact(orgId);
    }

}
