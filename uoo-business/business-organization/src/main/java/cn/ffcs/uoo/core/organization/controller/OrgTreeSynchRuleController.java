package cn.ffcs.uoo.core.organization.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.entity.OrgTreeSynchRule;
import cn.ffcs.uoo.core.organization.service.IOrgTreeSynchRuleService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.OrgTreeSynchRuleVO;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-03-04
 */
@RestController
@RequestMapping("/org/orgTreeSynchRule")
public class OrgTreeSynchRuleController {
    @Autowired
    IOrgTreeSynchRuleService iOrgTreeSynchRuleService;
    @ApiOperation(value = "GET_ID", notes = "GET_ID")
    @ApiImplicitParams({
    })
    @UooLog(key="get",value="get")
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public ResponseResult<OrgTreeSynchRule> get(@RequestParam("id")Long id){
        return ResponseResult.createSuccessResult(iOrgTreeSynchRuleService.selectById(id),"");
    }
    @ApiOperation(value = "getTree", notes = "getTree")
    @ApiImplicitParams({
    })
    @UooLog(key="getTree",value="getTree")
    @RequestMapping(value="/getTree",method = RequestMethod.GET)
    public ResponseResult<List<OrgTree>> getTree(@RequestParam("id")Long id){
        return ResponseResult.createSuccessResult(iOrgTreeSynchRuleService.getRelTree(id),"");
    }
    @ApiOperation(value = "LIST_PAGE", notes = "LIST_PAGE")
    @ApiImplicitParams({
    })
    @UooLog(key="LIST",value="LIST")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ResponseResult<List<OrgTreeSynchRuleVO>> list(@RequestParam("orgTreeId")Long orgTreeId){
        return ResponseResult.createSuccessResult(iOrgTreeSynchRuleService.listByToOrgTreeId(orgTreeId) ,"");
    }
    @ApiOperation(value = "add", notes = "add")
    @ApiImplicitParams({
    })
    @UooLog(key="add",value="add")
    @Transactional
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody OrgTreeSynchRule orgTreeSynchRule){
        orgTreeSynchRule.setCreateDate(new Date());
        orgTreeSynchRule.setOrgTreeSynchRuleId(iOrgTreeSynchRuleService.getId());
        orgTreeSynchRule.setStatusCd("1000");
        orgTreeSynchRule.setStatusDate(new Date());
        iOrgTreeSynchRuleService.insert(orgTreeSynchRule);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "update", notes = "update")
    @ApiImplicitParams({
    })
    @UooLog(key="update",value="update")
    @Transactional
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody OrgTreeSynchRule orgTreeSynchRule){
        orgTreeSynchRule.setUpdateDate(new Date());
        iOrgTreeSynchRuleService.updateById(orgTreeSynchRule);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "delete", notes = "delete")
    @ApiImplicitParams({
    })
    @UooLog(key="delete",value="delete")
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @Transactional
    public ResponseResult<Void> delete(@RequestBody OrgTreeSynchRule orgTreeSynchRule){
        orgTreeSynchRule.setStatusCd("1100");
        orgTreeSynchRule.setStatusDate(new Date());
        iOrgTreeSynchRuleService.updateById(orgTreeSynchRule);
        return ResponseResult.createSuccessResult("");
    }
}
