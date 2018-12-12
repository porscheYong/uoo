package cn.ffcs.uoo.web.maindata.common.system.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysMenuClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysUserClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysMenu;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;

@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysMenuClientHystrix.class)
public interface SysMenuClient {
    
    @GetMapping("/system/sysMenu/listPage/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult<List<SysMenu>> listPage(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);
    @RequestMapping(value = "/system/sysMenu/getMenuByAccout/{accout}", method = RequestMethod.POST)
    public ResponseResult<List<SysMenu>> getMenuByAccout(@PathVariable(value = "accout") String accout);
}
