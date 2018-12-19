/**
 * Copyright (C), 2017-2018, 中电福富信息科技有限公司
 * FileName: SysDeptController
 * Author:   linmingxu
 * Date:     2018/12/4 15:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.ffcs.uoo.web.maindata.common.system.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysOperationLogClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysRoleClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysOperationLog;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;

 
@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysOperationLogClientHystrix.class)
public interface SysOperationLogClient {
     
    @GetMapping("/get/{id}")
    public ResponseResult<SysOperationLog> get(@PathVariable(value="id" ,required=true) Long id);

    @GetMapping("/listPage/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult<List<SysOperationLog>> listPage(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);

     
    @RequestMapping(value = "/add", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> add(@RequestBody SysOperationLog sysOperationLog);
}
