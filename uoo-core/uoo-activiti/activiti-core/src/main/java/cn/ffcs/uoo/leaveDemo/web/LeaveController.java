package cn.ffcs.uoo.leaveDemo.web;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.leaveDemo.constant.ApprovalConstant;
import cn.ffcs.uoo.leaveDemo.entity.AtiCategory;
import cn.ffcs.uoo.leaveDemo.entity.AtiDelegateInfo;
import cn.ffcs.uoo.leaveDemo.service.AtiCategoryService;
import cn.ffcs.uoo.leaveDemo.service.AtiDelegateInfoService;
import cn.ffcs.uoo.leaveDemo.service.AtiProcessService;
import cn.ffcs.uoo.leaveDemo.service.AtiTaskService;
import cn.ffcs.uoo.leaveDemo.utils.ResponseResult;
import cn.ffcs.uoo.leaveDemo.vo.HistoricFlow;
import cn.ffcs.uoo.leaveDemo.vo.HistoricTaskVo;
import cn.ffcs.uoo.leaveDemo.vo.ProcessDefinitionVo;
import cn.ffcs.uoo.leaveDemo.vo.TaskVo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请假流程前端控制器
 * Created by liuxiaodong on 2018/9/20.
 */
@RestController
@RequestMapping("/oa")
public class LeaveController extends BaseController {

    @Resource
    private AtiProcessService atiProcessService;
    @Resource
    private AtiTaskService atiTaskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private TaskService taskService;
    @Resource
    private AtiCategoryService atiCategoryService;
    @Resource
    private AtiDelegateInfoService atiDelegateInfoService;

    /**
     * 流程部署
     *
     * @param category 流程分类标识
     * @param file 部署文件
     * @return
     */
    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public ResponseResult<Void> deploy(String category, MultipartFile file) {
        ResponseResult<Void> result = new ResponseResult<>();

        if (StringUtils.isEmpty(category)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程分类不允许为空！");
            return result;
        }
        AtiCategory atiCategory = atiCategoryService.selectById(category);
        if (null == atiCategory) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程分类不存在！");
            return result;
        }
        if (null == file) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("请选择要部署的流程文件！");
            return result;
        }
        String fileName = file.getOriginalFilename();
        String message;
        if (StringUtils.isEmpty(fileName)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("请选择要部署的流程文件！");
            return result;
        } else {
            try {
                message = atiProcessService.deploy(category, file);
            } catch (Exception e) {
                result.setState(ResponseResult.STATE_ERROR);
                result.setMessage("部署失败");
                return result;
            }
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage(message);
        return result;
    }

    /**
     * 流程定义列表
     *
     * @param categoryId 流程分类标识
     * @return
     */
    @RequestMapping(value = "/processList", method = RequestMethod.GET)
    public ResponseResult<List<ProcessDefinitionVo>> getProcessList(String categoryId) {
        ResponseResult<List<ProcessDefinitionVo>> result = new ResponseResult<>();
        if (StringUtils.isNotEmpty(categoryId)) {
            try {
                Long atiCategoryId = Long.valueOf(categoryId);
                AtiCategory atiCategory = atiCategoryService.selectById(atiCategoryId);
                if (null == atiCategory) {
                    result.setState(ResponseResult.PARAMETER_ERROR);
                    result.setMessage("参数无效，流程分类标识不存在！");
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.setState(ResponseResult.PARAMETER_ERROR);
                result.setMessage("参数无效！");
                return result;
            }
        }

        List<ProcessDefinitionVo> processDefinitionVoList = null;
        try {
            processDefinitionVoList = atiProcessService.processList(categoryId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_OK);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setData(processDefinitionVoList);
        result.setMessage("已获取流程定义列表！");
        return result;
    }

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    @RequestMapping(value = "/deleteDeployment", method = RequestMethod.POST)
    public ResponseResult<Void> deleteDeployment(String deploymentId) {
        ResponseResult<Void> result = new ResponseResult<>();
        if (StringUtils.isEmpty(deploymentId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程部署ID不允许为空！");
            return result;
        }
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (null == deployment) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程部署ID不存在！");
            return result;
        }
        try {
            atiProcessService.deleteDeployment(deploymentId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("已成功删除流程定义！");
        return result;
    }

    /**
     * 删除部署的流程实例
     *
     * @param procInsId 流程实例ID
     * @param deleteReason 删除原因
     */
    @RequestMapping(value = "/deleteProcIns", method = RequestMethod.POST)
    public ResponseResult<Void> deleteProcIns(String procInsId, String deleteReason) {
        ResponseResult<Void> result = new ResponseResult<>();
        if (StringUtils.isEmpty(procInsId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例不允许为空！");
            return result;
        }
        if (StringUtils.isEmpty(deleteReason)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("删除原因不允许为空！");
            return result;
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(procInsId).active().singleResult();
        if (null == processInstance) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例不存在！");
            return result;
        }
        try {
            atiProcessService.deleteProcIns(procInsId, deleteReason);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("已成功删除流程实例！");
        return result;
    }


    /**
     * 启动请假流程
     * @param startProcessVars 启动流程需要的变量，包括applyUser,categoryId,procDefKey,vars
     * @return
     */
    @RequestMapping(value = "/startProcess", method = RequestMethod.POST)
    public ResponseResult<Void> startLeaveProcess(@RequestBody Map<String, Object> startProcessVars) {

        ResponseResult<Void> result = new ResponseResult<>();

        if (StringUtils.isEmpty((String) startProcessVars.get("applyUser"))) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("创建人不允许为空！");
            return result;
        }
        if (StringUtils.isEmpty((String) startProcessVars.get("categoryId"))) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程分类不允许为空！");
            return result;
        }
        try {
            AtiCategory atiCategory = atiCategoryService.
                    selectById(Long.valueOf((String) startProcessVars.get("categoryId")));
            if (null == atiCategory) {
                result.setState(ResponseResult.PARAMETER_ERROR);
                result.setMessage("流程分类不存在！");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("参数无效！");
            return result;
        }
        if (StringUtils.isEmpty((String) startProcessVars.get("procDefKey"))) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程定义不允许为空！");
            return result;
        }
        try {
            atiTaskService.startLeaveProcess(startProcessVars);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("请假流程已启动！");
        return result;

    }

    /**
     * 待办任务
     *
     * @param assignName 签收人
     * @return
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
     * 处理 请假流程
     *
     * @param taskId 待办任务标识
     * @param procInsId 流程实例标识
     * @param comment 任务处理意见
     * @param user 处理人
     * @param flag 是否同意，1：同意，0：不同意
     * @return
     */
    @RequestMapping(value = "/completeProcess", method = RequestMethod.POST)
    public ResponseResult<Void> completeLeaveProcess(String taskId, String user, String procInsId, String comment, String flag) {
        ResponseResult<Void> result = new ResponseResult<>();
        Task currentTask;
        try {
            currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务标识参数无效！");
            return result;
        }
        if (null == currentTask) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务不存在或已处理完毕！");
            return result;
        }
        if (StringUtils.isEmpty(user)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("签收人不允许为空！");
            return result;
        }
        Map<String, Object> map = atiTaskService.getCurrentTaskAssignNames(procInsId);
        List<String> names = (List<String>) map.get("names");
        boolean isAssignee = false;
        if (null != names && names.size() > 0) {
            for (String name : names) {
                if (user.equals(name)) {
                    isAssignee = true;
                    break;
                }
            }
        }
        if (!isAssignee) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage(user + "不允许签收当前任务！");
            return result;
        }
        ProcessInstance processInstance;
        try {
            processInstance = runtimeService.createProcessInstanceQuery().
                    processInstanceId(procInsId).active().singleResult();
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例标识参数无效！");
            return result;
        }
        if (null == processInstance) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例不存在！");
            return result;
        }
        Task task = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
        if (null != task && !task.getId().equals(currentTask.getId())) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("参数无效！");
            return result;
        }
        if (!"0".equals(flag) || !"1".equals(flag)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("参数无效！");
            return result;
        }
        // 任务签收
        try {
            atiTaskService.claim(taskId, user);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }
        // 任务处理
        Map<String, Object> vars = new HashMap<>();
        vars.put("pass", flag);
        try {
            atiTaskService.completeLeaveProcess(taskId, procInsId, comment, vars);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("success");
        return result;
    }

    /**
     * 流程流转信息接口
     *
     * @param procInstId
     * @return
     */
    @RequestMapping(value = "/historicFlows", method = RequestMethod.GET)
    public ResponseResult<List<HistoricFlow>> historicFlows(String procInstId) {
        ResponseResult<List<HistoricFlow>> result = new ResponseResult<>();
        if (StringUtils.isEmpty(procInstId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例ID不允许为空！");
            return result;
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().
                processInstanceId(procInstId).singleResult();
        if (null == processInstance) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例不存在！");
            return result;
        }
        List<HistoricFlow> historicFlowList = null;
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
     * @param assignName
     * @param categoryId
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/taskHistoric", method = RequestMethod.GET)
    public ResponseResult<List<HistoricTaskVo>> getHistoricTasks(String assignName, String categoryId, String startTime, String endTime) {
        ResponseResult<List<HistoricTaskVo>> result = new ResponseResult<>();
        Date startDate;
        try {
            startDate = DateUtils.parseDate(startTime);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("开始时间格式不正确！");
            return result;
        }
        Date endDate = null;
        try {
            endDate = DateUtils.parseDate(endTime);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("结束时间格式不正确！");
            return result;
        }
        if (startDate.getTime() > endDate.getTime()) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("开始时间大于结束时间！");
            return result;
        }
        List<HistoricTaskVo> historicFlowList = null;
        try {
            historicFlowList = atiTaskService.getHistToricTaskList(assignName, categoryId, startTime, endTime);
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

    /**
     * 流程实例任务指派
     *
     * @param procInstId 流程实例ID
     * @param taskDefKey 指定流程中的环节，用任务定义KEY表示
     * @param users      指定用户代理
     * @return
     */
    @RequestMapping(value = "/alterTaskAssignee", method = RequestMethod.POST)
    public ResponseResult<Void> alterTaskAssignee(String procInstId, String taskDefKey, @RequestBody List<String> users) {
        ResponseResult<Void> result = new ResponseResult<>();

        if (StringUtils.isEmpty(procInstId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例ID不允许为空！");
            return result;
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).active().singleResult();
        if (null == processInstance) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例不存在！");
            return result;
        }
        if (StringUtils.isEmpty(taskDefKey)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务定义值不允许为空！");
            return result;
        }
        if (!ApprovalConstant.TASK_DEF_KEYS[2].equals(taskDefKey) || !ApprovalConstant.TASK_DEF_KEYS[3].equals(taskDefKey)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务定义值不正确！");
            return result;
        }
        if (null == users || users.size() == 0) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("没有可指派的用户代理！");
            return result;
        }

        try {
            atiProcessService.alterAssignee(procInstId, taskDefKey, users);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("success");
        return result;
    }

    /**
     * 取消任务指派
     * @param procInstId
     * @param variable
     * @return
     */
    @RequestMapping(value = "/removeTaskAlternative", method = RequestMethod.POST)
    public ResponseResult<Void> removeTaskAlternative(String procInstId, String variable) {
        ResponseResult<Void> result = new ResponseResult<>();
        if (StringUtils.isEmpty(procInstId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例ID不允许为空！");
            return result;
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).active().singleResult();
        if (null == processInstance) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程实例不存在！");
            return result;
        }
        if (StringUtils.isEmpty(variable)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("变量名不允许为空！");
            return result;
        }
        Object var = runtimeService.getVariable(procInstId, variable);
        if (null == var) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("无此变量值！");
            return result;
        }
        try {
            atiProcessService.removeTaskAlternative(procInstId, variable);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("执行成功！");
        return result;

    }

    /**
     * 添加任务委托
     * @param atiDelegateInfo
     * @return
     */
    @RequestMapping(value = "/addDelegate", method = RequestMethod.POST)
    public ResponseResult<Void> addDelegate(@RequestBody AtiDelegateInfo atiDelegateInfo) {
        ResponseResult<Void> result = new ResponseResult<>();

        String assignee = atiDelegateInfo.getAssignee();
        if (StringUtils.isEmpty(assignee)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("原办理人不允许为空！");
            return result;
        }
        String attorney = atiDelegateInfo.getAttorney();
        if (StringUtils.isEmpty(attorney)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托人不允许为空！");
            return result;
        }
        String startTime = atiDelegateInfo.getStartTime();
        if (StringUtils.isEmpty(startTime)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托开始时间不允许为空！");
            return result;
        }
        String endTime = atiDelegateInfo.getEndTime();
        if (StringUtils.isEmpty(endTime)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托截止时间不允许为空！");
            return result;
        }
        Date startDate;
        try {
            startDate = DateUtils.parseDate(startTime);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托开始时间格式不正确！");
            return result;
        }
        Date endDate;
        try {
            endDate = DateUtils.parseDate(endTime);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托截止时间格式不正确！");
            return result;
        }
        if (startDate.getTime() > endDate.getTime()) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托开始时间大于截止时间！");
            return result;
        }
        Long categoryId = atiDelegateInfo.getAtiCategoryId();
        AtiCategory category = atiCategoryService.selectById(categoryId);
        if (null == category) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程分类不存在！");
            return result;
        }
        String delegateReason = atiDelegateInfo.getDelegateReason();
        if (StringUtils.isEmpty(delegateReason)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("委托原因不允许为空！");
            return result;
        }

        try {
            atiDelegateInfoService.insert(atiDelegateInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("执行成功！");
        return result;
    }

    /**
     * 修改任务委托
     * @param atiDelegateInfo
     * @return
     */
    @RequestMapping(value = "/modifyDelegate", method = RequestMethod.POST)
    public ResponseResult<Void> modifyDelegate(@RequestBody AtiDelegateInfo atiDelegateInfo) {
        ResponseResult<Void> result = new ResponseResult<>();

        Long delegateInfoId = atiDelegateInfo.getAtiDelegateInfoId();
        AtiDelegateInfo delegateInfo = atiDelegateInfoService.selectById(delegateInfoId);
        if (null == delegateInfo) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务委托信息标识不正确！");
            return result;
        }
        if (StringUtils.isNotEmpty(atiDelegateInfo.getStartTime()) && StringUtils.isEmpty(atiDelegateInfo.getEndTime())) {
            if (DateUtils.parseDate(atiDelegateInfo.getStartTime()).getTime() > DateUtils.parseDate(delegateInfo.getEndTime()).getTime()) {
                result.setState(ResponseResult.PARAMETER_ERROR);
                result.setMessage("开始时间不能大于截止时间！");
                return result;
            }
        }
        if (StringUtils.isEmpty(atiDelegateInfo.getStartTime()) && StringUtils.isNotEmpty(atiDelegateInfo.getEndTime())) {
            if (DateUtils.parseDate(atiDelegateInfo.getEndTime()).getTime() < DateUtils.parseDate(delegateInfo.getStartTime()).getTime()) {
                result.setState(ResponseResult.PARAMETER_ERROR);
                result.setMessage("截止时间不能小于开始开始时间！");
                return result;
            }
        }
        if (StringUtils.isNotEmpty(atiDelegateInfo.getStartTime()) && StringUtils.isNotEmpty(atiDelegateInfo.getEndTime())) {
            if (DateUtils.parseDate(atiDelegateInfo.getEndTime()).getTime() < DateUtils.parseDate(atiDelegateInfo.getStartTime()).getTime()) {
                result.setState(ResponseResult.PARAMETER_ERROR);
                result.setMessage("截止时间不能小于开始开始时间！");
                return result;
            }
        }

        try {
            atiDelegateInfoService.modifyOneDelegateInfo(atiDelegateInfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("执行成功！");
        return result;
    }


    /**
     * 删除任务委托
     * @param delegateInfoId
     * @return
     */
    @RequestMapping(value = "/removeDelegate", method = RequestMethod.POST)
    public ResponseResult<Void> removeDelegate(String delegateInfoId) {
        ResponseResult<Void> result = new ResponseResult<>();
        if (StringUtils.isEmpty(delegateInfoId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务委托标识不允许为空！");
            return result;
        }
        AtiDelegateInfo delegateInfo = atiDelegateInfoService.selectById(delegateInfoId);
        if (null == delegateInfo) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务委托不存在！");
            return result;
        }
        try {
            atiDelegateInfoService.removeOneDelegateInfo(delegateInfoId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("执行失败！");
            return result;
        }

        result.setState(ResponseResult.STATE_OK);
        result.setMessage("执行成功！");
        return result;
    }

}
