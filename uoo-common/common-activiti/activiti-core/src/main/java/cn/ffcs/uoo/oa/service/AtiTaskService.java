package cn.ffcs.uoo.oa.service;


import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.oa.constant.ApprovalConstant;
import cn.ffcs.uoo.oa.entity.AtiActCategory;
import cn.ffcs.uoo.oa.entity.AtiBaseForm;
import cn.ffcs.uoo.oa.entity.AtiSpecificForm;
import cn.ffcs.uoo.oa.utils.ResponseResult;
import cn.ffcs.uoo.oa.vo.HistoricFlow;
import cn.ffcs.uoo.oa.vo.HistoricTaskVo;
import cn.ffcs.uoo.oa.vo.TaskVo;
import cn.ffcs.uoo.oa.vo.UeccVo;
import cn.ffcs.uoo.ueccUser.entity.AtiUser;
import cn.ffcs.uoo.ueccUser.service.AtiUserService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 流程任务相关Service
 * Created by liuxiaodong on 2018/9/20.
 */
@Service
public class AtiTaskService {

    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private IdentityService identityService;
    @Resource
    private HistoryService historyService;
    @Resource
    private RepositoryService repositoryService;

    @Resource
    private UeccService ueccService;
    @Resource
    private AtiBaseFormService atiBaseFormService;
    @Resource
    private AtiSpecificFormService atiSpecificFormService;
    @Resource
    private AtiUserService atiUserService;
    @Resource
    private AtiActCategoryService atiActCategoryService;

    /**
     * 启动请假流程
     *
     * @param startProcessVars 启动流程需要的变量
     */
    @Transactional
    public String startLeaveProcess(Map<String, Object> startProcessVars) {
        //  引擎自动把createUser保存到 activiti:initiator中
        identityService.setAuthenticatedUserId((String) startProcessVars.get("formSender"));

        // 添加相关变量
        Map<String, Object> vars = new HashMap<>();
        vars.put("formSender", startProcessVars.get("formSender"));
        vars.put("categoryId", startProcessVars.get("categoryId"));
        vars.put("procDefKey", startProcessVars.get("procDefKey"));
        vars.put("vars", startProcessVars.get("vars"));

        // *持久化
        UeccVo ueccVo = ueccService.getOneUeccVo(startProcessVars);
        atiBaseFormService.addAtiBaseForm(ueccVo);
        List<AtiSpecificForm> specificForms = ueccService.getSpecificForms(ueccVo);
        for (AtiSpecificForm atiSpecificForm : specificForms) {
            atiSpecificFormService.insert(atiSpecificForm);
        }

        // 启动流程
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey((String) startProcessVars.get("procDefKey"), "ati_base_form", vars);

        // 更新流程实例id
        atiBaseFormService.updateProcInstIdByBaseFormId(processInstance.getId(), ueccVo.getAtiBaseFormId());

        Task currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        claim(currentTask.getId(), (String) startProcessVars.get("formSender"));
        completeLeaveProcess(currentTask.getId(), processInstance.getId(), null, null);

        return processInstance.getId();

    }

    /**
     * 任务待办
     *
     * @param assignName 签收人
     * @return List<TaskVo> 任务集合
     */
    public List<TaskVo> actList(String assignName) {

        List<TaskVo> taskVoList = new ArrayList<>();
        TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(assignName)
                .includeProcessVariables().active().orderByTaskCreateTime().desc();
        List<Task> toClaimList = toClaimQuery.list();
        if (null != toClaimList && toClaimList.size() > 0) {
            for (Task task : toClaimList) {
                TaskVo taskVo = setTaskVo(task);
                taskVo.setStatus("toClaim");
                taskVoList.add(taskVo);
            }
        }

        TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(assignName).active()
                .includeProcessVariables().orderByTaskCreateTime().desc();
        List<Task> todoList = todoTaskQuery.list();
        if (null != todoList && todoList.size() > 0) {
            for (Task task : todoList) {
                TaskVo taskVo = setTaskVo(task);
                taskVo.setStatus("todo");
                taskVoList.add(taskVo);
            }
        }

        return taskVoList;
    }

    /**
     * 签收任务
     *
     * @param taskId 任务ID
     * @param user   签收用户
     */
    public void claim(String taskId, String user) {
        taskService.claim(taskId, user);
    }

    /**
     * 处理任务
     *
     * @param taskId    任务标识
     * @param procInsId 流程实例标识
     * @param comment   处理意见
     * @param vars      处理意见标识变量
     */
    public void completeLeaveProcess(String taskId, String procInsId, String comment, Map<String, Object> vars) {

        // 添加意见
        if (StringUtils.isNotEmpty(procInsId) && StringUtils.isNotEmpty(comment)) {
            taskService.addComment(taskId, procInsId, comment);
        }

        AtiBaseForm atiBaseForm = atiBaseFormService.getBaseFormByProcInstId(procInsId);
        Task currentTask = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        switch (currentTask.getTaskDefinitionKey()) {
            case "deptLeaderAudit":
                AtiSpecificForm atiSpecificForm = atiSpecificFormService
                        .selectOne(new EntityWrapper<AtiSpecificForm>().eq("ati_base_form_id", atiBaseForm.getAtiBaseFormId())
                                .eq("parameter", "SECOND_TEXT"));
                atiSpecificForm.setParamValue(comment);
                atiSpecificFormService.updateById(atiSpecificForm);
                break;
            case "hrAudit":
                atiSpecificForm = atiSpecificFormService
                        .selectOne(new EntityWrapper<AtiSpecificForm>().eq("ati_base_form_id", atiBaseForm.getAtiBaseFormId())
                                .eq("parameter", "THIRD_TEXT"));
                atiSpecificForm.setParamValue(comment);
                atiSpecificFormService.updateById(atiSpecificForm);
                break;
        }

        // 设置流程变量
        if (vars == null) {
            vars = Maps.newHashMap();
        }
        // 设置流程定义名称
        ProcessInstance procInst = runtimeService.createProcessInstanceQuery().
                processInstanceId(procInsId).active().singleResult();
        vars.put("procDefName", procInst.getProcessDefinitionName());
        // 提交任务
        taskService.complete(taskId, vars);
    }

    /**
     * 已办任务
     *
     * @param assignName 任务办理人
     * @param categoryId 流程分类标识
     * @param startTime  任务接收时间
     * @param endTime    任务处理时间
     * @return List<HistoricTaskVo> 历史任务集合
     */
    public List<HistoricTaskVo> getHistToricTaskList(String assignName, String categoryId, String isOnApproval, String startTime, String endTime) {
        List<HistoricTaskVo> historicTasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(assignName).includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();

        if (StringUtils.isEmpty(startTime)) {
            startTime = "1000-01-01";
        }
        if (StringUtils.isEmpty(endTime)) {
            endTime = "9999-12-31";
        }
        try {
            if (StringUtils.isEmpty(categoryId)) {
                if (StringUtils.isEmpty(isOnApproval)) {
                    List<HistoricTaskInstance> histList = histTaskQuery.taskCompletedAfter(sdf.parse(startTime)).
                            taskCompletedBefore(sdf.parse(endTime)).list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }
                if ("0".equals(isOnApproval)) {
                    List<HistoricTaskInstance> histList = histTaskQuery.taskCompletedAfter(sdf.parse(startTime)).
                            taskCompletedBefore(sdf.parse(endTime)).processUnfinished().list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }
                if ("1".equals(isOnApproval)) {
                    List<HistoricTaskInstance> histList = histTaskQuery.taskCompletedAfter(sdf.parse(startTime)).
                            taskCompletedBefore(sdf.parse(endTime)).processFinished().list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }
            }
            if (StringUtils.isNotEmpty(categoryId)) {
                if (StringUtils.isEmpty(isOnApproval)) {
                    List<HistoricTaskInstance> histList = histTaskQuery
                            .processVariableValueEquals("categoryId", categoryId)
                            .taskCompletedAfter(sdf.parse(startTime)).taskCompletedBefore(sdf.parse(endTime)).list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }
                if ("0".equals(isOnApproval)) {
                    List<HistoricTaskInstance> histList = histTaskQuery
                            .processVariableValueEquals("categoryId", categoryId)
                            .taskCompletedAfter(sdf.parse(startTime)).taskCompletedBefore(sdf.parse(endTime)).processUnfinished().list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }
                if ("1".equals(isOnApproval)) {
                    List<HistoricTaskInstance> histList = histTaskQuery
                            .processVariableValueEquals("categoryId", categoryId)
                            .taskCompletedAfter(sdf.parse(startTime)).taskCompletedBefore(sdf.parse(endTime)).processFinished().list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return historicTasks;
    }

    /**
     * 流程流转信息
     *
     * @param procInsId 流程实例标识
     * @return List<HistoricFlow> 流程流转对象集合
     */
    public List<HistoricFlow> histoicFlowList(String procInsId) {
        List<HistoricFlow> actList = Lists.newArrayList();
        // 获取历史待办并排序
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
                .orderByHistoricActivityInstanceStartTime().desc().orderByHistoricActivityInstanceEndTime().desc().list();

        Map<String, Integer> actMap = Maps.newHashMap();

        for (HistoricActivityInstance histIns : list) {

            //只显示开始节点，结束节点，用户任务节点
            if (ApprovalConstant.USER_ACTIVITY_TYPE.equals(histIns.getActivityType())
                    || ApprovalConstant.START_ACTIVITY_TYPE.equals(histIns.getActivityType())
                    || ApprovalConstant.END_ACTIVITY_TYPE.equals(histIns.getActivityType())) {

                // 给节点增加一个序号
                actMap.computeIfAbsent(histIns.getId(), V -> actMap.size() + 1);

                HistoricFlow historicFlow = new HistoricFlow();
                historicFlow.setActNum(actMap.get(histIns.getId()));
                historicFlow.setStartTime(histIns.getStartTime());
                historicFlow.setEndTime(histIns.getEndTime());
                historicFlow.setDuringTime(String.valueOf(histIns.getDurationInMillis()));
                historicFlow.setTaskDefKey(histIns.getActivityName());
                historicFlow.setAssignee(histIns.getAssignee());

                // 获取意见评论内容
                if (StringUtils.isNotBlank(histIns.getTaskId())) {
                    List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
                    if (commentList.size() > 0) {
                        historicFlow.setComment(commentList.get(0).getFullMessage());
                    }
                }

                actList.add(historicFlow);
            }

        }
        return actList;
    }

    private HistoricTaskVo setHistoricTask(HistoricTaskInstance taskInstance) {
        HistoricTaskVo historicTask = new HistoricTaskVo();
        historicTask.setProcessDefinitionId(taskInstance.getProcessDefinitionId());
        historicTask.setProcessInstanceId(taskInstance.getProcessInstanceId());
        historicTask.setAssignee(taskInstance.getAssignee());
        historicTask.setDurationInMillis(taskInstance.getDurationInMillis());
        historicTask.setStartTime(taskInstance.getStartTime());
        historicTask.setEndTime(taskInstance.getEndTime());
        historicTask.setName(taskInstance.getName());
        historicTask.setTaskVars(taskInstance.getTaskLocalVariables());
        historicTask.setProcVars(taskInstance.getProcessVariables());

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().
                processDefinitionId(taskInstance.getProcessDefinitionId()).list();
        if (null != list && list.size() > 0) {
            historicTask.setProcDefName(list.get(0).getName());
        }

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(taskInstance.getProcessInstanceId()).singleResult();
        if (null != processInstance) {
            Map<String, Object> map = getCurrentTaskAssignNames(processInstance.getId());
            List<String> names = (List<String>) map.get("names");
            historicTask.setCurrentCandidates(names);
        }

        return historicTask;
    }

    private TaskVo setTaskVo(Task task) {
        TaskVo taskVo = new TaskVo();
        taskVo.setTaskId(task.getId());
        taskVo.setTaskName(task.getName());
        taskVo.setProcDefKey(task.getProcessDefinitionId().split(":")[0]);
        taskVo.setTaskDefKey(task.getTaskDefinitionKey());
        taskVo.setProcDefId(task.getProcessDefinitionId());
        taskVo.setProcInsId(task.getProcessInstanceId());
        taskVo.setProcVars(task.getProcessVariables());
        taskVo.setTaskVars(task.getTaskLocalVariables());
        taskVo.setTaskCreateDate(DateUtils.formatDateTime(task.getCreateTime()));
        return taskVo;
    }

    /**
     * 获取当前流程任务和签收人
     *
     * @param procInstId 流程实例标识
     * @return 当前任务和当前任务的候选人
     */
    public Map<String, Object> getCurrentTaskAssignNames(String procInstId) {

        try {
            ProcessInstance procInst = runtimeService.createProcessInstanceQuery().
                    processInstanceId(procInstId).active().singleResult();
            if (null == procInst) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();

        Task currentTask = taskService.createTaskQuery().processInstanceId(procInstId).active().singleResult();
        TaskVo taskVo = setTaskVo(currentTask);
        map.put("task", taskVo);

        List<String> names = Lists.newArrayList();

        // 返回重新修改环节的处理人，即流程发起人
        if (ApprovalConstant.TASK_DEF_KEYS[4].equals(currentTask.getTaskDefinitionKey())) {
            String applyUser = (String) runtimeService.getVariable(procInstId, "applyUserId");
            names.add(applyUser);
            map.put("names", names);
            return map;
        }
        // 其他用户环节，获取在监听器中设置的用户变量
        List<String> currentUsers = (List<String>) taskService
                .getVariable(currentTask.getId(), currentTask.getId() + currentTask.getTaskDefinitionKey());
        map.put("names", currentUsers);

        return map;
    }

    /**
     * 启动请假流程校验参数
     *
     * @param startProcessJson 启动流程需要的变量，包括formSender,categoryId,procDefKey,vars
     */
    public ResponseResult<String> checkStartLeaveProcess(String startProcessJson) {

        ResponseResult<String> result = new ResponseResult<>();

        Map<String, Object> startProcessVars;
            try {
                startProcessVars = JSON.parseObject(startProcessJson);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("参数无效！");
            return result;
        }

        String formSender = (String) startProcessVars.get("formSender");
        if (StringUtils.isEmpty(formSender)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("创建人不允许为空！");
            return result;
        }
        AtiUser user = atiUserService.selectOne(new EntityWrapper<AtiUser>().eq("no", formSender));
        if (user == null || "n18004840170".equals(formSender) || "n13514713646".equals(formSender)) {
            result.setMessage("当前用户不允许发起流程！");
            result.setState(ResponseResult.PARAMETER_ERROR);
            return result;
        }
        if (StringUtils.isEmpty((String) startProcessVars.get("categoryId"))) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("流程分类不允许为空！");
            return result;
        }
        try {
            AtiActCategory atiActCategory = atiActCategoryService.
                    selectById(Long.valueOf((String) startProcessVars.get("categoryId")));
            if (null == atiActCategory) {
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
        return result;
    }

    /**
     * 签收校验参数
     *
     * @param taskId     待办任务标识
     * @param assignName 处理人
     */
    public ResponseResult<Void> checkClaim(String taskId, String assignName) {
        ResponseResult<Void> result = new ResponseResult<>();
        if (StringUtils.isEmpty(taskId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务标识不允许为空！");
            return result;
        }
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
        if (StringUtils.isEmpty(assignName)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("签收人不允许为空！");
            return result;
        }
        Map<String, Object> map = getCurrentTaskAssignNames(currentTask.getProcessInstanceId());
        boolean isAssignee = false;
        if (null != map) {
            List<String> names = (List<String>) map.get("names");
            if (null != names && names.size() > 0) {
                for (String name : names) {
                    if (assignName.equals(name)) {
                        isAssignee = true;
                        break;
                    }
                }
            }
        }
        if (!isAssignee) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage(assignName + "不允许签收当前任务！");
            return result;
        }
        return result;
    }

    /**
     * 处理流程添加校验
     *
     * @param taskId     待办任务
     * @param procInstId 流程实例标识
     * @param flag       是否同意，1：同意，0：不同意
     */
    public ResponseResult<Void> checkCompleteLeaveProcess(String taskId, String procInstId, String flag) {
        ResponseResult<Void> result = new ResponseResult<>();
        if (StringUtils.isEmpty(taskId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("任务标识不允许为空！");
            return result;
        }
        ProcessInstance processInstance;
        try {
            processInstance = runtimeService.createProcessInstanceQuery().
                    processInstanceId(procInstId).active().singleResult();
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
        if (!"0".equals(flag) && !"1".equals(flag)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("参数无效！");
            return result;
        }
        Task currentTask = taskService.createTaskQuery().processInstanceId(procInstId).active().singleResult();
        if (!currentTask.getId().equals(taskId)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("参数无效！");
            return result;
        }
        return result;
    }

    /**
     * 获取历史待办校验参数
     *
     * @param assignName 任务处理人
     * @param categoryId 流程分类标识
     * @param startTime  接收任务时间
     * @param endTime    处理任务时间
     */
    public ResponseResult<List<HistoricTaskVo>> checkHistoricTasks(String assignName, String categoryId, String isOnApproval, String startTime, String endTime) {
        ResponseResult<List<HistoricTaskVo>> result = new ResponseResult<>();
        if (StringUtils.isNotEmpty(categoryId)) {
            AtiActCategory atiCategory;
            try {
                atiCategory = atiActCategoryService.selectById(Long.valueOf(categoryId));
            } catch (Exception e) {
                e.printStackTrace();
                result.setState(ResponseResult.PARAMETER_ERROR);
                result.setMessage("参数无效！");
                return result;
            }
            if (null == atiCategory) {
                result.setState(ResponseResult.PARAMETER_ERROR);
                result.setMessage("流程分类不存在！");
                return result;
            }
        }
        if (StringUtils.isNotEmpty(isOnApproval) && !"0".equals(isOnApproval) && !"1".equals(isOnApproval)) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("参数无效！");
            return result;
        }
        Date startDate;
        try {
            startDate = DateUtils.parseDate(startTime);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("开始时间格式不正确！");
            return result;
        }
        Date endDate;
        try {
            endDate = DateUtils.parseDate(endTime);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("结束时间格式不正确！");
            return result;
        }
        if (null != startDate && null != endDate && startDate.getTime() > endDate.getTime()) {
            result.setState(ResponseResult.PARAMETER_ERROR);
            result.setMessage("开始时间大于结束时间！");
            return result;
        }
        return result;
    }

}
