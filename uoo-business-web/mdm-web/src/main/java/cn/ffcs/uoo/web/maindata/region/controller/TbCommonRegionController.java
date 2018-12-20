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
import cn.ffcs.uoo.web.maindata.region.dto.CommonRegionDTO;
import cn.ffcs.uoo.web.maindata.region.dto.TbCommonRegion;
import cn.ffcs.uoo.web.maindata.region.service.CommonRegionService;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 指对于各种专业电信管理区域的共性管理区域信息的抽象表达，包括省公司、本地网、营业区。因为使用目的不同，可以定义不同使用类型的管理区域，如：CRM使用、计费使用、营销使用等。
 * 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/region/commonRegion/")
public class TbCommonRegionController {
    @Autowired
    private CommonRegionService regionService;
    @OperateLog(type=OperateType.SELECT,module="公共管理区域模块",methods="获取子级信息",desc="")
    @ApiOperation(value = "根据ID获取下一级信息", notes = "根据ID获取下一级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path"), })
    @GetMapping("getChildCommonRegionInfo/{id}")
    public ResponseResult getChildCommonRegionInfo(@PathVariable(value="id") Long id){
        return regionService.getChildCommonRegionInfo(id);
         
    }
    @OperateLog(type=OperateType.SELECT,module="公共管理区域模块",methods="获取树级信息",desc="")
    @ApiOperation(value = "公共管理区域树", notes = "公共管理区域树")
    @GetMapping("getTreeCommonRegion")
    public ResponseResult getTreeCommonRegion(HttpServletRequest request){
        String id_ = request.getParameter("id");
        long id=0;
        if(id_!=null){
            try {
                id=Long.valueOf(id_);
            } catch (Exception e) {
            }
        }
        return regionService.getTreeCommonRegion(id);
    }
    @OperateLog(type=OperateType.SELECT,module="公共管理区域模块",methods="获取单条数据",desc="")
    @ApiOperation(value = "根据ID获取单条数据", notes = "根据ID获取单条数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path"),
    })
    //@UooLog(value = "根据ID获取单条数据", key = "getCommonRegion")
    @GetMapping("getCommonRegion/id={id}")
    public ResponseResult getCommonRegion(@PathVariable(value = "id") Long id){
        return regionService.getCommonRegion(id);
    }
    
    @OperateLog(type=OperateType.ADD,module="公共管理区域模块",methods="新增",desc="")
    @ApiOperation(value = "新增公共管理区域", notes = "新增公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "CommonRegionDTO"), })
    //@UooLog(value = "新增公共管理区域", key = "addCommonRegion")
    @PostMapping("addCommonRegion")
    //@Transactional
    public ResponseResult addCommonRegion(CommonRegionDTO commonRegion) {
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        commonRegion.setOperateUser(currentLoginUser.getUserId());
        return regionService.addCommonRegion(commonRegion);
    }
    @OperateLog(type=OperateType.UPDATE,module="公共管理区域模块",methods="修改",desc="")
    @ApiOperation(value = "修改公共管理区域", notes = "修改公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "CommonRegionDTO"), })
    //@UooLog(value = "修改公共管理区域", key = "updateCommonRegion")
    @PostMapping("updateCommonRegion")
    //@Transactional
    public ResponseResult updateCommonRegion(CommonRegionDTO commonRegion) {
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        commonRegion.setOperateUser(currentLoginUser.getUserId());
        return regionService.updateCommonRegion(commonRegion);
    }
    @OperateLog(type=OperateType.DELETE,module="公共管理区域模块",methods="删除",desc="")
    @ApiOperation(value = "删除公共管理区域", notes = "删除公共管理区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commonRegion", value = "公共管理区域信息", required = true, dataType = "TbCommonRegion"), })
    //@UooLog(value = "删除公共管理区域", key = "deleteCommonRegion")
    @PostMapping("deleteCommonRegion")
    //@Transactional(rollbackFor=Exception.class)
    public ResponseResult deleteCommonRegion(TbCommonRegion commonRegion) {
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        commonRegion.setUpdateUser(currentLoginUser.getUserId());
        return regionService.deleteCommonRegion(commonRegion);
    }
}
