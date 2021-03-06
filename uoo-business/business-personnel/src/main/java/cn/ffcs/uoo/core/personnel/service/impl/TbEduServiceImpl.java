package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.entity.TbEdu;
import cn.ffcs.uoo.core.personnel.dao.TbEduMapper;
import cn.ffcs.uoo.core.personnel.service.ModifyHistoryService;
import cn.ffcs.uoo.core.personnel.service.TbEduService;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import cn.ffcs.uoo.core.personnel.util.StrUtil;
import cn.ffcs.uoo.core.personnel.vo.TbEduVo;
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
public class TbEduServiceImpl extends ServiceImpl<TbEduMapper, TbEdu> implements TbEduService {

    @Autowired
    private ModifyHistoryService modifyHistoryService;

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public void delete(TbEdu tbEdu){
        baseMapper.delete(tbEdu);
    }

    @Override
    public Object saveTbEdu(TbEdu tbEdu){
        tbEdu.setEduId(this.getId());
        if(retBool(baseMapper.insert(tbEdu))){
            //modifyHistoryService.insertModifyHistory(tbEdu, tbEdu.getUpdateUser());
            return ResultUtils.success(tbEdu.getPersonnelId());
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object updateTbEdu(TbEdu tbEdu){
        if(retBool(baseMapper.updateById(tbEdu))){
            TbEdu oldTbEdu = this.getEduById(tbEdu.getEduId());
            //modifyHistoryService.updateModifyHistory(oldTbEdu, tbEdu, tbEdu.getUpdateUser());
            return ResultUtils.success(tbEdu.getPersonnelId());
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object delTbEdu(Long eduId, Long userId){
        TbEdu tbEdu = new TbEdu();
        tbEdu.setEduId(eduId);
        tbEdu.setUpdateUser(userId);
        tbEdu.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbEdu.setStatusDate(new Date());
        if(retBool(baseMapper.updateById(tbEdu))){
            TbEdu edu = getEduById(eduId);
            //modifyHistoryService.deleteModifyHistory(tbEdu, userId);
            return ResultUtils.success(edu.getPersonnelId());
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    public TbEdu getEduById(Long eduId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        params.put(BaseUnitConstants.TBEDU_EDU_ID, eduId);
        return this.selectOne(new EntityWrapper<TbEdu>().allEq(params));
    }

    @Override
    public Object getTbEduById(Long eduId){
        return ResultUtils.success(getEduById(eduId));
    }

    @Override
    public Object delTbEduByPsnId(Long personnelId, Long userId){
        TbEdu tbEdu = new TbEdu();
        tbEdu.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbEdu.setStatusDate(new Date());
        tbEdu.setUpdateUser(userId);
        EntityWrapper<TbEdu> wrapper = new EntityWrapper<TbEdu>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        if(retBool(baseMapper.update(tbEdu, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object getTbEduPage(Long personnelId, Integer pageNo, Integer pageSize){
        Page<TbEdu> page = this.getTbEduPageByPsnId(personnelId, pageNo, pageSize);
        return ResultUtils.success(page);
    }

    @Override
    public Page<TbEdu> getTbEduPage(Long personnelId){
        return getTbEduPageByPsnId(personnelId, 0, 0);
    }

    public Page<TbEdu> getTbEduPageByPsnId(Long personnelId, Integer pageNo, Integer pageSize){
        EntityWrapper<TbEdu> wrapper = new EntityWrapper<TbEdu>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        Page<TbEdu> page = this.selectPage(new Page<TbEdu>(StrUtil.intiPageNo(pageNo), StrUtil.intiPageSize(pageSize)), wrapper);
        return page;
    }
}
