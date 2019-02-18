package cn.ffcs.uoo.system.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.entity.SysDataRuleGroup;
import cn.ffcs.uoo.system.entity.SysPermission;
import cn.ffcs.uoo.system.entity.SysPermissionDataRulesRel;
import cn.ffcs.uoo.system.entity.SysPermissionElementRel;
import cn.ffcs.uoo.system.entity.SysPermissionFuncRel;
import cn.ffcs.uoo.system.entity.SysPermissionMenuRel;
import cn.ffcs.uoo.system.entity.SysPrivFileRel;
import cn.ffcs.uoo.system.service.ISysDataRuleGroupService;
import cn.ffcs.uoo.system.service.ISysDataRuleService;
import cn.ffcs.uoo.system.service.ISysElementService;
import cn.ffcs.uoo.system.service.ISysFileService;
import cn.ffcs.uoo.system.service.ISysFunctionService;
import cn.ffcs.uoo.system.service.ISysPermissionDataRulesRelService;
import cn.ffcs.uoo.system.service.ISysPermissionElementRelService;
import cn.ffcs.uoo.system.service.ISysPermissionFuncRelService;
import cn.ffcs.uoo.system.service.ISysPermissionMenuRelService;
import cn.ffcs.uoo.system.service.ISysPermissionService;
import cn.ffcs.uoo.system.service.ISysPrivFileRelService;
import cn.ffcs.uoo.system.service.ISysRolePermissionRefService;
import cn.ffcs.uoo.system.service.SysMenuService;
import cn.ffcs.uoo.system.vo.DataRuleGroupEditVO;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysDataRuleVo;
import cn.ffcs.uoo.system.vo.SysPermissionDTO;
import cn.ffcs.uoo.system.vo.SysPermissionEditDTO;
import cn.ffcs.uoo.system.vo.SysPermissionPrivDTO;
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
    ISysDataRuleService dataRuleSvc;
    @Autowired
    ISysDataRuleGroupService dataRuleGroupSvc;
    
    @Autowired
    ISysPermissionElementRelService permEleRelSvc;
    @Autowired
    ISysElementService eleSvc;
    
    @Autowired
    ISysPermissionFuncRelService permFuncRelSvc;
    @Autowired
    ISysFunctionService funcSvc;
    
    @Autowired
    ISysPermissionMenuRelService permMenuRelSvc;
    @Autowired
    SysMenuService menuSvc;
    
    @Autowired
    ISysPrivFileRelService permFileRelSvc;
    @Autowired
    ISysFileService fileSvc;
    
    @Autowired
    ISysRolePermissionRefService permRoleRelSvc;
    
    
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getSysPermission",value="获取单个数据")
    @GetMapping("/get/{id}")
    public ResponseResult<SysPermissionPrivDTO> get(@PathVariable(value="id" ,required=true) Long id){
        SysPermissionPrivDTO dto=new SysPermissionPrivDTO();
        SysPermissionDTO sp = permSvc.selectOne(id);
        BeanUtils.copyProperties(sp, dto);
        
        dto.setFiles(fileSvc.listByPermissionId(id));
        dto.setFuncs(funcSvc.listByPermissionId(id));
        dto.setElements(eleSvc.listByPermissionId(id));
        dto.setMenus(menuSvc.listByPermissionId(id));
        
        List<DataRuleGroupEditVO> dataRuleGroups=new ArrayList<>();
        List<SysDataRuleGroup> listByPermCode = dataRuleGroupSvc.listByPermCode(dto.getPermissionCode());
        for (SysDataRuleGroup sysDataRuleGroup : listByPermCode) {
            DataRuleGroupEditVO vo =new DataRuleGroupEditVO();
            BeanUtils.copyProperties(sysDataRuleGroup, vo);
            List<SysDataRuleVo> listSysDataRuleVoByGroupId = dataRuleSvc.listSysDataRuleVoByGroupId(sysDataRuleGroup.getDataRuleGroupId());
            vo.setDataRules(listSysDataRuleVoByGroupId);
             dataRuleGroups.add(vo);
        }
        dto.setDataRules(dataRuleGroups);
        
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
        Page<SysPermissionDTO> page=new Page<>(pageNo,pageSize);
        List<SysPermissionDTO> list=permSvc.findList(page,map);
        page.setRecords(list);
        ResponseResult<Page<SysPermissionDTO>> createSuccessResult = ResponseResult.createSuccessResult("");
        createSuccessResult.setData(page);
        return createSuccessResult;
    }
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParam(name = "sysPermissionEditDTO", value = "新增", required = true, dataType = "SysPermissionEditDTO")
    @UooLog(value = "新增", key = "add")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody @Valid SysPermissionEditDTO sysPermissionEditDTO) {
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
                entity.setFuncCode(entity.getFuncCode());
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
        List<DataRuleGroupEditVO> dataRuleGroups = sysPermissionEditDTO.getDataRuleGroups();
        for (DataRuleGroupEditVO dataRuleGroupEditVO : dataRuleGroups) {
            //保存数据权的组信息
            SysDataRuleGroup group=new SysDataRuleGroup();
            BeanUtils.copyProperties(dataRuleGroupEditVO, group);
            group.setDataRuleGroupId(dataRuleGroupSvc.getId());
            group.setStatusCd(StatusCD.VALID);
            group.setCreateDate(new Date());
            group.setCreateUser(sysPermissionEditDTO.getCreateUser());
            dataRuleGroupSvc.insert(group);
            List<SysDataRuleVo> dataRules = dataRuleGroupEditVO.getDataRules();
          //保存数据权的关系信息
            SysPermissionDataRulesRel rel=new SysPermissionDataRulesRel();
            rel.setPrivDataRelId(permDataRulesRelSvc.getId());
            rel.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
            rel.setCreateDate(new Date());
            rel.setCreateUser(sysPermissionEditDTO.getCreateUser());
            rel.setStatusCd(StatusCD.VALID);
            rel.setDataRuleGroupId(group.getDataRuleGroupId());
            permDataRulesRelSvc.insert(rel);
            for (SysDataRule sysDataRule : dataRules) {
                //保存数据权的信息
                sysDataRule.setDataRuleGroupId(group.getDataRuleGroupId());
                sysDataRule.setDataRuleId(dataRuleSvc.getId());
                sysDataRule.setStatusCd(StatusCD.VALID);
                sysDataRule.setCreateDate(new Date());
                sysDataRule.setCreateUser(sysPermissionEditDTO.getCreateUser());
                dataRuleSvc.insert(sysDataRule);
                
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
    public ResponseResult<Void> update(@RequestBody @Valid SysPermissionEditDTO sysPermissionEditDTO) {
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
            permFuncRelSvc.updateForSet("PERMISSION_CODE='"+permissionCode+"'", Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", one.getPermissionCode()));
            permMenuRelSvc.updateForSet("PERMISSION_CODE='"+permissionCode+"'", Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", one.getPermissionCode()));
            permEleRelSvc.updateForSet("PERMISSION_CODE='"+permissionCode+"'", Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", one.getPermissionCode()));
            permFileRelSvc.updateForSet("PERMISSION_CODE='"+permissionCode+"'", Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", one.getPermissionCode()));
            permDataRulesRelSvc.updateForSet("PERMISSION_CODE='"+permissionCode+"'", Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", one.getPermissionCode()));
            permRoleRelSvc.updateForSet("PERMISSION_CODE='"+permissionCode+"'", Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("PERMISSION_CODE", one.getPermissionCode()));
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
        // 1.删除关系   2.重构关系
        List<Long> idList=new ArrayList<>();
        for (SysPermissionDataRulesRel sysPermissionDataRulesRel : exitDataRuleRels) {
            idList.add(sysPermissionDataRulesRel.getPrivDataRelId());
        }
        permDataRulesRelSvc.deleteBatchIds(idList);
        List<DataRuleGroupEditVO> dataRuleGroups = sysPermissionEditDTO.getDataRuleGroups();
        for (DataRuleGroupEditVO dataRuleGroupEditVO : dataRuleGroups) {
            //保存数据权的组信息
            SysDataRuleGroup group=new SysDataRuleGroup();
            BeanUtils.copyProperties(dataRuleGroupEditVO, group);
            group.setStatusCd(StatusCD.VALID);
            group.setCreateDate(new Date());
            group.setCreateUser(sysPermissionEditDTO.getCreateUser());
            if(group.getDataRuleGroupId()==null||group.getDataRuleGroupId().longValue()==0){
                group.setDataRuleGroupId(dataRuleGroupSvc.getId());
                dataRuleGroupSvc.insert(group);
            }else{
                dataRuleGroupSvc.updateById(group);
            }
          //保存数据权的关系信息
            SysPermissionDataRulesRel rel=new SysPermissionDataRulesRel();
            rel.setPrivDataRelId(permDataRulesRelSvc.getId());
            rel.setPermissionCode(sysPermissionEditDTO.getPermissionCode());
            rel.setCreateDate(new Date());
            rel.setUpdateUser(sysPermissionEditDTO.getUpdateUser());
            rel.setStatusCd(StatusCD.VALID);
            rel.setDataRuleGroupId(group.getDataRuleGroupId());
            permDataRulesRelSvc.insert(rel);
            //把该group下的数据权的关系解除  
            dataRuleSvc.updateForSet("DATA_RULE_GROUP_ID=0", Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("DATA_RULE_GROUP_ID", group.getDataRuleGroupId()));
            List<SysDataRuleVo> dataRules = dataRuleGroupEditVO.getDataRules();
            for (SysDataRule sysDataRule : dataRules) {
                //保存数据权的信息
                sysDataRule.setDataRuleGroupId(group.getDataRuleGroupId());
                
                sysDataRule.setStatusCd(StatusCD.VALID);
                sysDataRule.setCreateDate(new Date());
                sysDataRule.setUpdateUser(sysPermissionEditDTO.getUpdateUser());
                if(sysDataRule.getDataRuleId()==null||sysDataRule.getDataRuleId().longValue()==0){
                    sysDataRule.setDataRuleId(dataRuleSvc.getId());
                    dataRuleSvc.insert(sysDataRule);
                }else{
                    dataRuleSvc.updateById(sysDataRule);
                }
               
            }
            
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
        // 删除关系
        permFuncRelSvc.delete(Condition.create().eq("PERMISSION_CODE", permission.getPermissionCode()));
        permMenuRelSvc.delete(Condition.create().eq("PERMISSION_CODE", permission.getPermissionCode()));
        permEleRelSvc.delete(Condition.create().eq("PERMISSION_CODE", permission.getPermissionCode()));
        permFileRelSvc.delete(Condition.create().eq("PERMISSION_CODE", permission.getPermissionCode()));
        permDataRulesRelSvc.delete(Condition.create().eq("PERMISSION_CODE", permission.getPermissionCode()));
        permRoleRelSvc.delete(Condition.create().eq("PERMISSION_CODE", permission.getPermissionCode()));
        return ResponseResult.createSuccessResult("删除成功");
    }
}

