package cn.ffcs.uoo.web.maindata.common.system.vo;

import cn.ffcs.uoo.web.maindata.common.system.dto.SysMenu;

public class PermMenu extends SysMenu{
    private static final long serialVersionUID = 1L;
    private Long relId;
    public Long getRelId() {
        return relId;
    }
    public void setRelId(Long relId) {
        this.relId = relId;
    }
}