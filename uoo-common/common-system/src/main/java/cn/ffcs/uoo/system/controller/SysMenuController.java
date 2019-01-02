/**
 * Copyright (C), 2017-2018, 中电福富信息科技有限公司
 * FileName: SysMenuController
 * Author:   linmingxu
 * Date:     2018/12/4 15:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.ffcs.uoo.system.controller;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.entity.SysPermission;
import cn.ffcs.uoo.system.service.ISysPermissionMenuRelService;
import cn.ffcs.uoo.system.service.SysMenuService;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysMenuVO;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author linmingxu
 * @create 2018/12/4
 * @since 1.0.0
 */
@RestController
@RequestMapping("/system/sysMenu")
public class SysMenuController {
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    ISysPermissionMenuRelService permMenuSvc;
    

    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getMenu",value="获取单个数据")
    @GetMapping("/get/{id}")
    public ResponseResult<SysMenuVO> get(@PathVariable(value="id" ,required=true) Long id){
        SysMenu Menu = sysMenuService.selectById(id);
        if(Menu== null ){
            return ResponseResult.createErrorResult("无效数据");
        }
        SysMenuVO vo=new SysMenuVO();
        BeanUtils.copyProperties(Menu, vo);
        if(StringUtils.isNotBlank(vo.getParentMenuCode())){
            Wrapper<SysMenu> w=Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("MENU_CODE", vo.getParentMenuCode());
            List<SysMenu> list = sysMenuService.selectList(w);
            if(list!=null&&!list.isEmpty()){
                vo.setParentMenuName(list.get(0).getMenuName());
            }
        }
        return ResponseResult.createSuccessResult(vo, "success");
    }

    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="listPage",value="获取分页列表")
    @GetMapping("/listPage")
    public ResponseResult<List<SysMenu>> listPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord") String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;

        Wrapper<SysMenu> wrapper = Condition.create().eq("STATUS_CD", StatusCD.VALID);
        if(StringUtils.isNotBlank(keyWord)){
            wrapper.like("MENU_NAME", keyWord);
        }
        Page<SysMenu> page = sysMenuService.selectPage(new Page<SysMenu>(pageNo, pageSize), wrapper);

        return ResponseResult.createSuccessResult(page , "");
    }

    @ApiOperation(value = "修改",notes = "修改")
    @ApiImplicitParam(name = "sysMenu", value = "修改", required = true, dataType = "Roles")
    @UooLog(value = "修改", key = "updateTbRoles")
    @Transactional
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody SysMenu sysMenu) {
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        // 校验必填项
        if(sysMenu.getMenuId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入id");
            return responseResult;
        }
        String menuCode = sysMenu.getMenuCode();
        SysMenu one = sysMenuService.selectById(sysMenu.getMenuId());
        if(one==null){
            return ResponseResult.createErrorResult("ID不存在");
        }
        List<SysMenu> tmp = sysMenuService.selectList(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("MENU_CODE", menuCode));
        if(tmp!=null&&!tmp.isEmpty()){
            if(tmp.size()>1){
                return ResponseResult.createErrorResult("编码已存在");
            }else{
                SysMenu obj = tmp.get(0);
                if(!obj.getMenuId().equals(sysMenu.getMenuId())){
                    return ResponseResult.createErrorResult("编码已存在");
                }
            }
        }
        if(!menuCode.equals(one.getMenuCode())){
            permMenuSvc.updateForSet("MENU_CODE="+menuCode, Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("MENU_CODE", one.getMenuCode()));
        }
        sysMenu.setUpdateDate(new Date());
        sysMenuService.updateById(sysMenu);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("修改成功");
        return responseResult;
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysMenu", value = "新增", required = true, dataType = "SysMenu")
    @UooLog(value = "新增", key = "add")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysMenu sysMenu) {
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        String menuCode = sysMenu.getMenuCode();
        long size = sysMenuService.selectCount(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("MENU_CODE", menuCode));
        if(size>0){
            return ResponseResult.createErrorResult("编码已存在");
        }
        sysMenu.setCreateDate(new Date());
        sysMenu.setMenuId((sysMenuService.getId()));
        sysMenu.setStatusCd(StatusCD.VALID);
        sysMenu.setStatusDate(new Date());
        sysMenuService.insert(sysMenu);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增角色成功");
        return responseResult;
    }

    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenu", value = "sysMenu", required = true, dataType = "sysMenu"  ),
    })
    @UooLog(key="delete=",value="删除")
    @Transactional
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult<Void> deletePrivilege(@RequestBody SysMenu sysMenu) {
        Long MenuId = sysMenu.getMenuId();
        if (MenuId == null) {
            return ResponseResult.createErrorResult("无效数据");
        }
        SysMenu obj = sysMenuService.selectById(MenuId);
        if (obj == null ) {
            return ResponseResult.createErrorResult("不能删除无效数据");
        }

        obj.setStatusCd(StatusCD.INVALID);
        obj.setStatusDate(new Date());
        obj.setUpdateDate(new Date());
        sysMenuService.updateById(obj);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "获取单个用户的菜单", notes = "获取单个用户的菜单")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "accout", value = "accout", required = true, dataType = "String" ,paramType="path"),
    })
    @UooLog(key="getMenuByAccout=",value="获取单个用户的菜单")
    @Transactional
    @RequestMapping(value = "/getMenuByAccout/{accout}", method = RequestMethod.GET)
    public ResponseResult<List<SysMenu>> getMenuByAccout(@PathVariable(value = "accout") String accout){
        if(accout==null||accout.trim().length()<=0){
            return ResponseResult.createErrorResult("账号不存在");
        }
        HashMap<String , Object> map=new HashMap<>();
        map.put("accout", accout);
        return ResponseResult.createSuccessResult(sysMenuService.getMenuByAccout(map), "");
    }
}
