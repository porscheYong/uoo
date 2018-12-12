package cn.ffcs.uoo.web.maindata.busipublic.expando.controller;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbExpandocolumn;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.TbExpandocolumnClient;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(description = "扩展列", value = "tbExpandocolumn")
@RestController
@RequestMapping("/tbExpandocolumn")
public class TbExpandocolumnController {
    @Resource
    TbExpandocolumnClient tbExpandocolumnClient;

    @ApiOperation(value = "修改扩展列", notes = "修改扩展列")
    @ApiImplicitParam(name = "tbExpandocolumn", value = "扩展列", required = true, dataType = "TbExpandocolumn")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbExpandocolumn> updateTbExpandocolumn(@RequestBody TbExpandocolumn tbExpandocolumn) {
        return tbExpandocolumnClient.updateTbExpandocolumn(tbExpandocolumn);
    }

    @ApiOperation(value = "新增扩展列", notes = "新增扩展列")
    @ApiImplicitParam(name = "tbExpandocolumn", value = "扩展列", required = true, dataType = "TbExpandocolumn")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbExpandocolumn> addTbExpandocolumn(@RequestBody TbExpandocolumn tbExpandocolumn) {
        return tbExpandocolumnClient.addTbExpandocolumn(tbExpandocolumn);
    }

    @ApiOperation(value = "删除扩展列", notes = "删除扩展列")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "columnId", value = "扩展列标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbExpandocolumn> removeTbExpandocolumn(@RequestBody Long columnId, @RequestBody Long updateUser) {
        return tbExpandocolumnClient.removeTbExpandocolumn(columnId, updateUser);
    }

    @ApiOperation(value = "查询扩展字段列表", notes = "查询扩展字段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableId", value = "系统表标识",required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping(value = "/getList/{tableId}", method = RequestMethod.GET)
    public List<TbExpandocolumn> queryListByTableId(@PathVariable Long tableId) {
        return tbExpandocolumnClient.queryListByTableId(tableId);
    }

    @ApiOperation(value = "根据表标识资源标识查询扩展字段列表", notes = "根据表标识资源标识查询扩展字段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableId", value = "系统表标识",required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "resourceId", value = "资源标识",required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/getColumnList/{tableId}/{resourceId}", method = RequestMethod.GET)
    public List<TbExpandocolumn> queryColumnList(@PathVariable Long tableId, @PathVariable String resourceId) {
        return tbExpandocolumnClient.queryColumnList(tableId, resourceId);
    }
}
