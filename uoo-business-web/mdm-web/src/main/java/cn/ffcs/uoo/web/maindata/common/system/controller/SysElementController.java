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

import cn.ffcs.uoo.web.maindata.common.system.client.SysElementClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysElement;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysElementVO;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/system/SysElement")
public class SysElementController {
    @Autowired
    SysElementClient eleClient;
    @ApiOperation(value = "分页查询元素", notes = "分页查询元素")
    @ApiImplicitParams({
    })
    @OperateLog(type=OperateType.SELECT,module="平台系统元素模块",methods="分页查询",desc="")
    @RequestMapping("/list")
    public ResponseResult<List<SysElement>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord){
        return eleClient.list(pageNo, pageSize, keyWord);
    }
    @OperateLog(type=OperateType.SELECT,module="平台系统元素模块",methods="查询账号下的元素",desc="")
    @ApiOperation(value = "查询账号下的元素", notes = "查询账号下的元素")
    @ApiImplicitParams({
    })
    @RequestMapping("/getElementByAccout")
    public ResponseResult<List<SysElement>> getElementByAccout(String accout){
        return eleClient.getElementByAccout(accout);
    }
    @OperateLog(type=OperateType.SELECT,module="平台系统元素模块",methods="查询单个数据",desc="")
    @ApiOperation(value = "get", notes = "get")
    @ApiImplicitParams({
    })
    @RequestMapping("/get")
    public ResponseResult<SysElementVO> get(@RequestParam("id")Long id){
         
        return eleClient.get(id);
    }
    @OperateLog(type=OperateType.ADD,module="平台系统元素模块",methods="新增",desc="")
    @ApiOperation(value = "新增", notes = "新增")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysElement ele){
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        ele.setCreateUser(sysuser.getUserId());
        return eleClient.add(ele);
    }
    @OperateLog(type=OperateType.UPDATE,module="平台系统元素模块",methods="更新",desc="")
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody SysElement ele){
        Subject subject=SecurityUtils.getSubject();
        SysUser sysuser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        ele.setUpdateUser(sysuser.getUserId());
        return eleClient.update(ele);
    }
    @OperateLog(type=OperateType.DELETE,module="平台系统元素模块",methods="删除",desc="")
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
    })
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public ResponseResult<Void> delete(@RequestBody SysElement fun){
        return eleClient.delete(fun);
    }
}
