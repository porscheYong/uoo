package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.dao.TbPersonnelMapper;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import cn.ffcs.uoo.core.personnel.util.StrUtil;
import cn.ffcs.uoo.core.personnel.vo.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
    public Long getSeqPsnCode(){ return baseMapper.getSeqPsnCode();}

    @Override
    public PsnByUserVo getPsnByUser(PsnByUserVo psnByUserVo){
        return baseMapper.getPsnByUser(psnByUserVo);
    }

    @Override
    public Object delTbPersonnelByPsnId(Long personnelId){
        TbPersonnel tbPersonnel = new TbPersonnel();
        tbPersonnel.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbPersonnel.setStatusDate(new Date());
        EntityWrapper<TbPersonnel> wrapper = new EntityWrapper<TbPersonnel>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        if(retBool(baseMapper.update(tbPersonnel, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Page<PsnBasicInfoVo> getPsnBasicInfo(String keyWord, Integer pageNo, Integer pageSize){
        PsnBasicInfoVo psnBasicInfoVo = new PsnBasicInfoVo();
        psnBasicInfoVo.setKeyWord(keyWord);
        psnBasicInfoVo.setPageNo(StrUtil.intiPageNo(pageNo));
        psnBasicInfoVo.setPageSize(StrUtil.intiPageSize(pageSize));
        Page<PsnBasicInfoVo> page = new Page<PsnBasicInfoVo>(psnBasicInfoVo.getPageNo(), psnBasicInfoVo.getPageSize());
        List<PsnBasicInfoVo> list = baseMapper.getPsnBasicInfo(page, psnBasicInfoVo);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean isExistsAcct( Long personnelId){
        if(baseMapper.getAcctNumByPsnId(personnelId) > 0){
            return true;
        }
        return false;
    }

    @Override
    public Object insertOrUpdateTbPsn(TbPersonnel tbPersonnel){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, tbPersonnel.getPersonnelId());
        TbPersonnel personnel = this.selectOne(new EntityWrapper<TbPersonnel>().allEq(map));

        if(StrUtil.isNullOrEmpty(tbPersonnel.getNcCode())){
            String psnCode = "H" + StrUtil.padLeading(String.valueOf(this.getSeqPsnCode()), 8 , "0");
            tbPersonnel.setPsnCode(psnCode);
            tbPersonnel.setPsnNbr(psnCode);
            tbPersonnel.setNcCode("");
        }else{
            String[] arr = tbPersonnel.getNcCode().split("@");
            tbPersonnel.setPsnCode(arr[0]);
            tbPersonnel.setPsnNbr(arr[0]);
        }

        if(StrUtil.isNullOrEmpty(personnel)){
            tbPersonnel.setUuid(StrUtil.getUUID());
            baseMapper.insert(tbPersonnel);
        }else {
            baseMapper.updateById(tbPersonnel);
        }
        return ResultUtils.success(null);
    }

    @Override
    public Page<FreePsnInfoVo> getFreePsnInfo(String keyWord, Integer pageNo, Integer pageSize){
        Page<FreePsnInfoVo> page = new Page<FreePsnInfoVo>(StrUtil.intiPageNo(pageNo), StrUtil.intiPageSize(pageSize));
        page.setRecords(baseMapper.getFreePsnInfo(page, keyWord));
        return page;
    }

    @Override
    public UomGrpUserOrgInfoVo getIdCardNcCode(String certNo){
        return baseMapper.getIdCardNcCode(certNo);
    }
}
