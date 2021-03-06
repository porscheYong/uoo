package cn.ffcs.uoo.core.organization.vo;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-10-24
 */

import cn.ffcs.uoo.base.common.vo.BaseVo;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/24
 */
public class PsonOrgVo extends BaseVo {

    /**
     * 主键id
     */
    private Long orgPersonId;

    private Long orgContactRelId;

/************************组织**************************/
    /**
     * 组织树根节点
     */
    private Long orgTreeId;

    private String refCode;

    /**
     * 组织根节点
     */
    private Long orgRootId;
    /**
     * 组织标识
     */
    private Long orgId;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 组织全程
     */
    private String fullName;
    /**
     * 用工性质
     */
    private String property;

    /**
     * 排序
     */
    private String sort;

    /**
     * 职位标识
     */
    private String postId;

    /**
     * 职位名称
     */
    private String postName;

    /**
     * 组织人员关系类型
     */
    private String refType;

    /**
     * 重名称呼
     */
    private String doubleName;

    /**
     * 状态
     */
    private String statusCd;

    /**
     * 组织树名称
     */
    private String orgTreeName;

/************************员工**************************/
    /**
     * 用户标识
     */
    private Long personnelId;

    /**
     * 人员姓名
     */
    private String psnName;

    /**
     * 员工编码
     */
    private String psnCode;


    /**
     * 员工工号
     */
    private String psnNbr;

    /**
     * 人力编码
     */
    private String ncCode;

    /**
     * 联系方式
     */
    private String mobile;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 身份证
     */
    private String certNo;

    /**
     * 员工id
     */
    private String userId;

    /**
     * 账号或者
     */
    private String acct;
    /**
     * 从账号
     */
    private String slaveAcct;

    public String getSlaveAcct() {
        return slaveAcct;
    }

    public void setSlaveAcct(String slaveAcct) {
        this.slaveAcct = slaveAcct;
    }

    private Long slaveAcctId;

    public Long getSlaveAcctId() {
        return slaveAcctId;
    }

    public void setSlaveAcctId(Long slaveAcctId) {
        this.slaveAcctId = slaveAcctId;
    }

    /********************人员相关********************/
    /**
     * 组织账号或者从账号标识
     */
    private String accId;
    /**
     * 账号类型 1 主账号 2 从账号
     */
    private String type;
    /**
     * 账号类型 主账号 从账号
     */
    private String typeName;

    /******************* 搜索相关********************/

    /**
     * 关键字检索
     */
    private String search;

    private String isSearchlower;

    private String isSearchNum;

    private String isSearchLikeCert;

    private String isSearchLikeMobole;

    //排序字段
    private String sortField;
    //排序方式
    private String sortOrder;

    /***********************权限************************/

    private String tabOrgParams;

    private String tabOrgTreeParams;

    private String tabOrgPerRelParams;

    private String tabOrgOrgTypeParams;

    private String tabAccountOrgRelParams;

    private String tabOrgOrgTreeRelParams;

    /** 账号权限**/
    private String accout;
    private Long sysUserId;


    public String getTabOrgOrgTreeRelParams() {
        return tabOrgOrgTreeRelParams;
    }

    public void setTabOrgOrgTreeRelParams(String tabOrgOrgTreeRelParams) {
        this.tabOrgOrgTreeRelParams = tabOrgOrgTreeRelParams;
    }

    public String getIsSearchLikeCert() {
        return isSearchLikeCert;
    }

    public void setIsSearchLikeCert(String isSearchLikeCert) {
        this.isSearchLikeCert = isSearchLikeCert;
    }

    public String getIsSearchLikeMobole() {
        return isSearchLikeMobole;
    }

    public void setIsSearchLikeMobole(String isSearchLikeMobole) {
        this.isSearchLikeMobole = isSearchLikeMobole;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getTabAccountOrgRelParams() {
        return tabAccountOrgRelParams;
    }

    public void setTabAccountOrgRelParams(String tabAccountOrgRelParams) {
        this.tabAccountOrgRelParams = tabAccountOrgRelParams;
    }

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getTabOrgOrgTypeParams() {
        return tabOrgOrgTypeParams;
    }

    public void setTabOrgOrgTypeParams(String tabOrgOrgTypeParams) {
        this.tabOrgOrgTypeParams = tabOrgOrgTypeParams;
    }

    public String getTabOrgPerRelParams() {
        return tabOrgPerRelParams;
    }

    public void setTabOrgPerRelParams(String tabOrgPerRelParams) {
        this.tabOrgPerRelParams = tabOrgPerRelParams;
    }

    public String getTabOrgParams() {
        return tabOrgParams;
    }

    public void setTabOrgParams(String tabOrgParams) {
        this.tabOrgParams = tabOrgParams;
    }

    public String getTabOrgTreeParams() {
        return tabOrgTreeParams;
    }

    public void setTabOrgTreeParams(String tabOrgTreeParams) {
        this.tabOrgTreeParams = tabOrgTreeParams;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getIsSearchlower() {
        return isSearchlower;
    }

    public void setIsSearchlower(String isSearchlower) {
        this.isSearchlower = isSearchlower;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public Long getOrgContactRelId() {
        return orgContactRelId;
    }

    public void setOrgContactRelId(Long orgContactRelId) {
        this.orgContactRelId = orgContactRelId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrgTreeName() {
        return orgTreeName;
    }

    public void setOrgTreeName(String orgTreeName) {
        this.orgTreeName = orgTreeName;
    }

    public Long getOrgPersonId() {
        return orgPersonId;
    }

    public void setOrgPersonId(Long orgPersonId) {
        this.orgPersonId = orgPersonId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Long getOrgRootId() {
        return orgRootId;
    }

    public void setOrgRootId(Long orgRootId) {
        this.orgRootId = orgRootId;
    }

    public String getNcCode() {
        return ncCode;
    }

    public void setNcCode(String ncCode) {
        this.ncCode = ncCode;
    }

    public String getPsnName() {
        return psnName;
    }

    public void setPsnName(String psnName) {
        this.psnName = psnName;
    }

    public String getPsnCode() {
        return psnCode;
    }

    public void setPsnCode(String psnCode) {
        this.psnCode = psnCode;
    }

    public String getPsnNbr() {
        return psnNbr;
    }

    public void setPsnNbr(String psnNbr) {
        this.psnNbr = psnNbr;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public String getDoubleName() {
        return doubleName;
    }

    public void setDoubleName(String doubleName) {
        this.doubleName = doubleName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getIsSearchNum() {
        return isSearchNum;
    }

    public void setIsSearchNum(String isSearchNum) {
        this.isSearchNum = isSearchNum;
    }
}
