package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.*;
import cn.ffcs.uoo.core.user.service.*;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-09-14
 */
@RestController
@RequestMapping("/tbUser")
public class TbUserController extends BaseController {

    @Autowired
    private TbUserService tbUserService;

    @Autowired
    private TbAcctExtService tbAcctExtService;

    @Autowired
    private TbAcctService tbAcctService;

    @Autowired
    private TbSlaveAcctService tbSlaveAcctService;

    private String acctType = "1";

    private String slaveAcctType = "2";

    @ApiOperation(value = "选择用户信息", notes = "选择用户信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "人员查看用户查询", key = "getUserList")
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public Object getUserList(Long personnelId, Integer pageNo, Integer pageSize, String account){
        return ResultUtils.success(tbUserService.getUserList(personnelId, pageNo, pageSize, account));
    }

    @ApiOperation(value = "新增人员(主从账号)用户", notes = "新增人员(主从账号)用户")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "userType", value = "用户类型", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "account", value = "人员标识", required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "新增人员(主从账号)用户", key = "addPsnUser")
    @RequestMapping(value = "/getPsnUser", method = RequestMethod.GET)
    public Object addUser(String userType, Long personnelId, String account){
        TbAcct tbAcct = (TbAcct) tbAcctService.getTbAcctByPsnId(personnelId);
        if(acctType.equals(userType)){
            if(!StrUtil.isNullOrEmpty(tbAcct)){
                return getFormAcct(tbAcct.getAcctId(), account);
            }
            FormAcctVo formAcctVo = new FormAcctVo();
            formAcctVo.setUserType("1");
            formAcctVo.setPersonnelId(personnelId);
            PersonnelInfoVo tbPersonnel = tbUserService.getPersonnelInfo(personnelId);
            BeanUtils.copyProperties(tbPersonnel, formAcctVo);
            return ResultUtils.success(formAcctVo);
        }
        if(slaveAcctType.equals(userType)){
            if(StrUtil.isNullOrEmpty(tbAcct)){
                return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST_RE);
            }
            FormSlaveAcctVo formSlaveAcctVo = new FormSlaveAcctVo();
            //从账号
            formSlaveAcctVo.setUserType("2");
            //应用
            formSlaveAcctVo.setSlaveAcctType("1");
            formSlaveAcctVo.setPersonnelId(personnelId);
            PersonnelInfoVo tbPersonnel = tbUserService.getPersonnelInfo(personnelId);
            BeanUtils.copyProperties(tbPersonnel, formSlaveAcctVo);
            return ResultUtils.success(formSlaveAcctVo);
        }

        return null;
    }

    @ApiOperation(value = "主账号查询",notes = "主账号查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acctId", value = "主账号标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "account", value = "人员标识", required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "主账号查询",key = "getFormAcct")
    @RequestMapping(value = "/getFormAcct", method = RequestMethod.GET)
    public Object getFormAcct(Long acctId, String account){
        FormAcctVo formAcctVo = new FormAcctVo();
        formAcctVo.setUserType("1");
        //主账号
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_ACCT_ID, acctId);
        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().allEq(map));
        if(StrUtil.isNullOrEmpty(tbAcct)){
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST);
        }
        formAcctVo.setTbAcct(tbAcct);

        AcctCrossRelVo acctCrossRelVo = tbSlaveAcctService.getAcctCrossRel(tbAcct.getAcctId());
        if(!StrUtil.isNullOrEmpty(acctCrossRelVo)){
            formAcctVo.setCrossTran(acctCrossRelVo.getCrossTran());
        }

        //人员信息
        formAcctVo.setPersonnelId(Long.valueOf(tbAcct.getPersonnelId()));
        PersonnelInfoVo tbPersonnel = tbUserService.getPersonnelInfo(tbAcct.getPersonnelId());
        BeanUtils.copyProperties(tbPersonnel, formAcctVo);

        //角色
        List<TbRoles> tbRolesList = tbAcctService.getTbRoles(1L,tbAcct.getAcctId());
        formAcctVo.setTbRolesList(tbRolesList);

        //归属组织信息
        Page<ListAcctOrgVo> acctOrgVoPage = tbUserService.getAcctOrg(tbAcct.getAcctId(), 0, 50, account);
        formAcctVo.setAcctOrgVoPage(acctOrgVoPage);

        //从账号
        ListSlaveAcctOrgVo slaveAcctOrgVo = new ListSlaveAcctOrgVo();
        slaveAcctOrgVo.setAcctId(tbAcct.getAcctId());
        Page<ListSlaveAcctOrgVo> slaveAcctOrgVoPage = tbSlaveAcctService.getSlaveAcctOrg(slaveAcctOrgVo, account);
        formAcctVo.setSlaveAcctOrgVoPage(slaveAcctOrgVoPage);

        return ResultUtils.success(formAcctVo);
    }

    @ApiOperation(value = "从账号查询",notes = "从账号查询")
    @ApiImplicitParam(name = "acctId", value = "从账号标识", required = true, dataType = "Long", paramType = "path")
    @UooLog(value = "从账号查询",key = "getFormSlaveAcct")
    @RequestMapping(value = "/getFormSlaveAcct", method = RequestMethod.GET)
    public Object getFormSlaveAcct(Long acctId){
        FormSlaveAcctVo formSlaveAcctVo = new FormSlaveAcctVo();
        //从账号
        formSlaveAcctVo.setUserType("2");
        //应用
        formSlaveAcctVo.setSlaveAcctType("1");

        //从账号
        Map<String, Object> sAcctMap = new HashMap<String, Object>(16);
        sAcctMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        sAcctMap.put(BaseUnitConstants.TABLE_SLAVE_ACCT_ID, acctId);
        TbSlaveAcct tbSlaveAcct = tbSlaveAcctService.selectOne(new EntityWrapper<TbSlaveAcct>().allEq(sAcctMap));
        formSlaveAcctVo.setTbSlaveAcct(tbSlaveAcct);
        if(StrUtil.isNullOrEmpty(tbSlaveAcct)){
            return ResultUtils.error(EumUserResponeCode.SLAVE_ACCT_NO_EXITST);
        }

        //主账号
        Map<String, Object> acctMap = new HashMap<String, Object>(16);
        acctMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        acctMap.put(BaseUnitConstants.TABLE_ACCT_ID, tbSlaveAcct.getAcctId());
        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().allEq(acctMap));
        if(StrUtil.isNullOrEmpty(tbAcct)){
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST);
        }
        formSlaveAcctVo.setAcct(tbAcct.getAcct());
        formSlaveAcctVo.setAcctId(tbAcct.getAcctId());

        AcctCrossRelVo acctCrossRelVo = tbSlaveAcctService.getAcctCrossRel(tbSlaveAcct.getAcctId());
        if(!StrUtil.isNullOrEmpty(acctCrossRelVo)){
            formSlaveAcctVo.setCrossTran(acctCrossRelVo.getCrossTran());
        }

        //人员
        PersonnelInfoVo tbPersonnel = tbUserService.getPersonnelInfo(tbAcct.getPersonnelId());
        BeanUtils.copyProperties(tbPersonnel, formSlaveAcctVo);

        //角色
        List<TbRoles> tbRolesList = tbAcctService.getTbRoles(2L,tbSlaveAcct.getSlaveAcctId());
        formSlaveAcctVo.setTbRolesList(tbRolesList);

        //扩展信息
        Map<String, Object> acctExtMap = new HashMap<String, Object>(16);
        acctExtMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        acctExtMap.put(BaseUnitConstants.TABLE_SLAVE_ACCT_ID, tbSlaveAcct.getSlaveAcctId());
        TbAcctExt tbAcctExt = tbAcctExtService.selectOne(new EntityWrapper<TbAcctExt>().allEq(acctExtMap));
        if(!StrUtil.isNullOrEmpty(tbAcctExt)){
            formSlaveAcctVo.setTbAcctExt(tbAcctExt);
        }

        //归属组织信息
        ListAcctOrgVo acctOrgVo = new ListAcctOrgVo();
        acctOrgVo.setAcctOrgRelId(tbSlaveAcct.getAcctOrgRelId());
        List<ListAcctOrgVo> acctOrgVoList = tbUserService.getSlaveAcctOrg(acctOrgVo);
        formSlaveAcctVo.setAcctOrgVoList(acctOrgVoList);

        return ResultUtils.success(formSlaveAcctVo);
    }

    @ApiOperation(value = "主账号组织关系",notes = "主账号组织关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "resourceObjId", value = "应用系统标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "主账号组织关系",key = "getAcctOrgByPsnId")
    @RequestMapping(value = "/getAcctOrgByPsnId", method = RequestMethod.GET)
    public Object getAcctOrgByPsnId(Long personnelId, Long resourceObjId, String account){
        if(StrUtil.isNullOrEmpty(personnelId)){
            return ResultUtils.error(EumUserResponeCode.PSN_ID_NO_NULL);
        }
        if(StrUtil.isNullOrEmpty(resourceObjId)){
            return ResultUtils.error(EumUserResponeCode.SYS_ID_NO_NULL);
        }
        return ResultUtils.success(tbUserService.getAcctOrgByPsnId(personnelId, resourceObjId, account));
    }

}

