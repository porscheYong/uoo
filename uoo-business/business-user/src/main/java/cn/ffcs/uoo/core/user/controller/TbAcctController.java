package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.*;
import cn.ffcs.uoo.core.user.service.*;
import cn.ffcs.uoo.core.user.util.*;
import cn.ffcs.uoo.core.user.vo.EditFormAcctVo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主账号 前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-10-25
 */
@Api(description="主账号",value="Acct")
@RestController
@RequestMapping("/tbAcct")
public class TbAcctController extends BaseController {

    @Autowired
    private TbAcctService tbAcctService;
    @Autowired
    private RabbitMqService rabbitMqService;
    @Autowired
    private TbUserRoleService tbUserRoleService;
    @Autowired
    private TbAccountOrgRelService tbAccountOrgRelService;
    @Autowired
    private TbSlaveAcctService tbSlaveAcctService;




    @ApiOperation(value = "新增主账号信息", notes = "主账号信息新增")
    @ApiImplicitParam(name = "editFormAcctVo", value = "主账号信息", required = true, dataType = "EditFormAcctVo")
    @UooLog(value = "新增主账号信息", key = "addTbAcct")
    @RequestMapping(value = "/addTbAcct", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object saveAcct(@RequestBody EditFormAcctVo editFormAcctVo) {

        // 校验
        Map<String, Object> tbAcctMap = new HashMap<String, Object>();
        tbAcctMap.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        tbAcctMap.put(BaseUnitConstants.TABLE_ACCT, editFormAcctVo.getAcct());
        List<TbAcct> tbAcctList = tbAcctService.selectByMap(tbAcctMap);
        if (tbAcctList.size() > 0) {
            return ResultUtils.error(EumUserResponeCode.ACCT_IS_EXIST);
        }
        Long acctId = tbAcctService.getId();
        Long userId = editFormAcctVo.getUserId();
        Object obj = tbAcctService.insertOrUpdateTbAcct(editFormAcctVo, null, acctId, userId);
        if(!StrUtil.isNullOrEmpty(obj)){
            return obj;
        }

        //角色
        if(!StrUtil.isNullOrEmpty(editFormAcctVo.getTbRolesList())){
            tbUserRoleService.saveUserRole(editFormAcctVo.getTbRolesList(), acctId, 1L, userId);
        }
        //组织
        tbAccountOrgRelService.saveAcctOrg(editFormAcctVo.getAcctOrgVoList(), acctId, userId);

        rabbitMqService.sendMqMsg("person", "insert", "personnelId", editFormAcctVo.getPersonnelId());
        return ResultUtils.success(null);
    }

    @ApiOperation(value = "删除主账号信息", notes = "主账号相关信息删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "操作人标识", required = true, dataType = "Long", paramType = "path")
    })
    @UooLog(value = "删除主账号信息", key = "deleteTbAcct")
    @RequestMapping(value = "/delTbAcctByPsnId", method = RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public Object delTbAcctByPsnId(Long personnelId, Long userId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_PERSONNEL_ID, personnelId);
        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().allEq(map));
        if(!StrUtil.isNullOrEmpty(tbAcct)){
            removeAcct(tbAcct.getAcctId(), userId);
        }
        return null;
    }

    @ApiOperation(value = "删除主账号信息", notes = "主账号相关信息删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acctID", value = "主账号标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "操作人标识", required = true, dataType = "Long", paramType = "path")
    })
    @UooLog(value = "删除主账号信息", key = "deleteTbAcct")
    @RequestMapping(value = "/deleteTbAcct", method = RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public Object removeAcct(Long acctId, Long userId) {

        // 1.删除主账号
        tbAcctService.removeTbAcct(acctId, userId);

        //2、删除角色
        tbUserRoleService.removeUserRole(acctId, 1L, userId);

        //3、删除组织
        tbAccountOrgRelService.removeAcctOrg(null, acctId, null, null, userId);

        TbAcct tbAcct = tbAcctService.selectById(acctId);

        //从账号关联信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_ACCT_ID, tbAcct.getAcctId());
        List<TbSlaveAcct> tbSlaveAccts = tbSlaveAcctService.selectByMap(map);
        if(tbSlaveAccts != null && tbSlaveAccts.size() > 0 ){
            for(TbSlaveAcct tbSlaveAcct : tbSlaveAccts){
                tbSlaveAcctService.delAllTbSlaveAcct(tbSlaveAcct.getSlaveAcctId(), userId);
            }
        }
        rabbitMqService.sendMqMsg("person", "delete", "personnelId", tbAcct.getPersonnelId());

        return ResultUtils.success(null);
    }

    @ApiOperation(value = "修改主账号",notes = "主账号修改")
    @ApiImplicitParam(name = "editFormAcctVo", value = "主账号信息", required = true, dataType = "EditFormAcctVo")
    @UooLog(value = "修改主账号",key = "updateAcct")
    @RequestMapping(value = "/updateAcct", method = RequestMethod.PUT)
    public Object updateAcct(@RequestBody EditFormAcctVo editFormAcctVo) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_ACCT_ID, editFormAcctVo.getAcctId());
        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().allEq(map));
        if (StrUtil.isNullOrEmpty(tbAcct)) {
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST);
        }
        Long userId = editFormAcctVo.getUserId();
        Object obj = tbAcctService.insertOrUpdateTbAcct(editFormAcctVo, tbAcct, tbAcct.getAcctId(), userId);
        if(!StrUtil.isNullOrEmpty(obj)){
            return obj;
        }

        //角色
        List<TbRoles> oldTbRolesList = tbAcctService.getTbRoles(1L,tbAcct.getAcctId());
        tbUserRoleService.updateUserRole(editFormAcctVo.getTbRolesList(), oldTbRolesList, tbAcct.getAcctId(), 1L, userId);

        rabbitMqService.sendMqMsg("person", "update", "personnelId", tbAcct.getPersonnelId());

        return ResultUtils.success(null);

    }

    @ApiOperation(value = "主账号信息",notes = "主账号信息")
    @ApiImplicitParam(name = "acct", value = "主账号", required = true, dataType = "String", paramType = "path")
    @UooLog(value = "主账号信息",key = "getTbAcct")
    @RequestMapping(value = "/getTbAcct", method = RequestMethod.GET)
    public ResponseResult<TbAcct> getTbAcct(String acct){
        ResponseResult<TbAcct> result = new ResponseResult<TbAcct>();
        if(StrUtil.isNullOrEmpty(acct)){
            result.setState(EumUserResponeCode.ACCT_NOT_NULL.getState());
            result.setMessage(EumUserResponeCode.ACCT_NOT_NULL.getMessage());
            return result;
        }
        TbAcct tbAcct = tbAcctService.getTbAcctByAcct(acct);
        if(StrUtil.isNullOrEmpty(tbAcct)){
            result.setState(EumUserResponeCode.ACCT_NO_EXIST.getState());
            result.setMessage(EumUserResponeCode.ACCT_NO_EXIST.getMessage());
            return result;
        }
        result.setState(EumUserResponeCode.USER_RESPONSE_SUCCESS.getState());
        result.setMessage(EumUserResponeCode.USER_RESPONSE_SUCCESS.getMessage());
        result.setData(tbAcct);
        return result;
    }

}

