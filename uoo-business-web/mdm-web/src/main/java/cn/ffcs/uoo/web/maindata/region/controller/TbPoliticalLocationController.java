package cn.ffcs.uoo.web.maindata.region.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.log.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.log.OperateType;
import cn.ffcs.uoo.web.maindata.region.dto.TbPoliticalLocation;
import cn.ffcs.uoo.web.maindata.region.service.PoliticalLocationService;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 行政区域 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/region/politicalLocation")
public class TbPoliticalLocationController  {
    @Autowired
    private PoliticalLocationService polLocSvc;
    @OperateLog(type=OperateType.SELECT,module="行政管理区域",methods="获取子级信息",desc="")
    @ApiOperation(value = "根据ID获取下一级信息", notes = "根据ID获取下一级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path"), })
    //@UooLog(value = "根据ID获取下一级信息", key = "getChildPoliticalLocationInfo")
    @GetMapping("getChildPoliticalLocationInfo/{id}")
    public ResponseResult getChildPoliticalLocationInfo(@PathVariable(value="id") Long id){
        return polLocSvc.getChildPoliticalLocationInfo(id);
    }
    @OperateLog(type=OperateType.SELECT,module="行政管理区域",methods="获取树级信息",desc="")
    @ApiOperation(value = "行政区域树", notes = "行政区域树")
    //@UooLog(value = "行政区域树", key = "getTreePoliticalLocation")
    @GetMapping("getTreePoliticalLocation")
    public ResponseResult getTreePoliticalLocation(HttpServletRequest request){
        String id_ = request.getParameter("id");
        long id=0;
        if(id_!=null){
            try {
                id=Long.valueOf(id_);
            } catch (Exception e) {
            }
        }
        return polLocSvc.getTreePoliticalLocation(id);
    }
    @OperateLog(type=OperateType.SELECT,module="行政管理区域",methods="获取单条信息",desc="")
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    //@UooLog(value = "根据ID获取单条数据", key = "getPoliticalLocation")
    @GetMapping("getPoliticalLocation/id={id}")
    public ResponseResult getPoliticalLocation(@PathVariable(value = "id") Long id){
         
        return polLocSvc.getPoliticalLocation(id);
    }
    @OperateLog(type=OperateType.SELECT,module="行政管理区域",methods="获取分页信息",desc="")
    @ApiOperation(value = "行政区域列表", notes = "行政区域列表")
    //@UooLog(value = "行政区域列表", key = "listAllPoliticalLocation")
    @GetMapping("listAllPoliticalLocation")
    public ResponseResult listAllPoliticalLocation() {
        //只查询有效的
        return polLocSvc.listAllPoliticalLocation();
    }
    @OperateLog(type=OperateType.ADD,module="行政管理区域",methods="新增",desc="")
    @ApiOperation(value = "新增行政区域", notes = "新增行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    //@UooLog(value = "新增行政区域", key = "addPoliticalLocation")
    @PostMapping("addPoliticalLocation")
    //@Transactional
    public ResponseResult addPoliticalLocation(TbPoliticalLocation polLoc) {
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        polLoc.setCreateUser(currentLoginUser.getUserId());
        return polLocSvc.addPoliticalLocation(polLoc);
    }
    @OperateLog(type=OperateType.UPDATE,module="行政管理区域",methods="修改",desc="")
    @ApiOperation(value = "修改行政区域", notes = "修改行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    //@UooLog(value = "修改行政区域", key = "updatePoliticalLocation")
    @PostMapping("updatePoliticalLocation")
    //@Transactional
    public ResponseResult updatePoliticalLocation(TbPoliticalLocation polLoc) {
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        polLoc.setUpdateUser(currentLoginUser.getUserId());
        return polLocSvc.updatePoliticalLocation(polLoc);
    }
    @OperateLog(type=OperateType.DELETE,module="行政管理区域",methods="删除",desc="")
    @ApiOperation(value = "删除行政区域", notes = "删除行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "polLoc", value = "行政区域信息", required = true, dataType = "TbPoliticalLocation"), })
    //@UooLog(value = "删除行政区域", key = "deletePoliticalLocation")
    @PostMapping("deletePoliticalLocation")
    //@Transactional(rollbackFor=Exception.class)
    public ResponseResult deletePoliticalLocation(TbPoliticalLocation polLoc) {
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        polLoc.setUpdateUser(currentLoginUser.getUserId());
        return polLocSvc.deletePoliticalLocation(polLoc);
    }
}