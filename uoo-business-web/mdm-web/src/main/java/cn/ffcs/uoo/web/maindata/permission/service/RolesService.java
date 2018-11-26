package cn.ffcs.uoo.web.maindata.permission.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.permission.dto.Roles;
import cn.ffcs.uoo.web.maindata.permission.service.fallback.RolesHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

/**
 * <p>
 * 角色
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-24
 */
 
@FeignClient(value = "uoo-permission-provider", fallback = RolesHystrix.class)
public interface RolesService {
    @GetMapping("/permission/tbRoles/get/{id}")
    public ResponseResult get(@PathVariable(value="id" ,required=true) Long id);
    
     
    @GetMapping("/permission/tbRoles/listRoles/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listPageRoles(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);

    @GetMapping("/permission/tbRoles/listRoles/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRoles();


    @RequestMapping(value = "/permission/tbRoles/del", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult removeTbRoles(@RequestBody Roles role ) ;

     
    @RequestMapping(value = "/permission/tbRoles/update", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult updateTbRoles(@RequestBody Roles tbRoles) ;

     
    @RequestMapping(value = "/permission/tbRoles/add", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addTbRoles(@RequestBody Roles role) ;
}

