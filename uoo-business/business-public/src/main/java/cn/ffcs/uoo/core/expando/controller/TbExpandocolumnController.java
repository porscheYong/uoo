package cn.ffcs.uoo.core.expando.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.constant.StatusEnum;
import cn.ffcs.uoo.core.vo.ResponseResult;
import cn.ffcs.uoo.core.expando.entity.TbExpandocolumn;
import cn.ffcs.uoo.core.expando.entity.TbExpandovalue;
import cn.ffcs.uoo.core.expando.service.TbExpandocolumnService;
import cn.ffcs.uoo.core.expando.service.TbExpandovalueService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 扩展列 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
@Api(description = "扩展列", value = "tbExpandocolumn")
@RestController
@RequestMapping("/tbExpandocolumn")
public class TbExpandocolumnController extends BaseController {
    @Autowired
    TbExpandocolumnService tbExpandocolumnService;
    @Autowired
    TbExpandovalueService tbExpandovalueService;

    @ApiOperation(value = "修改扩展列", notes = "修改扩展列")
    @ApiImplicitParam(name = "tbExpandocolumn", value = "扩展列", required = true, dataType = "TbExpandocolumn")
    @UooLog(value = "修改扩展列", key = "updateTbExpandovalue")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbExpandocolumn> updateTbExpandocolumn(@RequestBody TbExpandocolumn tbExpandocolumn) {
        ResponseResult<TbExpandocolumn> responseResult = new ResponseResult<TbExpandocolumn>();

        // 校验必填项
        if (tbExpandocolumn.getColumnId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入扩展列标识");
            return responseResult;
        }

        tbExpandocolumn.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandocolumn.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandocolumnService.updateById(tbExpandocolumn);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("扩展列修改成功");
        return responseResult;
    }

    @ApiOperation(value = "新增扩展列", notes = "新增扩展列")
    @ApiImplicitParam(name = "tbExpandocolumn", value = "扩展列", required = true, dataType = "TbExpandocolumn")
    @UooLog(value = "新增扩展列", key = "addTbExpandocolumn")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbExpandocolumn> addTbExpandocolumn(@RequestBody TbExpandocolumn tbExpandocolumn) {
        ResponseResult<TbExpandocolumn> responseResult = new ResponseResult<TbExpandocolumn>();

        // 校验必填项
        if (tbExpandocolumn.getTableId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入系统表标识");
            return responseResult;
        }
        if (tbExpandocolumn.getColLenght() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入字段长度");
            return responseResult;
        }
        if (tbExpandocolumn.getSort() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入排序");
            return responseResult;
        }

        tbExpandocolumn.setUpdateDate(DateUtils.parseDate(DateUtils.getDate()));
        tbExpandocolumn.setStatusDate(DateUtils.parseDate(DateUtils.getDate()));
        tbExpandocolumn.setStatusCd(StatusEnum.VALID.getValue());
        tbExpandocolumn.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        // 新增扩展列
        tbExpandocolumnService.save(tbExpandocolumn);

        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("新增扩展列成功");
        return responseResult;
    }

    @ApiOperation(value = "删除扩展列", notes = "删除扩展列")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "columnId", value = "扩展列标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @UooLog(value = "删除扩展列", key = "removeTbExpandocolumn")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbExpandocolumn> removeTbExpandocolumn(Long columnId, Long updateUser) {
        ResponseResult<TbExpandocolumn> responseResult = new ResponseResult<TbExpandocolumn>();

        // 校验必填项
        if (columnId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入扩展列标识");
            return responseResult;
        }
        if (updateUser == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入修改人");
            return responseResult;
        }

        // 查询是否存在有效的扩展值
        TbExpandovalue tbExpandovalue = new TbExpandovalue();
        tbExpandovalue.setColumnId(columnId);
        tbExpandovalue.setStatusCd(StatusEnum.VALID.getValue());
        List<TbExpandovalue> tbExpandovalueList = tbExpandovalueService.selectValueList(tbExpandovalue);

        // 该扩展列存在有效扩展值，不能删除
        if(tbExpandovalueList != null && tbExpandovalueList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("该扩展列存在有效扩展值，不能删除");
            return responseResult;
        }

        tbExpandocolumnService.remove(columnId, updateUser);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除扩展列成功");
        return responseResult;
    }

    @ApiOperation(value = "查询扩展字段列表", notes = "查询扩展字段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableId", value = "系统表标识",required = true, dataType = "Long", paramType = "path")
    })
    @UooLog(value = "查询扩展字段列表", key = "queryListByTableId")
    @RequestMapping(value = "/getList/{tableId}", method = RequestMethod.GET)
    public List<TbExpandocolumn> queryListByTableId(@PathVariable Long tableId) {
        // 校验必填项
        if(tableId == null) {
            return null;
        }

        Wrapper<TbExpandocolumn> wrapper = new EntityWrapper<TbExpandocolumn>();
        wrapper.eq("TABLE_ID", tableId);
        wrapper.eq("STATUS_CD", "1000");
        List<TbExpandocolumn> tbExpandocolumnList = tbExpandocolumnService.selectList(wrapper);
        return tbExpandocolumnList;
    }

    @ApiOperation(value = "根据表标识资源标识查询扩展字段列表", notes = "根据表标识资源标识查询扩展字段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableId", value = "系统表标识",required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "resourceId", value = "资源标识",required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "根据表标识资源标识查询扩展字段列表", key = "queryColumnList")
    @RequestMapping(value = "/getColumnList/{tableId}/{resourceId}", method = RequestMethod.GET)
    public List<TbExpandocolumn> queryColumnList(@PathVariable  Long tableId, @PathVariable String resourceId) {
        // 校验必填项
        if(tableId == null) {
            return null;
        }
        if(StringUtils.isEmpty(resourceId)) {
            return null;
        }

        return tbExpandocolumnService.queryColumnList(tableId, resourceId);
    }
}

