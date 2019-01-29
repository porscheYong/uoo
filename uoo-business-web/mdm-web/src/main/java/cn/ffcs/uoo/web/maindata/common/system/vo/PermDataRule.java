package cn.ffcs.uoo.web.maindata.common.system.vo;

import cn.ffcs.uoo.web.maindata.common.system.dto.SysDataRule;

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