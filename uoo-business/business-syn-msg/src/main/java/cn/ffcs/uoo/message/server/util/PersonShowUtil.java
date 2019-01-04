package cn.ffcs.uoo.message.server.util;

import cn.ffcs.uoo.message.server.vo.TbAcctVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonShowUtil {
    private static Logger logger = LoggerFactory.getLogger(PersonShowUtil.class);
    //true：校验后结果不生效，false:校验后结果生效。
    private static boolean f = true;

    public static void noShow(TbAcctVo vo) {

        //校验数据

        if (vo == null ) {
            return;
        }
        if(!check(vo)){
            vo = null;
            return;
        }
        vo.setSlaveAcctId(null);
        vo.setPersonnelId(null);

        if (vo.getTbAcctCrossRel() != null) {
            vo.getTbAcctCrossRel().forEach((temp) -> {
                //temp.setCrossTran(null);//temp.setRelaType(null);
                temp.setAcctCrossId(null);
                temp.setAcctId(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
            });
        }

        if (vo.getTbAcctOrgRel() != null) {
            vo.getTbAcctOrgRel().forEach((temp) -> {
                temp.setLocId(null);
                temp.setAreaCodeId(null);
                temp.setOrgCode(null);
                temp.setShortName(null);
                temp.setOrgNameEn(null);
                temp.setFullName(null);
                temp.setCityTown(null);
                temp.setOfficePhone(null);
                temp.setFoundingTime(null);
                temp.setOrgScale(null);
                temp.setOrgLevel(null);
                temp.setOrgPositionLevel(null);
                temp.setSort(null);
                temp.setOrgContent(null);
                temp.setOrgDesc(null);
                temp.setAddress(null);
                temp.setUuid(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
            });
        }

        if (vo.getTbCert() != null) {
            vo.getTbCert().forEach((temp) -> {
                //temp.setCertName(null);//temp.setCertType(null);//temp.setCertNo(null);//temp.setAddress(null);
                //temp.setIssuing(null);//temp.setIsReal(null);//temp.setSource(null);
                temp.setCertId(null);
                temp.setPersonnelId(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
                temp.setUuid(null);
            });
        }

        if (vo.getTbContact() != null) {
            vo.getTbContact().forEach((temp) -> {
                //temp.setContactType(null);//temp.setContent(null);//temp.setFirstFlag(null);
                temp.setContactId(null);
                temp.setPersonnelId(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
                temp.setUuid(null);
            });
        }

        if (vo.getTbEdu() != null) {
            vo.getTbEdu().forEach((temp) -> {
                //temp.setSchool(null);//temp.setSchoolType(null);//temp.setMajor(null);//temp.setMajortype(null);
                //temp.setEducation(null);//temp.setEdusystem(null);//temp.setDegree(null);//temp.setFirstEducation(null);
                //temp.setLastEducation(null);//temp.setLastDegree(null);//temp.setBegindate(null);//temp.setEnddate(null);
                //temp.setCertifcode(null);//temp.setIsFullTimeHighEdu(null);//temp.setIsInServiceHighEdu(null);
                temp.setEduId(null);
                temp.setPersonnelId(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
            });
        }

        if (vo.getTbFamily() != null) {
            vo.getTbFamily().forEach((temp) -> {
                //temp.setMemRelation(null);//temp.setMemName(null);//temp.setRelaEmail(null);//temp.setRelaPhone(null);
                //temp.setRelaAddr(null);
                temp.setFamilyId(null);
                temp.setPersonnelId(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
            });
        }

        if (vo.getTbPersonnel() != null) {
            //vo.getTbPersonnel().setPersonnelId(null);//vo.getTbPersonnel().setPsnName(null);//vo.getTbPersonnel().setPsnCode(null);
            //vo.getTbPersonnel().setNcCode(null);//vo.getTbPersonnel().setPsnNbr(null);//vo.getTbPersonnel().setNationality(null);
            //vo.getTbPersonnel().setGender(null);//vo.getTbPersonnel().setNation(null);//vo.getTbPersonnel().setMarriage(null);
            //vo.getTbPersonnel().setPliticalStatus(null);//vo.getTbPersonnel().setReason(null);
            //vo.getTbPersonnel().setToWorkTime(null);//vo.getTbPersonnel().setServingAge(null);//vo.getTbPersonnel().setLengthService(null);
            //vo.getTbPersonnel().setUuid(null);//vo.getTbPersonnel().setNotes(null);
            vo.getTbPersonnel().setStatusCd(null);
            vo.getTbPersonnel().setCreateDate(null);
            vo.getTbPersonnel().setCreateUser(null);
            vo.getTbPersonnel().setUpdateDate(null);
            vo.getTbPersonnel().setUpdateUser(null);
            vo.getTbPersonnel().setStatusDate(null);
            vo.getTbPersonnel().setImage(null);
        }

        if (vo.getTbPsnjob() != null) {
            vo.getTbPsnjob().forEach((temp) -> {
                //temp.setOrgId(null);//temp.setBeginTime(null);//temp.setEndTime(null);
                temp.setPsnjobId(null);
                temp.setPersonnelId(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
            });
        }

        if (vo.getTbSlaveAcct() != null) {

            if (vo.getTbSlaveAcct().getTbAcctExt() != null) {
                vo.getTbSlaveAcct().getTbAcctExt().forEach((temp) -> {
                    //temp.setName(null);//temp.setContactWay(null);//temp.setWorkEmail(null);//temp.setCertType(null);
                    //temp.setCertNo(null);//temp.setGender(null);//temp.setNation(null);//temp.setNativePlace(null);
                    temp.setAcctExtId(null);
                    temp.setSlaveAcctId(null);
                    temp.setStatusCd(null);
                    temp.setCreateDate(null);
                    temp.setCreateUser(null);
                    temp.setUpdateDate(null);
                    temp.setUpdateUser(null);
                    temp.setStatusDate(null);
                });
            }
            //vo.getTbSlaveAcct().setSlaveAcctId(null);//vo.getTbSlaveAcct().setSlaveAcct(null);//vo.getTbSlaveAcct().setSalt(null);
            //vo.getTbSlaveAcct().setPassword(null);//vo.getTbSlaveAcct().setSymmetryPassword(null);//vo.getTbSlaveAcct().setSlaveAcctType(null);
            //vo.getTbSlaveAcct().setEnableDate(null);//vo.getTbSlaveAcct().setDisableDate(null);//vo.getTbSlaveAcct().setSystemName(null);
            //vo.getTbSlaveAcct().setBusinessSystemId(null);vo.getTbSlaveAcct().setBusinessSystemId(null);
            vo.getTbSlaveAcct().setResourceObjId(null);
            vo.getTbSlaveAcct().setStatusCd(null);
            vo.getTbSlaveAcct().setCreateDate(null);
            vo.getTbSlaveAcct().setCreateUser(null);
            vo.getTbSlaveAcct().setUpdateDate(null);
            vo.getTbSlaveAcct().setUpdateUser(null);
            vo.getTbSlaveAcct().setStatusDate(null);
            vo.getTbSlaveAcct().setAcctHostId(null);
            vo.getTbSlaveAcct().setAcctId(null);
        }
    }

    private static boolean check(TbAcctVo vo){
        //flag > 0 缺少对应的数据。
        int flag = 0;
        if(vo.getAcctId() == null){
            logger.warn("主账号标识不存在");
            flag++;
        }
        if(vo.getAcct() == null || "".equals(vo.getAcct())){
            logger.warn("主账号不存在");
            flag++;
        }
        if(vo.getAcctType()== null || "".equals(vo.getAcctType())){
            logger.warn("账号类型不存在");
            /*flag++;*/
        }
        if(vo.getUserHostType()== null || "".equals(vo.getUserHostType())){
            logger.warn("用户主体类型不存在");
            /*flag++;*/
        }
        if(vo.getEnableDate()== null){
            logger.warn("生效时间不存在");
            /*flag++;*/
        }

        if(vo.getDisableDate()== null){
            logger.warn("失效时间不存在");
            /*flag++;*/
        }

        if(vo.getTbAcctOrgRel() == null){
            logger.warn("账号组织关系不存在");
            flag++;
        }

        if( vo.getTbPersonnel()==null){
            logger.warn("人员不存在");
            flag++;
        }

        if( vo.getTbContact()==null){
            logger.warn("联系方式不存在");
            flag++;
        }

        if( vo.getTbCert()==null){
            logger.warn("证件不存在");
            flag++;
        }
        if(flag >=1 ){
            return true;
        }
        return true;
    }
}