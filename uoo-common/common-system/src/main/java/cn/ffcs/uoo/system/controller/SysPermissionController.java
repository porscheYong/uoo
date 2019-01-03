package cn.ffcs.uoo.system.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysPermission;
import cn.ffcs.uoo.system.entity.SysPermissionDataRulesRel;
import cn.ffcs.uoo.system.entity.SysPermissionElementRel;
import cn.ffcs.uoo.system.entity.SysPermissionFuncRel;
import cn.ffcs.uoo.system.entity.SysPermissionMenuRel;
import cn.ffcs.uoo.system.entity.SysPrivFileRel;
import cn.ffcs.uoo.system.service.ISysPermissionDataRulesRelService;
import cn.ffcs.uoo.system.service.ISysPermissionElementRelService;
import cn.ffcs.uoo.system.service.ISysPermissionFuncRelService;
import cn.ffcs.uoo.system.service.ISysPermissionMenuRelService;
import cn.ffcs.uoo.system.service.ISysPermissionService;
import cn.ffcs.uoo.system.service.ISysPrivFileRelService;
import cn.ffcs.uoo.system.service.ISysRolePermissionRefService;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysPermissionDTO;
import cn.ffcs.uoo.system.vo.SysPermissionEditDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@RestController
@RequestMapping("/system/sysPermission")
public class SysPermissionController {
    @Autowired
    ISysPermissionService permSvc;
    
    @Autowired
    ISysPermissionDataRulesRelService permDataRulesRelSvc;
    @Autowired
    ISysPermissionElementRelService permEleRelSvc;
    @Autowired
    ISysPermissionFuncRelService permFuncRelSvc;
    @Autowired
    ISysPermissionMenuRelService permMenuRelSvc;
    @Autowired
    ISysPrivFileRelService permFileRelSvc;
    @Autowired
    ISysRolePermissionRefService permRoleRelSvc;
    
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getSysPermission",value="获取单个数据")
    @GetMapping("/get/{id}")
    public ResponseResult<SysPermissionEditDTO> get(@PathVariable(value="id" ,required=true) Long id){
        SysPermissionEditDTO dto=new SysPermissionEditDTO();
        SysPermissionDTO sp = permSvc.selectOne(id);
        BeanUtils.copyProperties(sp, dto);
        dto.setDataRuleRels(permDataRulesRelSvc.selectList(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", sp.getPermissionCode())));
        dto.setElementRels(permEleRelSvc.selectList(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", sp.getPermissionCode())));
        dto.setFileRels(permFileRelSvc.selectList(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", sp.getPermissionCode())));
        dto.setFuncRels(permFuncRelSvc.selectList(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", sp.getPermissionCode())));
        dto.setMenuRels(permMenuRelSvc.selectList(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", sp.getPermissionCode())));
        return ResponseResult.createSuccessResult(dto, "");
    }
    
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="listPage",value="获取分页列表")
    @GetMapping("/listPage")
    public ResponseResult<Page<SysPermissionDTO>> listPage(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord")String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        HashMap<String,Object> map=new HashMap<>();
        if(keyWord!=null&&keyWord.trim().length()>0){
            map.put("keyWord", "%"+keyWord+"%");
        }
        map.put("from", (pageNo-1)*pageSize);
        map.put("end", pageNo * pageSize);
        List<SysPermissionDTO> list=permSvc.findList(map);
        Long count = permSvc.countList(map);
        Page<SysPermissionDTO> page=new Page<>(pageNo,pageSize);
        page.setTotal(count);
        page.setRecords(list);
        ResponseResult<Page<SysPermissionDTO>> createSuccessResult = ResponseResult.createSuccessResult("");
        createSuccessResult.setTotalRecords(count);
        createSuccessResult.setData(page);
        return createSuccessResult;
    }
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysPermissionEditDTO", value = "新增", required = true, dataType = "SysPermissionEditDTO")
    @UooLog(value = "新增", key = "add")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysPermissionEditDTO sysPermissionEditDTO) {
        //校验编码
        String permissionCode = sysPermissionEditDTO.getPermissionCode();
        int selectCount = permSvc.selectCount(Condition.create().eq("PERMISSION_CODE", permissionCode).eq("STATUS_CD", StatusCD.VALID));
        if(selectCount>0){
            return ResponseResult.createErrorResult("权限编码已存在");
        }
        
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        sysPermissionEditDTO.setCreateDate(new Date());
        sysPermissionEditDTO.setPermissionId(permSvc.getId());
        sysPermissionEditDTO.setStatusCd(StatusCD.VALID);
        sysPermissionEditDTO.setStatusDate(new Date());
        permSvc.insert(sysPermissionEditDTO);
        List<SysPermissionFuncRel> funcRels = sysPermissionEditDTO.getFuncRels();
        if(funcRels!=null&&!funcRels.isEmpty()){
            funcRels.forEach(entity->{
                entity.setPrivFuncId(permFuncRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permFuncRelSvc.insert(entity);
            });
        }
        List<SysPermissionMenuRel> menuRels = sysPermissionEditDTO.getMenuRels();
        if(menuRels!=null&&!menuRels.isEmpty()){
            menuRels.forEach(entity->{
                entity.setPrivMenuId(permMenuRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permMenuRelSvc.insert(entity);
            });
        }
        List<SysPermissionElementRel> elementRels = sysPermissionEditDTO.getElementRels();
        if(elementRels!=null&&!elementRels.isEmpty()){
            elementRels.forEach(entity->{
                entity.setPrivElementId(permEleRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permEleRelSvc.insert(entity);
            });
        }
        
        List<SysPrivFileRel> fileRels = sysPermissionEditDTO.getFileRels();
        if(fileRels!=null&&!fileRels.isEmpty()){
            fileRels.forEach(entity->{
                entity.setPrivFileId(permFileRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permFileRelSvc.insert(entity );
            });
        }
        List<SysPermissionDataRulesRel> dataRuleRels = sysPermissionEditDTO.getDataRuleRels();
        if(dataRuleRels!=null&&!dataRuleRels.isEmpty()){
            dataRuleRels.forEach(entity->{
                entity.setPrivDataRelId(permDataRulesRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permDataRulesRelSvc.insert(entity);
            });
        }
        
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增成功");
        return responseResult;
    }
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "修改",notes = "修改")
    @ApiImplicitParam(name = "sysPermissionEditDTO", value = "修改", required = true, dataType = "SysPermissionEditDTO")
    @UooLog(value = "修改", key = "update")
    @Transactional
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody SysPermissionEditDTO sysPermissionEditDTO) {
        //校验编码
        String permissionCode = sysPermissionEditDTO.getPermissionCode();
        SysPermission one = permSvc.selectById(sysPermissionEditDTO.getPermissionId());
        if(one==null){
            return ResponseResult.createErrorResult("ID异常");
        }
        List<SysPermission> tmp = permSvc.selectList(Condition.create().eq("PERMISSION_CODE", permissionCode).eq("STATUS_CD", StatusCD.VALID));
        if(tmp!=null&&!tmp.isEmpty()){
            if(tmp.size()>1){
                return ResponseResult.createErrorResult("权限编码已存在");
            }else{
                SysPermission obj = tmp.get(0);
                if(!obj.getPermissionId().equals(sysPermissionEditDTO.getPermissionId())){
                    return ResponseResult.createErrorResult("权限编码已存在");
                }
            }
        }
        if(!one.getPermissionCode().equals(permissionCode)){
            //修改了编码  先把对应的关系表改一下
            permFuncRelSvc.updatePermissionCode(one.getPermissionCode(),permissionCode);
            permMenuRelSvc.updatePermissionCode(one.getPermissionCode(),permissionCode);
            permEleRelSvc.updatePermissionCode(one.getPermissionCode(),permissionCode);
            permFileRelSvc.updatePermissionCode(one.getPermissionCode(),permissionCode);
            permDataRulesRelSvc.updatePermissionCode(one.getPermissionCode(),permissionCode);
            permRoleRelSvc.updatePermissionCode(one.getPermissionCode(),permissionCode);
        }
        
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        sysPermissionEditDTO.setCreateDate(new Date());
        sysPermissionEditDTO.setStatusCd(StatusCD.VALID);
        sysPermissionEditDTO.setStatusDate(new Date());
        permSvc.updateById(sysPermissionEditDTO);
        List<SysPermissionFuncRel> funcRels = sysPermissionEditDTO.getFuncRels();
        List<SysPermissionFuncRel> exitFuncRels = permFuncRelSvc.selectList(Condition.create().eq("PERMISSION_CODE", permissionCode).eq("STATUS_CD", StatusCD.VALID));
        List<Long> updateIds=new ArrayList<>();
        List<Long> deleteIds=new ArrayList<>();
        if(funcRels!=null&&!funcRels.isEmpty()){
            for (SysPermissionFuncRel entity : funcRels) {
                entity.setStatusCd(StatusCD.VALID);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                if(entity.getPrivFuncId()==null){
                    entity.setPrivFuncId(permFuncRelSvc.getId());
                    entity.setCreateDate(new Date());
                    entity.setCreateUser(sysPermissionEditDTO.getUpdateUser());
                    permFuncRelSvc.insert(entity);
                }else{
                    updateIds.add(entity.getPrivFuncId());
                    entity.setUpdateDate(new Date());
                    entity.setUpdateUser(sysPermissionEditDTO.getUpdateUser());
                    permFuncRelSvc.updateById(entity);
                }
            }
        }
        if(exitFuncRels!=null&&!exitFuncRels.isEmpty()){
            List<Long> collect = exitFuncRels.stream().filter(obj->!updateIds.contains(obj.getPrivFuncId())).map(SysPermissionFuncRel::getPrivFuncId).collect(Collectors.toList());
           /* for(SysPermissionFuncRel obj : exitFuncRels){
                if(!updateIds.contains(obj.getPrivFuncId())){
                    deleteIds.add(obj.getPrivFuncId());
                }
            }*/
            if(collect!=null){
                deleteIds.addAll(collect);
                permFuncRelSvc.deleteBatchIds(deleteIds);
            }
        }
        
        updateIds.clear();
        deleteIds.clear();
        List<SysPermissionMenuRel> exitMenuRels = permMenuRelSvc.selectList(Condition.create().eq("PERMISSION_CODE", permissionCode).eq("STATUS_CD", StatusCD.VALID));
        List<SysPermissionMenuRel> menuRels = sysPermissionEditDTO.getMenuRels();
        if(menuRels!=null&&!menuRels.isEmpty()){
            for (SysPermissionMenuRel entity : menuRels) {
                entity.setStatusCd(StatusCD.VALID);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                if(entity.getPrivMenuId()==null){
                    entity.setPrivMenuId(permMenuRelSvc.getId());
                    entity.setCreateDate(new Date());
                    entity.setCreateUser(sysPermissionEditDTO.getUpdateUser());
                    permMenuRelSvc.insert(entity);
                }else{
                    updateIds.add(entity.getPrivMenuId());
                    entity.setUpdateDate(new Date());
                    entity.setUpdateUser(sysPermissionEditDTO.getUpdateUser());
                    permMenuRelSvc.updateById(entity);
                }
            }
        }
        if(exitMenuRels!=null&&!exitMenuRels.isEmpty()){
            for(SysPermissionMenuRel obj : exitMenuRels){
                if(!updateIds.contains(obj.getPrivMenuId())){
                    deleteIds.add(obj.getPrivMenuId());
                }
            }
            permMenuRelSvc.deleteBatchIds(deleteIds);
        }
        
        updateIds.clear();
        deleteIds.clear();
        List<SysPermissionElementRel> elementRels = sysPermissionEditDTO.getElementRels();
        List<SysPermissionElementRel> exitElementRels = permEleRelSvc.selectList(Condition.create().eq("PERMISSION_CODE", permissionCode).eq("STATUS_CD", StatusCD.VALID));
        if(elementRels!=null&&!elementRels.isEmpty()){
            for (SysPermissionElementRel entity : elementRels) {
                entity.setStatusCd(StatusCD.VALID);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                if(entity.getPrivElementId()==null){
                    entity.setPrivElementId(permEleRelSvc.getId());
                    entity.setCreateDate(new Date());
                    entity.setCreateUser(sysPermissionEditDTO.getUpdateUser());
                    permEleRelSvc.insert(entity);
                }else{
                    updateIds.add(entity.getPrivElementId());
                    entity.setUpdateDate(new Date());
                    entity.setUpdateUser(sysPermissionEditDTO.getUpdateUser());
                    permEleRelSvc.updateById(entity);
                }
            }
        }
        if(exitElementRels!=null&&!exitElementRels.isEmpty()){
            for(SysPermissionElementRel obj : exitElementRels){
                if(!updateIds.contains(obj.getPrivElementId())){
                    deleteIds.add(obj.getPrivElementId());
                }
            }
            permEleRelSvc.deleteBatchIds(deleteIds);
        }
        
        updateIds.clear();
        deleteIds.clear();
        List<SysPrivFileRel> exitFileRels = permFileRelSvc.selectList(Condition.create().eq("PERMISSION_CODE", permissionCode).eq("STATUS_CD", StatusCD.VALID));
        List<SysPrivFileRel> fileRels = sysPermissionEditDTO.getFileRels();
        if(fileRels!=null&&!fileRels.isEmpty()){
            for (SysPrivFileRel entity : fileRels) {
                entity.setStatusCd(StatusCD.VALID);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                if(entity.getPrivFileId()==null){
                    entity.setPrivFileId(permFileRelSvc.getId());
                    entity.setCreateDate(new Date());
                    entity.setCreateUser(sysPermissionEditDTO.getUpdateUser());
                    permFileRelSvc.insert(entity);
                }else{
                    updateIds.add(entity.getPrivFileId());
                    entity.setUpdateDate(new Date());
                    entity.setUpdateUser(sysPermissionEditDTO.getUpdateUser());
                    permFileRelSvc.updateById(entity);
                }
            }
        }
        if(exitFileRels!=null&&!exitFileRels.isEmpty()){
            for(SysPrivFileRel obj : exitFileRels){
                if(!updateIds.contains(obj.getPrivFileId())){
                    deleteIds.add(obj.getPrivFileId());
                }
            }
            permFileRelSvc.deleteBatchIds(deleteIds);
        }
        
        updateIds.clear();
        deleteIds.clear();
        List<SysPermissionDataRulesRel> exitDataRuleRels = permDataRulesRelSvc.selectList(Condition.create().eq("PERMISSION_CODE", permissionCode).eq("STATUS_CD", StatusCD.VALID));
        List<SysPermissionDataRulesRel> dataRuleRels = sysPermissionEditDTO.getDataRuleRels();
        if(dataRuleRels!=null&&!dataRuleRels.isEmpty()){
            for (SysPermissionDataRulesRel entity : dataRuleRels) {
                entity.setStatusCd(StatusCD.VALID);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                if(entity.getPrivDataRelId()==null){
                    entity.setPrivDataRelId(permDataRulesRelSvc.getId());
                    entity.setCreateDate(new Date());
                    entity.setCreateUser(sysPermissionEditDTO.getUpdateUser());
                    permDataRulesRelSvc.insert(entity);
                }else{
                    updateIds.add(entity.getPrivDataRelId());
                    entity.setUpdateDate(new Date());
                    entity.setUpdateUser(sysPermissionEditDTO.getUpdateUser());
                    permDataRulesRelSvc.updateById(entity);
                }
            }
        }
        if(exitDataRuleRels!=null&&!exitDataRuleRels.isEmpty()){
            for(SysPermissionDataRulesRel obj : exitDataRuleRels){
                if(!updateIds.contains(obj.getPrivDataRelId())){
                    deleteIds.add(obj.getPrivDataRelId());
                }
            }
            permDataRulesRelSvc.deleteBatchIds(deleteIds);
        }
        
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增成功");
        return responseResult;
    }
    
    @ApiOperation(value = "删除",notes = "删除")
    @ApiImplicitParam(name = "id", value = "删除", required = true, dataType = "Long",paramType="path")
    @UooLog(value = "删除", key = "delete")
    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseResult<Void> delete(@PathVariable(value="id" ,required=true) Long id){
        SysPermission permission = permSvc.selectById(id);
        if(permission==null){
            return ResponseResult.createErrorResult("不能删除空数据");
        }
        permission.setStatusCd(StatusCD.INVALID);
        permission.setStatusDate(new Date());
        permSvc.updateById(permission);
        return ResponseResult.createSuccessResult("删除成功");
    }
}

