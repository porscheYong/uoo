package cn.ffcs.uoo.core.resource.vo;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-16
 */

import cn.ffcs.uoo.base.common.vo.BaseVo;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/16
 */
public class SystemOrgTreeRuleVo extends BaseVo {

    private Long systemOrgTreeId;
    private Long businessSystemId;
    private Long orgTreeId;
    private String orgTreeName;
    private String systemName;
    private String createDate;
    private String includePsn;
    private String includeSlaveAcct;


    List<SystemIndividuaRuleVo> systemIndividuaRuleList;
    //收索
    private String search;


    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public List<SystemIndividuaRuleVo> getSystemIndividuaRuleList() {
        return systemIndividuaRuleList;
    }

    public void setSystemIndividuaRuleList(List<SystemIndividuaRuleVo> systemIndividuaRuleList) {
        this.systemIndividuaRuleList = systemIndividuaRuleList;
    }

    public Long getSystemOrgTreeId() {
        return systemOrgTreeId;
    }

    public void setSystemOrgTreeId(Long systemOrgTreeId) {
        this.systemOrgTreeId = systemOrgTreeId;
    }

    public Long getBusinessSystemId() {
        return businessSystemId;
    }

    public void setBusinessSystemId(Long businessSystemId) {
        this.businessSystemId = businessSystemId;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public String getOrgTreeName() {
        return orgTreeName;
    }

    public void setOrgTreeName(String orgTreeName) {
        this.orgTreeName = orgTreeName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIncludePsn() {
        return includePsn;
    }

    public void setIncludePsn(String includePsn) {
        this.includePsn = includePsn;
    }

    public String getIncludeSlaveAcct() {
        return includeSlaveAcct;
    }

    public void setIncludeSlaveAcct(String includeSlaveAcct) {
        this.includeSlaveAcct = includeSlaveAcct;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
