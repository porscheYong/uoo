package cn.ffcs.uoo.leaveDemo.constant;

/**
 * 流程常量
 * Created by liuxiaodong on 2018/9/20.
 */
public class ApprovalConstant {

    /**
     * 定义一个通用流程的taskDefKey数组
     */
    public static final String[] TASK_DEF_KEYS = {"startEvent", "staffAudit", "deptLeaderAudit", "hrAudit", "modify", "endEvent"};
    /**
     * 用户类型任务
     */
    public static final String USER_ACTIVITY_TYPE = "userTask";
    /**
     * 开始事件
     */
    public static final String START_ACTIVITY_TYPE = "startEvent";
    /**
     * 结束事件
     */
    public static final String END_ACTIVITY_TYPE = "endEvent";
}
