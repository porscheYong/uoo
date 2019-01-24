package cn.ffcs.common.gol.web.controller;

import cn.ffcs.common.gol.entity.ControllerAccessLog;
import cn.ffcs.common.gol.service.AccessLogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="日志服务入口",value="AccessLog")
@RestController
@RequestMapping("/accessLog")
public class AccessLogController {


    @Autowired
    private AccessLogService accessLogService;


    @ApiOperation(value = "ccessLog", notes = "根据条件获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "标识", dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "clazz", value = "类", dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "method", value = "方法", required = true, dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "userId", value = "用户标识", dataType = "Long",paramType="path")
        }
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/ccessLog",method = RequestMethod.POST)
    public Page<ControllerAccessLog> getControllerAccessLog(Long id, String clazz,@PathVariable("method") String method, @PathVariable("userId")  Long userId){

        ControllerAccessLog controllerAccessLog = new ControllerAccessLog();

        controllerAccessLog.setClazz(clazz);
        controllerAccessLog.setMethod(method);
        return accessLogService.findTiy(0,0,controllerAccessLog);
    }

}
