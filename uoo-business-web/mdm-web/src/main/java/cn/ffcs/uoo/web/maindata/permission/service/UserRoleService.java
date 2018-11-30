package cn.ffcs.uoo.web.maindata.permission.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.permission.dto.UserRole;
import cn.ffcs.uoo.web.maindata.permission.service.fallback.UserRoleHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.BatchAddRoleUserVO;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.permission.vo.UserPersonnelVo;

/**
 * <p>
 * 用户角色关系
 * 描述系统用户与角色之间的对应关系，是多对多关系。一个系统用户除了拥有系统岗位所带的角色和权限，也可以拥有多个私有的角色，一个角色可以分配给多个系统用户。
 * 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-24
 */
@FeignClient(value = "uoo-permission-provider", fallback = UserRoleHystrix.class)
public interface UserRoleService {
    @RequestMapping(value = "/permission/tbUserRole/del", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult removeTbUserRole(@RequestBody UserRole userRole);

    @RequestMapping(value = "/permission/tbUserRole/add", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addTbUserRole(@RequestBody UserRole tbUserRole);

    @RequestMapping(value = "/permission/tbUserRole/addTbUserRoleBatch", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addTbUserRoleBatch(@RequestBody BatchAddRoleUserVO batchAddRoleUserVO);

    @RequestMapping(value = "/permission/tbUserRole/getPage/{pageNo}/{pageSize}/{roleId}", method = RequestMethod.GET)
    public Page<UserPersonnelVo> getUserPersonnelVoPage(@PathVariable(value = "pageNo") Integer pageNo,
            @PathVariable(value = "pageSize") Integer pageSize, @PathVariable(value = "roleId") Long roleId);
}
