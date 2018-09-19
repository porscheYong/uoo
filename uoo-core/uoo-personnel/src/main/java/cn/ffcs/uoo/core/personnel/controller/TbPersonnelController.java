package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.personnel.entity.TbCert;
import cn.ffcs.uoo.core.personnel.entity.TbContact;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.service.TbCertService;
import cn.ffcs.uoo.core.personnel.service.TbContactService;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.vo.PersonnelRelationInfoVo;
import cn.ffcs.uoo.core.personnel.vo.TbPersonnelVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "人员查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "人员查询", key = "getPersonnel")
    @RequestMapping(value = "/getPage/pageNo={pageNo}&pageSize={pageSize}",method = RequestMethod.GET)
    public Page<TbPersonnel> getPersonnel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {

        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        return tbPersonnelService.selectPage(new Page<TbPersonnel>(pageNo, pageSize));
    }

    @ApiOperation(value = "人员条件查询", notes = "条件分页查询")
    @ApiImplicitParam(name = "tbPersonnelVo", value = "人员条件VO", required = true, dataType = "TbPersonnelVo")
    @UooLog(value = "人员条件查询", key = "getPersonnelCondition")
    @RequestMapping(value = "/getPage/",method = RequestMethod.POST)
    public Page<TbPersonnel> getPersonnelCondition(@RequestBody TbPersonnelVo tbPersonnelVo) {
        int pageSize = tbPersonnelVo.getPageSize()==0?12:tbPersonnelVo.getPageSize();

        Wrapper wrapper= Condition.create().eq(StringUtils.isNotEmpty(tbPersonnelVo.getPsnNbr()),"PSN_NBR",tbPersonnelVo.getPsnNbr());

        return tbPersonnelService.selectPage(new Page<TbPersonnel>(tbPersonnelVo.getPageNo(), pageSize),wrapper);
    }

    @ApiOperation(value = "人员综合信息查询", notes = "人员综合信息分页")
    @ApiImplicitParam(name = "tbPersonnelVo",value = "人员查询条件",required = true,dataType = "TbPersonnelVo")
    @UooLog(value = "人员综合信息查询",key = "getPersonnelRelationInfo")
    @RequestMapping(value = "/allInfoPage",method = RequestMethod.POST)
    public Page<PersonnelRelationInfoVo> getPersonnelRelationInfo(TbPersonnelVo tbPersonnelVo) {
        return tbPersonnelService.getPersonnelRelationInfo(tbPersonnelVo);
    }

    @ApiOperation(value = "新增人员信息",notes = "人员信息新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbPersonnel",value = "人员信息",required = true,dataType = "TbPersonnel"),
            @ApiImplicitParam(name = "tbCert",value = "证件信息",required = true,dataType = "TbCert"),
            @ApiImplicitParam(name = "tbContact",value = "联系方式信息",required = true,dataType = "TbContact")
    })
    @UooLog(value = "新增人员信息",key = "addPersonnel")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public void addPersonnel(@RequestBody TbPersonnel tbPersonnel, @RequestBody TbCert tbCert, @RequestBody TbContact tbContact) {
        tbPersonnelService.insert(tbPersonnel);
        tbCertService.insert(tbCert);
        tbContactService.insert(tbContact);
    }

    @ApiOperation(value = "修改人员信息",notes = "人员信息修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbPersonnel",value = "人员信息",required = true,dataType = "TbPersonnel"),
            @ApiImplicitParam(name = "tbCert",value = "证件信息",required = true,dataType = "TbCert"),
            @ApiImplicitParam(name = "tbContact",value = "联系方式信息",required = true,dataType = "TbContact")
    })
    @UooLog(value = "修改人员信息",key = "updatePersonnel")
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public void updatePersonnel(@RequestBody TbPersonnel tbPersonnel, @RequestBody TbCert tbCert, @RequestBody TbContact tbContact) {
        Wrapper personnelWrapper = Condition.create().eq(StringUtils.isNotEmpty(tbPersonnel.getPsnNbr()),"PSN_NBR",tbPersonnel.getPsnNbr());
        tbPersonnelService.update(tbPersonnel,personnelWrapper);
        Wrapper certWrapper = Condition.create().eq(StringUtils.isNotEmpty(tbCert.getCertNo()),"CERT_NO", tbCert.getCertNo());
        tbCertService.update(tbCert,certWrapper);
        Wrapper contactWrapper =  Condition.create().eq(StringUtils.isNotEmpty(tbContact.getContent()),"CONTENT", tbContact.getContent());
        tbContactService.update(tbContact,contactWrapper);
    }

    @ApiOperation(value="删除人员信息",notes="人员信息删除")
    @ApiImplicitParam(name = "tbPersonnel",value = "人员信息",required = true,dataType = "TbPersonnel")
    @UooLog(value = "删除人员信息",key = "deletePersonnel")
    @RequestMapping(value="",method = RequestMethod.DELETE)
    public void deletePersonnel(@RequestBody TbPersonnel tbPersonnel) {
        tbPersonnelService.deleteById(tbPersonnel.getPersonnelId());
        // 根据personnelId查询tbContact
        Wrapper contactWrapper = Condition.create().eq("PERSONNEL_ID",tbPersonnel.getPersonnelId());
        TbContact tbContact = tbContactService.selectOne(contactWrapper);
        // 根据id删除tbContact
        tbContactService.deleteById(tbContact.getContactId());

        // 根据personnelId查询tbCert
        Wrapper certWrapper = Condition.create().eq("PERSONNEL_ID",tbPersonnel.getPersonnelId());
        TbCert tbCert = tbCertService.selectOne(certWrapper);
        // 根据id删除tbCert
        tbCertService.deleteById(tbCert.getCertId());
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
}

