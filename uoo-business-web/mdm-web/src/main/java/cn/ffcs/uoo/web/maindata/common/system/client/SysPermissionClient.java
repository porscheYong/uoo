package cn.ffcs.uoo.web.maindata.common.system.client;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysPermissionClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysPermission;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysPermissionDTO;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysPermissionEditDTO;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysPermissionPrivDTO;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysPermissionClientHystrix.class)
public interface SysPermissionClient {
    
    @GetMapping("/system/sysPermission/get/{id}")
    public ResponseResult<SysPermissionPrivDTO> get(@PathVariable(value="id" ,required=true) Long id);
    
    @GetMapping("/system/sysPermission/listPage")
    public ResponseResult<Page<SysPermissionDTO>> listPage(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord")String keyWord);
    
     
    @RequestMapping(value = "/system/sysPermission/add", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> add(@RequestBody SysPermissionEditDTO sysPermissionEditDTO) ;
     
    @RequestMapping(value = "/system/sysPermission/update", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> update(@RequestBody SysPermissionEditDTO sysPermissionEditDTO);
    
    @RequestMapping(value = "/system/sysPermission/delete", method = RequestMethod.POST)
    public ResponseResult<Void> delete(@RequestBody SysPermission perm);
}

