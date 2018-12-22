package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.*;
import cn.ffcs.uoo.core.user.service.*;
import cn.ffcs.uoo.core.user.util.ResponseResult;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

    @Autowired
    private TbUserService tbUserService;

    @Autowired
    private TbAcctExtService tbAcctExtService;

    @Autowired
    private TbAcctService tbAcctService;

    @Autowired
    private TbSlaveAcctService tbSlaveAcctService;

    @Autowired
    private AmqpTemplate template;



    @ApiOperation(value = "用户组织条件查询", notes = "条件分页查询")
    @ApiImplicitParam(name = "psonOrgVo", value = "用户条件VO", required = true, dataType = "PsonOrgVo")
    @UooLog(value = "用户组织条件查询", key = "getPageUserOrg")
    @RequestMapping(value = "/getPageUserOrg/", method = RequestMethod.POST)
    public Object getPageUserOrg(@RequestBody PsonOrgVo psonOrgVo){
        Page<ListUserOrgVo> userOrgVoPage = tbUserService.selectUserOrgPage(psonOrgVo);
        return ResultUtils.success(userOrgVoPage);
    }



    @ApiOperation(value = "用户信息", notes = "用户信息")
    @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "String",paramType="path")
    @UooLog(value = "用户组织条件查询", key = "getUser")
    @RequestMapping(value = "/getUserByPersonnelId", method = RequestMethod.GET)
    public Object getUser(String personnelId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_PERSONNEL_ID, personnelId);
        TbUser tbUser = tbUserService.selectOne(new EntityWrapper<TbUser>().allEq(map));
        if(StrUtil.isNullOrEmpty(tbUser)){
            return ResultUtils.error(EumUserResponeCode.USER_NOT_EXIST);
        }
        EditFormUserVo editFormUserVo = new EditFormUserVo();
        editFormUserVo.setUserId(tbUser.getUserId());

        /**
         * todo
         *  人员信息 调用personnel接口
         */
        //ResponseResult result = (ResponseResult) restTemplate.getForObject(
        ResponseResult result = restTemplate.getForObject(
                "http://PERSONNEL-SERVICE/personnel/getPsnByUser?personnelId=" + personnelId,
                ResponseResult.class
        );

        JSONObject jsonObject= JSONObject.fromObject(result); // 将数据转成json字符串
        ResponseResult per = (ResponseResult)JSONObject.toBean(jsonObject, ResponseResult.class); //将json转成需要的对象
        if("0".equals(per.getState())){
            editFormUserVo.setPsnByUserVo((PsnByUserVo) per.getData());
        }else{
            return  ResultUtils.error(per.getState(), per.getMessage());
        }

        /**
         * 角色信息
         */
        List<TbRoles> tbRolesList = tbUserService.getRoleByUserId(Long.valueOf(tbUser.getUserId()));
        editFormUserVo.setTbRolesList(tbRolesList);
        /**
         * todo
         * 归属组织信息 调用org接口
         */

        //主账号
        map.remove(BaseUnitConstants.TABLE_PERSONNEL_ID);
        map.put(BaseUnitConstants.TBALE_USER_ID, tbUser.getUserId());
        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().allEq(map));
        BeanUtils.copyProperties(tbAcct, editFormUserVo);

        //从账号
        List<ListSlaveAcctVo> slaveAcctVoList =  tbUserService.getSlaveAcctInfo(Long.valueOf(tbUser.getUserId()), tbAcct.getAcctId());
        editFormUserVo.setSlaveAcctVoList(slaveAcctVoList);

        return ResultUtils.success(editFormUserVo);
    }

    //-todo---新版本------------------------------------------------------------------------------------

    @ApiOperation(value = "选择用户信息", notes = "选择用户信息")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path")
    })
    @UooLog(value = "人员查看用户查询", key = "getUserList")
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public Object getUserList(Long personnelId, Integer pageNo, Integer pageSize){
        return ResultUtils.success(tbUserService.getUserList(personnelId, pageNo, pageSize));
    }

    @ApiOperation(value = "新增人员(主从账号)用户", notes = "新增人员(主从账号)用户")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "userType", value = "用户类型", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path")
    })
    @UooLog(value = "新增人员(主从账号)用户", key = "addPsnUser")
    @RequestMapping(value = "/getPsnUser", method = RequestMethod.GET)
    public Object addUser(String userType, Long personnelId){
        TbAcct tbAcct = (TbAcct) tbAcctService.getTbAcctByPsnId(personnelId);
        if("1".equals(userType)){
            if(!StrUtil.isNullOrEmpty(tbAcct)){
                return getFormAcct(tbAcct.getAcctId());
            }
            FormAcctVo formAcctVo = new FormAcctVo();
            formAcctVo.setUserType("1");
            formAcctVo.setPersonnelId(personnelId);
            PersonnelInfoVo tbPersonnel = tbUserService.getPersonnelInfo(personnelId);
            BeanUtils.copyProperties(tbPersonnel, formAcctVo);
            return ResultUtils.success(formAcctVo);
        }
        if("2".equals(userType)){
            if(StrUtil.isNullOrEmpty(tbAcct)){
                return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST_RE);
            }
            FormSlaveAcctVo formSlaveAcctVo = new FormSlaveAcctVo();
            formSlaveAcctVo.setUserType("2");//从账号
            formSlaveAcctVo.setSlaveAcctType("1");//应用
            formSlaveAcctVo.setPersonnelId(personnelId);
            PersonnelInfoVo tbPersonnel = tbUserService.getPersonnelInfo(personnelId);
            BeanUtils.copyProperties(tbPersonnel, formSlaveAcctVo);
            return ResultUtils.success(formSlaveAcctVo);
        }

        return null;
    }

    @ApiOperation(value = "主账号查询",notes = "主账号查询")
    @ApiImplicitParam(name = "acctId", value = "主账号标识", required = true, dataType = "Long", paramType = "path")
    @UooLog(value = "主账号查询",key = "getFormAcct")
    @RequestMapping(value = "/getFormAcct", method = RequestMethod.GET)
    public Object getFormAcct(Long acctId){
        FormAcctVo formAcctVo = new FormAcctVo();
        formAcctVo.setUserType("1");
        //主账号
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_ACCT_ID, acctId);
        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().allEq(map));
        if(StrUtil.isNullOrEmpty(tbAcct)){
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST);
        }
        formAcctVo.setTbAcct(tbAcct);

        //人员信息
        formAcctVo.setPersonnelId(Long.valueOf(tbAcct.getPersonnelId()));
        PersonnelInfoVo tbPersonnel = tbUserService.getPersonnelInfo(tbAcct.getPersonnelId());
        BeanUtils.copyProperties(tbPersonnel, formAcctVo);

        //角色
        List<TbRoles> tbRolesList = tbAcctService.getTbRoles(1L,tbAcct.getAcctId());
        formAcctVo.setTbRolesList(tbRolesList);

        //归属组织信息
        Page<ListAcctOrgVo> acctOrgVoPage = tbUserService.getAcctOrg(tbAcct.getAcctId(), 0, 0);
        formAcctVo.setAcctOrgVoPage(acctOrgVoPage);

        //从账号
        ListSlaveAcctOrgVo slaveAcctOrgVo = new ListSlaveAcctOrgVo();
        slaveAcctOrgVo.setAcctId(tbAcct.getAcctId());
        Page<ListSlaveAcctOrgVo> slaveAcctOrgVoPage = tbSlaveAcctService.getSlaveAcctOrg(slaveAcctOrgVo);
        formAcctVo.setSlaveAcctOrgVoPage(slaveAcctOrgVoPage);

        return ResultUtils.success(formAcctVo);
    }

    @ApiOperation(value = "从账号查询",notes = "从账号查询")
    @ApiImplicitParam(name = "acctId", value = "从账号标识", required = true, dataType = "Long", paramType = "path")
    @UooLog(value = "主账号查询",key = "getFormAcct")
    @RequestMapping(value = "/getFormSlaveAcct", method = RequestMethod.GET)
    public Object getFormSlaveAcct(Long acctId){
        FormSlaveAcctVo formSlaveAcctVo = new FormSlaveAcctVo();
        formSlaveAcctVo.setUserType("2");//从账号
        formSlaveAcctVo.setSlaveAcctType("1");//应用

        //从账号
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_SLAVE_ACCT_ID, acctId);
        TbSlaveAcct tbSlaveAcct = tbSlaveAcctService.selectOne(new EntityWrapper<TbSlaveAcct>().allEq(map));
        formSlaveAcctVo.setTbSlaveAcct(tbSlaveAcct);

        //人员
        map.remove(BaseUnitConstants.TABLE_SLAVE_ACCT_ID);
        map.put(BaseUnitConstants.TABLE_ACCT_ID, tbSlaveAcct.getAcctId());
        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().allEq(map));
        PersonnelInfoVo tbPersonnel = tbUserService.getPersonnelInfo(tbAcct.getPersonnelId());
        BeanUtils.copyProperties(tbPersonnel, formSlaveAcctVo);

        //角色
        List<TbRoles> tbRolesList = tbAcctService.getTbRoles(2L,tbSlaveAcct.getSlaveAcctId());
        formSlaveAcctVo.setTbRolesList(tbRolesList);

        //扩展信息
        map.remove(BaseUnitConstants.TABLE_ACCT_ID);
        map.put(BaseUnitConstants.TABLE_SLAVE_ACCT_ID, tbSlaveAcct.getSlaveAcctId());
        TbAcctExt tbAcctExt = tbAcctExtService.selectOne(new EntityWrapper<TbAcctExt>().allEq(map));
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
            @ApiImplicitParam(name = "resourceObjId", value = "应用系统标识", required = true, dataType = "Long", paramType = "path")
    })
    @UooLog(value = "主账号组织关系",key = "getAcctOrgByPsnId")
    @RequestMapping(value = "/getAcctOrgByPsnId", method = RequestMethod.GET)
    public Object getAcctOrgByPsnId(Long personnelId, Long resourceObjId){
        if(StrUtil.isNullOrEmpty(personnelId)){
            return ResultUtils.error(EumUserResponeCode.PSN_ID_NO_NULL);
        }
        if(StrUtil.isNullOrEmpty(resourceObjId)){
            return ResultUtils.error(EumUserResponeCode.SYS_ID_NO_NULL);
        }
        return ResultUtils.success(tbUserService.getAcctOrgByPsnId(personnelId, resourceObjId));
    }

}

