package cn.ffcs.uoo.core.permission.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.entity.DataPrivRule;
import cn.ffcs.uoo.core.permission.entity.PrivDataRel;
import cn.ffcs.uoo.core.permission.service.IDataPrivRuleService;
import cn.ffcs.uoo.core.permission.service.IPrivDataRelService;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@Controller
@RequestMapping("/permission/dataPrivRule")
public class DataPrivRuleController {
    @Autowired
    private IDataPrivRuleService ruleSvc;
    @Autowired
    private IPrivDataRelService relSvc;
    
    @Transactional
    @RequestMapping(value="addDataPrivRule",method=RequestMethod.POST)
    public ResponseResult addDataPrivRule(@RequestBody DataPrivRule dataPrivRule){
        Long privDataRelId = dataPrivRule.getPrivDataRelId();
        if(privDataRelId==null){
            return ResponseResult.createErrorResult("请选择权限数据");
        }
        PrivDataRel selectById = relSvc.selectById(privDataRelId);
        if(selectById==null || !StatusCD.VALID.equals(selectById.getStatusCd())){
            return ResponseResult.createErrorResult("无效的权限数据");
        }
        dataPrivRule.setPrivRuleId(ruleSvc.getId());
        dataPrivRule.setStatusCd(StatusCD.VALID);
        dataPrivRule.setCreateDate(new Date());
        dataPrivRule.insert();
        return ResponseResult.createSuccessResult("success");
    }
    
    @Transactional
    @RequestMapping(value="updateDataPrivRule",method=RequestMethod.POST)
    public ResponseResult updateDataPrivRule(@RequestBody DataPrivRule dataPrivRule){
        Long privDataRelId = dataPrivRule.getPrivDataRelId();
        if(privDataRelId==null){
            return ResponseResult.createErrorResult("请选择权限数据");
        }
        PrivDataRel selectById = relSvc.selectById(privDataRelId);
        if(selectById==null || !StatusCD.VALID.equals(selectById.getStatusCd())){
            return ResponseResult.createErrorResult("无效的权限数据");
        }
        dataPrivRule.setUpdateDate(new Date());
        dataPrivRule.updateById();
        return ResponseResult.createSuccessResult("success");
    }
    
    @Transactional
    @RequestMapping(value="deleteDataPrivRule",method=RequestMethod.POST)
    public ResponseResult deleteDataPrivRule(@RequestBody DataPrivRule dataPrivRule){
        Long ruleId = dataPrivRule.getPrivRuleId();
        if(ruleId==null){
            return ResponseResult.createErrorResult("请选择权限规则");
        }
        DataPrivRule entity=new DataPrivRule();
        entity.setPrivRuleId(ruleId);
        entity.setStatusCd(StatusCD.INVALID);
        entity.setStatusDate(new Date());
        ruleSvc.updateById(entity);
        return ResponseResult.createSuccessResult("success");
    }
}

