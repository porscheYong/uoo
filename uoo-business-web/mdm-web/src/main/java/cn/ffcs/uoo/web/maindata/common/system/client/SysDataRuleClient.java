package cn.ffcs.uoo.web.maindata.common.system.client;

import java.util.List;

import cn.ffcs.uoo.web.maindata.common.system.dto.SysTable;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysTableColumn;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysDataRuleVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.*;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysDataRuleClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysDataRule;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;

@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysDataRuleClientHystrix.class)
public interface SysDataRuleClient {
    
    @GetMapping("/system/sysDataRule/getDataRuleByAccout/{accout}/{tableName}")
    public ResponseResult<List<SysDataRule>> getDataRuleByAccout(@PathVariable(value = "accout") String accout,@PathVariable(value = "tableName") String tableName);


    @RequestMapping(value = "/system/sysDataRule/getDataRulePage", method = RequestMethod.GET)
    public ResponseResult<Page<SysDataRuleVo>> getDataRulePage(@RequestParam(value = "search",required = false)String search,
                                                               @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                               @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                               @RequestParam(value = "userId",required = false)Long userId,
                                                               @RequestParam(value = "accout",required = false)String accout);

    @RequestMapping(value = "/system/sysDataRule/getDataRule", method = RequestMethod.GET)
    public ResponseResult<SysDataRuleVo> getDataRule(@RequestParam(value = "id",required = false)String id,
                                                     @RequestParam(value = "userId",required = false)Long userId,
                                                     @RequestParam(value = "accout",required = false)String accout);

    @RequestMapping(value = "/system/sysDataRule/addDataRule", method = RequestMethod.POST)
    public ResponseResult<String> addDataRule(@RequestBody SysDataRuleVo sysDataRuleVo);


    @RequestMapping(value = "/system/sysDataRule/updateDataRule", method = RequestMethod.POST)
    public ResponseResult<String> updateDataRule(@RequestBody SysDataRuleVo sysDataRuleVo);

    @RequestMapping(value = "/system/sysDataRule/getTab", method = RequestMethod.GET)
    public ResponseResult<List<SysTable>> getTab();

    @RequestMapping(value = "/system/sysDataRule/getTabColumn", method = RequestMethod.GET)
    public ResponseResult<List<SysTableColumn>> getTabColumn(@RequestParam(value = "tabId",required = false)String tabId);

    @RequestMapping(value = "/system/sysDataRule/deleteDataRule", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> deleteDataRule(@RequestBody SysDataRuleVo sysDataRuleVo);
}
