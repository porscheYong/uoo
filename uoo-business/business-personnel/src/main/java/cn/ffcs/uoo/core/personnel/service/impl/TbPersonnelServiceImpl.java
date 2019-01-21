package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.dao.TbPersonnelMapper;
import cn.ffcs.uoo.core.personnel.entity.TbCert;
import cn.ffcs.uoo.core.personnel.entity.TbContact;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.service.ModifyHistoryService;
import cn.ffcs.uoo.core.personnel.service.TbCertService;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.util.IdCardVerification;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import cn.ffcs.uoo.core.personnel.util.StrUtil;
import cn.ffcs.uoo.core.personnel.vo.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private TbCertService tbCertService;
    @Autowired
    private ModifyHistoryService modifyHistoryService;

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
    public Object delTbPersonnelByPsnId(Long personnelId, Long userId, String batchNum){

        EntityWrapper<TbPersonnel> wrapper = new EntityWrapper<TbPersonnel>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        TbPersonnel tbPersonnel = this.selectOne(wrapper);
        if(!StrUtil.isNullOrEmpty(tbPersonnel)){
            tbPersonnel.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
            tbPersonnel.setStatusDate(new Date());
            tbPersonnel.setUpdateUser(userId);
            if(retBool(baseMapper.updateById(tbPersonnel))){
                modifyHistoryService.deleteModifyHistory(tbPersonnel, userId, batchNum);
            }
        }
        return ResultUtils.success(null);
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
            if(StrUtil.isNullOrEmpty(personnel)){
                String psnCode = "H" + StrUtil.padLeading(String.valueOf(this.getSeqPsnCode()), 8 , "0");
                tbPersonnel.setPsnCode(psnCode);
                tbPersonnel.setPsnNbr(psnCode);
                tbPersonnel.setNcCode("");
            }
        }else{
            String[] arr = tbPersonnel.getNcCode().split("@");
            tbPersonnel.setPsnCode(arr[0]);
            tbPersonnel.setPsnNbr(arr[0]);
        }

        if(StrUtil.isNullOrEmpty(personnel)){
            tbPersonnel.setUuid(StrUtil.getUUID());
            baseMapper.insert(tbPersonnel);
            modifyHistoryService.insertModifyHistory(tbPersonnel, tbPersonnel.getUpdateUser(), tbPersonnel.getBatchNum());
        }else {
            TbPersonnel oldTbPsn = baseMapper.selectById(tbPersonnel.getPersonnelId());
            baseMapper.updateById(tbPersonnel);
            modifyHistoryService.updateModifyHistory(oldTbPsn, tbPersonnel, tbPersonnel.getUpdateUser(), tbPersonnel.getBatchNum());

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

    @Override
    public Object getHomeStatistics(String labelType){
        return ResultUtils.success(baseMapper.getHomeStatistics(labelType));
    }

    /**
     *  人员信息 校验
     * @param editFormPersonnelVo
     * @return
     */
    @Override
    public Object checkFormPersonnel(EditFormPersonnelVo editFormPersonnelVo){
        /**  人员姓名、证件类型、身份证号、手机号、邮箱   非空判断
         *   身份证、手机号、邮箱 校验   */
        if (StrUtil.isNullOrEmpty(editFormPersonnelVo.getPsnName())){
            return ResultUtils.error(EumPersonnelResponseCode.PSN_NAME_IS_NULL);
        }
        if (StrUtil.isNullOrEmpty(editFormPersonnelVo.getCertType())){
            return ResultUtils.error(EumPersonnelResponseCode.CERT_TYPE_IS_NULL);
        }
        if (StrUtil.isNullOrEmpty(editFormPersonnelVo.getCertNo())){
            return  ResultUtils.error(EumPersonnelResponseCode.CERT_NO_IS_NULL);
        }
        if(BaseUnitConstants.INTI_CERT_TYPE.equals(editFormPersonnelVo.getCertType())
                && !IdCardVerification.idCardValidate(editFormPersonnelVo.getCertNo())){
            return ResultUtils.certError();
        }

        List<TbContact> tbMobileVoList = editFormPersonnelVo.getTbMobileVoList();
        if(tbMobileVoList != null && tbMobileVoList.size() > 0){
            Object mobileObj = checkRepeat(tbMobileVoList, EumPersonnelResponseCode.MOBILE_REPEAT);
            if(!StrUtil.isNullOrEmpty(mobileObj)){
                return mobileObj;
            }

            for (TbContact tbContact : tbMobileVoList) {
                if( StrUtil.isNullOrEmpty(tbContact.getContent())){
                    return ResultUtils.error(EumPersonnelResponseCode.MOBILE_IS_NULL);
                }else if( !StrUtil.checkTelephoneNumber(tbContact.getContent())){
                    return ResultUtils.error(EumPersonnelResponseCode.MOBILE_ERROR);
                }
// else{
//                    Map<String, Object> phoneMap = new HashMap<String, Object>();
//                    phoneMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
//                    phoneMap.put(BaseUnitConstants.TBCONTACT_CONTENT, tbContact.getContent());
//                    phoneMap.put(BaseUnitConstants.TBCONTACT_CONTACT_TYPE, "1");
//                    TbContact tbContact1 = tbContactService.selectOne(new EntityWrapper<TbContact>().allEq(phoneMap));
//                    if((StrUtil.isNullOrEmpty(editFormPersonnelVo.getPersonnelId()) && !StrUtil.isNullOrEmpty(tbContact1))
//                            || (!StrUtil.isNullOrEmpty(tbContact1) && !tbContact1.getPersonnelId().equals(editFormPersonnelVo.getPersonnelId()))){
//                        Map<String, Object> psnMap = new HashMap<String, Object>();
//                        psnMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
//                        psnMap.put(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, tbContact1.getPersonnelId());
//                        TbPersonnel tbPersonnel = tbPersonnelService.selectOne(new EntityWrapper<TbPersonnel>().allEq(psnMap));
//                        return ResultUtils.error(EumPersonnelResponseCode.MOBILE_IS_EXIST.getState(), "手机号已被【" + tbPersonnel.getPsnName() + "】使用");
//                    }
//                }
            }
        }

        List<TbContact> tbEamilVoList = editFormPersonnelVo.getTbEamilVoList();
        if(tbEamilVoList != null && tbEamilVoList.size() > 0) {
            Object mobileObj = checkRepeat(tbEamilVoList, EumPersonnelResponseCode.EMAIL_REPEAT);
            if(!StrUtil.isNullOrEmpty(mobileObj)){
                return mobileObj;
            }
            for (TbContact tbContact : tbEamilVoList) {
//                if(StrUtil.isNullOrEmpty(tbContact.getContent())){
//                    return ResultUtils.error(EumPersonnelResponseCode.EMAIL_IS_NULL);
//                }
                if(!StrUtil.checkEmail(tbContact.getContent())){
                    return ResultUtils.error(EumPersonnelResponseCode.EMAIL_ERROR);
                }
            }
        }

        //身份证是否被占用
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TBCERT_CERT_NO, editFormPersonnelVo.getCertNo());
        TbCert tbCert = tbCertService.selectOne(new EntityWrapper<TbCert>().allEq(map));
        if((StrUtil.isNullOrEmpty(editFormPersonnelVo.getPersonnelId()) && !StrUtil.isNullOrEmpty(tbCert))
                || (!StrUtil.isNullOrEmpty(tbCert) && !tbCert.getPersonnelId().equals(editFormPersonnelVo.getPersonnelId()))){
            map.remove(BaseUnitConstants.TBCERT_CERT_NO);
            map.put(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, tbCert.getPersonnelId());
            TbPersonnel tbPersonnel = this.selectOne(new EntityWrapper<TbPersonnel>().allEq(map));
            return ResultUtils.error(EumPersonnelResponseCode.CERT_IS_EXIST.getState(), "证件号已被【" + tbPersonnel.getPsnName() + "】使用");
        }
        return null;
    }

    /**
     * 联系方式重复判断
     * @param tbContactList
     * @param responseCode
     * @return
     */
    public Object checkRepeat(List<TbContact> tbContactList, EumPersonnelResponseCode responseCode){
        HashSet<String> hashSet = new HashSet<>();
        for(TbContact tbContact : tbContactList){
            if(!hashSet.add(tbContact.getContent())){
                return ResultUtils.error(responseCode);
            }
        }
        return null;
    }
}
