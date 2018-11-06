package cn.ffcs.uoo.core.expando.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.vo.ResponseResult;
import cn.ffcs.uoo.core.expando.entity.TbExpandorow;
import cn.ffcs.uoo.core.expando.entity.TbExpandovalue;
import cn.ffcs.uoo.core.expando.service.TbExpandorowService;
import cn.ffcs.uoo.core.expando.service.TbExpandovalueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class TbExpandovalueController extends BaseController {
    @Autowired
    TbExpandovalueService tbExpandovalueService;
    @Autowired
    TbExpandorowService tbExpandorowService;

    @ApiOperation(value = "新增扩展值", notes = "新增扩展值")
    @ApiImplicitParam(name = "tbExpandovalue", value = "扩展值", required = true, dataType = "TbExpandovalue")
    @UooLog(value = "新增扩展值", key = "addTbExpandovalue")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbExpandovalue> addTbExpandovalue(TbExpandovalue tbExpandovalue) {
        ResponseResult<TbExpandovalue> responseResult = new ResponseResult<TbExpandovalue>();

        // 校验必填项
        if (tbExpandovalue.getTableId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请填写系统表id");
            return responseResult;
        }
        if (tbExpandovalue.getColumnId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请填写扩展列id");
            return responseResult;
        }

        // 新增扩展行
        TbExpandorow tbExpandorow = new TbExpandorow();
        tbExpandorow.setTableId(tbExpandovalue.getTableId());
        tbExpandorow.setResourceId(tbExpandovalue.getResourceId());
        tbExpandorow.setRecordId(tbExpandovalue.getRecordId());
        tbExpandorow.setStatusCd(tbExpandovalue.getStatusCd());
        tbExpandorow.setCreateDate(tbExpandovalue.getCreateDate());
        tbExpandorow.setCreateUser(tbExpandovalue.getCreateUser());
        tbExpandorow.setStatusDate(tbExpandovalue.getStatusDate());
        tbExpandorowService.save(tbExpandorow);

        // 新增扩展值
        tbExpandovalue.setRowId(tbExpandorow.getRowId());
        tbExpandovalueService.save(tbExpandovalue);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增扩展值成功");
        return responseResult;
    }

    @ApiOperation(value = "删除扩展值", notes = "删除扩展值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "valueId", value = "扩展值标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbExpandovalue> removeTbExpandovalue(Long valueId, Long updateUser) {
        ResponseResult<TbExpandovalue> responseResult = new ResponseResult<TbExpandovalue>();

        // 校验必填项
        if (valueId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入扩展值id");
            return responseResult;
        }
        if (updateUser == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入更新人");
            return responseResult;
        }

        TbExpandovalue tbExpandovalue = new TbExpandovalue();
        tbExpandovalue.setValueId(valueId);
        tbExpandovalue.setStatusCd("1000");

        List<TbExpandovalue>  tbExpandovalueList = tbExpandovalueService.selectValueList(tbExpandovalue);
        if(tbExpandovalueList == null || tbExpandovalueList.size() <= 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("该扩展值id对应的扩展值不存在");
            return responseResult;
        }

        // 查询出对应的扩展行id
        TbExpandovalue tbExpandovalueOut = tbExpandovalueList.get(0);

        // 删除扩展值
        tbExpandovalueService.remove(valueId, updateUser);

        // 删除扩展行
        tbExpandorowService.remove(tbExpandovalueOut.getRowId(), updateUser);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除成功");
        return responseResult;
    }

    @ApiOperation(value = "修改扩展值", notes = "修改扩展值")
    @ApiImplicitParam(name = "tbExpandovalue", value = "扩展值", required = true, dataType = "TbExpandovalue")
    @UooLog(value = "修改扩展值", key = "updateTbExpandovalue")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbExpandovalue> updateTbExpandovalue(TbExpandovalue tbExpandovalue) {
        ResponseResult<TbExpandovalue> responseResult = new ResponseResult<TbExpandovalue>();

        // 校验必填项
        if (tbExpandovalue.getValueId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入扩展值标识");
            return responseResult;
        }

        tbExpandovalueService.updateById(tbExpandovalue);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("修改扩展值成功");
        return responseResult;
    }

    @ApiOperation(value = "查询扩展值列表", notes = "查询扩展值列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceId", value = "资源标识", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "tableId", value = "系统表标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "columnId", value = "扩展列标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "recordId", value = "业务记录标识", required = true, dataType = "Long", paramType = "path")
    })
    @UooLog(value = "查询扩展值列表", key = "queryValueList")
    @RequestMapping(value = "/getList/{resourceId}/{tableId}/{columnId}/{recordId}", method = RequestMethod.GET)
    public List<TbExpandovalue> queryValueList(@PathVariable String resourceId, @PathVariable Long tableId,
                                               @PathVariable Long columnId, @PathVariable String recordId) {
        // 校验必填项
        if (StringUtils.isEmpty(resourceId)) {
            return null;
        }
        if (tableId == null) {
            return null;
        }
        if (columnId == null) {
            return null;
        }
        if (StringUtils.isEmpty(recordId)) {
            return null;
        }

        TbExpandovalue tbExpandovalue = new TbExpandovalue();
        tbExpandovalue.setResourceId(resourceId);
        tbExpandovalue.setTableId(tableId);
        tbExpandovalue.setColumnId(columnId);
        tbExpandovalue.setRecordId(recordId);

        return tbExpandovalueService.selectValueList(tbExpandovalue);
    }
}
