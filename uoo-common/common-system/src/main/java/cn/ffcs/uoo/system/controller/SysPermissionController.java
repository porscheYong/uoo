package cn.ffcs.uoo.system.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysPermission;
import cn.ffcs.uoo.system.entity.SysPermissionDataRulesRel;
import cn.ffcs.uoo.system.entity.SysPermissionElementRel;
import cn.ffcs.uoo.system.entity.SysPermissionFuncRel;
import cn.ffcs.uoo.system.entity.SysPermissionMenuRel;
import cn.ffcs.uoo.system.entity.SysPrivFileRel;
import cn.ffcs.uoo.system.entity.SysRole;
import cn.ffcs.uoo.system.entity.SysRolePermissionRef;
import cn.ffcs.uoo.system.service.ISysPermissionDataRulesRelService;
import cn.ffcs.uoo.system.service.ISysPermissionElementRelService;
import cn.ffcs.uoo.system.service.ISysPermissionFuncRelService;
import cn.ffcs.uoo.system.service.ISysPermissionMenuRelService;
import cn.ffcs.uoo.system.service.ISysPermissionService;
import cn.ffcs.uoo.system.service.ISysPrivFileRelService;
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
    
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getSysPermission",value="获取单个数据")
    @GetMapping("/get/{id}")
    public ResponseResult<SysPermission> get(@PathVariable(value="id" ,required=true) Long id){
        return ResponseResult.createSuccessResult(permSvc.selectById(id), "");
    }
    
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="listPage",value="获取分页列表")
    @GetMapping("/listPage")
    public ResponseResult<List<SysPermissionDTO>> listPage(Integer pageNo, Integer pageSize,String keyWord){
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
        
        ResponseResult<List<SysPermissionDTO>> createSuccessResult = ResponseResult.createSuccessResult(list, "");
        createSuccessResult.setTotalRecords(count);
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
        List<String> funcCodes = sysPermissionEditDTO.getFuncCodes();
        if(funcCodes!=null&&!funcCodes.isEmpty()){
            for (String code : funcCodes) {
                SysPermissionFuncRel entity =new SysPermissionFuncRel();
                entity.setPrivFuncId(permFuncRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setFuncCode(code);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permFuncRelSvc.insert(entity );
            }
        }
        List<String> menuCodes = sysPermissionEditDTO.getMenuCodes();
        if(menuCodes!=null&&!menuCodes.isEmpty()){
            for (String code : menuCodes) {
                SysPermissionMenuRel entity=new SysPermissionMenuRel();
                entity.setPrivMenuId(permMenuRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setMenuCode(code);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permMenuRelSvc.insert(entity);
            }
        }
        List<String> elementCodes = sysPermissionEditDTO.getElementCodes();
        if(elementCodes!=null&&!elementCodes.isEmpty()){
            for (String code : elementCodes) {
                SysPermissionElementRel entity = new SysPermissionElementRel();
                entity.setPrivElementId(permEleRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setElementCode(code);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permEleRelSvc.insert(entity);
            }
        }
        
        List<Long> fileIds = sysPermissionEditDTO.getFileIds();
        if(fileIds!=null&&!fileIds.isEmpty()){
            for (Long id : fileIds) {
                SysPrivFileRel entity = new SysPrivFileRel();
                entity.setPrivFileId(permFileRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setFileId(id.intValue());
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permFileRelSvc.insert(entity );
            }
        }
        List<Long> dataRuleIds = sysPermissionEditDTO.getDataRuleIds();
        if(dataRuleIds!=null&&!dataRuleIds.isEmpty()){
            for (Long id : dataRuleIds) {
                SysPermissionDataRulesRel entity=new SysPermissionDataRulesRel();
                entity.setPrivDataRelId(permDataRulesRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setDataRuleId(id);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permDataRulesRelSvc.insert(entity);
            }
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
        int selectCount = permSvc.selectCount(Condition.create().eq("PERMISSION_CODE", permissionCode).eq("STATUS_CD", StatusCD.VALID));
        if(selectCount>0){
            return ResponseResult.createErrorResult("权限编码已存在");
        }
        
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        sysPermissionEditDTO.setCreateDate(new Date());
        sysPermissionEditDTO.setStatusCd(StatusCD.VALID);
        sysPermissionEditDTO.setStatusDate(new Date());
        permSvc.updateById(sysPermissionEditDTO);
        List<String> funcCodes = sysPermissionEditDTO.getFuncCodes();
        if(funcCodes!=null&&!funcCodes.isEmpty()){
            
            for (String code : funcCodes) {
                SysPermissionFuncRel entity =new SysPermissionFuncRel();
                entity.setPrivFuncId(permFuncRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setFuncCode(code);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permFuncRelSvc.insert(entity );
            }
        }
        List<String> menuCodes = sysPermissionEditDTO.getMenuCodes();
        if(menuCodes!=null&&!menuCodes.isEmpty()){
            for (String code : menuCodes) {
                SysPermissionMenuRel entity=new SysPermissionMenuRel();
                entity.setPrivMenuId(permMenuRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setMenuCode(code);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permMenuRelSvc.insert(entity);
            }
        }
        List<String> elementCodes = sysPermissionEditDTO.getElementCodes();
        if(elementCodes!=null&&!elementCodes.isEmpty()){
            for (String code : elementCodes) {
                SysPermissionElementRel entity = new SysPermissionElementRel();
                entity.setPrivElementId(permEleRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setElementCode(code);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permEleRelSvc.insert(entity);
            }
        }
        
        List<Long> fileIds = sysPermissionEditDTO.getFileIds();
        if(fileIds!=null&&!fileIds.isEmpty()){
            for (Long id : fileIds) {
                SysPrivFileRel entity = new SysPrivFileRel();
                entity.setPrivFileId(permFileRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setFileId(id.intValue());
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permFileRelSvc.insert(entity );
            }
        }
        List<Long> dataRuleIds = sysPermissionEditDTO.getDataRuleIds();
        if(dataRuleIds!=null&&!dataRuleIds.isEmpty()){
            for (Long id : dataRuleIds) {
                SysPermissionDataRulesRel entity=new SysPermissionDataRulesRel();
                entity.setPrivDataRelId(permDataRulesRelSvc.getId());
                entity.setCreateDate(new Date());
                entity.setCreateUser(sysPermissionEditDTO.getCreateUser());
                entity.setStatusCd(StatusCD.VALID);
                entity.setDataRuleId(id);
                entity.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
                permDataRulesRelSvc.insert(entity);
            }
        }
        
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增成功");
        return responseResult;
    }
    
}

