package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.entity.TbFamily;
import cn.ffcs.uoo.core.personnel.dao.TbFamilyMapper;
import cn.ffcs.uoo.core.personnel.service.TbFamilyService;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import cn.ffcs.uoo.core.personnel.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@Service
public class TbFamilyServiceImpl extends ServiceImpl<TbFamilyMapper, TbFamily> implements TbFamilyService {

    @Override
    public void save(TbFamily tbFamily){
        baseMapper.save(tbFamily);
    }

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public void delete(TbFamily tbFamily){
        baseMapper.delete(tbFamily);
    }

    @Override
    public Object saveTbFamily(TbFamily tbFamily) {
        tbFamily.setFamilyId(this.getId());
        if(retBool(baseMapper.insert(tbFamily))){
            return ResultUtils.success(tbFamily.getPersonnelId());
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object updateTbFamily(TbFamily tbFamily) {
        if(retBool(baseMapper.updateById(tbFamily))){
            return ResultUtils.success(tbFamily.getPersonnelId());
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object delTbFamily(Long familyId) {
        TbFamily tbFamily = new TbFamily();
        tbFamily.setFamilyId(familyId);
        tbFamily.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbFamily.setStatusDate(new Date());
        if(retBool(baseMapper.updateById(tbFamily))){
            TbFamily family = getFamilyById(familyId);
            return ResultUtils.success(family.getPersonnelId());
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    public TbFamily getFamilyById(Long familyId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        params.put(BaseUnitConstants.TBFAMILY_FAMILY_ID, familyId);
        return this.selectOne(new EntityWrapper<TbFamily>().allEq(params));
    }

    @Override
    public Object getTbFamilyById(Long familyId) {
        return ResultUtils.success(getFamilyById(familyId));
    }

    @Override
    public Object delTbFamilyByPsnId(Long personnelId){
        TbFamily tbFamily = new TbFamily();
        tbFamily.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbFamily.setStatusDate(new Date());
        EntityWrapper<TbFamily> wrapper = new EntityWrapper<TbFamily>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        if(retBool(baseMapper.update(tbFamily, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object getTbFamilyPage(Long personnelId, Integer pageNo, Integer pageSize){
        Page<TbFamily> page = getTbFamilyPageByPsnId(personnelId, pageNo, pageSize);
        return ResultUtils.success(page);
    }

    @Override
    public Page<TbFamily> getTbFamilyPage(Long personnelId){
        return getTbFamilyPageByPsnId(personnelId, 0, 0);
    }

    public Page<TbFamily> getTbFamilyPageByPsnId(Long personnelId, Integer pageNo, Integer pageSize){
        EntityWrapper<TbFamily> wrapper = new EntityWrapper<TbFamily>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        Page<TbFamily> page = this.selectPage(new Page<TbFamily>(StrUtil.intiPageNo(pageNo), StrUtil.intiPageSize(pageSize)), wrapper);
        return page;
    }

}
