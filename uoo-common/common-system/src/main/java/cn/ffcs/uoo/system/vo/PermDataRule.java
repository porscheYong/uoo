package cn.ffcs.uoo.system.vo;

import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.entity.SysElement;

public class PermDataRule extends SysDataRule{
    private static final long serialVersionUID = 1L;
    private Long relId;
    public Long getRelId() {
        return relId;
    }
    public void setRelId(Long relId) {
        this.relId = relId;
    }
}