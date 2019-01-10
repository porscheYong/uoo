package cn.ffcs.uoo.web.maindata.common.system.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysMenuClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysMenu;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysMenuVO;

@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysMenuClientHystrix.class)
public interface SysMenuClient {
    @GetMapping("/system/sysMenu/listPage")
    public ResponseResult<Page<SysMenu>> listPage(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord") String keyWord);
    @RequestMapping(value = "/system/sysMenu/getMenuByAccout/{accout}", method = RequestMethod.GET)
    public ResponseResult<List<SysMenu>> getMenuByAccout(@PathVariable(value = "accout") String accout);
    @GetMapping("/system/sysMenu/get/{id}")
    public ResponseResult<SysMenuVO> get(@PathVariable(value="id" ,required=true) Long id);

    @RequestMapping(value = "/system/sysMenu/update", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> update(@RequestBody SysMenu sysMenu);

    @RequestMapping(value = "/system/sysMenu/add", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> add(@RequestBody SysMenu sysMenu);

    @RequestMapping(value = "/system/sysMenu/delete", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> deletePrivilege(@RequestBody SysMenu sysMenu);
}
