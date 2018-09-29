package cn.ffcs.uoo.leaveDemo.service;


import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.leaveDemo.constant.ApprovalConstant;
import cn.ffcs.uoo.leaveDemo.vo.HistoricFlow;
import cn.ffcs.uoo.leaveDemo.vo.HistoricTaskVo;
import cn.ffcs.uoo.leaveDemo.vo.TaskVo;
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

    /**
     * 启动请假流程
     * @param startProcessVars 启动流程需要的变量
     */
    public void startLeaveProcess(Map<String, Object> startProcessVars) {
        //  引擎自动把createUser保存到 activiti:initiator中
        identityService.setAuthenticatedUserId((String) startProcessVars.get("applyUser"));

//        leaveService.addLeave(leave);
//        leaveService.insert(leave);
        // 添加相关变量
        Map<String, Object> vars = new HashMap<>();
        vars.put("startProcessVars", startProcessVars);
        // 启动流程
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey((String) startProcessVars.get("procDefKey"), "oa_leave", vars);

        Task currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        claim(currentTask.getId(), (String) startProcessVars.get("applyUser"));
        completeLeaveProcess(currentTask.getId(), processInstance.getId(), null, null);


    }

    /**
     * 任务待办
     *
     * @param assignName 签收人
     * @return
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
     * @param taskId 任务标识
     * @param procInsId 流程实例标识
     * @param comment 处理意见
     * @param vars 处理意见标识变量
     */
    public void completeLeaveProcess(String taskId, String procInsId, String comment, Map<String, Object> vars) {

        // 添加意见
        if (StringUtils.isNotEmpty(procInsId) && StringUtils.isNotEmpty(comment)) {
            taskService.addComment(taskId, procInsId, comment);
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
     * @param assignName
     * @param categoryId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<HistoricTaskVo> getHistToricTaskList(String assignName, String categoryId, String startTime, String endTime) {
        List<HistoricTaskVo> historicTasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(assignName).finished().includeProcessVariables()
                .orderByHistoricTaskInstanceEndTime().desc();

        try {
            if (null == categoryId || categoryId.isEmpty()) {
                if ((null == startTime || startTime.isEmpty()) && (null == endTime || endTime.isEmpty())) {
                    List<HistoricTaskInstance> histList = histTaskQuery.list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }
                if ((null == startTime || startTime.isEmpty()) && (null != endTime && !endTime.isEmpty())) {
                    List<HistoricTaskInstance> histList = histTaskQuery.taskCompletedBefore(sdf.parse(endTime)).list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        if (taskInstance.getEndTime().getTime() <= sdf.parse(endTime).getTime()) {
                            HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                            historicTasks.add(historicTask);
                        }
                    }
                }
                if ((null != startTime && !startTime.isEmpty()) && (null == endTime || endTime.isEmpty())) {
                    List<HistoricTaskInstance> histList = histTaskQuery.taskCompletedAfter(sdf.parse(startTime)).list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        if (taskInstance.getStartTime().getTime() >= sdf.parse(startTime).getTime()) {
                            HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                            historicTasks.add(historicTask);
                        }
                    }
                }
                if ((null != startTime && !startTime.isEmpty()) && (null != endTime && !endTime.isEmpty())) {
                    List<HistoricTaskInstance> histList = histTaskQuery.taskCompletedAfter(sdf.parse(startTime))
                            .taskCompletedBefore(sdf.parse(endTime)).list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }
            }
            if (null != categoryId && !categoryId.isEmpty()) {
                if ((null == startTime || startTime.isEmpty()) && (null == endTime || endTime.isEmpty())) {
                    List<HistoricTaskInstance> histList = histTaskQuery.processVariableValueEquals("categoryId", Long.valueOf(categoryId)).list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }
                if ((null == startTime || startTime.isEmpty()) && (null != endTime && !endTime.isEmpty())) {
                    List<HistoricTaskInstance> histList = histTaskQuery.processVariableValueEquals("categoryId", Long.valueOf(categoryId))
                            .taskCompletedBefore(sdf.parse(endTime)).list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }
                if ((null != startTime && !startTime.isEmpty()) && (null == endTime || endTime.isEmpty())) {
                    List<HistoricTaskInstance> histList = histTaskQuery.processVariableValueEquals("categoryId", Long.valueOf(categoryId))
                            .taskCompletedAfter(sdf.parse(startTime)).list();
                    if (null == histList || histList.size() == 0) {
                        return null;
                    }
                    for (HistoricTaskInstance taskInstance : histList) {
                        HistoricTaskVo historicTask = setHistoricTask(taskInstance);
                        historicTasks.add(historicTask);
                    }
                }
                if ((null != startTime && !startTime.isEmpty()) && (null != endTime && !endTime.isEmpty())) {
                    List<HistoricTaskInstance> histList = histTaskQuery.processVariableValueEquals("categoryId", Long.valueOf(categoryId))
                            .taskCompletedAfter(sdf.parse(startTime)).taskCompletedBefore(sdf.parse(endTime)).list();
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
     * @param procInsId
     * @return
     */
    public List<HistoricFlow> histoicFlowList(String procInsId) {
        List<HistoricFlow> actList = Lists.newArrayList();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
                .orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();

        boolean start = false;
        Map<String, Integer> actMap = Maps.newHashMap();

        for (int i = 0; i < list.size(); i++) {

            HistoricActivityInstance histIns = list.get(i);

            // 过滤开始节点前的节点
            if (ApprovalConstant.TASK_DEF_KEYS[0].equals(histIns.getActivityId())) {
                start = true;
            }
            if (!start) {
                continue;
            }

            //只显示开始节点，结束节点，用户任务节点
            if (ApprovalConstant.USER_ACTIVITY_TYPE.equals(histIns.getActivityType())
                    || ApprovalConstant.START_ACTIVITY_TYPE.equals(histIns.getActivityType())
                    || ApprovalConstant.END_ACTIVITY_TYPE.equals(histIns.getActivityType())) {

                // 给节点增加一个序号
                Integer actNum = actMap.get(histIns.getId());
                if (actNum == null) {
                    actMap.put(histIns.getId(), actMap.size() + 1);
                }

                HistoricFlow historicFlow = new HistoricFlow();
                historicFlow.setActNum(actMap.get(histIns.getId()));
                historicFlow.setStartTime(histIns.getStartTime());
                historicFlow.setEndTime(histIns.getEndTime());
                historicFlow.setDuringTime(String.valueOf(histIns.getDurationInMillis()));
                historicFlow.setTaskDefKey(histIns.getActivityName());
                historicFlow.setAssignee(histIns.getAssignee());

//                // 获取意见评论内容
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
        historicTask.setAtiCategoryId(Long.valueOf(taskInstance.getCategory()));

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().
                processDefinitionId(taskInstance.getProcessDefinitionId()).list();
        historicTask.setProcDefName(list.get(0).getName());

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
     * @param procInstId 流程实例标识
     * @return
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
            map.put("names",names);
            return map;
        }
        // 其他用户环节，获取在监听器中设置的用户变量
        List<String> currentUsers = (List<String>) taskService
                .getVariable(currentTask.getId(), currentTask.getTaskDefinitionKey());
        map.put("names", currentUsers);

        return map;
    }

}
