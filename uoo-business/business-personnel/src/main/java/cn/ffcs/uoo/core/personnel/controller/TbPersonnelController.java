package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.personnel.annotion.SendMqMsg;
import cn.ffcs.uoo.core.personnel.client.OrgPersonRelClient;
import cn.ffcs.uoo.core.personnel.client.UserClient;
import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.entity.*;
import cn.ffcs.uoo.core.personnel.exception.PersonnelException;
import cn.ffcs.uoo.core.personnel.service.*;
import cn.ffcs.uoo.core.personnel.util.*;
import cn.ffcs.uoo.core.personnel.vo.*;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static cn.ffcs.uoo.core.personnel.util.IdCardVerification.IDCardValidate;
import static cn.ffcs.uoo.core.personnel.util.IdCardVerification.idCardValidate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-09-06
 */
@Api(description="人员",value="Personnel")
@RestController
@RequestMapping("/personnel")
public class TbPersonnelController extends BaseController {
    @Resource
    private TbPersonnelService tbPersonnelService;
    @Autowired
    private TbCertService tbCertService;
    @Autowired
    private TbContactService tbContactService;
    @Autowired
    private TbPsnjobService tbPsnjobService;
    @Autowired
    private TbEduService tbEduService;
    @Autowired
    private TbFamilyService tbFamilyService;
    @Autowired
    private TbPersonnelImageService tbPersonnelImageService;
    @Autowired
    private OrgPersonRelClient orgPersonRelClient;
    @Autowired
    private UserClient userClient;



    @ApiOperation(value = "人员查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "人员查询", key = "getPersonnelPage")
    @RequestMapping(value = "/getPage/pageNo={pageNo}&pageSize={pageSize}",method = RequestMethod.GET)
    public Object getPersonnel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {

        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 20 : pageSize;
        Page<TbPersonnel> page = tbPersonnelService.selectPage(new Page<TbPersonnel>(pageNo, pageSize));

        return ResultUtils.success(page);
    }

    @ApiOperation(value = "人员条件查询", notes = "条件分页查询")
    @ApiImplicitParam(name = "tbPersonnelVo", value = "人员条件VO", required = true, dataType = "TbPersonnelVo")
    @UooLog(value = "人员条件查询", key = "getPersonnelCondition")
    @RequestMapping(value = "/getPage/", method = RequestMethod.POST)
    public Object getPersonnelCondition(@RequestBody TbPersonnelVo tbPersonnelVo) {

        int pageSize = tbPersonnelVo.getPageSize() == 0 ? 12 : tbPersonnelVo.getPageSize();
        Wrapper wrapper= Condition.create().eq(StringUtils.isNotEmpty(tbPersonnelVo.getPsnNbr()),"PSN_NBR", tbPersonnelVo.getPsnNbr());
        Page<TbPersonnel> page = tbPersonnelService.selectPage(new Page<TbPersonnel>(tbPersonnelVo.getPageNo(), pageSize), wrapper);

        return ResultUtils.success(page);
    }

    @ApiOperation(value = "人员综合信息查询", notes = "人员综合信息分页")
    @ApiImplicitParam(name = "tbPersonnelVo",value = "人员查询条件",required = true,dataType = "TbPersonnelVo")
    @UooLog(value = "人员综合信息查询",key = "getPersonnelRelationInfo")
    @RequestMapping(value = "/allInfoPage",method = RequestMethod.POST)
    public Page<PersonnelRelationInfoVo> getPersonnelRelationInfo(TbPersonnelVo tbPersonnelVo) {
        return tbPersonnelService.getPersonnelRelationInfo(tbPersonnelVo);
    }

    @ApiOperation(value = "根据组织查询人员", notes = "根据组织查询人员信息分页")
    @ApiImplicitParam(name = "tbPersonnelVo",value = "人员查询条件",required = true,dataType = "TbPersonnelVo")
    @UooLog(value = "根据组织查询人员",key = "getPersonnelOrg")
    @RequestMapping(value = "/getPersonnelOrg",method = RequestMethod.POST)
    public Object getPersonnelOrg(TbPersonnelVo tbPersonnelVo){
        Page<PersonnelOrgVo> page = tbPersonnelService.getPersonnelOrg(tbPersonnelVo);
        return ResultUtils.success(page);
    }

    @ApiOperation(value = "人员信息", notes = "查看人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "orgTreeId", value = "业务树标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long",paramType="path")
    })
    @UooLog(value = "查看人员信息", key = "getFormPersonnel")
    @RequestMapping(value = "/getFormPersonnel",method = RequestMethod.GET)
    public Object getFormPersonnel(Long personnelId,
                                   Long orgTreeId,
                                   Long orgId){
        FormPersonnelVo formPersonnelVo = new FormPersonnelVo();
        /**  1、基本信息    */
        TbPersonnel tbPersonnel = tbPersonnelService.selectById(personnelId);
        if(StrUtil.isNullOrEmpty(tbPersonnel)){
            return ResultUtils.error(EumPersonnelResponseCode.PSN_NOT_EXIST);
        }
        BeanUtils.copyProperties(tbPersonnel, formPersonnelVo);

        //身份证
        TbCert tbCert = tbCertService.getTbCertByPersonnelId(Long.valueOf(personnelId));
        formPersonnelVo.setCertType(tbCert.getCertType());
        formPersonnelVo.setCertNo(tbCert.getCertNo());
        formPersonnelVo.setAddress(tbCert.getAddress());

        /**  2、联系信息    */
        List<TbContact> tbMobileList = tbContactService.getTbContactByPsnId(Long.valueOf(personnelId), "1");
        List<TbContact> tbEamilList = tbContactService.getTbContactByPsnId(Long.valueOf(personnelId), "2");
        formPersonnelVo.setTbMobileVoList(tbMobileList);
        formPersonnelVo.setTbEamilVoList(tbEamilList);

        /**  3、归属组织信息 */

        ResponseResult<Page<PsonOrgVo>> obj = orgPersonRelClient.getPerOrgRelPage(orgId.intValue(), orgTreeId, personnelId.intValue(), "", null, null);

        if(BaseUnitConstants.ENTT_STATE_ACTIVE.equals(String.valueOf(obj.getState()))
                && !StrUtil.isNullOrEmpty(obj.getData())){
            formPersonnelVo.setPsonOrgVoList(obj.getData());
        }

        /**  4、工作履历信息 */
        Page<TbPsnjobVo> tbPsnjobPage = tbPsnjobService.getPsnjobPageBypsnId(personnelId, 0, 0);
        formPersonnelVo.setTbPsnjobVoList(tbPsnjobPage);

        /**  5、教育信息 */
        Page<TbEdu> tbEduPage = tbEduService.getTbEduPage(personnelId);
        formPersonnelVo.setTbEduVoList(tbEduPage);

        /**  6、家庭成员信息 */
        Page<TbFamily> tbFamilyPage = tbFamilyService.getTbFamilyPage(personnelId);
        formPersonnelVo.setTbFamilyVoList(tbFamilyPage);

        return ResultUtils.success(formPersonnelVo);
    }

    @ApiOperation(value = "新增人员信息",notes = "人员信息新增")
    @ApiImplicitParam(name = "editFormPersonnelVo",value = "人员信息",required = true,dataType = "EditFormPersonnelVo")
    @UooLog(value = "新增人员信息",key = "savePersonnel")
    @RequestMapping(value = "/savePersonnel",method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object savePersonnel(@RequestBody EditFormPersonnelVo editFormPersonnelVo) {
        /**  人员表单信息验证 */
        Object reObj = checkFormPersonnel(editFormPersonnelVo);
        if(!StrUtil.isNullOrEmpty(reObj)){
            return reObj;
        }
        TbPersonnel tbPersonnel = new TbPersonnel();
        BeanUtils.copyProperties(editFormPersonnelVo, tbPersonnel);

        Long personnelId = tbPersonnelService.getId();
        tbPersonnel.setPersonnelId(personnelId);
        tbPersonnel.setGender(IDCardUtil.getGender(editFormPersonnelVo.getCertNo()));
        tbPersonnelService.insertOrUpdateTbPsn(tbPersonnel);

        /**  2、证件           */
        tbCertService.insertOrUpdateTbCert(personnelId, editFormPersonnelVo.getCertType(),editFormPersonnelVo.getCertNo(),
                editFormPersonnelVo.getPsnName(), editFormPersonnelVo.getAddress());

        /**  2、组织信息           */
        List<PsonOrgVo> psonOrgVoList = editFormPersonnelVo.getPsonOrgVoList();
        List<PsonOrgVo> psonOrgVos = new ArrayList<PsonOrgVo>();
        for(PsonOrgVo psonOrgVo : psonOrgVoList){
            psonOrgVo.setPersonnelId(personnelId);
            psonOrgVos.add(psonOrgVo);
        }
        if(psonOrgVos != null && psonOrgVos.size() > 0){
            orgPersonRelClient.addOrgPsn(psonOrgVos);
        }

        /**  3、联系方式           */
        List<TbContact> tbContactList = editFormPersonnelVo.getTbMobileVoList();
        tbContactList.addAll(editFormPersonnelVo.getTbEamilVoList());
        if(tbContactList != null && tbContactList.size() > 0){
            for(TbContact tbContact : tbContactList){
                tbContact.setContactId(tbContactService.getId());
                tbContact.setPersonnelId(personnelId);
                tbContactService.insert(tbContact);
            }
        }

        /**  4、工作履历信息       */
        List<TbPsnjob> tbPsnjobList = editFormPersonnelVo.getTbPsnjobList();
        if(tbPsnjobList != null && tbPsnjobList.size() > 0){
            for(TbPsnjob tbPsnjob : tbPsnjobList){
                tbPsnjob.setPsnjobId(tbPsnjobService.getId());
                tbPsnjob.setPersonnelId(personnelId);
                tbPsnjobService.insert(tbPsnjob);
            }
        }

        /**  5、教育信息           */
        List<TbEdu> tbEduList = editFormPersonnelVo.getTbEduList();
        if(tbEduList != null && tbEduList.size() > 0 ){
            for(TbEdu tbEdu : tbEduList){
                tbEdu.setEduId(tbEduService.getId());
                tbEdu.setPersonnelId(personnelId);
                tbEduService.insert(tbEdu);
            }
        }

        /**  6、家庭成员信息      */
        List<TbFamily> tbFamilyList = editFormPersonnelVo.getTbFamilyList();
        if(tbFamilyList != null && tbFamilyList.size() > 0 ){
            for(TbFamily tbFamily : tbFamilyList){
                tbFamily.setFamilyId(tbFamilyService.getId());
                tbFamily.setPersonnelId(personnelId);
                tbFamilyService.insert(tbFamily);
            }
        }

        return ResultUtils.success(personnelId);

    }

    @ApiOperation(value = "修改人员基本信息",notes = "人员基本信息修改")
    @ApiImplicitParam(name = "personnelVo",value = "人员基本信息",required = true,dataType = "PersonnelVo")
    @UooLog(value = "修改人员信息",key = "updatePersonnel")
    @SendMqMsg(type = "person", handle ="update", column ="personnelId")
    @RequestMapping(value = "updatePersonnel",method = RequestMethod.PUT)
    public Object upPersonnel(@RequestBody PersonnelVo personnelVo){
        EditFormPersonnelVo editFormPersonnelVo = new EditFormPersonnelVo();
        BeanUtils.copyProperties(personnelVo, editFormPersonnelVo);
        /**  人员表单信息验证 */
        Object reObj = checkFormPersonnel(editFormPersonnelVo);
        if(!StrUtil.isNullOrEmpty(reObj)){
            return reObj;
        }

        TbPersonnel tbPersonnel = new TbPersonnel();
        BeanUtils.copyProperties(personnelVo, tbPersonnel);
        tbPersonnel.setGender(IDCardUtil.getGender(editFormPersonnelVo.getCertNo()));

        tbPersonnelService.insertOrUpdateTbPsn(tbPersonnel);

        /**  2、证件           */
        tbCertService.insertOrUpdateTbCert(personnelVo.getPersonnelId(), personnelVo.getCertType(),personnelVo.getCertNo(),
                personnelVo.getPsnName(), personnelVo.getAddress());

        /**  3、联系方式           */
        tbContactService.addOrUpdateTbContact(personnelVo.getTbMobileVoList(), editFormPersonnelVo.getPersonnelId(), "1");
        tbContactService.addOrUpdateTbContact(personnelVo.getTbEamilVoList(), editFormPersonnelVo.getPersonnelId(), "2");

//        if(tbPersonnelService.isExistsAcct(editFormPersonnelVo.getPersonnelId())){
//            return ResultUtils.success(editFormPersonnelVo.getPersonnelId());
//        }
        return ResultUtils.success(editFormPersonnelVo.getPersonnelId());
    }



    @ApiOperation(value = "修改人员信息",notes = "人员信息修改")
    @ApiImplicitParam(name = "editFormPersonnelVo",value = "人员信息",required = true,dataType = "EditFormPersonnelVo")
    @UooLog(value = "修改人员信息",key = "updateAllPersonnel")
    @RequestMapping(value = "updateAllPersonnel",method = RequestMethod.PUT)
    public Object updatePersonnel(@RequestBody EditFormPersonnelVo editFormPersonnelVo) {

        /**  人员表单信息验证 */
        Object reObj = checkFormPersonnel(editFormPersonnelVo);
        if(!StrUtil.isNullOrEmpty(reObj)){
            return reObj;
        }



        TbPersonnel tbPersonnel = new TbPersonnel();
        BeanUtils.copyProperties(editFormPersonnelVo, tbPersonnel);
        tbPersonnel.setGender(IDCardUtil.getGender(editFormPersonnelVo.getCertNo()));
        tbPersonnel.updateById();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TBCERT_CERT_NO, editFormPersonnelVo.getCertNo());
        TbCert tbCert = tbCertService.selectOne(new EntityWrapper<TbCert>().allEq(map));
        tbCert.setCertType(editFormPersonnelVo.getCertType());
        tbCert.setCertNo(editFormPersonnelVo.getCertNo());
        tbCert.setCertName(editFormPersonnelVo.getPsnName());
        tbCertService.updateAllColumnById(tbCert);

        /**  2、组织信息           */

        /**  3、联系方式           */
        List<TbContact> tbContactList = editFormPersonnelVo.getTbMobileVoList();
        tbContactList.addAll(editFormPersonnelVo.getTbEamilVoList());
        if(tbContactList != null && tbContactList.size() > 0){
            for(TbContact tbContact : tbContactList){
                if(!StrUtil.isNullOrEmpty(tbContact.getContactId())){
                    tbContactService.updateById(tbContact);
                }else{
                    tbContact.setContactId(tbContactService.getId());
                    tbContact.setPersonnelId(editFormPersonnelVo.getPersonnelId());
                    tbContactService.insert(tbContact);
                }
            }
        }

        /**  4、工作履历信息       */
        List<TbPsnjob> tbPsnjobList = editFormPersonnelVo.getTbPsnjobList();
        if(tbPsnjobList != null && tbPsnjobList.size() > 0){
            for(TbPsnjob tbPsnjob : tbPsnjobList){
                if(!StrUtil.isNullOrEmpty(tbPsnjob.getPsnjobId())){
                    tbPsnjobService.updateById(tbPsnjob);
                }else{
                    tbPsnjob.setPsnjobId(tbPsnjobService.getId());
                    tbPsnjob.setPersonnelId(editFormPersonnelVo.getPersonnelId());
                    tbPsnjobService.insert(tbPsnjob);
                }
            }
        }

        /**  5、教育信息           */
        List<TbEdu> tbEduList = editFormPersonnelVo.getTbEduList();
        if(tbEduList != null && tbEduList.size() > 0 ){
            for(TbEdu tbEdu : tbEduList){
                if(!StrUtil.isNullOrEmpty(tbEdu.getEduId())){
                    tbEduService.updateById(tbEdu);
                }else{
                    tbEdu.setEduId(tbEduService.getId());
                    tbEdu.setPersonnelId(editFormPersonnelVo.getPersonnelId());
                    tbEduService.insert(tbEdu);
                }
            }
        }

        /**  6、家庭成员信息      */
        List<TbFamily> tbFamilyList = editFormPersonnelVo.getTbFamilyList();
        if(tbFamilyList != null && tbFamilyList.size() > 0 ){
            for(TbFamily tbFamily : tbFamilyList){
                if(!StrUtil.isNullOrEmpty(tbFamily.getFamilyId())){
                    tbFamilyService.updateById(tbFamily);
                }else{
                    tbFamily.setFamilyId(tbFamilyService.getId());
                    tbFamily.setPersonnelId(editFormPersonnelVo.getPersonnelId());
                    tbFamilyService.insert(tbFamily);
                }
            }
        }
        return ResultUtils.success(null);
    }

    @ApiOperation(value="删除人员信息",notes="人员信息删除")
    @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path")
    @UooLog(value = "删除人员信息",key = "deletePersonnel")
    @SendMqMsg(type = "person", handle ="delete", column ="personnelId")
    @RequestMapping(value="/deletePersonnel",method = RequestMethod.DELETE)
    public Object deletePersonnel(Long personnelId) {
        tbPersonnelService.delTbPersonnelByPsnId(personnelId);

        // 根据personnelId 删除tbCert
        tbCertService.delTbCertByPsnId(personnelId);

        /**  3、联系方式           */
        // 根据id删除tbContact
        tbContactService.delTbContactByPsnId(personnelId);

        /**  4、工作履历信息       */
        tbPsnjobService.delTbPsnjobByPsnId(personnelId);

        /**  5、教育信息           */
        tbEduService.delTbEduByPsnId(personnelId);

        /**  6、家庭成员信息      */
        tbFamilyService.delTbFamilyByPsnId(personnelId);

        /**  7、家庭成员信息      */
        tbPersonnelImageService.delTbPersonnelImageByPsnId(personnelId);

        /**  8、删除人员组织关系      */
        PsonOrgVo psonOrgVo = new PsonOrgVo();
        psonOrgVo.setPersonnelId(personnelId);
        orgPersonRelClient.deletePsnRel(psonOrgVo);
        /**  9、删除账号相关信息      */
        userClient.removeAcct(personnelId);
        return ResultUtils.success(personnelId);
    }



    //@ApiOperation(value="图片上传",notes="图片上传")
    //@UooLog(value = "图片上传",key = "uploadImg")
    //@RequestMapping(value="/uploadImg")
    public Object uploadImg(@RequestParam("editormd-image-file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new PersonnelException(EumPersonnelResponseCode.IMG_NOT_EMPTY);
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            throw new PersonnelException(EumPersonnelResponseCode.IMG_FORMAT_ERROR);
        }
        //String root_fileName = multipartFile.getOriginalFilename();
        //处理图片
        String filePath = "";
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()){
                path = new File("");
            System.out.println("path:"+path.getAbsolutePath());
            }

            //如果上传目录为/static/images/upload/，则可以如下获取：
            File upload = new File(path.getAbsolutePath(),"static/images/upload/");
            if(!upload.exists()) {
                upload.mkdirs();
                filePath = upload.getAbsolutePath();
                System.out.println("upload url:" + upload.getAbsolutePath());
            }
            //在开发测试模式时，得到的地址为：{项目跟目录}/target/static/images/upload/
            //在打包成jar正式发布时，得到的地址为：{发布jar包目录}/static/images/upload/
        }catch (Exception e){

        }

        //logger.info("图片保存路径={}", filePath);
        String file_name = null;
        try {
            file_name = ImageUtil.saveImg(multipartFile, filePath);
            UpLoadImgVo upLoadImgVo = new UpLoadImgVo();
            if(StringUtils.isNotBlank(file_name)){
                upLoadImgVo.setImage(file_name);
                upLoadImgVo.setImgUrl(filePath + File.separator + file_name);
            }
            //logger.info("返回值：{}",markDVo);
            return ResultUtils.success(upLoadImgVo);
        } catch (IOException e) {
            throw new PersonnelException(EumPersonnelResponseCode.SAVE_IMG_ERROE);
        }
    }

    /**
     *  人员信息 校验
     * @param editFormPersonnelVo
     * @return
     */
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
                }else{
                    Map<String, Object> phoneMap = new HashMap<String, Object>();
                    phoneMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
                    phoneMap.put(BaseUnitConstants.TBCONTACT_CONTENT, tbContact.getContent());
                    phoneMap.put(BaseUnitConstants.TBCONTACT_CONTACT_TYPE, "1");
                    TbContact tbContact1 = tbContactService.selectOne(new EntityWrapper<TbContact>().allEq(phoneMap));
                    if((StrUtil.isNullOrEmpty(editFormPersonnelVo.getPersonnelId()) && !StrUtil.isNullOrEmpty(tbContact1))
                            || (!StrUtil.isNullOrEmpty(tbContact1) && !tbContact1.getPersonnelId().equals(editFormPersonnelVo.getPersonnelId()))){
                        Map<String, Object> psnMap = new HashMap<String, Object>();
                        psnMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
                        psnMap.put(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, tbContact1.getPersonnelId());
                        TbPersonnel tbPersonnel = tbPersonnelService.selectOne(new EntityWrapper<TbPersonnel>().allEq(psnMap));
                        return ResultUtils.error(EumPersonnelResponseCode.MOBILE_IS_EXIST.getState(), "手机号已被【" + tbPersonnel.getPsnName() + "】使用");
                    }
                }
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
            TbPersonnel tbPersonnel = tbPersonnelService.selectOne(new EntityWrapper<TbPersonnel>().allEq(map));
            return ResultUtils.error(EumPersonnelResponseCode.CERT_IS_EXIST.getState(), "证件号已被【" + tbPersonnel.getPsnName() + "】使用");
        }
        return null;
    }

    public Object checkRepeat(List<TbContact> tbContactList, EumPersonnelResponseCode responseCode){
        HashSet<String> hashSet = new HashSet<>();
        for(TbContact tbContact : tbContactList){
            if(!hashSet.add(tbContact.getContent())){
                return ResultUtils.error(responseCode);
            }
        }
        return null;
    }

    @ApiOperation(value = "用户对应人员基本信息", notes = "用户对应人员基本信息")
    @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "String",paramType="path")
    @UooLog(value = "查看人员信息", key = "getPsnByUser")
    @RequestMapping(value = "/getPsnByUser",method = RequestMethod.GET)
    public Object getPsnByUser(String personnelId){
        PsnByUserVo psnByUserVo = new PsnByUserVo();
        psnByUserVo.setPersonnelId(Long.valueOf(personnelId));
        psnByUserVo = tbPersonnelService.getPsnByUser(psnByUserVo);
        return ResultUtils.success(psnByUserVo);
    }

    @ApiOperation(value="人员选择查询",notes="人员选择查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键字", required = true, dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path")
    })
    @UooLog(value = "人员选择查询",key = "getPsnBasicInfo")
    @RequestMapping(value="/getPsnBasicInfo",method = RequestMethod.GET)
    public Object getPsnBasicInfo(String keyWord, Integer pageNo, Integer pageSize){
        return ResultUtils.success(tbPersonnelService.getPsnBasicInfo(keyWord, pageNo, pageSize));
    }

    @ApiOperation(value = "身份证对应信息", notes = "身份证对应信息")
    @ApiImplicitParam(name = "certNo", value = "身份证号", required = true, dataType = "String",paramType="path")
    @UooLog(value = "身份证对应信息", key = "getIdCardInfo")
    @RequestMapping(value = "/getIdCardInfo",method = RequestMethod.GET)
    public Object getIdCardInfo(String certNo){
        return IdCardVerification.parseCertificateNo(certNo);
    }

    @ApiOperation(value="游离人员选择查询",notes="游离人员选择查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键字", required = true, dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path")
    })
    @UooLog(value = "游离人员选择查询",key = "getPsnBasicInfo")
    @RequestMapping(value="/getFreePsnInfo",method = RequestMethod.GET)
    public Object getFreePsnInfo(String keyWord, Integer pageNo, Integer pageSize){
        return ResultUtils.success(tbPersonnelService.getFreePsnInfo(keyWord, pageNo, pageSize));
    }

    @ApiOperation(value = "身份证对应人力编码", notes = "身份证对应人力编码")
    @ApiImplicitParam(name = "certNo", value = "身份证号", required = true, dataType = "String",paramType="path")
    @UooLog(value = "身份证对应人力编码", key = "getIdCardNcCode")
    @RequestMapping(value = "/getIdCardNcCode",method = RequestMethod.GET)
    public Object getIdCardNcCode(String certNo){
        if(StrUtil.isNullOrEmpty(certNo)){
            return ResultUtils.error(EumPersonnelResponseCode.CERT_NO_IS_NULL);
        }
        return ResultUtils.success(tbPersonnelService.getIdCardNcCode(certNo));
    }

}

