package cn.ffcs.uoo.core.resource.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.resource.entity.TbBusinessSystem;
import cn.ffcs.uoo.core.resource.service.ModifyHistoryService;
import cn.ffcs.uoo.core.resource.service.TbBusinessSystemService;
import cn.ffcs.uoo.core.resource.util.StrUtil;
import cn.ffcs.uoo.core.resource.vo.Org;
import cn.ffcs.uoo.core.vo.ResponseResult;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 是否为每张业务表设计历史流水记录表？？ 前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-24
 */

@Api(value = "modifyHistory", description = "日志")
@RestController
@RequestMapping("/modifyHistory")
public class ModifyHistoryController {

    @Autowired
    private ModifyHistoryService modifyHistoryService;

    @ApiOperation(value = "新增变化日志表", notes = "新增变化日志表")
    @UooLog(value = "新增变化日志表", key = "addModifyHistory")
    @RequestMapping(value = "/addModifyHistory", method = RequestMethod.POST)
    public ResponseResult<String> addModifyHistory(Object oldObj,Object newObj) {
        ResponseResult<String> ret = new ResponseResult<>();
        if(oldObj==null && newObj==null){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("新值和旧值不能同时为空");
            return ret;
        }
        String retstr = modifyHistoryService.addModifyHistory(oldObj,newObj);
        if(StrUtil.isNullOrEmpty(retstr)){
            ret.setState(ResponseResult.STATE_OK);
            ret.setMessage("成功");
            return ret;
        }
        ret.setState(ResponseResult.STATE_ERROR);
        return ret;
    }

    @ApiOperation(value = "新增变化日志表", notes = "新增变化日志表")
    @UooLog(value = "新增变化日志表", key = "addModifyHistory11")
    @RequestMapping(value = "/addModifyHistory11", method = RequestMethod.POST)
    public ResponseResult<String> addModifyHistory11(Object oldObj) {
        ResponseResult<String> ret = new ResponseResult<>();
        if(oldObj==null){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("新值和旧值不能同时为空");
            return ret;
        }
        ret.setState(ResponseResult.STATE_ERROR);
        return ret;
    }
}

