package cn.ffcs.uoo.web.maindata.busipublic.expando.controller;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbSystemtable;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.TbSystemtableClient;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 系统表登记
 * 所有业务表都要登记，扩展表需要登记,非扩展表无须定义 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
@Api(value = "tbSystemtable", description = "系统表登记")
@RestController
@RequestMapping("/tbSystemtable")
public class TbSystemtableController {
    @Resource
    private TbSystemtableClient tbSystemtableClient;

    @ApiOperation(value = "新增系统表登记", notes = "新增系统表登记")
    @ApiImplicitParam(name = "tbSystemtable", value = "系统表登记", required = true, dataType = "TbSystemtable")
    @OperateLog(type= OperateType.ADD,module="扩展模块",methods="新增系统表登记",desc="新增系统表登记")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbSystemtable> addTbSystemtable(@RequestBody TbSystemtable tbSystemtable) {
        return tbSystemtableClient.addTbSystemtable(tbSystemtable);
    }

    @ApiOperation(value = "修改系统表标记", notes = "修改系统表标记")
    @ApiImplicitParam(name = "tbSystemtable", value = "系统表标记", required = true, dataType = "TbSystemtable")
    @OperateLog(type= OperateType.UPDATE,module="扩展模块",methods="修改系统表登记",desc="修改系统表登记")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbSystemtable> updateTbSystemtable(@RequestBody TbSystemtable tbSystemtable) {
        return tbSystemtableClient.updateTbSystemtable(tbSystemtable);
    }

    @ApiOperation(value = "删除系统表登记", notes = "删除系统表登记")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableId", value = "系统表标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @OperateLog(type= OperateType.DELETE,module="扩展模块",methods="删除系统表登记",desc="删除系统表登记")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbSystemtable> removeTbSystemtable(Long tableId, Long updateUser) {
        return tbSystemtableClient.removeTbSystemtable(tableId, updateUser);
    }
}

