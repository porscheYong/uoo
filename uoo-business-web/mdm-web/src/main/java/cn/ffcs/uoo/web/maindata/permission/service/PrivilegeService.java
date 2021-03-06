package cn.ffcs.uoo.web.maindata.permission.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.permission.dto.Privilege;
import cn.ffcs.uoo.web.maindata.permission.service.fallback.PrivilegeHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.AccoutPermissionVO;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

/**
 * <p>
 * 权限规格表，记录权限的配置 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@FeignClient(value = "business-permission", fallback = PrivilegeHystrix.class)
public interface PrivilegeService {
     
    @GetMapping("/permission/privilege/listPrivilege")
    public ResponseResult<List<Privilege>> listPrivilege(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);    
     
    @RequestMapping(value = "/permission/privilege/addPrivilege", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> addPrivilege(@RequestBody Privilege privilege) ;

     
    @RequestMapping(value = "/permission/privilege/updatePrivilege", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> updatePrivilege(@RequestBody Privilege privilege);
     
    @RequestMapping(value = "/permission/privilege/deletePrivilege", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> deletePrivilege(@RequestBody Privilege privilege) ;
    @RequestMapping(value = "/permission/privilege/getAccoutMenuPermission/{accoutId}", method = RequestMethod.GET)
    public ResponseResult<AccoutPermissionVO> getAccoutMenuPermission(@PathVariable(value = "accoutId") Long accoutId);
}
