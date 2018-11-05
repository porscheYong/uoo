package cn.ffcs.uoo.oa.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * 流程任务对象
 * Created by liuxiaodong on 2018/9/21.
 */
public class TaskVo implements Serializable {

    private static final long serialVersionUID = 4131601630063483269L;

    private String taskId;

    private String taskName;

    private String taskDefKey;

    private String procInsId;

    private String procDefId;

    private String procDefKey;

    private Map<String, Object> procVars;

    private Map<String, Object> taskVars;

    private String taskCreateDate;

    private String procDefName;

    private String status;

    private Boolean isNewRecord;

    private Boolean toDoTask;

    private Boolean finishTask;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public Map<String, Object> getProcVars() {
        return procVars;
    }

    public void setProcVars(Map<String, Object> procVars) {
        this.procVars = procVars;
    }

    public Map<String, Object> getTaskVars() {
        return taskVars;
    }

    public void setTaskVars(Map<String, Object> taskVars) {
        this.taskVars = taskVars;
    }

    public String getTaskCreateDate() {
        return taskCreateDate;
    }

    public void setTaskCreateDate(String taskCreateDate) {
        this.taskCreateDate = taskCreateDate;
    }

    public String getProcDefName() {
        return procDefName;
    }

    public void setProcDefName(String procDefName) {
        this.procDefName = procDefName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(Boolean newRecord) {
        isNewRecord = newRecord;
    }

    public Boolean getToDoTask() {
        return toDoTask;
    }

    public void setToDoTask(Boolean toDoTask) {
        this.toDoTask = toDoTask;
    }

    public Boolean getFinishTask() {
        return finishTask;
    }

    public void setFinishTask(Boolean finishTask) {
        this.finishTask = finishTask;
    }
}
