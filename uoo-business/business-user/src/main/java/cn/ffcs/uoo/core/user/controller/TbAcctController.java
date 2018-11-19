package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.*;
import cn.ffcs.uoo.core.user.service.*;
import cn.ffcs.uoo.core.user.util.*;
import cn.ffcs.uoo.core.user.vo.EditFormAcctVo;
import cn.ffcs.uoo.core.user.vo.FormAcctVo;
import cn.ffcs.uoo.core.user.vo.ListSlaveAcctOrgVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
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

import javax.annotation.Resource;
import java.util.Date;
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
    private TbUserRoleService tbUserRoleService;
    @Autowired
    private TbAccountOrgRelService tbAccountOrgRelService;




    @ApiOperation(value = "新增主账号信息", notes = "主账号信息新增")
    @ApiImplicitParam(name = "editFormAcctVo", value = "主账号信息", required = true, dataType = "EditFormAcctVo")
    @UooLog(value = "新增主账号信息", key = "addTbAcct")
    @RequestMapping(value = "/addTbAcct", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object saveAcct(@RequestBody EditFormAcctVo editFormAcctVo) {

        // 校验

        Map<String, Object> tbAcctMap = new HashMap<String, Object>();
        tbAcctMap.put("STATUS_CD", "1000");
        tbAcctMap.put("ACCT", editFormAcctVo.getAcct());
        List<TbAcct> tbAcctList = tbAcctService.selectByMap(tbAcctMap);
        if (tbAcctList.size() > 0) {
            return ResultUtils.error(EumUserResponeCode.ACCT_IS_EXIST);
        }

        TbAcct tbAcct = new TbAcct();
        tbAcct.setPersonnelId(editFormAcctVo.getPersonnelId());
        // 获取盐
        String salt = MD5Tool.getSalt();
        // 非对称密码
        String password = MD5Tool.md5Encoding(tbAcct.getPassword(), salt);
        // 对称密码
        String symmetryPassword = AESTool.AESEncode(tbAcct.getPassword());
        // 来源 todo
        Long acctId = tbAcctService.getId();

        tbAcct.setAcctId(acctId);
        tbAcct.setSalt(salt);
        tbAcct.setPassword(password);
        tbAcct.setSymmetryPassword(symmetryPassword);
        tbAcctService.insert(tbAcct);

        //角色
        tbUserRoleService.saveUserRole(editFormAcctVo.getTbRolesList(), acctId, 1L);

        //组织
        tbAccountOrgRelService.saveAcctOrg(editFormAcctVo.getAcctOrgVoList());

        return ResultUtils.success(null);
    }

    @ApiOperation(value = "删除主账号信息", notes = "主账号相关信息删除")
    @ApiImplicitParam(name = "acctID", value = "主账号标识", required = true, dataType = "Long", paramType = "path")
    @UooLog(value = "删除主账号信息", key = "deleteTbAcct")
    @RequestMapping(value = "/deleteTbAcct", method = RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public Object removeAcct(Long acctId) {

        // 1.删除主账号
        tbAcctService.removeTbAcct(acctId);

        //2、删除角色
        tbUserRoleService.removeUserRole(acctId, 1L);

        //3、删除组织
        tbAccountOrgRelService.removeAcctOrg(acctId, null);
        return ResultUtils.successfulTip(EumUserResponeCode.ACCT_IS_DELETE);
    }

    @ApiOperation(value = "修改主账号",notes = "主账号修改")
    @ApiImplicitParam(name = "editFormAcctVo", value = "主账号信息", required = true, dataType = "EditFormAcctVo")
    @UooLog(value = "修改主账号",key = "updateAcct")
    @RequestMapping(value = "/updateAcct", method = RequestMethod.PUT)
    public Object updateAcct(@RequestBody EditFormAcctVo editFormAcctVo) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_ACCT, editFormAcctVo.getAcct());
        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().allEq(map));
         if (StrUtil.isNullOrEmpty(tbAcct)) {
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST);
        }

        boolean isRight = MD5Tool.verify(editFormAcctVo.getPassword(), tbAcct.getSalt(), tbAcct.getPassword());
        if (isRight) {
            return ResultUtils.error(EumUserResponeCode.PWD_COMPARE_ERROR);
        }
        // 获取盐
        String salt = MD5Tool.getSalt();
        // 非对称密码
        String password = MD5Tool.md5Encoding(editFormAcctVo.getPassword(), salt);
        // 对称密码
        String symmetryPassword = AESTool.AESEncode(editFormAcctVo.getPassword());
        tbAcct.setSalt(salt);
        tbAcct.setPassword(password);
        tbAcct.setSymmetryPassword(symmetryPassword);
        tbAcct.updateById();

        //角色
        List<TbRoles> oldTbRolesList = tbAcctService.getTbRoles(1L,tbAcct.getAcctId());
        tbUserRoleService.updateUserRole(editFormAcctVo.getTbRolesList(), oldTbRolesList, tbAcct.getAcctId(), 1L);

        return ResultUtils.successfulTip(EumUserResponeCode.ACCT_UPDATE_SUCCESS);

    }

}

