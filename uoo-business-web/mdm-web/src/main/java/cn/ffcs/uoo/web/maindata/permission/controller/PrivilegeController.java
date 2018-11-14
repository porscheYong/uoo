package cn.ffcs.uoo.web.maindata.permission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.permission.dto.Privilege;
import cn.ffcs.uoo.web.maindata.permission.service.PrivilegeService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
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
    private PrivilegeService privilegeService;
     
    
    @ApiOperation(value = "获取权限", notes = "获取权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
        @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @GetMapping("/listPrivilege")
    public ResponseResult listPrivilege(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        return privilegeService.listPrivilege(pageNo, pageSize);
    }
    
    @ApiOperation(value = "添加权限", notes = "添加权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privilege", value = "privilege", required = true, dataType = "Privilege"  ),
    })
    @RequestMapping(value = "/addPrivilege", method = RequestMethod.POST)
    public ResponseResult addPrivilege(@RequestBody Privilege privilege) {
        
        return privilegeService.addPrivilege(privilege);
    }

    @ApiOperation(value = "修改权限", notes = "修改权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privilege", value = "privilege", required = true, dataType = "Privilege"  ),
    })
    @RequestMapping(value = "/updatePrivilege", method = RequestMethod.POST)
    public ResponseResult updatePrivilege(@RequestBody Privilege privilege) {
         
        return privilegeService.updatePrivilege(privilege);
    }
    @ApiOperation(value = "删除权限", notes = "删除权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privilege", value = "privilege", required = true, dataType = "Privilege"  ),
    })
    @RequestMapping(value = "/deletePrivilege", method = RequestMethod.POST)
    public ResponseResult deletePrivilege(@RequestBody Privilege privilege) {
         
        return privilegeService.deletePrivilege(privilege);
    }
    
}
