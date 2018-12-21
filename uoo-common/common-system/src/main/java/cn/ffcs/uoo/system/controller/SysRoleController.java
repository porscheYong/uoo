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
package cn.ffcs.uoo.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysRole;
import cn.ffcs.uoo.system.entity.SysRolePermissionRef;
import cn.ffcs.uoo.system.service.ISysDeptRoleRefService;
import cn.ffcs.uoo.system.service.ISysPositiontRoleRefService;
import cn.ffcs.uoo.system.service.ISysRolePermissionRefService;
import cn.ffcs.uoo.system.service.ISysUserRoleRefService;
import cn.ffcs.uoo.system.service.SysRoleService;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysRoleDTO;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
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
    SysRoleService sysRoleService;
    @Autowired
    ISysDeptRoleRefService deptRoleRefSvc;
    @Autowired
    ISysPositiontRoleRefService posiRoleRefSvc;
    @Autowired
    ISysRolePermissionRefService rolePermRefSvc;
    @Autowired
    ISysUserRoleRefService userRoleRefSvc;
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getRole",value="获取单个数据")
    @GetMapping("/get/{id}")
    public ResponseResult<SysRoleDTO> get(@PathVariable(value="id" ,required=true) Long id){
        
        SysRoleDTO Roles = sysRoleService.selectOne(id);
        return ResponseResult.createSuccessResult(Roles, "success");
    }
    @ApiOperation(value = "角色树", notes = "角色树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentRoleCode", value = "parentRoleCode", required = false, dataType = "String"  ),
    })
    @UooLog(key="treeRole",value="treeRole")
    @GetMapping("/treeRole")
    public ResponseResult<List<TreeNodeVo>> treeRole( String parentRoleCode){
        Wrapper<SysRole> wrapper=Condition.create();
        if("null".equals(parentRoleCode)||StringUtils.isBlank(parentRoleCode)){
            wrapper.eq("STATUS_CD", StatusCD.VALID).isNull("PARENT_ROLE_CODE");
        }else{
            wrapper.eq("STATUS_CD", StatusCD.VALID).eq("PARENT_ROLE_CODE", parentRoleCode);
        }
        List<SysRole> selectList = sysRoleService.selectList(wrapper);
        List<TreeNodeVo> list=new ArrayList<>();
        if(selectList!=null)
        for (SysRole sysRole : selectList) {
            TreeNodeVo vo=new TreeNodeVo();
            vo.setExtField1(sysRole.getRoleId().toString());
            vo.setId(sysRole.getRoleCode());
            vo.setName(sysRole.getRoleName());
            vo.setPid(sysRole.getParentRoleCode());
            int i=sysRoleService.selectCount(Condition.create().eq("PARENT_ROLE_CODE", sysRole.getRoleCode()).eq("STATUS_CD", StatusCD.VALID));
            vo.setParent(i>0);
            list.add(vo);
        }
        return ResponseResult.createSuccessResult(list, "");
    }
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="listPage",value="获取分页列表")
    @GetMapping("/listPage")
    public ResponseResult<List<SysRoleDTO>> listPage(Integer pageNo, Integer pageSize,String keyWord,String parentRoleCode,Integer includChild){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        HashMap<String,Object> map=new HashMap<>();
        if(keyWord!=null&&keyWord.trim().length()>0){
            map.put("keyWord", "%"+keyWord+"%");
        }
        if(parentRoleCode!=null&&parentRoleCode.trim().length()>0){
            map.put("PARENT_ROLE_CODE",  parentRoleCode );
        }
        map.put("from", (pageNo-1)*pageSize);
        map.put("end", pageNo * pageSize);
        if(includChild==1){
            
            map.put("includeChild",includChild);
        } 
        List<SysRoleDTO> Roles=sysRoleService.findList(map);
        Long count = sysRoleService.countList(map);
        
        ResponseResult<List<SysRoleDTO>> createSuccessResult = ResponseResult.createSuccessResult(Roles, "");
        createSuccessResult.setTotalRecords(count);
        return createSuccessResult;
    }

    @ApiOperation(value = "修改",notes = "修改")
    @ApiImplicitParam(name = "sysRole", value = "修改", required = true, dataType = "Roles")
    @UooLog(value = "修改角色", key = "updateTbRoles")
    @Transactional
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody SysRoleDTO sysRole) {
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        // 校验必填项
        if(sysRole.getRoleId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入id");
            return responseResult;
        }
        sysRole.setUpdateDate(new Date());
        SysRole obj=new SysRole();
        BeanUtils.copyProperties(sysRole, obj);
        sysRoleService.updateById(obj);
        rolePermRefSvc.delete(Condition.create().eq("ROLE_CODE", sysRole.getRoleCode()));
        String permissionCodes = sysRole.getPermissionCodes();
        if(permissionCodes!=null&&!permissionCodes.trim().isEmpty()){
            String[] split = permissionCodes.split(",");
            for (String s : split) {
                SysRolePermissionRef entity=new SysRolePermissionRef();
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysRole.getCreateUser());
                entity.setPermissionCode(s);
                entity.setRoleCode(sysRole.getRoleCode());
                entity.setRolePermissionRefId(rolePermRefSvc.getId());
                rolePermRefSvc.insert(entity);
            }
        }
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("修改成功");
        return responseResult;
    }

    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysRole", value = "新增", required = true, dataType = "SysRole")
    @UooLog(value = "新增", key = "add")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysRoleDTO sysRole) {
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        sysRole.setCreateDate(new Date());
        sysRole.setRoleId((sysRoleService.getId()));
        sysRole.setStatusCd(StatusCD.VALID);
        sysRole.setStatusDate(new Date());
        SysRole obj=new SysRole();
        BeanUtils.copyProperties(sysRole, obj);
        sysRoleService.insert(obj);
        String permissionCodes = sysRole.getPermissionCodes();
        if(permissionCodes!=null&&!permissionCodes.trim().isEmpty()){
            String[] split = permissionCodes.split(",");
            for (String s : split) {
                SysRolePermissionRef entity=new SysRolePermissionRef();
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysRole.getCreateUser());
                entity.setPermissionCode(s);
                entity.setRoleCode(sysRole.getRoleCode());
                entity.setRolePermissionRefId(rolePermRefSvc.getId());
                rolePermRefSvc.insert(entity);
            }
        }
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增成功");
        return responseResult;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "sysRole", required = true, dataType = "sysRole"  ),
    })
    @UooLog(key="delete=",value="删除")
    @Transactional
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult<Void> deleteRole(@RequestBody SysRole sysRole) {
        Long RoleId = sysRole.getRoleId();
        if (RoleId == null) {
            return ResponseResult.createErrorResult("无效数据");
        }
        SysRole obj = sysRoleService.selectById(RoleId);
        if (obj == null ) {
            return ResponseResult.createErrorResult("不能删除无效数据");
        }
        obj.setStatusCd(StatusCD.INVALID);
        obj.setStatusDate(new Date());
        obj.setUpdateDate(new Date());
        sysRoleService.updateById(obj);
        //删除角色的关联信息
        rolePermRefSvc.delete(Condition.create().eq("ROLE_CODE", obj.getRoleCode()));
        deptRoleRefSvc.delete(Condition.create().eq("ROLE_CODE", obj.getRoleCode()));
        posiRoleRefSvc.delete(Condition.create().eq("ROLE_CODE", obj.getRoleCode()));
        userRoleRefSvc.delete(Condition.create().eq("ROLE_CODE", obj.getRoleCode()));
        return ResponseResult.createSuccessResult("success");
    }
}
