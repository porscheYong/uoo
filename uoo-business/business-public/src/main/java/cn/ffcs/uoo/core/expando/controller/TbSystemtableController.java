package cn.ffcs.uoo.core.expando.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.vo.ResponseResult;
import cn.ffcs.uoo.core.expando.entity.TbExpandocolumn;
import cn.ffcs.uoo.core.expando.entity.TbExpandorow;
import cn.ffcs.uoo.core.expando.entity.TbSystemtable;
import cn.ffcs.uoo.core.expando.service.TbExpandocolumnService;
import cn.ffcs.uoo.core.expando.service.TbExpandorowService;
import cn.ffcs.uoo.core.expando.service.TbSystemtableService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
public class TbSystemtableController extends BaseController {
    @Autowired
    TbSystemtableService tbSystemtableService;
    @Autowired
    TbExpandocolumnService tbExpandocolumnService;
    @Autowired
    TbExpandorowService tbExpandorowService;

    @ApiOperation(value = "新增系统表登记", notes = "新增系统表登记")
    @ApiImplicitParam(name = "tbSystemtable", value = "系统表登记", required = true, dataType = "TbSystemtable")
    @UooLog(value = "新增系统表登记", key = "addTbSystemtable")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbSystemtable> addTbSystemtable(TbSystemtable tbSystemtable) {
        ResponseResult<TbSystemtable> responseResult = new ResponseResult<TbSystemtable>();

        tbSystemtableService.save(tbSystemtable);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增系统表登记成功");
        return responseResult;
    }

    @ApiOperation(value = "修改系统表标记", notes = "修改系统表标记")
    @ApiImplicitParam(name = "tbSystemtable", value = "系统表标记", required = true, dataType = "TbSystemtable")
    @UooLog(value = "修改系统表登记", key = "updateTbSystemtable")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbSystemtable> updateTbSystemtable(TbSystemtable tbSystemtable) {
        ResponseResult<TbSystemtable> responseResult = new ResponseResult<TbSystemtable>();

        // 校验必填项
        if(tbSystemtable.getTableId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入系统表标识");
            return responseResult;
        }

        // 根据主键更新系统表登记
        tbSystemtableService.updateById(tbSystemtable);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("更新系统表登记成功");
        return responseResult;
    }

    @ApiOperation(value = "删除系统表登记", notes = "删除系统表登记")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableId", value = "系统表标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbSystemtable> removeTbSystemtable(Long tableId, Long updateUser) {
        ResponseResult<TbSystemtable> responseResult = new ResponseResult<TbSystemtable>();

        // 校验必填项
        if(tableId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入系统表标识");
            return responseResult;
        }
        if(updateUser == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入修改人");
            return responseResult;
        }

        // 是否存在有效扩展列
        Wrapper<TbExpandocolumn> tbExpandocolumnWrapper = new EntityWrapper<TbExpandocolumn>();
        tbExpandocolumnWrapper.eq("TABLE_ID", tableId);
        tbExpandocolumnWrapper.eq("STATUS_CD", "1000");
        List<TbExpandocolumn> tbExpandocolumnList = tbExpandocolumnService.selectList(tbExpandocolumnWrapper);

        // 该系统表登记存在有效扩展列，不能删除
        if(tbExpandocolumnList != null && tbExpandocolumnList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("该系统表登记存在有效扩展列，不能删除");
            return responseResult;
        }

        // 是否存在有效扩展行
        TbExpandorow tbExpandorow = new TbExpandorow();
        tbExpandorow.setTableId(tableId);
        tbExpandorow.setStatusCd("1000");
        List<TbExpandorow> tbExpandorowList = tbExpandorowService.queryRowList(tbExpandorow);

        // 该系统表登记存在有效扩展行，不能删除
        if(tbExpandocolumnList != null && tbExpandocolumnList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("该系统表登记存在有效扩展行，不能删除");
            return responseResult;
        }

        // 删除系统表登记
        tbSystemtableService.remove(tableId,updateUser);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除系统表登记成功");
        return responseResult;
    }
}

