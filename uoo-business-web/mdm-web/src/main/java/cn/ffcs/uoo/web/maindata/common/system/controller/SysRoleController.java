/**
 * Copyright (C), 2017-2018, 中电福富信息科技有限公司
 * FileName: SysRoleController
 * Author:   linmingxu
 * Date:     2018/12/4 15:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.ffcs.uoo.web.maindata.common.system.controller;

import java.util.List;

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

import cn.ffcs.uoo.web.maindata.common.system.client.SysRoleClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysRole;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysRoleDTO;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author linmingxu
 * @create 2018/12/4
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system/sysRole")
public class SysRoleController {
    @Autowired
    SysRoleClient sysRoleClient;
    @OperateLog(type=OperateType.SELECT,module="平台系统角色模块",methods="角色树",desc="")
    @ApiOperation(value = "角色树", notes = "角色树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "parentId", required = false, dataType = "Long"  ),
    })
    @GetMapping("/treeRole")
    public ResponseResult<List<TreeNodeVo>> treeRole( @RequestParam("id") Long id){
        return sysRoleClient.treeRole(id);
    }
    
    @OperateLog(type=OperateType.SELECT,module="平台系统角色模块",methods="分页查询角色列表",desc="")
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
    })
    @GetMapping("/listPage")
    public ResponseResult<Page<SysRoleDTO>> listPage(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord") String keyWord,@RequestParam("parentRoleCode")String parentRoleCode,@RequestParam("includChild")Integer includChild){
        return sysRoleClient.listPage(pageNo, pageSize, keyWord, parentRoleCode, includChild);
    }
    @OperateLog(type=OperateType.UPDATE,module="平台系统角色模块",methods="更新",desc="")
    @ApiOperation(value = "修改",notes = "修改")
    @ApiImplicitParam(name = "sysRole", value = "修改", required = true, dataType = "SysRoleDTO")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody SysRoleDTO sysRole) {
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser= (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysRole.setUpdateUser(sysuser.getUserId());
        return sysRoleClient.update(sysRole);
    }
    @OperateLog(type=OperateType.ADD,module="平台系统角色模块",methods="新增",desc="")
    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysRole", value = "新增", required = true, dataType = "SysRoleDTO")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysRoleDTO sysRole) {
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser= (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysRole.setCreateUser(sysuser.getUserId());
        return sysRoleClient.add(sysRole);
    }
    @OperateLog(type=OperateType.DELETE,module="平台系统角色模块",methods="删除",desc="")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "sysRole", required = true, dataType = "SysRole"  ),
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult<Void> deleteRole(@RequestBody SysRole sysRole) {
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser= (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysRole.setUpdateUser(sysuser.getUserId());
        return sysRoleClient.deleteRole(sysRole);
    }
    @OperateLog(type=OperateType.SELECT,module="平台系统角色模块",methods="获取单个数据",desc="")
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("/get/{id}")
    public ResponseResult<SysRoleDTO> get(@PathVariable(value="id" ,required=true) Long id){
        return sysRoleClient.get(id);
    }
}
