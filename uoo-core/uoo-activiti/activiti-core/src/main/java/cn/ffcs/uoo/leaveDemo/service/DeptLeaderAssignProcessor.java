package cn.ffcs.uoo.leaveDemo.service;

import cn.ffcs.uoo.leaveDemo.constant.ApprovalConstant;
import cn.ffcs.uoo.ueccUser.dao.AtiUserMapper;
import cn.ffcs.uoo.ueccUser.entity.AtiUser;
import cn.ffcs.uoo.ueccUser.service.AtiUserService;
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
    @Resource
    private AtiUserService atiUserService;

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("进入第一审批人环节");

        // 本环节任务候选人
        String  applyUserName = (String) delegateTask.getVariable("applyUserId");
        List<String> originalCandidates = new ArrayList<>();
        List<AtiUser> atiUsers = atiUserService.getDeptLeaderUsersByNo(applyUserName);
        for (AtiUser atiUser: atiUsers) {
            originalCandidates.add(atiUser.getNo());
        }

        taskService.setVariable(delegateTask.getId(), ApprovalConstant.TASK_DEF_KEYS[2], originalCandidates);

        //分配候选人之前先查看是否有任务改派
        List<String> deptLeaderUsers = (List<String>) runtimeService.getVariable(delegateTask.getProcessInstanceId(),delegateTask.getProcessInstanceId() + ApprovalConstant.TASK_DEF_KEYS[2]);
        if(deptLeaderUsers!=null) {
            delegateTask.addCandidateUsers(deptLeaderUsers);
            taskService.setVariable(delegateTask.getId(),delegateTask.getId() + ApprovalConstant.TASK_DEF_KEYS[2],deptLeaderUsers);
            return;
        }

        // 添加本环节任务候选人
        delegateTask.addCandidateUsers(originalCandidates);

        //添加委托人
        deptLeaderUsers = addDelegateUsers(originalCandidates,delegateTask);
        taskService.setVariable(delegateTask.getId(), delegateTask.getId() + ApprovalConstant.TASK_DEF_KEYS[2],deptLeaderUsers);
    }
}
