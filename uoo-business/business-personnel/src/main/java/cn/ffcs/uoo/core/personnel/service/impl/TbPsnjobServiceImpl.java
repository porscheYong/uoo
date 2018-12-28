package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.entity.TbPsnjob;
import cn.ffcs.uoo.core.personnel.dao.TbPsnjobMapper;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.service.TbPsnjobService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import cn.ffcs.uoo.core.personnel.util.StrUtil;
import cn.ffcs.uoo.core.personnel.vo.PsonOrgVo;
import cn.ffcs.uoo.core.personnel.vo.TbPsnjobVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
public class TbPsnjobServiceImpl extends ServiceImpl<TbPsnjobMapper, TbPsnjob> implements TbPsnjobService {

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public void delete(TbPsnjob tbPsnjob){
        baseMapper.delete(tbPsnjob);
    }

    @Override
    public Object saveTbPsnjob(TbPsnjob tbPsnjob){
        tbPsnjob.setPsnjobId(this.getId());
        if(retBool(baseMapper.insert(tbPsnjob))){
            return ResultUtils.success(tbPsnjob.getPersonnelId());
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object updateTbPsnjob(TbPsnjob tbPsnjob) {
        if(retBool(baseMapper.updateById(tbPsnjob))){
            return ResultUtils.success(tbPsnjob.getPersonnelId());
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object delTbPsnjob(Long psnjobId, Long userId){
        TbPsnjob tbPsnjob = new TbPsnjob();
        tbPsnjob.setPsnjobId(psnjobId);
        tbPsnjob.setUpdateUser(userId);
        tbPsnjob.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbPsnjob.setStatusDate(new Date());
        if(retBool(baseMapper.updateById(tbPsnjob))){
            TbPsnjob psnjob = getPsnjobById(psnjobId);
            return ResultUtils.success(psnjob.getPersonnelId());
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    public TbPsnjob getPsnjobById(Long familyId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        params.put(BaseUnitConstants.TBFAMILY_FAMILY_ID, familyId);
        return this.selectOne(new EntityWrapper<TbPsnjob>().allEq(params));
    }

    @Override
    public Object getTbPsnjobById(Long psnjobId){
        return ResultUtils.success(getPsnjobById(psnjobId));
    }

    @Override
    public Object selectTbPsnjobPage(Long personnelId, Integer pageNo, Integer pageSize){
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 5 : pageSize;

        Wrapper tbPsnjobwrapper= Condition.create().eq(true,"PERSONNEL_ID", personnelId);
        Page<TbPsnjobVo> tbPsnjobPage = this.selectPage(new Page<TbPsnjob>(pageNo,pageSize, null, false),tbPsnjobwrapper);
        return ResultUtils.success(tbPsnjobPage);
    }

    @Override
    public Object delTbPsnjobByPsnId(Long personnelId, Long userId){
        TbPsnjob tbPsnjob = new TbPsnjob();
        tbPsnjob.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbPsnjob.setStatusDate(new Date());
        tbPsnjob.setUpdateUser(userId);
        EntityWrapper<TbPsnjob> wrapper = new EntityWrapper<TbPsnjob>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        if(retBool(baseMapper.update(tbPsnjob, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Page<TbPsnjobVo> getPsnjobPageBypsnId(Long personnelId, Integer pageNo, Integer pageSize){
        Page<TbPsnjobVo> page = new Page<TbPsnjobVo>(StrUtil.intiPageNo(pageNo)
                , StrUtil.intiPageSize(pageSize));
        List<TbPsnjobVo> tbPsnjobVoList = baseMapper.getPsnjobPageBypsnId(page, personnelId);
        page.setRecords(tbPsnjobVoList);
        return page;
    }
}
