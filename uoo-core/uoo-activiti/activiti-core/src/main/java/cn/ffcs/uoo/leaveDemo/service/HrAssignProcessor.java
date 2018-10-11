package cn.ffcs.uoo.leaveDemo.service;

import cn.ffcs.uoo.leaveDemo.constant.ApprovalConstant;
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
 * 第二审批人监听器
 * Created by liuxiaodong on 2018/9/20.
 */
@Service
public class HrAssignProcessor extends OaBaseTaskListenerService implements TaskListener {

    private static final long serialVersionUID = 6385605398648585601L;

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private AtiUserService atiUserService;

    @Override
    public void notify(DelegateTask delegateTask) {

        System.out.println("进入第二审批人");

        // 本环节任务候选人
        List<String> originalCandidates = new ArrayList<>();
        String applyUserName = (String) delegateTask.getVariable("applyUserId");
        List<AtiUser> atiUsers = atiUserService.getHrUsersByNo(applyUserName);
        for (AtiUser atiUser : atiUsers) {
            originalCandidates.add(atiUser.getNo());
        }
        taskService.setVariable(delegateTask.getId(), ApprovalConstant.TASK_DEF_KEYS[3], originalCandidates);

        //分配候选人之前先查看是否有任务改派
        List<String> deptLeaderUsers = (List<String>) runtimeService.getVariable(delegateTask.getProcessInstanceId(),delegateTask.getProcessInstanceId() + ApprovalConstant.TASK_DEF_KEYS[3]);
        if(deptLeaderUsers!=null) {
            delegateTask.addCandidateUsers(deptLeaderUsers);
            taskService.setVariable(delegateTask.getId(),delegateTask.getId() + ApprovalConstant.TASK_DEF_KEYS[3],deptLeaderUsers);
            return;
        }

        // 本环节任务候选人
        delegateTask.addCandidateUsers(originalCandidates);

        //添加委托人
        deptLeaderUsers = addDelegateUsers(originalCandidates,delegateTask);
        taskService.setVariable(delegateTask.getId(), delegateTask.getId() + ApprovalConstant.TASK_DEF_KEYS[3],deptLeaderUsers);

    }
}
