package cn.ffcs.uoo.core.permission.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.entity.DataPrivRule;
import cn.ffcs.uoo.core.permission.entity.PrivDataRel;
import cn.ffcs.uoo.core.permission.entity.Privilege;
import cn.ffcs.uoo.core.permission.service.IDataPrivRuleService;
import cn.ffcs.uoo.core.permission.service.IPrivDataRelService;
import cn.ffcs.uoo.core.permission.service.IPrivilegeService;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 记录权限与业务对象之间多对多的关系 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@RestController
@RequestMapping("/permission/privDataRel")
public class PrivDataRelController {
    @Autowired
    private IPrivDataRelService relSvc;
    @Autowired
    private IDataPrivRuleService ruleSvc;
    @Autowired
    private IPrivilegeService privilegeService;
    
    @ApiOperation(value = "添加权限与业务对象之间多对多的关系", notes = "添加权限与业务对象之间多对多的关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privDataRel", value = "privDataRel", required = true, dataType = "PrivDataRel"  ),
    })
    @UooLog(key="addPrivDataRel",value="添加权限与业务对象之间多对多的关系")
    @Transactional
    @RequestMapping(value="addPrivDataRel",method=RequestMethod.POST)
    public ResponseResult addPrivDataRel(@RequestBody PrivDataRel privDataRel){
        Long privId = privDataRel.getPrivId();
        if(privId==null){
            return ResponseResult.createErrorResult("权限标识不能为空");
        }
        Privilege privilege = privilegeService.selectById(privId);
        if(!StatusCD.VALID.equals(privilege.getStatusCd())){
            return ResponseResult.createErrorResult("无效的权限标识");
        }
        privDataRel.setCreateDate(new Date());
        privDataRel.setPrivDataRelId(relSvc.getId());
        privDataRel.setStatusCd(StatusCD.VALID);
        relSvc.insert(privDataRel);
        return ResponseResult.createSuccessResult("success");
    }
    
    @ApiOperation(value = "修改权限与业务对象之间多对多的关系", notes = "修改权限与业务对象之间多对多的关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privDataRel", value = "privDataRel", required = true, dataType = "PrivDataRel"  ),
    })
    @UooLog(key="updatePrivDataRel",value="修改权限与业务对象之间多对多的关系")
    @Transactional
    @RequestMapping(value="updatePrivDataRel",method=RequestMethod.POST)
    public ResponseResult updatePrivDataRel(@RequestBody PrivDataRel privDataRel){
        Long privId = privDataRel.getPrivId();
        if(privId==null){
            return ResponseResult.createErrorResult("权限标识不能为空");
        }
        Privilege privilege = privilegeService.selectById(privId);
        if(!StatusCD.VALID.equals(privilege.getStatusCd())){
            return ResponseResult.createErrorResult("无效的权限标识");
        }
        privDataRel.setUpdateDate(new Date());
        relSvc.updateById(privDataRel);
        return ResponseResult.createSuccessResult("success");
    }
    @ApiOperation(value = "删除权限与业务对象之间多对多的关系", notes = "删除权限与业务对象之间多对多的关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "privDataRel", value = "privDataRel", required = true, dataType = "PrivDataRel"  ),
    })
    @UooLog(key="deletePrivDataRel",value="删除权限与业务对象之间多对多的关系")
    @SuppressWarnings("unchecked")
    @Transactional
    @RequestMapping(value="deletePrivDataRel",method=RequestMethod.POST)
    public ResponseResult deletePrivDataRel(@RequestBody PrivDataRel privDataRel){
        if(privDataRel.getPrivDataRelId()==null){
            return ResponseResult.createErrorResult("不能删除无效数据");
        }
        Wrapper<DataPrivRule> wrapper = Condition.create().eq("PRIV_DATA_REL_ID", privDataRel.getPrivDataRelId());
        List<DataPrivRule> selectList = ruleSvc.selectList(wrapper);
        if(selectList!=null){
            for (DataPrivRule dpr : selectList) {
                if(StatusCD.VALID.equals(dpr.getStatusCd())){
                    return ResponseResult.createErrorResult("请先删除相关数据权限规则");
                }
            }
            
        }
        PrivDataRel del=new PrivDataRel();
        del.setUpdateDate(new Date());
        del.setStatusCd(StatusCD.INVALID);
        del.setStatusDate(new Date());
        del.setPrivDataRelId(privDataRel.getPrivDataRelId());
        relSvc.updateById(del);
        return ResponseResult.createSuccessResult("success");
    }
}

