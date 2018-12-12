package cn.ffcs.uoo.web.maindata.busipublic.expando.controller;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbExpandorow;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.TbExpandorowClient;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
public class TbExpandorowController {
    @Resource
    private TbExpandorowClient tbExpandorowClient;

    @ApiOperation(value = "查询扩展行列表", notes = "根据表标识资源标识查询扩展字段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableId", value = "系统表标识",required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "resourceId", value = "资源标识",required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "recordId", value = "业务记录标识",required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/getList/{tableId}/{resourceId}/{recordId}", method = RequestMethod.GET)
    public List<TbExpandorow> queryRowList(@PathVariable Long tableId, @PathVariable String resourceId, @PathVariable String recordId) {
        return tbExpandorowClient.queryRowList(tableId, resourceId, recordId);
    }
}

