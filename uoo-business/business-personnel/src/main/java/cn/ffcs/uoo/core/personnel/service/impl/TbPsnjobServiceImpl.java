package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.entity.TbPsnjob;
import cn.ffcs.uoo.core.personnel.dao.TbPsnjobMapper;
import cn.ffcs.uoo.core.personnel.service.TbPsnjobService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import cn.ffcs.uoo.core.personnel.vo.PsonOrgVo;
import cn.ffcs.uoo.core.personnel.vo.TbPsnjobVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object updateTbPsnjob(TbPsnjob tbPsnjob) {
        if(retBool(baseMapper.updateById(tbPsnjob))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object delTbPsnjob(Long psnjobId){
        TbPsnjob tbPsnjob = new TbPsnjob();
        tbPsnjob.setPsnjobId(psnjobId);
        tbPsnjob.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        if(retBool(baseMapper.updateById(tbPsnjob))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object getTbPsnjobById(Long psnjobId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        params.put(BaseUnitConstants.TBPSNJOB_PSNJOB_ID, psnjobId);
        return ResultUtils.success(this.selectOne(new EntityWrapper<TbPsnjob>().allEq(params)));
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
    public Object delTbPsnjobByPsnId(Long personnelId){
        TbPsnjob tbPsnjob = new TbPsnjob();
        tbPsnjob.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
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
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 5 : pageSize;
        TbPsnjobVo psnjobVo = new TbPsnjobVo();
        psnjobVo.setPageNo(pageNo);
        psnjobVo.setPageSize(pageSize);
        Page<TbPsnjobVo> page = new Page<TbPsnjobVo>(psnjobVo.getPageNo() == 0 ? 1 : psnjobVo.getPageNo()
                ,psnjobVo.getPageSize() == 0 ? 5 : psnjobVo.getPageSize());
        List<TbPsnjobVo> tbPsnjobVoList = baseMapper.getPsnjobPageBypsnId(page, psnjobVo);
        page.setRecords(tbPsnjobVoList);
        return page;
    }
}
