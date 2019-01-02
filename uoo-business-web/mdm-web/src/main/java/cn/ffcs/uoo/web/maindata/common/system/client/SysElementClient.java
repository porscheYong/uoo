package cn.ffcs.uoo.web.maindata.common.system.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysElementClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysElement;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysElementVO;
@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysElementClientHystrix.class)
public interface SysElementClient {
     
    @RequestMapping("/system/SysElement/list")
    public ResponseResult<List<SysElement>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord);
     
    @RequestMapping("/system/SysElement/getElementByAccout")
    public ResponseResult<List<SysElement>> getElementByAccout(String accout);
     
    @RequestMapping("/system/SysElement/get")
    public ResponseResult<SysElementVO> get(@RequestParam("id")Long id);
     
    @RequestMapping(value="/system/SysElement/add",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> add(@RequestBody SysElement ele);
     
    @RequestMapping(value="/system/SysElement/update",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> update(@RequestBody SysElement ele);
     
    @RequestMapping(value="/system/SysElement/delete",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> delete(@RequestBody SysElement fun);
}
