package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.entity.*;
import cn.ffcs.uoo.core.personnel.exception.PersonnelException;
import cn.ffcs.uoo.core.personnel.service.*;
import cn.ffcs.uoo.core.personnel.util.IDCardUtil;
import cn.ffcs.uoo.core.personnel.util.ImageUtil;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import cn.ffcs.uoo.core.personnel.util.StrUtil;
import cn.ffcs.uoo.core.personnel.vo.*;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            @ApiImplicitParam(name = "orgRootId", value = "业务树标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long",paramType="path"),
    })

    @UooLog(value = "查看人员信息", key = "getFormPersonnel")
    @RequestMapping(value = "/getFormPersonnel",method = RequestMethod.GET)
    public Object getFormPersonnel(Long personnelId,
                                   Long orgRootId,
                                   Long orgId){
        FormPersonnelVo formPersonnelVo = new FormPersonnelVo();
        /**  1、基本信息    */
        TbPersonnel tbPersonnel = tbPersonnelService.selectById(personnelId);
        BeanUtils.copyProperties(tbPersonnel, formPersonnelVo);

        //身份证
        TbCert tbCert = tbCertService.getTbCertByPersonnelId(Long.valueOf(personnelId));
        formPersonnelVo.setCertType(tbCert.getCertType());
        formPersonnelVo.setCertNo(tbCert.getCertNo());

        /**  2、联系信息    */
        List<TbContact> tbMobileList = tbContactService.getTbContactByPsnId(Long.valueOf(personnelId), "1");
        List<TbContact> tbEamilList = tbContactService.getTbContactByPsnId(Long.valueOf(personnelId), "2");
        formPersonnelVo.setTbMobileVoList(tbMobileList);
        formPersonnelVo.setTbEamilVoList(tbEamilList);

        /**  3、归属组织信息 (需要调用uoo-organization)*/
        PsonOrgVo psonOrgVo = new PsonOrgVo();
        psonOrgVo.setOrgId(Long.valueOf(orgId));
        psonOrgVo.setOrgRootId(Long.valueOf(orgRootId));
        psonOrgVo.setPersonId(Long.valueOf(personnelId));
        Page<PsonOrgVo> psonOrgVoPage = tbPersonnelService.selectPsonOrgPage(psonOrgVo);
        if(psonOrgVoPage.getRecords().size() > 0){
            formPersonnelVo.setPsonOrgVoList(psonOrgVoPage);
        }

        /**  4、工作履历信息 */
        Wrapper tbPsnjobwrapper= Condition.create().eq(true,"PERSONNEL_ID", personnelId);
        Page<TbPsnjobVo> tbPsnjobPage = tbPsnjobService.selectPage(new Page<TbPsnjob>(0, 12), tbPsnjobwrapper);
        formPersonnelVo.setTbPsnjobVoList(tbPsnjobPage);

        /**  5、教育信息 */
        Wrapper tbEduwrapper= Condition.create().eq(true,"PERSONNEL_ID", personnelId);
        Page<TbEduVo> tbEduPage = tbEduService.selectPage(new Page<TbEdu>(0, 12), tbEduwrapper);
        formPersonnelVo.setTbEduVoList(tbEduPage);

        /**  6、家庭成员信息 */
        Wrapper tbFamilywrapper= Condition.create().eq(true,"PERSONNEL_ID", personnelId);
        Page<TbFamilyVo> tbFamilyPage = tbFamilyService.selectPage(new Page<TbFamily>(0, 12), tbFamilywrapper);
        formPersonnelVo.setTbFamilyVoList(tbFamilyPage);

        return ResultUtils.success(formPersonnelVo);
    }

    @ApiOperation(value = "新增人员信息",notes = "人员信息新增")
    @ApiImplicitParam(name = "editFormPersonnelVo",value = "人员信息",required = true,dataType = "EditFormPersonnelVo")
    @UooLog(value = "新增人员信息",key = "savePersonnel")
    @RequestMapping(value = "/savePersonnel",method = RequestMethod.POST)
    public Object savePersonnel(@RequestBody EditFormPersonnelVo editFormPersonnelVo) {
        /**  人员表单信息验证 */
        Object reObj = checkFormPersonnel(editFormPersonnelVo);
        if(!StrUtil.isNullOrEmpty(reObj)){
            return reObj;
        }

        /**
         *  1、选择人员
         *  2、直接填（人员已存在）
         *  3、直接填（人员不存在）
         */
        if(StrUtil.isNullOrEmpty(editFormPersonnelVo.getPersonnelId())){
            Map<String, Object> tbCertMap = new HashMap<String, Object>();
            tbCertMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
            tbCertMap.put(BaseUnitConstants.TBCERT_CERT_NO, editFormPersonnelVo.getCertNo());
            TbCert tbCert = tbCertService.selectOne(new EntityWrapper<TbCert>().allEq(tbCertMap));
            if(StrUtil.isNullOrEmpty(tbCert)){
                return addAllPersonnel(editFormPersonnelVo);
            }else{
                editFormPersonnelVo.setPersonnelId(tbCert.getPersonnelId());
                return updatePersonnel(editFormPersonnelVo);
            }
        }else{
            return updatePersonnel(editFormPersonnelVo);
        }



    }

    @ApiOperation(value = "修改人员基本信息",notes = "人员基本信息修改")
    @ApiImplicitParam(name = "personnelVo",value = "人员基本信息",required = true,dataType = "PersonnelVo")
    @UooLog(value = "修改人员信息",key = "updatePersonnel")
    @RequestMapping(value = "updatePersonnel",method = RequestMethod.PUT)
    public Object upPersonnel(PersonnelVo personnelVo){
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
        tbPersonnel.updateById();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TBCERT_CERT_NO, personnelVo.getCertNo());
        TbCert tbCert = tbCertService.selectOne(new EntityWrapper<TbCert>().allEq(map));
        tbCert.setCertType(personnelVo.getCertType());
        tbCert.setCertNo(personnelVo.getCertNo());
        tbCert.setCertName(personnelVo.getPsnName());
        tbCertService.updateById(tbCert);

        /**  3、联系方式           */
        List<TbContact> tbContactList = personnelVo.getTbMobileVoList();
        tbContactList.addAll(personnelVo.getTbEamilVoList());
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

        return ResultUtils.success(null);
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
        return ResultUtils.success(null);
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
        if (StrUtil.isNullOrEmpty(IDCardUtil.IDCardValidate(editFormPersonnelVo.getCertNo()))) {
            return ResultUtils.certError();
        }

        List<TbContact> tbMobileVoList = editFormPersonnelVo.getTbMobileVoList();
        if(tbMobileVoList != null && tbMobileVoList.size() > 0){
            for (TbContact tbContact : tbMobileVoList) {
                if( StrUtil.isNullOrEmpty(tbContact.getContent())){
                    return ResultUtils.error(EumPersonnelResponseCode.MOBILE_IS_NULL);
                }else if( !StrUtil.checkTelephoneNumber(tbContact.getContent())){
                    return ResultUtils.error(EumPersonnelResponseCode.MOBILE_ERROR);
                }else{
                    Map<String, Object> phoneMap = new HashMap<String, Object>();
                    phoneMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
                    phoneMap.put("CONTENT", editFormPersonnelVo.getCertNo());
                    phoneMap.put("CONTACT_TYPE", editFormPersonnelVo.getCertType());
                    TbContact tbContact1 = tbContactService.selectOne(new EntityWrapper<TbContact>().allEq(phoneMap));
                    if((StrUtil.isNullOrEmpty(editFormPersonnelVo.getPersonnelId()) && !StrUtil.isNullOrEmpty(tbContact1))
                            || (!StrUtil.isNullOrEmpty(tbContact1) && tbContact1.getPersonnelId().equals(editFormPersonnelVo.getPersonnelId()))){
                        Map<String, Object> psnMap = new HashMap<String, Object>();
                        psnMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
                        psnMap.put(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, tbContact1.getPersonnelId());
                        TbPersonnel tbPersonnel = tbPersonnelService.selectOne(new EntityWrapper<TbPersonnel>().allEq(psnMap));
                        return ResultUtils.error(EumPersonnelResponseCode.CERT_IS_EXIST.getCode(), "身份证已被" + tbPersonnel.getPsnName() + "占用");
                    }
                }
            }
        }

        List<TbContact> tbEamilVoList = editFormPersonnelVo.getTbEamilVoList();
        if(tbEamilVoList != null && tbEamilVoList.size() > 0) {
            for (TbContact tbContact : tbEamilVoList) {
                if(StrUtil.isNullOrEmpty(tbContact.getContent())){
                    return ResultUtils.error(EumPersonnelResponseCode.EMAIL_IS_NULL);
                }
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
           || (!StrUtil.isNullOrEmpty(tbCert) && tbCert.getPersonnelId().equals(editFormPersonnelVo.getPersonnelId()))){
            map.remove(BaseUnitConstants.TBCERT_CERT_NO);
            map.put(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, tbCert.getPersonnelId());
            TbPersonnel tbPersonnel = tbPersonnelService.selectOne(new EntityWrapper<TbPersonnel>().allEq(map));
            return ResultUtils.error(EumPersonnelResponseCode.CERT_IS_EXIST.getCode(), "身份证已被" + tbPersonnel.getPsnName() + "占用");
        }

        return null;
    }

    public Object addAllPersonnel(EditFormPersonnelVo editFormPersonnelVo){

        TbPersonnel tbPersonnel = new TbPersonnel();
        BeanUtils.copyProperties(editFormPersonnelVo, tbPersonnel);

        Long personnelId = tbPersonnelService.getId();
        tbPersonnel.setPersonnelId(personnelId);
        tbPersonnel.setGender(IDCardUtil.getGender(editFormPersonnelVo.getCertNo()));
        /**  自动生成 员工工号 */
        Long psnNbrId = tbPersonnelService.getPsnNbrId();
        tbPersonnel.setPsnNbr("21" + StrUtil.getCurrentYear().substring(2, 4) + StrUtil.padLeading(String.valueOf(psnNbrId), 6, "0"));

        /**  自动生成 人员编码 */
        Long psnCodeId = tbPersonnelService.getPsnCodeId();
        tbPersonnel.setPsnCode("W" + StrUtil.padLeading(String.valueOf(psnCodeId), 8 , "0"));

        /**  自动生成 人力编码 (需要调用集团人员接口，后期开发)*/
        tbPersonnel.setNcCode("");

        tbPersonnel.setUuid(StrUtil.getUUID());
        tbPersonnelService.insert(tbPersonnel);

        TbCert tbCert = new TbCert();
        tbCert.setCertId(tbCertService.getId());
        tbCert.setCertType(editFormPersonnelVo.getCertType());
        tbCert.setCertNo(editFormPersonnelVo.getCertNo());
        tbCert.setCertName(editFormPersonnelVo.getPsnName());
        tbCert.setPersonnelId(personnelId);
        tbCertService.insert(tbCert);

        /**  2、组织信息           */

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

        return ResultUtils.success(null);
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


}

