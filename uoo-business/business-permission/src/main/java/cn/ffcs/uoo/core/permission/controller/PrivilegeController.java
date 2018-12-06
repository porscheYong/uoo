package cn.ffcs.uoo.core.permission.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.permission.consts.PrivGrantObjectTypeConsts;
import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.entity.FuncComp;
import cn.ffcs.uoo.core.permission.entity.FuncMenu;
import cn.ffcs.uoo.core.permission.entity.PrivDataRel;
import cn.ffcs.uoo.core.permission.entity.PrivFuncRel;
import cn.ffcs.uoo.core.permission.entity.PrivGrant;
import cn.ffcs.uoo.core.permission.entity.Privilege;
import cn.ffcs.uoo.core.permission.entity.UserRole;
import cn.ffcs.uoo.core.permission.service.FuncCompService;
import cn.ffcs.uoo.core.permission.service.FuncMenuService;
import cn.ffcs.uoo.core.permission.service.IPrivDataRelService;
import cn.ffcs.uoo.core.permission.service.IPrivGrantService;
import cn.ffcs.uoo.core.permission.service.IPrivilegeService;
import cn.ffcs.uoo.core.permission.service.IUserRoleService;
import cn.ffcs.uoo.core.permission.service.PrivFuncRelService;
import cn.ffcs.uoo.core.permission.vo.AccoutPermissionVO;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 权限规格表，记录权限的配置 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@RestController
@RequestMapping("/permission/privilege")
public class PrivilegeController {
    /*@Autowired
    private RestTemplate restTemplate;*/
     
    @Autowired
    private IPrivilegeService privilegeService;
    @Autowired
    private IPrivDataRelService relSvc;
    @Autowired 
    IUserRoleService userRoleSvc;
    @Autowired
    IPrivGrantService privGrantSvc;
    @Autowired
    PrivFuncRelService privFuncRelSvc;
    @Autowired
    FuncCompService funcCompSvc;
    @Autowired
    FuncMenuService funcMenuSvc;
    
    @ApiOperation(value = "获取权限", notes = "获取权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Integer" ,paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Integer" ,paramType="path"),
    })
    @UooLog(key="listPrivilege",value="获取权限")
    @GetMapping("/listPrivilege/{pageNo}/{pageSize}")
    public ResponseResult listPrivilege(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Wrapper<Privilege> wrapper =Condition.create().eq("STATUS_CD", StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<Privilege> selectList = privilegeService.selectPage(new Page<Privilege>(pageNo, pageSize), wrapper);
        return ResponseResult.createSuccessResult(selectList.getRecords(), "",selectList);
    }
    
    @ApiOperation(value = "添加权限", notes = "添加权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privilege", value = "privilege", required = true, dataType = "Privilege"  ),
    })
    @UooLog(key="addPrivilege",value="添加权限")
    @Transactional
    @RequestMapping(value = "/addPrivilege", method = RequestMethod.POST)
    public ResponseResult addPrivilege(@RequestBody Privilege privilege) {
        // regionId 校验
        Long regionId = privilege.getRegionId();
       /* ResponseResult result = restTemplate.getForObject(
                "http://" + regionServiceName + "/region/commonRegion/getCommonRegion/id=" + regionId,
                ResponseResult.class);

        if (result.getState() != ResponseResult.STATE_OK) {
            return ResponseResult.createErrorResult("公用管理区域是无效数据");
        }*/
        privilege.setPrivId(privilegeService.getId());
        privilege.setStatusCd(StatusCD.VALID);
        privilege.setCreateDate(new Date());
        privilege.setUpdateDate(new Date());
        privilegeService.insert(privilege);
        return ResponseResult.createSuccessResult("success");
    }

    @ApiOperation(value = "修改权限", notes = "修改权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privilege", value = "privilege", required = true, dataType = "Privilege"  ),
    })
    @UooLog(key="updatePrivilege",value="修改权限")
    @Transactional
    @RequestMapping(value = "/updatePrivilege", method = RequestMethod.POST)
    public ResponseResult updatePrivilege(@RequestBody Privilege privilege) {
        Long privId = privilege.getPrivId();
        if (privId == null) {
            return ResponseResult.createErrorResult("无效数据");
        }
        Privilege obj = privilegeService.selectById(privId);
        if (obj == null || !StatusCD.VALID.equals(obj.getStatusCd())) {
            return ResponseResult.createErrorResult("不能修改无效数据");
        }
        // regionId 校验
        Long regionId = privilege.getRegionId();
       /* ResponseResult result = restTemplate.getForObject(
                "http://" + regionServiceName + "/region/commonRegion/getCommonRegion/id=" + regionId,
                ResponseResult.class);

        if (result.getState() != ResponseResult.STATE_OK) {
            return ResponseResult.createErrorResult("公用管理区域是无效数据");
        }*/
        // privilege.setPrivId(privilegeService.getId());
        privilege.setStatusCd(StatusCD.VALID);
        privilege.setUpdateDate(new Date());
        privilegeService.updateById(privilege);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "删除权限", notes = "删除权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privilege", value = "privilege", required = true, dataType = "Privilege"  ),
    })
    @UooLog(key="deletePrivilege",value="删除权限")
    @SuppressWarnings("unchecked")
    @Transactional
    @RequestMapping(value = "/deletePrivilege", method = RequestMethod.POST)
    public ResponseResult deletePrivilege(@RequestBody Privilege privilege) {
        Long privId = privilege.getPrivId();
        if (privId == null) {
            return ResponseResult.createErrorResult("无效数据");
        }
        Privilege obj = privilegeService.selectById(privId);
        if (obj == null ) {
            return ResponseResult.createErrorResult("不能删除无效数据");
        }
        Wrapper<PrivDataRel> wrapper = Condition.create().eq("PRIV_ID", privId);
        List<PrivDataRel> selectList = relSvc.selectList(wrapper);
        if(selectList!=null){
            for (PrivDataRel privDataRel : selectList) {
                if(StatusCD.VALID.equals(privDataRel.getStatusCd())){
                    return ResponseResult.createErrorResult("请先删除相关权限数据信息");
                }
            }
            
        }
        
        obj.setStatusCd(StatusCD.INVALID);
        obj.setStatusDate(new Date());
        obj.setUpdateDate(new Date());
        privilegeService.updateById(obj);
        return ResponseResult.createSuccessResult("success");
    }
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取菜单权限", notes = "获取菜单权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "accoutId", value = "accoutId", required = true, dataType = "Long"  ),
    })
    @UooLog(key="getAccoutMenuPermission",value="获取菜单权限")
    @Transactional
    @RequestMapping(value = "/getAccoutMenuPermission/{accoutId}", method = RequestMethod.GET)
    public ResponseResult getAccoutMenuPermission(@PathVariable(value = "accoutId") Long accoutId){
        Wrapper<UserRole> w1=Condition.create().eq("ACCT_ID", accoutId).eq("STATUS_CD", StatusCD.VALID);
        List<UserRole> userRoles = userRoleSvc.selectList(w1);
        if(userRoles==null||userRoles.isEmpty()){
            return ResponseResult.createErrorResult("当前账户没有角色");
        }
        StringBuilder roleIds=new StringBuilder();
        for (UserRole userRole : userRoles) {
            roleIds.append(userRole.getRoleId()).append(",");
        }
        Wrapper<PrivGrant> w2=Condition.create().in("GRANT_OBJ_ID", roleIds.toString().substring(0, roleIds.length()-1))
                .eq("STATUS_CD", StatusCD.VALID).eq("GRANT_OBJ_TYPE", PrivGrantObjectTypeConsts.GRANT_OBJECT_TYPE_ROLE);
        List<PrivGrant> privGrants = privGrantSvc.selectList(w2);
        if(privGrants==null||privGrants.isEmpty()){
            return ResponseResult.createErrorResult("账户角色没有授权");
        }
        StringBuilder privIds=new StringBuilder();
        for (PrivGrant privGrant : privGrants) {
            Long privId = privGrant.getPrivId();
            privIds.append(privId).append(",");
        }
        Wrapper<PrivFuncRel> w3=Condition.create().in("PRIV_ID", privIds.toString().substring(0, privIds.length()-1))
                .eq("STATUS_CD", StatusCD.VALID);
        List<PrivFuncRel> rels = privFuncRelSvc.selectList(w3);
        if(rels==null||rels.isEmpty()){
            return ResponseResult.createErrorResult("账户权限没有菜单权限");
        }
        List<Long> funcMenuIds=new ArrayList<>();
        List<Long> funcCompIds=new ArrayList<>();
        for (PrivFuncRel privFuncRel : rels) {
            if(privFuncRel.getMenuId()!=null)funcMenuIds.add(privFuncRel.getMenuId());
            if(privFuncRel.getCompId()!=null)funcCompIds.add(privFuncRel.getCompId());
        }
        
        AccoutPermissionVO data=new AccoutPermissionVO();
        data.setAccoutId(accoutId);
        if(!funcCompIds.isEmpty()){
            List<FuncComp> cs = funcCompSvc.selectBatchIds(funcCompIds);
            //剔除无效
            Iterator<FuncComp> it1 = cs.iterator();
            while(it1.hasNext()){
                FuncComp next = it1.next();
                if(StatusCD.INVALID.equals(next.getStatusCd())){
                    it1.remove();
                }
            }
            data.setFuncComps(cs);
        }
        if(!funcMenuIds.isEmpty()){
            List<FuncMenu> ms = funcMenuSvc.selectBatchIds(funcMenuIds);
            Iterator<FuncMenu> it2 = ms.iterator();
            while(it2.hasNext()){
                FuncMenu next = it2.next();
                if(StatusCD.INVALID.equals(next.getStatusCd())){
                    it2.remove();
                }
            }
            data.setFuncMemus(ms);
        }
        
        
        return ResponseResult.createSuccessResult(data,"");
    }
    
}
