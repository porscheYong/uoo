package cn.ffcs.uoo.system.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.entity.SysUserDeptRef;
import cn.ffcs.uoo.system.entity.SysUserPositionRef;
import cn.ffcs.uoo.system.service.SysUserDeptRefService;
import cn.ffcs.uoo.system.service.SysUserPositionRefService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysUserDeptPositionVo;
import cn.ffcs.uoo.system.vo.SysUserPositionRefVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.ffcs.uoo.base.controller.BaseController;

/**
 * <p>
 * 对部门可选岗位的限定 前端控制器
 * </p>
 *
 * @author wudj
 * @since 2019-01-05
 */
@RestController
@RequestMapping("/sysUserDeptRef")
public class SysUserDeptRefController extends BaseController {

    @Autowired
    private SysUserDeptRefService sysUserDeptRefService;

    @Autowired
    private SysUserPositionRefService sysUserPositionRefService;

    @ApiOperation(value = "新增用户组织职位", notes = "新增用户组织职位")
    @ApiImplicitParam(name = "sysUserDeptPositionVo", value = "新增用户组织职位", required = true, dataType = "SysUserDeptPositionVo")
    @UooLog(value = "新增用户组织职位", key = "addUserDeptPositionDef")
    @RequestMapping(value = "/addUserDeptPositionDef", method = RequestMethod.POST)
    public Object addUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo){
        SysUserDeptRef sysUserDeptRef = new SysUserDeptRef();
        BeanUtils.copyProperties(userDeptPositionVo, sysUserDeptRef);
        String msg = sysUserDeptRefService.checkAllRegister(sysUserDeptRef);
        if(!StrUtil.isNullOrEmpty(msg)){
            return ResponseResult.createErrorResult(msg);
        }
        sysUserDeptRefService.addSysUserDeptRef(sysUserDeptRef);
        for(SysUserPositionRefVo userPositionRefVo : userDeptPositionVo.getUserPositionRefList()){
            SysUserPositionRef userPositionRef = new SysUserPositionRef();
            BeanUtils.copyProperties(userPositionRefVo, userPositionRef);
            userPositionRef.setUserCode(sysUserDeptRef.getUserCode());
            userPositionRef.setCreateUser(sysUserDeptRef.getCreateUser());
            userPositionRef.setUpdateUser(sysUserDeptRef.getUpdateUser());
            sysUserPositionRefService.addSysUserPositionRef(userPositionRef);
        }
        return ResponseResult.createSuccessResult("新增成功");
    }

    @ApiOperation(value = "更新用户组织职位", notes = "更新用户组织职位")
    @ApiImplicitParam(name = "sysUserDeptPositionVo", value = "更新用户组织职位", required = true, dataType = "SysUserDeptPositionVo")
    @UooLog(value = "更新用户组织职位", key = "updateUserDeptPositionDef")
    @RequestMapping(value = "/updateUserDeptPositionDef", method = RequestMethod.POST)
    public Object updateUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo){
        SysUserDeptRef sysUserDeptRef = new SysUserDeptRef();
        BeanUtils.copyProperties(userDeptPositionVo, sysUserDeptRef);
        String msg = sysUserDeptRefService.checkAllRegister(sysUserDeptRef);
        if(!StrUtil.isNullOrEmpty(msg)){
            ResponseResult.createErrorResult(msg);
        }
        sysUserDeptRefService.updateSysUserDeptRef(sysUserDeptRef);
        sysUserPositionRefService.updateSysUserPositionRef(userDeptPositionVo.getUserPositionRefList(),
                sysUserDeptRef.getUserCode(), sysUserDeptRef.getOrgCode(), sysUserDeptRef.getUpdateUser());
        return ResponseResult.createSuccessResult("修改成功");
    }

    @ApiOperation(value = "删除用户组织职位", notes = "删除用户组织职位")
    @ApiImplicitParam(name = "sysUserDeptPositionVo", value = "删除用户组织职位", required = true, dataType = "SysUserDeptPositionVo")
    @UooLog(value = "删除用户组织职位", key = "delUserDeptPositionDef")
    @RequestMapping(value = "/delUserDeptPositionDef", method = RequestMethod.DELETE)
    public Object delUserDeptPositionDef(@RequestBody SysUserDeptPositionVo userDeptPositionVo){
        SysUserDeptRef sysUserDeptRef = new SysUserDeptRef();
        BeanUtils.copyProperties(userDeptPositionVo, sysUserDeptRef);

        sysUserDeptRefService.delSysUserDeptRef(sysUserDeptRef);
        sysUserPositionRefService.delSysUserPositionRef(
                sysUserDeptRef.getUserCode(), sysUserDeptRef.getOrgCode(), sysUserDeptRef.getUpdateUser());

        return ResponseResult.createSuccessResult("删除成功");
    }


}

