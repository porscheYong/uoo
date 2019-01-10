package cn.ffcs.uoo.web.maindata.common.system.controller;


import cn.ffcs.uoo.web.maindata.common.system.client.SysFileClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysFileVo;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/sysFile")
public class SysFileController {

    @Resource
    private SysFileClient sysFileClient;

    @ApiOperation(value = "查询文件信息-web", notes = "查询文件信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getSysFilePage", method = RequestMethod.GET)
    public ResponseResult<Page<SysFileVo>> getSysFilePage(@RequestParam(value = "search",required = false)String search,
                                                          @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                          @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                          @RequestParam(value = "userId",required = false)Long userId,
                                                          @RequestParam(value = "accout",required = false)String accout){

        return sysFileClient.getSysFilePage(search,pageSize,pageNo,userId,accout);
    }


    @ApiOperation(value = "查询文件信息-web", notes = "查询文件信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getSysFile", method = RequestMethod.GET)
    public ResponseResult<SysFileVo> getSysFile(@RequestParam(value = "id",required = false)String id,
                                                @RequestParam(value = "userId",required = false)Long userId,
                                                @RequestParam(value = "accout",required = false)String accout){
        return sysFileClient.getSysFile(id,userId,accout);
    }

    @ApiOperation(value = "新增系统文件", notes = "新增系统文件")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addSysFile", method = RequestMethod.POST)
    public ResponseResult<String> addSysFile(@RequestBody SysFileVo sysFileVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysFileVo.setUserId(currentLoginUser.getUserId());
        sysFileVo.setAccout(currentLoginUser.getAccout());
        return sysFileClient.addSysFile(sysFileVo);
    }

    @ApiOperation(value = "更新系统文件", notes = "更新系统文件")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateSysFile", method = RequestMethod.POST)
    public ResponseResult<String> updateSysFile(@RequestBody SysFileVo sysFileVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysFileVo.setUserId(currentLoginUser.getUserId());
        sysFileVo.setAccout(currentLoginUser.getAccout());
        return sysFileClient.updateSysFile(sysFileVo);
    }


    @ApiOperation(value = "删除系统文件", notes = "删除系统文件")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/deleteSysFile", method = RequestMethod.POST)
    public ResponseResult<String> deleteSysFile(@RequestBody SysFileVo sysFileVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysFileVo.setUserId(currentLoginUser.getUserId());
        sysFileVo.setAccout(currentLoginUser.getAccout());
        return sysFileClient.deleteSysFile(sysFileVo);
    }
}

