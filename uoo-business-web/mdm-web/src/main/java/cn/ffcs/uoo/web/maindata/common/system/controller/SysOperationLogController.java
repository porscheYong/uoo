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
package cn.ffcs.uoo.web.maindata.common.system.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.busipublic.resource.dto.ModifyHistory;
import cn.ffcs.uoo.web.maindata.common.system.client.SysOperationLogClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.common.system.vo.LogDTO;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
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
@RestController
@RequestMapping("/system/sysOperationLog")
public class SysOperationLogController {
    @Autowired
    SysOperationLogClient logClient;
    @OperateLog(type=OperateType.SELECT,module="平台系统日志模块",methods="查看日志",desc="")
    @ApiOperation(value = "获取单个数据", notes = "获取单个数据")
    @ApiImplicitParams({
    })
    @GetMapping("/get")
    public ResponseResult<Object> get(@RequestParam("id") Long id,@RequestParam("logEnum")String logEnum){
        return logClient.get(id, logEnum);
    }
    @ApiOperation(value = "关联本次操作日志的变更履历", notes = "获取单个数据")
    @ApiImplicitParams({
    })
    @GetMapping("/getOperatModifyHistory")
    public ResponseResult<List<ModifyHistory>> getOperatModifyHistory(@RequestParam("id") Long id){
        Subject sub=SecurityUtils.getSubject();
        SysUser sysuser = (SysUser) sub.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        return logClient.getOperatModifyHistory(id, sysuser.getUserCode(), sysuser.getUserId());
    }
    @OperateLog(type=OperateType.SELECT,module="平台系统日志模块",methods="分页查看日志",desc="")
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long"  ),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long"  ),
    })
    @GetMapping("/listPage")
    public ResponseResult<Page<LogDTO>> listPage(@RequestParam("pageNo")Integer pageNo, @RequestParam("pageSize") Integer pageSize,@RequestParam("keyWord") String keyWord){
        return logClient.listPage(pageNo, pageSize, keyWord);
    }
}
