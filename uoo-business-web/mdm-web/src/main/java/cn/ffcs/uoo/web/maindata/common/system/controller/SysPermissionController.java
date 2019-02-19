package cn.ffcs.uoo.web.maindata.common.system.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.common.system.client.SysPermissionClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysPermission;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysPermissionDTO;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysPermissionEditDTO;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysPermissionPrivDTO;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@RestController
@RequestMapping("/system/sysPermission")
public class SysPermissionController {
    @Autowired
    SysPermissionClient permSvc;
     
    @OperateLog(type=OperateType.SELECT,module="平台系统权限模块",methods="查看权限",desc="")
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("/get/{id}")
    public ResponseResult<SysPermissionPrivDTO> get(@PathVariable(value="id" ,required=true) Long id){
        return permSvc.get(id);
    }
    @OperateLog(type=OperateType.SELECT,module="平台系统权限模块",methods="分页查看权限",desc="")
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("/listPage")
    public ResponseResult<Page<SysPermissionDTO>> listPage(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord")String keyWord){
        return permSvc.listPage(pageNo, pageSize, keyWord);
    }
    @OperateLog(type=OperateType.ADD,module="平台系统权限模块",methods="新增权限",desc="")
    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysPermissionEditDTO", value = "新增", required = true, dataType = "SysPermissionEditDTO")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysPermissionEditDTO sysPermissionEditDTO) {
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser= (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysPermissionEditDTO.setCreateUser(sysuser.getUserId());
        return permSvc.add(sysPermissionEditDTO);
    }
    @OperateLog(type=OperateType.UPDATE,module="平台系统权限模块",methods="修改权限",desc="")
    @ApiOperation(value = "修改",notes = "修改")
    @ApiImplicitParam(name = "sysPermissionEditDTO", value = "修改", required = true, dataType = "SysPermissionEditDTO")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody SysPermissionEditDTO sysPermissionEditDTO) {
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser= (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysPermissionEditDTO.setUpdateUser(sysuser.getUserId());
        return permSvc.update(sysPermissionEditDTO);
    }
    @OperateLog(type=OperateType.DELETE,module="平台系统权限模块",methods="删除权限",desc="")
    @ApiOperation(value = "删除",notes = "删除")
    @ApiImplicitParam(name = "id", value = "删除", required = true, dataType = "Long",paramType="path")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseResult<Void> delete(@PathVariable(value="id" ,required=true) Long id){
        SysPermission perm=new SysPermission();
        perm.setPermissionId(id);
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser= (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        perm.setUpdateUser(sysuser.getUserId());
        return permSvc.delete(perm);
    }
}

