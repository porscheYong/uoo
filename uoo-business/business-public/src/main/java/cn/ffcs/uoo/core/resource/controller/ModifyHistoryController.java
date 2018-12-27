package cn.ffcs.uoo.core.resource.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.resource.entity.ModifyHistory;
import cn.ffcs.uoo.core.resource.service.ModifyHistoryService;
import cn.ffcs.uoo.core.resource.util.StrUtil;
import cn.ffcs.uoo.core.resource.vo.ModifyHistoryVo;
import cn.ffcs.uoo.core.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @UooLog(value = "新增变化日志表", key = "addModifyHistoryVo")
    @RequestMapping(value = "/addModifyHistoryVo", method = RequestMethod.POST)
    public ResponseResult<String> addModifyHistoryVo(ModifyHistoryVo modifyHistoryVo) {
        ResponseResult<String> ret = new ResponseResult<>();
        ModifyHistory modifyHistory = new ModifyHistory();
        Long mdyId = modifyHistoryService.getId();
        String batNum = modifyHistoryService.getBatchNumber();
        modifyHistory.setModifyId(mdyId);
        modifyHistory.setBatchNumber(batNum);
        Long tabId = modifyHistoryService.getCommonTableId(modifyHistoryVo.getTabName());
        modifyHistory.setTabId(tabId);
        modifyHistory.setRecordId(modifyHistoryVo.getRecordId());
        modifyHistory.setOperateType(modifyHistoryVo.getOperateType());
        modifyHistory.setBatchNumber(batNum);
        modifyHistory.setNote(modifyHistoryVo.getNote());
        modifyHistory.setFieldName(modifyHistoryVo.getFieldName());
        modifyHistory.setFieldValue(modifyHistoryVo.getFieldValue());
        modifyHistoryService.add(modifyHistory);
        ret.setState(ResponseResult.STATE_ERROR);
        return ret;
    }

}
