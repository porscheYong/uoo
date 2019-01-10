package cn.ffcs.uoo.web.maindata.common.system.controller;

import cn.ffcs.uoo.web.maindata.common.system.client.SysDataRuleClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysDataRule;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysTable;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysTableColumn;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.DataRuleRequestVO;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysDataRuleVo;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
只有需要权限控制的表才进行登记 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-21
 */
@RestController
@RequestMapping("/system/sysDataRule")
public class SysDataRuleController {

    @Resource
    private SysDataRuleClient sysDataRuleClient;



    @ApiOperation(value = "获取数据权限翻页", notes = "获取数据权限翻页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getDataRulePage", method = RequestMethod.GET)
    public ResponseResult<Page<SysDataRuleVo>> getDataRulePage(@RequestParam(value = "search",required = false)String search,
                                                               @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                               @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                               @RequestParam(value = "userId",required = false)Long userId,
                                                               @RequestParam(value = "accout",required = false)String accout){

        return sysDataRuleClient.getDataRulePage(search,pageSize,pageNo,userId,accout);

    }



    @ApiOperation(value = "获取数据权限", notes = "获取数据权限")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getDataRule", method = RequestMethod.GET)
    public ResponseResult<SysDataRuleVo> getDataRule(@RequestParam(value = "id",required = false)String id,
                                                     @RequestParam(value = "userId",required = false)Long userId,
                                                     @RequestParam(value = "accout",required = false)String accout){
        return  sysDataRuleClient.getDataRule(id,userId,accout);
    }

    @ApiOperation(value = "新增数据权限", notes = "新增数据权限")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addDataRule", method = RequestMethod.POST)
    public ResponseResult<String> addDataRule(@RequestBody SysDataRuleVo sysDataRuleVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysDataRuleVo.setUserId(currentLoginUser.getUserId());
        sysDataRuleVo.setAccout(currentLoginUser.getAccout());
       return sysDataRuleClient.addDataRule(sysDataRuleVo);
    }


    @ApiOperation(value = "编辑数据权限", notes = "编辑数据权限")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateDataRule", method = RequestMethod.POST)
    public ResponseResult<String> updateDataRule(@RequestBody SysDataRuleVo sysDataRuleVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysDataRuleVo.setUserId(currentLoginUser.getUserId());
        sysDataRuleVo.setAccout(currentLoginUser.getAccout());
        return sysDataRuleClient.updateDataRule(sysDataRuleVo);
    }


    @ApiOperation(value = " ", notes = "获取列名")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getTabColumn", method = RequestMethod.GET)
    public ResponseResult<List<SysTableColumn>> getTabColumn(@RequestParam(value = "tabId",required = false)String tabId){
        return sysDataRuleClient.getTabColumn(tabId);
    }
    @ApiOperation(value = " ", notes = "获取表名")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getTab", method = RequestMethod.GET)
    public ResponseResult<List<SysTable>> getTab(){
        return sysDataRuleClient.getTab();
    }

    @ApiOperation(value = "删除数据权限", notes = "删除数据权限")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/deleteDataRule", method = RequestMethod.POST)
    public ResponseResult<String> deleteDataRule(@RequestBody SysDataRuleVo sysDataRuleVo){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysDataRuleVo.setUserId(currentLoginUser.getUserId());
        sysDataRuleVo.setAccout(currentLoginUser.getAccout());
        return sysDataRuleClient.deleteDataRule(sysDataRuleVo);
    }

}

