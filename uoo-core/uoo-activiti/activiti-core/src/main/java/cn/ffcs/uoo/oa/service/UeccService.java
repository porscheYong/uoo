package cn.ffcs.uoo.oa.service;

import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.oa.entity.AtiSpecificForm;
import cn.ffcs.uoo.oa.vo.UeccVo;
import com.google.common.collect.Lists;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * uecc 服务类
 * Created by liuxiaodong on 2018/10/9.
 */
@Service
public class UeccService {
    @Resource
    private RepositoryService repositoryService;

    UeccVo getOneUeccVo(Map<String, Object> map) {
        UeccVo ueccVo = new UeccVo();
        ueccVo.setAtiActCategoryId(Long.valueOf((String) map.get("categoryId")));
        ueccVo.setFormSender((String) map.get("formSender"));
        ueccVo.setProcDefKey((String) map.get("procDefKey"));
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey((String) map.get("procDefKey")).singleResult();
        if (null != processDefinition) {
            ueccVo.setProcDefId(processDefinition.getId());
        }
        Map<String, Object> vars = (Map<String, Object>) map.get("vars");
        ueccVo.setFormTheme((String) vars.get("formTheme"));
        ueccVo.setFormContent((String) vars.get("formContent"));

        ueccVo.setStaffId(String.valueOf(vars.get("uec_staff_info")));
        ueccVo.setOutStaffId(String.valueOf(vars.get("uec_out_staff_info")));
        ueccVo.setTrialId(String.valueOf(vars.get("uec_trial")));
        ueccVo.setPsnjobIdModify(String.valueOf(vars.get("uec_psnjob_modi")));
        ueccVo.setPsnjobIdAdd(String.valueOf(vars.get("uec_psnjob_add")));
        ueccVo.setRetireId(String.valueOf(vars.get("uec_retire")));
        ueccVo.setEncId(String.valueOf(vars.get("uec_enc")));
        ueccVo.setPunishId(String.valueOf(vars.get("uec_punish")));
        ueccVo.setTalentTeamId(String.valueOf(vars.get("uec_talent_team")));
        ueccVo.setEduId(String.valueOf(vars.get("uec_doc_edu")));
        ueccVo.setFamilyId(String.valueOf(vars.get("uec_family")));
        ueccVo.setTitleId(String.valueOf(vars.get("uec_title")));
        ueccVo.setPartyLogId(String.valueOf(vars.get("uec_partylog")));
        ueccVo.setWadocIdModify(String.valueOf(vars.get("uec_psndoc_wadoc_modi")));
        ueccVo.setWadocIdAdd(String.valueOf(vars.get("uec_psndoc_wadoc_add")));
        ueccVo.setAssId(String.valueOf(vars.get("uec_ass")));
        ueccVo.setNationdutyId(String.valueOf(vars.get("uec_nationduty")));

        ueccVo.setStaffIdHis(String.valueOf(vars.get("uec_staff_info_his")));
        ueccVo.setOutStaffIdHis(String.valueOf(vars.get("uec_out_staff_info_his")));
        ueccVo.setTrialIdHis(String.valueOf(vars.get("uec_trial_his")));
        ueccVo.setPsnjobIdHis(String.valueOf(vars.get("uec_psnjob_his")));
        ueccVo.setRetireIdHis(String.valueOf(vars.get("uec_retire_his")));
        ueccVo.setEncIdHis(String.valueOf(vars.get("uec_enc_his")));
        ueccVo.setPunishIdHis(String.valueOf(vars.get("uec_punish_his")));
        ueccVo.setTalentTeamIdHis(String.valueOf(vars.get("uec_talent_team_his")));
        ueccVo.setEduIdHis(String.valueOf(vars.get("uec_doc_edu_his")));
        ueccVo.setFamilyIdHis(String.valueOf(vars.get("uec_family_his")));
        ueccVo.setTitleIdHis(String.valueOf(vars.get("uec_title_his")));
        ueccVo.setPartyLogIdHis(String.valueOf(vars.get("uec_partylog_his")));
        ueccVo.setWadocIdHis(String.valueOf(vars.get("uec_psndoc_wadoc_his")));
        ueccVo.setAssIdHis(String.valueOf(vars.get("uec_ass_his")));
        ueccVo.setNationdutyIdHis(String.valueOf(vars.get("uec_nationduty_his")));

        return ueccVo;
    }

    List<AtiSpecificForm> getSpecificForms(UeccVo oaBaseObject) {
        List<AtiSpecificForm> specificForms = Lists.newArrayList();

        if (StringUtils.isNotEmpty(oaBaseObject.getStaffId()) || !"null".equals(oaBaseObject.getStaffId())) {
            AtiSpecificForm specificFormStaffInfo = getSpecificForm(oaBaseObject);
            specificFormStaffInfo.setParameter("STAFF_ID");
            specificFormStaffInfo.setParamValue(String.valueOf(oaBaseObject.getStaffId()));
            specificForms.add(specificFormStaffInfo);
        }

        if (StringUtils.isNotEmpty(oaBaseObject.getStaffIdHis()) || !"null".equals(oaBaseObject.getStaffIdHis())) {
            AtiSpecificForm specificFormStaffInfo = getSpecificForm(oaBaseObject);
            specificFormStaffInfo.setParameter("STAFF_ID_HIS");
            specificFormStaffInfo.setParamValue(String.valueOf(oaBaseObject.getStaffIdHis()));
            specificForms.add(specificFormStaffInfo);
        }

        if (StringUtils.isNotEmpty(oaBaseObject.getOutStaffId()) || !"null".equals(oaBaseObject.getOutStaffId())) {
            AtiSpecificForm specificFormOutStaffInfo = getSpecificForm(oaBaseObject);
            specificFormOutStaffInfo.setParameter("OUT_STAFF_ID");
            specificFormOutStaffInfo.setParamValue(String.valueOf(oaBaseObject.getOutStaffId()));
            specificForms.add(specificFormOutStaffInfo);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getOutStaffIdHis()) || !"null".equals(oaBaseObject.getOutStaffIdHis())) {
            AtiSpecificForm specificFormOutStaffInfo = getSpecificForm(oaBaseObject);
            specificFormOutStaffInfo.setParameter("OUT_STAFF_ID_HIS");
            specificFormOutStaffInfo.setParamValue(String.valueOf(oaBaseObject.getOutStaffIdHis()));
            specificForms.add(specificFormOutStaffInfo);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getTrialId()) || !"null".equals(oaBaseObject.getTrialId())) {
            AtiSpecificForm specificFormTrial = getSpecificForm(oaBaseObject);
            specificFormTrial.setParameter("TRIAL_ID");
            specificFormTrial.setParamValue(String.valueOf(oaBaseObject.getTrialId()));
            specificForms.add(specificFormTrial);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getTrialIdHis()) || !"null".equals(oaBaseObject.getTrialIdHis())) {
            AtiSpecificForm specificFormTrial = getSpecificForm(oaBaseObject);
            specificFormTrial.setParameter("TRIAL_ID_HIS");
            specificFormTrial.setParamValue(String.valueOf(oaBaseObject.getTrialIdHis()));
            specificForms.add(specificFormTrial);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getPsnjobIdModify()) || !"null".equals(oaBaseObject.getPsnjobIdModify())) {
            AtiSpecificForm specificFormPsnjob = getSpecificForm(oaBaseObject);
            specificFormPsnjob.setParameter("PSNJOB_ID_MODIFY");
            specificFormPsnjob.setParamValue(String.valueOf(oaBaseObject.getPsnjobIdModify()));
            specificForms.add(specificFormPsnjob);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getPsnjobIdAdd()) || !"null".equals(oaBaseObject.getPsnjobIdAdd())) {
            AtiSpecificForm specificFormPsnjob = getSpecificForm(oaBaseObject);
            specificFormPsnjob.setParameter("PSNJOB_ID_ADD");
            specificFormPsnjob.setParamValue(String.valueOf(oaBaseObject.getPsnjobIdAdd()));
            specificForms.add(specificFormPsnjob);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getPsnjobIdHis()) || !"null".equals(oaBaseObject.getPsnjobIdHis())) {
            AtiSpecificForm specificFormPsnjob = getSpecificForm(oaBaseObject);
            specificFormPsnjob.setParameter("PSNJOB_ID_HIS");
            specificFormPsnjob.setParamValue(String.valueOf(oaBaseObject.getPsnjobIdHis()));
            specificForms.add(specificFormPsnjob);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getRetireId()) || !"null".equals(oaBaseObject.getRetireId())) {
            AtiSpecificForm specificFormRetire = getSpecificForm(oaBaseObject);
            specificFormRetire.setParameter("RETIRE_ID");
            specificFormRetire.setParamValue(String.valueOf(oaBaseObject.getRetireId()));
            specificForms.add(specificFormRetire);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getRetireIdHis()) || !"null".equals(oaBaseObject.getRetireIdHis())) {
            AtiSpecificForm specificFormRetire = getSpecificForm(oaBaseObject);
            specificFormRetire.setParameter("RETIRE_ID_HIS");
            specificFormRetire.setParamValue(String.valueOf(oaBaseObject.getRetireIdHis()));
            specificForms.add(specificFormRetire);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getEncId()) || !"null".equals(oaBaseObject.getEncId())) {
            AtiSpecificForm specificFormEnc = getSpecificForm(oaBaseObject);
            specificFormEnc.setParameter("ENC_ID");
            specificFormEnc.setParamValue(String.valueOf(oaBaseObject.getEncId()));
            specificForms.add(specificFormEnc);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getEncIdHis()) || !"null".equals(oaBaseObject.getEncIdHis())) {
            AtiSpecificForm specificFormEnc = getSpecificForm(oaBaseObject);
            specificFormEnc.setParameter("ENC_ID_HIS");
            specificFormEnc.setParamValue(String.valueOf(oaBaseObject.getEncIdHis()));
            specificForms.add(specificFormEnc);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getPunishId()) || !"null".equals(oaBaseObject.getPunishId())) {
            AtiSpecificForm specificFormPunish = getSpecificForm(oaBaseObject);
            specificFormPunish.setParameter("PUNISH_ID");
            specificFormPunish.setParamValue(String.valueOf(oaBaseObject.getPunishId()));
            specificForms.add(specificFormPunish);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getPunishIdHis()) || !"null".equals(oaBaseObject.getPunishIdHis())) {
            AtiSpecificForm specificFormPunish = getSpecificForm(oaBaseObject);
            specificFormPunish.setParameter("PUNISH_ID_HIS");
            specificFormPunish.setParamValue(String.valueOf(oaBaseObject.getPunishIdHis()));
            specificForms.add(specificFormPunish);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getTalentTeamId()) || !"null".equals(oaBaseObject.getTalentTeamId())) {
            AtiSpecificForm specificFormTalentTeam = getSpecificForm(oaBaseObject);
            specificFormTalentTeam.setParameter("TALENT_TEAM_ID");
            specificFormTalentTeam.setParamValue(String.valueOf(oaBaseObject.getTalentTeamId()));
            specificForms.add(specificFormTalentTeam);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getTalentTeamIdHis()) || !"null".equals(oaBaseObject.getTalentTeamIdHis())) {
            AtiSpecificForm specificFormTalentTeam = getSpecificForm(oaBaseObject);
            specificFormTalentTeam.setParameter("TALENT_TEAM_ID_HIS");
            specificFormTalentTeam.setParamValue(String.valueOf(oaBaseObject.getTalentTeamIdHis()));
            specificForms.add(specificFormTalentTeam);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getEduId()) || !"null".equals(oaBaseObject.getEduId())) {
            AtiSpecificForm specificFormEdu = getSpecificForm(oaBaseObject);
            specificFormEdu.setParameter("EDU_ID");
            specificFormEdu.setParamValue(String.valueOf(oaBaseObject.getEduId()));
            specificForms.add(specificFormEdu);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getEduIdHis()) || !"null".equals(oaBaseObject.getEduIdHis())) {
            AtiSpecificForm specificFormEdu = getSpecificForm(oaBaseObject);
            specificFormEdu.setParameter("EDU_ID_HIS");
            specificFormEdu.setParamValue(String.valueOf(oaBaseObject.getEduIdHis()));
            specificForms.add(specificFormEdu);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getFamilyId()) || !"null".equals(oaBaseObject.getFamilyId())) {
            AtiSpecificForm specificFormFamily = getSpecificForm(oaBaseObject);
            specificFormFamily.setParameter("FAMILY_ID");
            specificFormFamily.setParamValue(String.valueOf(oaBaseObject.getFamilyId()));
            specificForms.add(specificFormFamily);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getFamilyIdHis()) || !"null".equals(oaBaseObject.getFamilyIdHis())) {
            AtiSpecificForm specificFormFamily = getSpecificForm(oaBaseObject);
            specificFormFamily.setParameter("FAMILY_ID_HIS");
            specificFormFamily.setParamValue(String.valueOf(oaBaseObject.getFamilyIdHis()));
            specificForms.add(specificFormFamily);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getTitleId()) || !"null".equals(oaBaseObject.getTitleId())) {
            AtiSpecificForm specificFormTitle = getSpecificForm(oaBaseObject);
            specificFormTitle.setParameter("TITLE_ID");
            specificFormTitle.setParamValue(String.valueOf(oaBaseObject.getTitleId()));
            specificForms.add(specificFormTitle);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getTitleIdHis()) || !"null".equals(oaBaseObject.getTitleIdHis())) {
            AtiSpecificForm specificFormTitle = getSpecificForm(oaBaseObject);
            specificFormTitle.setParameter("TITLE_ID_HIS");
            specificFormTitle.setParamValue(String.valueOf(oaBaseObject.getTitleIdHis()));
            specificForms.add(specificFormTitle);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getPartyLogId()) || !"null".equals(oaBaseObject.getPartyLogId())) {
            AtiSpecificForm specificFormPartyLog = getSpecificForm(oaBaseObject);
            specificFormPartyLog.setParameter("PARTY_LOG_ID");
            specificFormPartyLog.setParamValue(String.valueOf(oaBaseObject.getPartyLogId()));
            specificForms.add(specificFormPartyLog);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getPartyLogIdHis()) || !"null".equals(oaBaseObject.getPartyLogIdHis())) {
            AtiSpecificForm specificFormPartyLog = getSpecificForm(oaBaseObject);
            specificFormPartyLog.setParameter("PARTY_LOG_ID_HIS");
            specificFormPartyLog.setParamValue(String.valueOf(oaBaseObject.getPartyLogIdHis()));
            specificForms.add(specificFormPartyLog);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getWadocIdAdd()) || !"null".equals(oaBaseObject.getWadocIdAdd())) {
            AtiSpecificForm specificFormWaDoc = getSpecificForm(oaBaseObject);
            specificFormWaDoc.setParameter("WADOC_ID_ADD");
            specificFormWaDoc.setParamValue(String.valueOf(oaBaseObject.getWadocIdAdd()));
            specificForms.add(specificFormWaDoc);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getWadocIdModify()) || !"null".equals(oaBaseObject.getWadocIdModify())) {
            AtiSpecificForm specificFormWaDoc = getSpecificForm(oaBaseObject);
            specificFormWaDoc.setParameter("WADOC_ID_MODIFY");
            specificFormWaDoc.setParamValue(String.valueOf(oaBaseObject.getWadocIdModify()));
            specificForms.add(specificFormWaDoc);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getWadocIdHis()) || !"null".equals(oaBaseObject.getWadocIdHis())) {
            AtiSpecificForm specificFormWaDoc = getSpecificForm(oaBaseObject);
            specificFormWaDoc.setParameter("WADOC_ID_HIS");
            specificFormWaDoc.setParamValue(String.valueOf(oaBaseObject.getWadocIdHis()));
            specificForms.add(specificFormWaDoc);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getAssId()) || !"null".equals(oaBaseObject.getAssId())) {
            AtiSpecificForm specificFormAss = getSpecificForm(oaBaseObject);
            specificFormAss.setParameter("ASS_ID");
            specificFormAss.setParamValue(oaBaseObject.getAssId());
            specificForms.add(specificFormAss);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getAssIdHis()) || !"null".equals(oaBaseObject.getAssIdHis())) {
            AtiSpecificForm specificFormAss = getSpecificForm(oaBaseObject);
            specificFormAss.setParameter("ASS_ID_HIS");
            specificFormAss.setParamValue(oaBaseObject.getAssIdHis());
            specificForms.add(specificFormAss);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getNationdutyId()) || !"null".equals(oaBaseObject.getNationdutyId())) {
            AtiSpecificForm specificFormNationduty = getSpecificForm(oaBaseObject);
            specificFormNationduty.setParameter("NATIONDUTY_ID");
            specificFormNationduty.setParamValue(oaBaseObject.getNationdutyId());
            specificForms.add(specificFormNationduty);
        }
        if (StringUtils.isNotEmpty(oaBaseObject.getNationdutyIdHis()) || !"null".equals(oaBaseObject.getNationdutyIdHis())) {
            AtiSpecificForm specificFormNationdutyHis = getSpecificForm(oaBaseObject);
            specificFormNationdutyHis.setParameter("NATIONDUTY_ID_HIS");
            specificFormNationdutyHis.setParamValue(oaBaseObject.getNationdutyIdHis());
            specificForms.add(specificFormNationdutyHis);
        }

        AtiSpecificForm specificFormPrimaryKeyHis = getSpecificForm(oaBaseObject);
        specificFormPrimaryKeyHis.setParameter("PRIMARY_ID_HIS");
        specificFormPrimaryKeyHis.setParamValue(String.valueOf(oaBaseObject.getPrimaryIdHis()));
        specificForms.add(specificFormPrimaryKeyHis);

        AtiSpecificForm specificFormFirstText = getSpecificForm(oaBaseObject);
        specificFormFirstText.setParameter("FIRST_TEXT");
        specificFormFirstText.setParamValue(oaBaseObject.getFifthText());
        specificForms.add(specificFormFirstText);

        AtiSpecificForm specificFormSecondText = getSpecificForm(oaBaseObject);
        specificFormSecondText.setParameter("SECOND_TEXT");
        specificFormSecondText.setParamValue(oaBaseObject.getSecondText());
        specificForms.add(specificFormSecondText);

        AtiSpecificForm specificFormThirdText = getSpecificForm(oaBaseObject);
        specificFormThirdText.setParameter("THIRD_TEXT");
        specificFormThirdText.setParamValue(oaBaseObject.getThirdText());
        specificForms.add(specificFormThirdText);

        AtiSpecificForm specificFormFourthText = getSpecificForm(oaBaseObject);
        specificFormFourthText.setParameter("FOURTH_TEXT");
        specificFormFourthText.setParamValue(oaBaseObject.getFourthText());
        specificForms.add(specificFormFourthText);

        AtiSpecificForm specificFormFifthText = getSpecificForm(oaBaseObject);
        specificFormFifthText.setParameter("FIFTH_TEXT");
        specificFormFifthText.setParamValue(oaBaseObject.getFifthText());
        specificForms.add(specificFormFifthText);

        return specificForms;
    }

    private AtiSpecificForm getSpecificForm(UeccVo ueccVo) {
        AtiSpecificForm atiSpecificForm = new AtiSpecificForm();
        atiSpecificForm.setAtiSpecificFormId(ueccVo.getAtiSpecificFormId());
        atiSpecificForm.setAtiBaseFormId(ueccVo.getAtiBaseFormId());
        atiSpecificForm.setAtiActCategoryId(String.valueOf(ueccVo.getAtiActCategoryId()));
        return atiSpecificForm;
    }
}
