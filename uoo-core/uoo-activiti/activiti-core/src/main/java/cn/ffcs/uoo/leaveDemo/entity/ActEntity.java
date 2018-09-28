package cn.ffcs.uoo.leaveDemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by liuxiaodong on 2018/9/20.
 */
public abstract class ActEntity<T> implements Serializable {

    private static final long serialVersionUID = -4149781130260205163L;

    protected Act act; 		// 流程任务对象

    public ActEntity() {
        super();
    }

    @JsonIgnore
    public Act getAct() {
        if (act == null){
            act = new Act();
        }
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    /**
     * 获取流程实例ID
     * @return
     */
    public String getProcInsId() {
        return this.getAct().getProcInsId();
    }

    /**
     * 设置流程实例ID
     * @param procInsId
     */
    public void setProcInsId(String procInsId) {
        this.getAct().setProcInsId(procInsId);
    }
}
