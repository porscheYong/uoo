package cn.ffcs.uoo.system.controller;


import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.service.ISysDataRuleService;
import cn.ffcs.uoo.system.vo.DataRuleRequestVO;
import cn.ffcs.uoo.system.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
只有需要权限控制的表才进行登记 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-21
 */
@RestController
@RequestMapping("/system/sysDataRule")
public class SysDataRuleController {
    @Autowired
    ISysDataRuleService dataRuleSvc;
    
    @ApiOperation(value = "获取单个用户的数据权限", notes = "获取单个用户的数据权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "requestVo", value = "requestVo", required = true, dataType = "DataRuleRequestVO"  ),
    })
    @UooLog(key="getDataRuleByAccout",value="获取单个用户的数据权限")
    @Transactional
    @RequestMapping(value = "/getDataRuleByAccout", method = RequestMethod.POST)
    public ResponseResult<List<SysDataRule>> getDataRuleByAccout(@RequestBody DataRuleRequestVO requestVo){
        if(requestVo.getTableNames()==null||requestVo.getTableNames().isEmpty()){
            return ResponseResult.createErrorResult("表名不能为空");
        }
        if(StringUtils.isBlank(requestVo.getAccout())){
            return ResponseResult.createErrorResult("账号名不能为空");
        }
        HashMap<String, Object> map=new HashMap<>();
        map.put("accout", requestVo.getAccout());
        map.put("tableNames", requestVo.getTableNames());
        return ResponseResult.createSuccessResult(dataRuleSvc.listByAccout(map), "");
    }
}

