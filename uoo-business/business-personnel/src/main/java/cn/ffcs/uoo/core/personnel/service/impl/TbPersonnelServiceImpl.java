package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.dao.TbPersonnelMapper;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import cn.ffcs.uoo.core.personnel.vo.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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

    @Override
    public Object delTbPersonnelByPsnId(Long personnelId){
        TbPersonnel tbPersonnel = new TbPersonnel();
        tbPersonnel.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        EntityWrapper<TbPersonnel> wrapper = new EntityWrapper<TbPersonnel>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        if(retBool(baseMapper.update(tbPersonnel, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Page<PsnBasicInfoVo> getPsnBasicInfo(String keyWord, int pageNo, int pageSize){
        PsnBasicInfoVo psnBasicInfoVo = new PsnBasicInfoVo();
        psnBasicInfoVo.setKeyWord(keyWord);
        psnBasicInfoVo.setPageNo(pageNo == 0 ? 1 : pageNo);
        psnBasicInfoVo.setPageSize(pageSize == 0 ? 5 : pageSize);
        Page<PsnBasicInfoVo> page = new Page<PsnBasicInfoVo>(psnBasicInfoVo.getPageNo(), psnBasicInfoVo.getPageSize());
        List<PsnBasicInfoVo> list = baseMapper.getPsnBasicInfo(page, psnBasicInfoVo);
        page.setRecords(list);
        return page;
    }

    @Override
    public Object addOrgPsn(List<PsonOrgVo> psonOrgVos){
        if(psonOrgVos != null && psonOrgVos.size() > 0){
            for(PsonOrgVo psonOrgVo : psonOrgVos){
                baseMapper.insertOrgPsnRel(psonOrgVo.getOrgId(), psonOrgVo.getPersonId());
                baseMapper.insertOrgTreeOrgPsnRel(psonOrgVo.getOrgRootId(), psonOrgVo.getPersonId());
            }
        }

        return null;
    }
}
