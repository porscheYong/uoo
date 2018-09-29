package cn.ffcs.uoo.leaveDemo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程流转信息
 * Created by liuxiaodong on 2018/9/20.
 */
public class HistoricFlow implements Serializable {

    private static final long serialVersionUID = -3437202530878851540L;

    /**
     * 序号
     */
    private Integer actNum;

    private String taskDefKey;

    private String assignee;

    private String comment;

    private Date startTime;

    private Date endTime;

    private String duringTime;

    public Integer getActNum() {
        return actNum;
    }

    public void setActNum(Integer actNum) {
        this.actNum = actNum;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getDuringTime() {
        return duringTime;
    }

    public void setDuringTime(String duringTime) {
        this.duringTime = duringTime;
    }
}
