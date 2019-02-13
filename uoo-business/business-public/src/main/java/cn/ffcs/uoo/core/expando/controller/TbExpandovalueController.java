package cn.ffcs.uoo.core.expando.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.constant.StatusEnum;
import cn.ffcs.uoo.core.expando.entity.TbExpandocolumn;
import cn.ffcs.uoo.core.expando.entity.TbSystemtable;
import cn.ffcs.uoo.core.expando.service.TbExpandocolumnService;
import cn.ffcs.uoo.core.expando.service.TbSystemtableService;
import cn.ffcs.uoo.core.expando.vo.ExpandovalueVo;
import cn.ffcs.uoo.core.vo.ResponseResult;
import cn.ffcs.uoo.core.expando.entity.TbExpandorow;
import cn.ffcs.uoo.core.expando.entity.TbExpandovalue;
import cn.ffcs.uoo.core.expando.service.TbExpandorowService;
import cn.ffcs.uoo.core.expando.service.TbExpandovalueService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    @Autowired
    TbSystemtableService tbSystemtableService;
    @Autowired
    TbExpandocolumnService tbExpandocolumnService;

    @ApiOperation(value = "通过值对象新增扩展值", notes = "通过值对象新增扩展值")
    @ApiImplicitParam(name = "tbExpandovalue", value = "扩展值", required = true, dataType = "TbExpandovalue")
    @UooLog(value = "通过值对象新增扩展值", key = "addExpandoInfo")
    @RequestMapping(value = "/addByVo", method = RequestMethod.POST)
    public ResponseResult<ExpandovalueVo> addExpandoInfo(@RequestBody ExpandovalueVo expandovalueVo) {
        ResponseResult<ExpandovalueVo> responseResult = new ResponseResult<ExpandovalueVo>();

        // 校验必填项
        if(StringUtils.isEmpty(expandovalueVo.getTableName())) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请填写系统表名");
            return responseResult;
        }
        if(StringUtils.isEmpty(expandovalueVo.getColumnName())) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请填写扩展列名");
            return responseResult;
        }
        if(StringUtils.isEmpty(expandovalueVo.getRecordId())) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请填写记录id");
            return responseResult;
        }
        if(StringUtils.isEmpty(expandovalueVo.getData())) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请填写数据值");
            return responseResult;
        }

        // 根据系统表名查询系统表id
        Wrapper<TbSystemtable> tbSystemtableWrapper = new EntityWrapper<TbSystemtable>();
        tbSystemtableWrapper.eq("TABLE_NAME", expandovalueVo.getTableName());
        tbSystemtableWrapper.eq("STATUS_CD", StatusEnum.VALID.getValue());
        List<TbSystemtable> tbSystemtables = tbSystemtableService.selectList(tbSystemtableWrapper);
        if(tbSystemtables == null || tbSystemtables.size() <= 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("该系统表名对应的记录不存在");
            return responseResult;
        }

        Long tableId = tbSystemtables.get(0).getTableId();

        // 根据扩展列名查询扩展列id
        Wrapper<TbExpandocolumn> tbExpandocolumnWrapper = new EntityWrapper<TbExpandocolumn>();
        tbExpandocolumnWrapper.eq("TABLE_ID", tableId);
        tbExpandocolumnWrapper.eq("COLUMN_NAME", expandovalueVo.getColumnName());
        tbExpandocolumnWrapper.eq("STATUS_CD", StatusEnum.VALID.getValue());
        List<TbExpandocolumn> tbExpandocolumns = tbExpandocolumnService.selectList(tbExpandocolumnWrapper);
        if(tbExpandocolumns == null || tbExpandocolumns.size() <= 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("该扩展列名对应的记录不存在");
            return responseResult;
        }

        Long columnId = tbExpandocolumns.get(0).getColumnId();

        // 判断当前记录扩展值是否存在
        Wrapper<TbExpandovalue> tbExpandovalueWrapper = new EntityWrapper<TbExpandovalue>();
        tbExpandovalueWrapper.eq("TABLE_ID", tableId);
        tbExpandovalueWrapper.eq("COLUMN_ID", columnId);
        tbExpandovalueWrapper.eq("RECORD_ID", expandovalueVo.getRecordId());
        tbExpandovalueWrapper.eq("DATA_", expandovalueVo.getData());
        tbExpandovalueWrapper.eq("STATUS_CD", StatusEnum.VALID.getValue());
        List<TbExpandovalue> tbExpandovalues = tbExpandovalueService.selectList(tbExpandovalueWrapper);
        if(tbExpandovalues != null && tbExpandovalues.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("当前记录该扩展值已存在");
            return responseResult;
        }

        // 新增扩展行
        TbExpandorow tbExpandorow = new TbExpandorow();
        tbExpandorow.setTableId(tableId);
        tbExpandorow.setRecordId(expandovalueVo.getRecordId());
        tbExpandorow.setStatusCd(StatusEnum.VALID.getValue());
        tbExpandorow.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandorow.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandorow.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandorowService.save(tbExpandorow);

        // 新增扩展值
        TbExpandovalue tbExpandovalue = new TbExpandovalue();
        tbExpandovalue.setTableId(tableId);
        tbExpandovalue.setColumnId(columnId);
        tbExpandovalue.setRecordId(expandovalueVo.getRecordId());
        tbExpandovalue.setData(expandovalueVo.getData());
        tbExpandovalue.setRowId(tbExpandorow.getRowId());
        tbExpandovalue.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandovalue.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandovalue.setStatusCd(StatusEnum.VALID.getValue());
        tbExpandovalue.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandovalueService.save(tbExpandovalue);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增扩展值成功");
        return responseResult;
    }

    @ApiOperation(value = "新增扩展值", notes = "新增扩展值")
    @ApiImplicitParam(name = "tbExpandovalue", value = "扩展值", required = true, dataType = "TbExpandovalue")
    @UooLog(value = "新增扩展值", key = "addTbExpandovalue")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbExpandovalue> addTbExpandovalue(@RequestBody TbExpandovalue tbExpandovalue) {
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
        tbExpandorow.setStatusCd(StatusEnum.VALID.getValue());
        tbExpandorow.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandorow.setCreateUser(tbExpandovalue.getCreateUser());
        tbExpandorow.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandorow.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandorow.setUpdateUser(tbExpandovalue.getUpdateUser());
        tbExpandorowService.save(tbExpandorow);

        // 新增扩展值
        tbExpandovalue.setRowId(tbExpandorow.getRowId());
        tbExpandovalue.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandovalue.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandovalue.setStatusCd(StatusEnum.VALID.getValue());
        tbExpandovalue.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
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
    @UooLog(value = "删除扩展值", key = "removeTbExpandovalue")
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
        tbExpandovalue.setStatusCd(StatusEnum.VALID.getValue());

        List<TbExpandovalue> tbExpandovalueList = tbExpandovalueService.selectValueList(tbExpandovalue);
        if (tbExpandovalueList == null || tbExpandovalueList.size() <= 0) {
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
    public ResponseResult<TbExpandovalue> updateTbExpandovalue(@RequestBody TbExpandovalue tbExpandovalue) {
        ResponseResult<TbExpandovalue> responseResult = new ResponseResult<TbExpandovalue>();

        // 校验必填项
        if (tbExpandovalue.getValueId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入扩展值标识");
            return responseResult;
        }

        tbExpandovalue.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandovalue.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandovalue.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
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

    @ApiOperation(value = "查询扩展值值对象列表", notes = "查询扩展值值对象列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "recordId", value = "记录id", required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "查询扩展值值对象列表", key = "queryExpandovalueVoList")
    @RequestMapping(value = "/getValueVoList/{tableName}/{recordId}", method = RequestMethod.GET)
    public ResponseResult<List<ExpandovalueVo>> queryExpandovalueVoList(@PathVariable String tableName,
                                                                        @PathVariable String recordId) {
        ResponseResult<List<ExpandovalueVo>> responseResult = new ResponseResult<List<ExpandovalueVo>>();

        // 校验必填项
        if(StringUtils.isEmpty(tableName)) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入表名");
            return responseResult;
        }
        if(StringUtils.isEmpty(recordId)) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入记录id");
            return responseResult;
        }

        // 查询扩展值值对象列表
        List<ExpandovalueVo> valueList = tbExpandovalueService.selectExpandovalueVoList(tableName, recordId);

        // 返回结果
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("查询成功");
        responseResult.setData(valueList);
        return responseResult;
    }

    @ApiOperation(value = "校验组织U5节点", notes = "校验组织U5节点")
    @ApiImplicitParam(name = "orgIds", value = "组织数组", required = true, dataType = "List")
    @UooLog(value = "校验组织U5节点", key = "checkOrganizationU5NodeType")
    @RequestMapping(value = "/checkOrgU5Node", method = RequestMethod.POST)
    public ResponseResult<String> checkOrganizationU5NodeType(@RequestBody List<String> orgIds) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        // 初始化为0,表示没有U5节点
        String flag = "0";

        // 校验必填项
        if(orgIds == null || orgIds.isEmpty()) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入组织id");
            return responseResult;
        }

        for(String orgId : orgIds) {
            TbExpandovalue tbExpandovalue = new TbExpandovalue();
            tbExpandovalue.setRecordId(orgId);
            tbExpandovalue.setStatusCd(StatusEnum.VALID.getValue());
            tbExpandovalue.setData("U5-%");
            List<TbExpandovalue> valueList = tbExpandovalueService.selectValueList(tbExpandovalue);

            // 存在U5类型组织
            if(valueList !=  null && valueList.size() >0) {
                flag = "1";
                break;
            }
        }

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("校验U5节点");
        responseResult.setData(flag);
        return responseResult;
    }
}

