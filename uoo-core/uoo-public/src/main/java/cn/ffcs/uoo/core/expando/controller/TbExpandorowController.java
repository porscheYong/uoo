package cn.ffcs.uoo.core.expando.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.expando.entity.TbExpandorow;
import cn.ffcs.uoo.core.expando.service.TbExpandorowService;
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
 * 保留，如通讯号码就对应多个 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
@RestController
@RequestMapping("/tbExpandorow")
public class TbExpandorowController extends BaseController {
    @Autowired
    TbExpandorowService tbExpandorowService;

    @ApiOperation(value = "查询扩展行列表", notes = "根据表标识资源标识查询扩展字段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableId", value = "系统表标识",required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "resourceId", value = "资源标识",required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "recordId", value = "业务记录标识",required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "查询扩展行列表", key = "queryRowList")
    @RequestMapping(value = "/getList/{tableId}/{resourceId}/{recordId}", method = RequestMethod.GET)
    public List<TbExpandorow> queryRowList(@PathVariable Long tableId, @PathVariable String resourceId, @PathVariable String recordId) {
        // 校验必填项
        if(tableId == null) {
            return null;
        }
        if(StringUtils.isEmpty(resourceId)) {
            return null;
        }
        if(StringUtils.isEmpty(recordId)) {
            return null;
        }

        TbExpandorow tbExpandorow = new TbExpandorow();
        tbExpandorow.setTableId(tableId);
        tbExpandorow.setResourceId(resourceId);
        tbExpandorow.setRecordId(recordId);
        return tbExpandorowService.queryRowList(tbExpandorow);
    }
}

