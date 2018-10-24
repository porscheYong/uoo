package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.dao.TbPersonnelMapper;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.vo.PersonnelOrgVo;
import cn.ffcs.uoo.core.personnel.vo.PersonnelRelationInfoVo;
import cn.ffcs.uoo.core.personnel.vo.TbPersonnelVo;
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
}
