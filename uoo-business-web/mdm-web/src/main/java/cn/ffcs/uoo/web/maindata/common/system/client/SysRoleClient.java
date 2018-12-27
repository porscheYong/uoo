/**
 * Copyright (C), 2017-2018, 中电福富信息科技有限公司
 * FileName: SysRoleController
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
import org.springframework.web.bind.annotation.RequestParam;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysRoleClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysRole;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author linmingxu
 * @create 2018/12/4
 * @since 1.0.0
 */
@FeignClient(value = "common-system",configuration = {FeignClientConfiguration.class},fallback = SysRoleClientHystrix.class)
public interface SysRoleClient {
     
     
    @GetMapping("/system/sysRole/listPage/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult<List<SysRole>> listPage(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);
    @GetMapping("/system/sysRole/treeRole")
    public ResponseResult<List<TreeNodeVo>> treeRole(@RequestParam("parentRoleCode") String parentRoleCode);
    
}
