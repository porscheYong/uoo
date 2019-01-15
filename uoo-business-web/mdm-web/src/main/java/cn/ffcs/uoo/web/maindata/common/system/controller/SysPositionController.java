package cn.ffcs.uoo.web.maindata.common.system.controller;


import cn.ffcs.uoo.web.maindata.common.system.client.SysPositionClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysPositionVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 根据不同部门的工作性质、责任轻重、难易程度和所需资格条件等进行分类，在平台上，不对职位进行过细的区分 前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@RestController
@RequestMapping("/sysPosition")
public class SysPositionController {

    @Resource
    private SysPositionClient sysPositionClient;

    @ApiOperation(value = "查询职位信息", notes = "查询职位信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getPositionTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getPositionTree(@RequestParam(value = "id",required = false)String id,
                                                            @RequestParam(value = "isSync",required = false)boolean isSync,
                                                            @RequestParam(value = "userId",required = false)Long userId,
                                                            @RequestParam(value = "accout",required = false)String accout){

        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return sysPositionClient.getPositionTree(id,isSync,userId,accout);
    }

    @ApiOperation(value = "查询职位下级", notes = "查询职位下级")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getPositionRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysPositionVo>> getPositionRelPage(@RequestParam(value = "positionCode",required = false)String positionCode,
                                                                  @RequestParam(value = "search",required = false)String search,
                                                                  @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                                  @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                                  @RequestParam(value = "isSearchlower",required = false)String isSearchlower,
                                                                  @RequestParam(value = "userId",required = false)Long userId,
                                                                  @RequestParam(value = "accout",required = false)String accout){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        userId = currentLoginUser.getUserId();
        accout = currentLoginUser.getAccout();
        return sysPositionClient.getPositionRelPage(positionCode,search,pageSize,pageNo,isSearchlower,
                                                   userId,accout);
    }

    @ApiOperation(value = "查询职位角色", notes = "查询职位角色")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getRolesByPositionId", method = RequestMethod.GET)
    public ResponseResult<HashMap<String,String>> getRolesByPositionId(@RequestParam(value = "positionId",required = false)String positionId){

        return sysPositionClient.getRolesByPositionId(positionId);
    }

    @ApiOperation(value = "查询职位", notes = "查询职位")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getPosition", method = RequestMethod.GET)
    public ResponseResult<SysPositionVo> getPosition(@RequestParam(value = "positionCode",required = false)String positionCode){

        return sysPositionClient.getPosition(positionCode);
    }

    @ApiOperation(value = "编辑职位", notes = "编辑职位")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updatePosition", method = RequestMethod.POST)
    public ResponseResult<String> updatePosition(@RequestBody  SysPositionVo sysPositionVo){

        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        sysPositionVo.setUserId(currentLoginUser.getUserId());
        sysPositionVo.setAccout(currentLoginUser.getAccout());

        return sysPositionClient.updatePosition(sysPositionVo);
    }


    @ApiOperation(value = "新增职位", notes = "新增职位")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addPosition", method = RequestMethod.POST)
    public ResponseResult<TreeNodeVo> addPosition(@RequestBody SysPositionVo pos){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        pos.setUserId(currentLoginUser.getUserId());
        pos.setAccout(currentLoginUser.getAccout());
        return sysPositionClient.addPosition(pos);
    }


    @ApiOperation(value = "删除职位", notes = "删除职位")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/deletePosition", method = RequestMethod.POST)
    public ResponseResult<String> deletePosition(@RequestBody SysPositionVo pos){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        pos.setUserId(currentLoginUser.getUserId());
        pos.setAccout(currentLoginUser.getAccout());
        return sysPositionClient.deletePosition(pos);
    }

}

