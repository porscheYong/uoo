package cn.ffcs.uoo.web.maindata.busipublic.expando.controller;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.ExpandovalueVo;
import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbExpandorow;
import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbExpandovalue;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.TbExpandovalueClient;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 扩展值 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
@Api(value = "tbExpandovalue", description = "扩展值")
@RestController
@RequestMapping("/tbExpandovalue")
public class TbExpandovalueController {
    @Resource
    private TbExpandovalueClient tbExpandovalueClient;

    @ApiOperation(value = "新增扩展值", notes = "新增扩展值")
    @ApiImplicitParam(name = "tbExpandovalue", value = "扩展值", required = true, dataType = "TbExpandovalue")
    @OperateLog(type= OperateType.ADD,module="扩展模块",methods="新增扩展值",desc="新增扩展值")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbExpandovalue> addTbExpandovalue(@RequestBody TbExpandovalue tbExpandovalue) {
        return tbExpandovalueClient.addTbExpandovalue(tbExpandovalue);
    }

    @ApiOperation(value = "删除扩展值", notes = "删除扩展值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "valueId", value = "扩展值标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @OperateLog(type= OperateType.DELETE,module="扩展模块",methods="删除扩展值",desc="删除扩展值")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbExpandovalue> removeTbExpandovalue(Long valueId, Long updateUser) {
        return tbExpandovalueClient.removeTbExpandovalue(valueId, updateUser);
    }

    @ApiOperation(value = "修改扩展值", notes = "修改扩展值")
    @ApiImplicitParam(name = "tbExpandovalue", value = "扩展值", required = true, dataType = "TbExpandovalue")
    @OperateLog(type= OperateType.UPDATE,module="扩展模块",methods="修改扩展值",desc="修改扩展值")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbExpandovalue> updateTbExpandovalue(@RequestBody TbExpandovalue tbExpandovalue) {
        return tbExpandovalueClient.updateTbExpandovalue(tbExpandovalue);
    }

    @ApiOperation(value = "查询扩展值列表", notes = "查询扩展值列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceId", value = "资源标识", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "tableId", value = "系统表标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "columnId", value = "扩展列标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "recordId", value = "业务记录标识", required = true, dataType = "Long", paramType = "path")
    })
    @OperateLog(type= OperateType.SELECT,module="扩展模块",methods="查询扩展值列表",desc="查询扩展值列表")
    @RequestMapping(value = "/getList/{resourceId}/{tableId}/{columnId}/{recordId}", method = RequestMethod.GET)
    public List<TbExpandovalue> queryValueList(@PathVariable String resourceId, @PathVariable Long tableId,
                                               @PathVariable Long columnId, @PathVariable String recordId) {
        return tbExpandovalueClient.queryValueList(resourceId, tableId, columnId, recordId);
    }

    @ApiOperation(value = "查询扩展值值对象列表", notes = "查询扩展值值对象列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "recordId", value = "记录id", required = true, dataType = "String", paramType = "path")
    })
    @OperateLog(type= OperateType.SELECT,module="扩展模块",methods="查询扩展值值对象列表",desc="查询扩展值值对象列表")
    @RequestMapping(value = "/getValueVoList/{tableName}/{recordId}", method = RequestMethod.GET)
    public ResponseResult<List<ExpandovalueVo>> queryExpandovalueVoList(@PathVariable String tableName,
                                                                        @PathVariable String recordId) {
        return tbExpandovalueClient.queryExpandovalueVoList(tableName, recordId);
    }

    @ApiOperation(value = "通过值对象新增扩展值", notes = "通过值对象新增扩展值")
    @ApiImplicitParam(name = "tbExpandovalue", value = "扩展值", required = true, dataType = "TbExpandovalue")
    @OperateLog(type= OperateType.ADD,module="扩展模块",methods="通过值对象新增扩展值",desc="通过值对象新增扩展值")
    @RequestMapping(value = "/addByVo", method = RequestMethod.POST)
    public ResponseResult<ExpandovalueVo> addExpandoInfo(@RequestBody ExpandovalueVo expandovalueVo) {
        return tbExpandovalueClient.addExpandoInfo(expandovalueVo);
    }

    @ApiOperation(value = "校验组织U5节点", notes = "校验组织U5节点")
    @ApiImplicitParam(name = "orgIds", value = "组织数组", required = true, dataType = "List")
    @OperateLog(type= OperateType.SELECT,module="扩展模块",methods="校验组织U5节点",desc="校验组织U5节点")
    @RequestMapping(value = "/checkOrgU5Node", method = RequestMethod.POST)
    public ResponseResult<String> checkOrganizationU5NodeType(@RequestBody List<String> orgIds) {
        return tbExpandovalueClient.checkOrganizationU5NodeType(orgIds);
    }
}

