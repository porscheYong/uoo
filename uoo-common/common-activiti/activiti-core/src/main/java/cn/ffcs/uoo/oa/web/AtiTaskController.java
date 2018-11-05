package cn.ffcs.uoo.oa.web;


import cn.ffcs.uoo.oa.service.AtiTaskService;
import cn.ffcs.uoo.oa.utils.ResponseResult;
import cn.ffcs.uoo.oa.vo.HistoricFlow;
import cn.ffcs.uoo.oa.vo.HistoricTaskVo;
import cn.ffcs.uoo.oa.vo.TaskVo;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务相关前端控制器
 * Created by liuxiaodong on 2018/10/12.
 */
@RestController
@RequestMapping("/oa")
public class AtiTaskController extends BaseController {

    @Resource
    private AtiTaskService atiTaskService;


    /**
     * 启动请假流程
     *
     * @param startProcessJson 启动流程需要的变量，包括formSender,categoryId,procDefKey,vars
     * @return 执行后的状态和消息
     */
    @RequestMapping(value = "/startProcess", method = RequestMethod.POST)
    public ResponseResult<String> startLeaveProcess(String startProcessJson) {
        //添加校验
        ResponseResult<String> result = atiTaskService.checkStartLeaveProcess(startProcessJson);
        if (StringUtils.isNotEmpty(result.getMessage())) {
            return result;
        }
        Map<String, Object> startProcessVars = JSON.parseObject(startProcessJson);
        String procInstId;
        try {
            procInstId = atiTaskService.startLeaveProcess(startProcessVars);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setData(procInstId);
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("请假流程已启动！");
        return result;

    }

    /**
     * 待办任务
     *
     * @param assignName 签收人
     * @return 任务集合和状态与消息
     */
    @RequestMapping(value = "/actTaskTodo", method = RequestMethod.GET)
    public ResponseResult<List<TaskVo>> testActTodo(String assignName) {
        ResponseResult<List<TaskVo>> result = new ResponseResult<>();
        if (StringUtils.isEmpty(assignName)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("签收人不允许为空！");
            return result;
        }
        List<TaskVo> acts;
        try {
            acts = atiTaskService.actList(assignName);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败");
            return result;
        }

        result.setData(acts);
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("获取待办列表成功！");
        return result;

    }

    /**
     * 签收
     *
     * @param taskId     待办任务标识
     * @param assignName 处理人
     * @return 执行后的状态和消息
     */
    @RequestMapping(value = "/claim", method = RequestMethod.POST)
    public ResponseResult<Void> claim(String taskId, String assignName) {
        // 添加校验
        ResponseResult<Void> result = atiTaskService.checkClaim(taskId, assignName);
        if (StringUtils.isNotEmpty(result.getMessage())) {
            return result;
        }
        // 任务签收
        try {
            atiTaskService.claim(taskId, assignName);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("任务已签收！");
        return result;
    }

    /**
     * 处理 请假流程
     *
     * @param taskId     待办任务
     * @param procInstId 流程实例标识
     * @param comment    任务处理意见
     * @param flag       是否同意，1：同意，0：不同意
     * @return 处理后的状态和信息
     */
    @RequestMapping(value = "/completeProcess", method = RequestMethod.POST)
    public ResponseResult<Void> completeLeaveProcess(String taskId, String procInstId, String comment, String flag) {
        // 添加校验
        ResponseResult<Void> result = atiTaskService.checkCompleteLeaveProcess(taskId, procInstId, flag);
        if (StringUtils.isNotEmpty(result.getMessage())) {
            return result;
        }
        // 任务处理
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", flag);
        try {
            atiTaskService.completeLeaveProcess(taskId, procInstId, comment, vars);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("任务已处理完毕！");
        return result;
    }

    /**
     * 流程流转信息接口
     *
     * @param procInstId 流程实例标识
     * @return 流程流转集合和状态与消息
     */
    @RequestMapping(value = "/historicFlows", method = RequestMethod.GET)
    public ResponseResult<List<HistoricFlow>> historicFlows(String procInstId) {
        ResponseResult<List<HistoricFlow>> result = new ResponseResult<>();
        if (StringUtils.isEmpty(procInstId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例ID不允许为空！");
            return result;
        }

        List<HistoricFlow> historicFlowList;
        try {
            historicFlowList = atiTaskService.histoicFlowList(procInstId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("已获取流程流转信息！");
        result.setData(historicFlowList);
        return result;
    }

    /**
     * 历史待办
     *
     * @param assignName 任务处理人
     * @param categoryId 流程分类标识
     * @param startTime  接收任务时间
     * @param endTime    处理任务时间
     * @return 历史任务集合和状态与集合
     */
    @RequestMapping(value = "/taskHistoric", method = RequestMethod.GET)
    public ResponseResult<List<HistoricTaskVo>> getHistoricTasks(String assignName, String categoryId, String isOnApproval, String startTime, String endTime) {
        // 添加校验
         ResponseResult<List<HistoricTaskVo>> result = atiTaskService.checkHistoricTasks(assignName, categoryId, isOnApproval, startTime, endTime);
         if (StringUtils.isNotEmpty(result.getMessage())) {
             return result;
         }
        List<HistoricTaskVo> historicFlowList;
        try {
            historicFlowList = atiTaskService.getHistToricTaskList(assignName, categoryId, isOnApproval, startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("success");
        result.setData(historicFlowList);
        return result;
    }

}
