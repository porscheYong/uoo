package cn.ffcs.uoo.leaveDemo.service;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

/**
 * Created by liuxiaodong on 2018/9/20.
 */
@Service
public class HrAssignProcessor implements TaskListener {

    private static final long serialVersionUID = 6385605398648585601L;

    @Override
    public void notify(DelegateTask delegateTask) {

        System.out.println("进入第二审批人");
        delegateTask.addCandidateUser("wangwu");

    }
}
