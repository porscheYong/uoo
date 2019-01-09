package cn.ffcs.uoo.web.maindata.common.system.controller;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
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

import cn.ffcs.uoo.web.maindata.common.system.client.SysMenuClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysMenu;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysMenuVO;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/system")
public class SysMenuController {
    @Autowired
    private SysMenuClient sysMenuClient;
    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    //type 必填，module必填，methods必填，desc选填
    @OperateLog(type=OperateType.SELECT,module="平台系统菜单模块",methods="查询账号所有菜单",desc="获取当前所有菜单")
    @ApiOperation(value = "查询账号所有菜单", notes = "查询账号所有菜单")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getAccoutMenu", method = RequestMethod.GET)
    public ResponseResult<List<SysMenu>> getAccoutMenu(){
        Subject sub=SecurityUtils.getSubject();
        Object primaryPrincipal = sub.getPrincipals().getPrimaryPrincipal();
        //alterPwdDTO.setAccout(primaryPrincipal.toString());
        //int i=1/0;
        ResponseResult<List<SysMenu>> alterPwd = sysMenuClient.getMenuByAccout(primaryPrincipal.toString());
        return alterPwd;
    }
    @OperateLog(type=OperateType.SELECT,module="平台系统菜单模块",methods="查询单个菜单",desc="获取单个菜单")
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("/sysMenu/get/{id}")
    public ResponseResult<SysMenuVO> get(@PathVariable(value="id" ,required=true) Long id){
        return sysMenuClient.get(id);
    }
    @OperateLog(type=OperateType.SELECT,module="平台系统菜单模块",methods="查询分页菜单",desc="获取分页菜单")
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("/sysMenu/listPage")
    public ResponseResult<Page<SysMenu>> listPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord") String keyWord){
        return sysMenuClient.listPage(pageNo, pageSize, keyWord);
    }
    @OperateLog(type=OperateType.UPDATE,module="平台系统菜单模块",methods="更新菜单",desc="更新菜单")
    @ApiOperation(value = "修改",notes = "修改")
    @ApiImplicitParam(name = "sysMenu", value = "修改", required = true, dataType = "SysMenu")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody SysMenu sysMenu) {
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysMenu.setUpdateUser(sysuser.getUserId());
        return sysMenuClient.update(sysMenu);
    }
    @OperateLog(type=OperateType.ADD,module="平台系统菜单模块",methods="新增菜单",desc="新增菜单")
    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysMenu", value = "新增", required = true, dataType = "SysMenu")
    @RequestMapping(value = "/sysMenu/add", method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysMenu sysMenu) {
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysMenu.setCreateUser(sysuser.getUserId());
        return sysMenuClient.add(sysMenu);
    }
    @OperateLog(type=OperateType.UPDATE,module="平台系统菜单模块",methods="删除菜单",desc="删除菜单")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenu", value = "sysMenu", required = true, dataType = "sysMenu"  ),
    })
    @RequestMapping(value = "/sysMenu/delete", method = RequestMethod.POST)
    public ResponseResult<Void> deletePrivilege(@RequestBody SysMenu sysMenu) {
        return sysMenuClient.deletePrivilege(sysMenu);
    }
}
