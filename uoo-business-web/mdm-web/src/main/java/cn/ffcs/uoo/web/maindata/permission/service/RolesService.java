package cn.ffcs.uoo.web.maindata.permission.service;


import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cn.ffcs.uoo.web.maindata.permission.dto.FuncMenu;
import cn.ffcs.uoo.web.maindata.permission.dto.Roles;
import cn.ffcs.uoo.web.maindata.permission.service.fallback.RolesHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.permission.vo.RoleSystemPermissionVO;

/**
 * <p>
 * 角色
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-24
 */
 
@FeignClient(value = "business-permission", fallback = RolesHystrix.class)
public interface RolesService {
    @GetMapping("/permission/tbRoles/get/{id}")
    public ResponseResult<Roles> get(@PathVariable(value="id" ,required=true) Long id);
    
     
    @GetMapping("/permission/tbRoles/listPageRoles/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult<List<Roles>> listPageRoles(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);

    @GetMapping("/permission/tbRoles/listRoles")
    public ResponseResult<List<Roles>> listRoles();


    @RequestMapping(value = "/permission/tbRoles/del", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> removeTbRoles(@RequestBody Roles role ) ;

     
    @RequestMapping(value = "/permission/tbRoles/update", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> updateTbRoles(@RequestBody Roles tbRoles) ;

     
    @RequestMapping(value = "/permission/tbRoles/add", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> addTbRoles(@RequestBody Roles role) ;

    @PostMapping("/permission/tbRoles/getRolesPermission/{acctId}/{systemInfoId}")
    public ResponseResult<List<RoleSystemPermissionVO>> getRolesPermission(@PathVariable(value = "systemInfoId") Long systemInfoId, @PathVariable(value = "acctId") Long acctId);


    @PostMapping("/permission/tbRoles/getPermissionMenu/{acctId}/{systemInfoId}")
    public ResponseResult<List<FuncMenu>> getPermissionMenu(@PathVariable(value = "systemInfoId") Long systemInfoId, @PathVariable(value = "acctId") Long acctId);
}

