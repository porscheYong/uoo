package cn.ffcs.uoo.message.server.util;

import cn.ffcs.uoo.message.server.vo.TbAcctVo;

public class PersonShowUtil {
    public static void noShow(TbAcctVo vo) {

        if (vo == null) {
            return;
        }

        vo.setSlaveAcctId(null);

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
                //temp.getTbOrg().setOrgId(null);//temp.getTbOrg().setOrgName(null);
                temp.getTbOrg().setLocId(null);
                temp.getTbOrg().setAreaCodeId(null);
                temp.getTbOrg().setOrgCode(null);
                temp.getTbOrg().setShortName(null);
                temp.getTbOrg().setOrgNameEn(null);
                temp.getTbOrg().setFullName(null);
                temp.getTbOrg().setCityTown(null);
                temp.getTbOrg().setOfficePhone(null);
                temp.getTbOrg().setFoundingTime(null);
                temp.getTbOrg().setOrgScale(null);
                temp.getTbOrg().setOrgLevel(null);
                temp.getTbOrg().setOrgPositionLevel(null);
                temp.getTbOrg().setSort(null);
                temp.getTbOrg().setOrgContent(null);
                temp.getTbOrg().setOrgDesc(null);
                temp.getTbOrg().setAddress(null);
                temp.getTbOrg().setUuid(null);
                temp.getTbOrg().setStatusCd(null);
                temp.getTbOrg().setCreateDate(null);
                temp.getTbOrg().setCreateUser(null);
                temp.getTbOrg().setUpdateDate(null);
                temp.getTbOrg().setUpdateUser(null);
                temp.getTbOrg().setStatusDate(null);
                temp.setAcctHostId(null);
                temp.setOrgId(null);
                temp.setAcctId(null);
                temp.setSort(null);
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
                //temp.setIssuing(null);//temp.setUuid(null);//temp.setIsReal(null);//temp.setSource(null);
                temp.setCertId(null);
                temp.setPersonnelId(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);
            });
        }

        if (vo.getTbContact() != null) {
            vo.getTbContact().forEach((temp) -> {
                //temp.setContactType(null);//temp.setContent(null);//temp.setUuid(null);//temp.setFirstFlag(null);
                temp.setContactId(null);
                temp.setPersonnelId(null);
                temp.setStatusCd(null);
                temp.setCreateDate(null);
                temp.setCreateUser(null);
                temp.setUpdateDate(null);
                temp.setUpdateUser(null);
                temp.setStatusDate(null);

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
            //vo.getTbPersonnel().setPliticalStatus(null);//vo.getTbPersonnel().setImage(null);//vo.getTbPersonnel().setReason(null);
            //vo.getTbPersonnel().setToWorkTime(null);//vo.getTbPersonnel().setServingAge(null);//vo.getTbPersonnel().setLengthService(null);
            //vo.getTbPersonnel().setUuid(null);//vo.getTbPersonnel().setNotes(null);
            vo.getTbPersonnel().setStatusCd(null);
            vo.getTbPersonnel().setCreateDate(null);
            vo.getTbPersonnel().setCreateUser(null);
            vo.getTbPersonnel().setUpdateDate(null);
            vo.getTbPersonnel().setUpdateUser(null);
            vo.getTbPersonnel().setStatusDate(null);
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

        if (vo.getTbSlaveAcctVo() != null) {

            if (vo.getTbSlaveAcctVo().getTbAcctExt() != null) {
                vo.getTbSlaveAcctVo().getTbAcctExt().forEach((temp) -> {
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
            //vo.getTbSlaveAcctVo().setSlaveAcctId(null);//vo.getTbSlaveAcctVo().setSlaveAcct(null);//vo.getTbSlaveAcctVo().setSalt(null);
            //vo.getTbSlaveAcctVo().setPassword(null);//vo.getTbSlaveAcctVo().setSymmetryPassword(null);//vo.getTbSlaveAcctVo().setSlaveAcctType(null);
            //vo.getTbSlaveAcctVo().setEnableDate(null);//vo.getTbSlaveAcctVo().setDisableDate(null);//vo.getTbSlaveAcctVo().setSystemName(null);
            //vo.getTbSlaveAcctVo().setBusinessSystemId(null);
            vo.getTbSlaveAcctVo().setResourceObjId(null);
            vo.getTbSlaveAcctVo().setStatusCd(null);
            vo.getTbSlaveAcctVo().setCreateDate(null);
            vo.getTbSlaveAcctVo().setCreateUser(null);
            vo.getTbSlaveAcctVo().setUpdateDate(null);
            vo.getTbSlaveAcctVo().setUpdateUser(null);
            vo.getTbSlaveAcctVo().setStatusDate(null);
            vo.getTbSlaveAcctVo().setAcctHostId(null);
            vo.getTbSlaveAcctVo().setAcctId(null);
        }
    }
}