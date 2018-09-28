package cn.ffcs.uoo.leaveDemo.service;

import cn.ffcs.uoo.leaveDemo.constant.ApprovalConstant;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 第一审批环节监听器
 * Created by liuxiaodong on 2018/9/20.
 */
@Service
public class DeptLeaderAssignProcessor extends OaBaseTaskListenerService implements TaskListener {

    private static final long serialVersionUID = -1712360663151180946L;

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("进入第一审批人环节");

        //分配候选人之前先查看是否有任务改派
        List<String> deptLeaderUsers = (List<String>) runtimeService.getVariable(delegateTask.getProcessInstanceId(),delegateTask.getProcessInstanceId() + ApprovalConstant.TASK_DEF_KEYS[2]);
        if(deptLeaderUsers!=null) {
            delegateTask.addCandidateUsers(deptLeaderUsers);
            taskService.setVariable(delegateTask.getId(),ApprovalConstant.TASK_DEF_KEYS[2],deptLeaderUsers);
            return;
        }

        // 本环节任务候选人
        deptLeaderUsers = new ArrayList<>();
        deptLeaderUsers.add("lisi");
        delegateTask.addCandidateUsers(deptLeaderUsers);

        //添加委托人
        deptLeaderUsers = addDelegateUsers(deptLeaderUsers,delegateTask);
        taskService.setVariable(delegateTask.getId(), ApprovalConstant.TASK_DEF_KEYS[2],deptLeaderUsers);
    }
}
