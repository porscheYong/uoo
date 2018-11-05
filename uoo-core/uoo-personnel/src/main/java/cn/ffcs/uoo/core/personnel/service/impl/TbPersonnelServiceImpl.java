package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.dao.TbPersonnelMapper;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.vo.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-09-06
 */
@Service
public class TbPersonnelServiceImpl extends ServiceImpl<TbPersonnelMapper, TbPersonnel> implements TbPersonnelService {
    @Override
    public Page<PersonnelRelationInfoVo> getPersonnelRelationInfo(TbPersonnelVo tbPersonnelVo) {
        List<PersonnelRelationInfoVo> list = baseMapper.getPersonnelRelationInfo(tbPersonnelVo);
        int pageSize = tbPersonnelVo.getPageSize() == 0?12:tbPersonnelVo.getPageSize();
        Page<PersonnelRelationInfoVo> page = new Page<PersonnelRelationInfoVo>(tbPersonnelVo.getPageNo(),pageSize);
        page.setRecords(list);
        return page;
    }

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public void delete(TbPersonnel tbPersonnel) {
        baseMapper.delete(tbPersonnel);
    }

    @Override
    public Page<PersonnelOrgVo> getPersonnelOrg(TbPersonnelVo tbPersonnelVo){
        List<PersonnelOrgVo> list = baseMapper.getPersonnelOrg(tbPersonnelVo);
        int pageSize = tbPersonnelVo.getPageSize() == 0 ? 12 : tbPersonnelVo.getPageSize();
        Page<PersonnelOrgVo> page = new Page<PersonnelOrgVo>(tbPersonnelVo.getPageNo(),pageSize);
        page.setRecords(list);
        return page;
    }

    @Override
    public Long getPsnNbrId(){ return baseMapper.getPsnNbrId();}

    @Override
    public Long getPsnCodeId(){ return baseMapper.getPsnCodeId();}

    @Override
    public Page<PsonOrgVo> selectPsonOrgPage(PsonOrgVo psonOrgVo){
        Page<PsonOrgVo> page = new Page<PsonOrgVo>(psonOrgVo.getPageNo()==0?1:psonOrgVo.getPageNo()
                ,psonOrgVo.getPageSize()==0?10:psonOrgVo.getPageSize());
        List<PsonOrgVo> list = baseMapper.getPsnOrg(page, psonOrgVo);
        page.setRecords(list);
        return page;
    }

    @Override
    public PsnByUserVo getPsnByUser(PsnByUserVo psnByUserVo){
        return baseMapper.getPsnByUser(psnByUserVo);
    }
}
