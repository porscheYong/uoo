package cn.ffcs.uoo.core.organization.vo;

import cn.ffcs.uoo.core.organization.entity.OrgTreeSynchRule;

public class OrgTreeSynchRuleVO extends OrgTreeSynchRule {
    private String fromOrgTreeName;
    private String toOrgTreeName;

    public String getToOrgTreeName() {
        return toOrgTreeName;
    }

    public void setToOrgTreeName(String toOrgTreeName) {
        this.toOrgTreeName = toOrgTreeName;
    }

    public String getFromOrgTreeName() {
        return fromOrgTreeName;
    }

    public void setFromOrgTreeName(String fromOrgTreeName) {
        this.fromOrgTreeName = fromOrgTreeName;
    }
}
