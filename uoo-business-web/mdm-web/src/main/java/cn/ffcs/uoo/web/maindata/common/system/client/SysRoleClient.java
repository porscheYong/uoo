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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysRoleClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysRole;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysRoleDTO;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
     
    @OperateLog(type=OperateType.SELECT,module="平台系统角色模块",methods="分页查询角色列表",desc="")
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ),
    })
    @GetMapping("/system/sysRole/listPage")
    public ResponseResult<Page<SysRoleDTO>> listPage(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord") String keyWord,@RequestParam("parentRoleCode")String parentRoleCode,@RequestParam("includChild")Integer includChild);

    @RequestMapping(value = "/system/sysRole/update", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> update(@RequestBody SysRoleDTO sysRole) ;

     
    @RequestMapping(value = "/system/sysRole/add", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> add(@RequestBody SysRoleDTO sysRole) ;

    @RequestMapping(value = "/system/sysRole/delete", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> deleteRole(@RequestBody SysRole sysRole);
     
    @GetMapping("/system/sysRole/get/{id}")
    public ResponseResult<SysRoleDTO> get(@PathVariable(value="id" ,required=true) Long id);
    @GetMapping("/system/sysRole/treeRole")
    public ResponseResult<List<TreeNodeVo>> treeRole(@RequestParam("id") Long id);
    
}
