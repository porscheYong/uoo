package cn.ffcs.uoo.core.expando.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.dictionary.vo.ResponseResult;
import cn.ffcs.uoo.core.expando.entity.TbExpandorow;
import cn.ffcs.uoo.core.expando.entity.TbExpandovalue;
import cn.ffcs.uoo.core.expando.service.TbExpandorowService;
import cn.ffcs.uoo.core.expando.service.TbExpandovalueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

        // 查询出对应的扩展行id
        TbExpandovalue tbExpandovalue = tbExpandovalueService.selectById(valueId);

        // 删除扩展值
        tbExpandovalueService.remove(valueId, updateUser);

        // 删除扩展行
        tbExpandorowService.remove(tbExpandovalue.getRowId(), updateUser);

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
        if (tbExpandovalue.getTableId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入系统表标识");
            return responseResult;
        }
        if (tbExpandovalue.getColumnId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入扩展列标识");
            return responseResult;
        }
        if (tbExpandovalue.getRowId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入扩展行标识");
            return responseResult;
        }

        tbExpandovalueService.updateById(tbExpandovalue);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("修改扩展值成功");
        return responseResult;
    }
}

