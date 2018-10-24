package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
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

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    @Autowired
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


    @ApiOperation(value = "人员查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "人员查询", key = "getPersonnel")
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
    @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Integer",paramType="path")
    @UooLog(value = "查看人员信息", key = "getFormPersonnel")
    @RequestMapping(value = "/getFormPersonnel/personnelId={personnelId}",method = RequestMethod.GET)
    public Object getFormPersonnel(@PathVariable(value = "personnelId") Integer personnelId){
        FormPersonnelVo formPersonnelVo = new FormPersonnelVo();
        /**  1、基本信息    */
        TbPersonnel tbPersonnel = tbPersonnelService.selectById(personnelId);
        BeanUtils.copyProperties(tbPersonnel, formPersonnelVo);

        Wrapper tbCertwrapper= Condition.create().eq(true,"PERSONNEL_ID", personnelId);
        Page<TbCert> tbCertPage = tbCertService.selectPage(new Page<TbCert>(0, 12), tbCertwrapper);
        BeanUtils.copyProperties(tbCertPage.getRecords().get(0), formPersonnelVo);

        /**  2、联系信息    */
        Wrapper tbContactwrapper= Condition.create().eq(true,"PERSONNEL_ID", personnelId);
        Page<TbContactVo> tbContactPage = tbContactService.selectPage(new Page<TbContact>(0, 12), tbContactwrapper);
        formPersonnelVo.setTbContactVoList(tbContactPage);

        /**  3、归属组织信息 (需要调用uoo-organization)*/


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
    @UooLog(value = "新增人员信息",key = "addPersonnel")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Object addPersonnel(@RequestBody EditFormPersonnelVo editFormPersonnelVo) {
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
        tbCert.setCertType(editFormPersonnelVo.getCertType());
        tbCert.setCertNo(editFormPersonnelVo.getCertNo());
        tbCert.setCertName(editFormPersonnelVo.getPsnName());
        tbCert.setPersonnelId(personnelId);
        tbCertService.insert(tbCert);

        /**  2、组织信息           */

        /**  3、联系方式           */
        List<TbContact> tbContactList = editFormPersonnelVo.getTbContactList();
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

    @ApiOperation(value = "修改人员信息",notes = "人员信息修改")
    @ApiImplicitParam(name = "editFormPersonnelVo",value = "人员信息",required = true,dataType = "EditFormPersonnelVo")

    @UooLog(value = "修改人员信息",key = "updatePersonnel")
    @RequestMapping(value = "updatePersonnel",method = RequestMethod.PUT)
    public Object updatePersonnel(@RequestBody EditFormPersonnelVo editFormPersonnelVo) {

        /**  人员表单信息验证 */
        Object reObj = checkFormPersonnel(editFormPersonnelVo);
        if(!StrUtil.isNullOrEmpty(reObj)){
            return reObj;
        }

        TbPersonnel tbPersonnel = new TbPersonnel();
        BeanUtils.copyProperties(editFormPersonnelVo, tbPersonnel);
        tbPersonnel.setGender(IDCardUtil.getGender(editFormPersonnelVo.getCertNo()));
        tbPersonnelService.updateById(tbPersonnel);

        Wrapper tbCertwrapper= Condition.create().eq(true,"PERSONNEL_ID", editFormPersonnelVo.getPersonnelId());
        Page<TbCert> tbCertPage = tbCertService.selectPage(new Page<TbCert>(0, 12), tbCertwrapper);
        TbCert tbCert = tbCertPage.getRecords().get(0);
        tbCert.setCertType(editFormPersonnelVo.getCertType());
        tbCert.setCertNo(editFormPersonnelVo.getCertNo());
        tbCert.setCertName(editFormPersonnelVo.getPsnName());
        tbCertService.updateById(tbCert);

        /**  2、组织信息           */

        /**  3、联系方式           */
        List<TbContact> tbContactList = editFormPersonnelVo.getTbContactList();
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
    @ApiImplicitParam(name = "tbPersonnel",value = "人员信息",required = true,dataType = "TbPersonnel")
    @UooLog(value = "删除人员信息",key = "deletePersonnel")
    @RequestMapping(value="",method = RequestMethod.DELETE)
    public void deletePersonnel(@RequestBody TbPersonnel tbPersonnel) {
        tbPersonnelService.delete(tbPersonnel);

        // 根据personnelId查询tbCert
        Wrapper certWrapper = Condition.create().eq("PERSONNEL_ID",tbPersonnel.getPersonnelId());
        TbCert tbCert = tbCertService.selectOne(certWrapper);
        // 根据id删除tbCert
        tbCertService.delete(tbCert);

        /**  3、联系方式           */
        // 根据id删除tbContact
        TbContact tbContact = new TbContact();
        tbContact.setPersonnelId(tbPersonnel.getPersonnelId());
        tbContactService.delete(tbContact);

        /**  4、工作履历信息       */
        TbPsnjob tbPsnjob = new TbPsnjob();
        tbPsnjob.setPersonnelId(tbPersonnel.getPersonnelId());
        tbPsnjobService.delete(tbPsnjob);

        /**  5、教育信息           */
        TbEdu tbEdu = new TbEdu();
        tbEdu.setPersonnelId(tbEdu.getPersonnelId());
        tbEduService.delete(tbEdu);

        /**  6、家庭成员信息      */
        TbFamily tbFamily = new TbFamily();
        tbFamily.setPersonnelId(tbFamily.getPersonnelId());
        tbFamilyService.delete(tbFamily);

    }

    @ApiOperation(value="图片上传",notes="图片上传")
    @UooLog(value = "图片上传",key = "uploadImg")
    @RequestMapping(value="/uploadImg")
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
            if(!path.exists()) path = new File("");
            System.out.println("path:"+path.getAbsolutePath());

            //如果上传目录为/static/images/upload/，则可以如下获取：
            File upload = new File(path.getAbsolutePath(),"static/images/upload/");
            if(!upload.exists()) upload.mkdirs();
            filePath = upload.getAbsolutePath();
            System.out.println("upload url:"+upload.getAbsolutePath());
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
            return ResultUtils.error(EumPersonnelResponseCode.PSN_NAME_IS_NULL.getCode(), EumPersonnelResponseCode.PSN_NAME_IS_NULL.getMsg());
        }
        if (StrUtil.isNullOrEmpty(editFormPersonnelVo.getCertType())){
            return ResultUtils.error(EumPersonnelResponseCode.CERT_TYPE_IS_NULL.getCode(), EumPersonnelResponseCode.CERT_TYPE_IS_NULL.getMsg());
        }
        if (StrUtil.isNullOrEmpty(editFormPersonnelVo.getCertNo())){
            return  ResultUtils.error(EumPersonnelResponseCode.CERT_NO_IS_NULL.getCode(), EumPersonnelResponseCode.CERT_NO_IS_NULL.getMsg());
        }
        if (StrUtil.isNullOrEmpty(IDCardUtil.IDCardValidate(editFormPersonnelVo.getCertNo()))) {
            return ResultUtils.certError();
        }
        List<TbContact> tbContactList = editFormPersonnelVo.getTbContactList();
        if(tbContactList != null && tbContactList.size() > 0) {
            for (TbContact tbContact : tbContactList) {
                if("1".equals(tbContact.getContactType()) && StrUtil.isNullOrEmpty(tbContact.getContent())){
                    return ResultUtils.error(EumPersonnelResponseCode.MOBILE_IS_NULL.getCode(), EumPersonnelResponseCode.MOBILE_IS_NULL.getMsg());
                }
                if("2".equals(tbContact.getContactType()) && StrUtil.isNullOrEmpty(tbContact.getContent())){
                    return ResultUtils.error(EumPersonnelResponseCode.EMAIL_IS_NULL.getCode(), EumPersonnelResponseCode.EMAIL_IS_NULL.getMsg());
                }
                if("1".equals(tbContact.getContactType()) && StrUtil.checkTelephoneNumber(tbContact.getContent())){
                    return ResultUtils.moblieError();
                }
                if("2".equals(tbContact.getContactType()) && StrUtil.checkEmail(tbContact.getContent())){
                    return ResultUtils.emailError();
                }
            }
        }
        return null;
    }



    @UooLog(value = "测试条件查询", key = "testPersonnel")
    @RequestMapping(value = "/testPage",method = RequestMethod.GET)
    public Page<TbPersonnel> testPersonnel() {
        return tbPersonnelService.selectPage(new Page<TbPersonnel>(0, 12));
    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test() {
        return "Hello Uoo";
    }

    @ApiImplicitParam(name = "tbTestVo",value = "人员查询条件",required = true,dataType = "TbTestVo")
    @UooLog(value = "根据组织查询人员",key = "getPersonnelOrg")
    @RequestMapping(value = "/getTestVo",method = RequestMethod.POST)
    public Object getPersonnelOrg(@RequestBody TbTestVo tbTestVo){
        System.out.println("11:" + tbTestVo.getId());
        System.out.println("22:" + tbTestVo.getName());
        List<TbEdu> tbEduList = tbTestVo.getTbEduList();
        for(TbEdu tbEdu : tbEduList){
            System.out.println("33:" + tbEdu.getEduId());
            System.out.println("44:" + tbEdu.getDegree());
        }
        return ResultUtils.success(tbTestVo);
    }
}

