package cn.ffcs.uoo.web.maindata.common.system.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.common.system.client.SysFunctionClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysFunction;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.realm.LoadUrlPermissionService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/system/sysFunction")
public class SysFunctionController {
    @Autowired
    SysFunctionClient funcClient;
    @Autowired
    LoadUrlPermissionService reloadPermSvc;
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
    })
    @OperateLog(type=OperateType.SELECT,module="平台系统功能模块",methods="分页查询",desc="")
    @RequestMapping("/listAll")
    public ResponseResult<Page<SysFunction>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord){
        return funcClient.list(pageNo, pageSize, keyWord);
    }
    @ApiOperation(value = "查询当前账号的功能", notes = "查询当前账号的功能")
    @ApiImplicitParams({
    })
    @OperateLog(type=OperateType.SELECT,module="平台系统功能模块",methods="查询当前账号下的",desc="")
    @RequestMapping("/getFunctionByAccout")
    public ResponseResult<List<SysFunction>> getFunctionByAccout(String accout){
        return funcClient.getFunctionByAccout(accout);
    }
    @OperateLog(type=OperateType.SELECT,module="平台系统功能模块",methods="GET",desc="")
    @ApiOperation(value = "get", notes = "get")
    @ApiImplicitParams({
    })
    @RequestMapping("/get")
    public ResponseResult<SysFunction> get(@RequestParam("id")Long id){
        return funcClient.get(id);
    }
    @OperateLog(type=OperateType.ADD,module="平台系统功能模块",methods="新增",desc="")
    @ApiOperation(value = "新增", notes = "新增")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysFunction fun){ 
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        fun.setCreateUser(sysuser.getUserId());
        ResponseResult<Void> result = funcClient.add(fun);
        if(result.getState()==1000){
            //reloadPermSvc.notifyAllWebNodeUpdateUrlPermission();
        }
        return result;
    }
    @OperateLog(type=OperateType.UPDATE,module="平台系统功能模块",methods="更新",desc="")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody SysFunction fun){
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        fun.setUpdateUser(sysuser.getUserId());
        ResponseResult<Void> result = funcClient.update(fun);
        if(result.getState()==1000){
            //reloadPermSvc.notifyAllWebNodeUpdateUrlPermission();
        }
        return result;
    }
    @OperateLog(type=OperateType.DELETE,module="平台系统功能模块",methods="删除",desc="")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public ResponseResult<Void> delete(@RequestBody SysFunction fun){
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser= (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        fun.setUpdateUser(sysuser.getUserId());
        ResponseResult<Void> result = funcClient.delete(fun);
        if(result.getState()==1000){
            //reloadPermSvc.notifyAllWebNodeUpdateUrlPermission();
        }
        return result;
    }
}
