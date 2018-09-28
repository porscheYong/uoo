package cn.ffcs.uoo.leaveDemo.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 历史任务对象
 * Created by liuxiaodong on 2018/9/25.
 */
public class HistoricTaskVo implements Serializable {

    private static final long serialVersionUID = 6175246883737961306L;

    private String procDefName;

    private Long atiCategoryId;

    private String processInstanceId;

    private String processDefinitionId;

    private Date startTime;

    private Date endTime;

    private Long durationInMillis;

    /**
     * taskDefName
     */
    private String name;

    // 当前审批人
    private List<String> currentCandidates;

    private String assignee;

    public String getProcDefName() {
        return procDefName;
    }

    public void setProcDefName(String procDefName) {
        this.procDefName = procDefName;
    }

    public Long getAtiCategoryId() {
        return atiCategoryId;
    }

    public void setAtiCategoryId(Long atiCategoryId) {
        this.atiCategoryId = atiCategoryId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(Long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCurrentCandidates() {
        return currentCandidates;
    }

    public void setCurrentCandidates(List<String> currentCandidates) {
        this.currentCandidates = currentCandidates;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
