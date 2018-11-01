package cn.ffcs.uoo.core.personnel.vo;/**
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

/************************组织**************************/
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

/************************员工**************************/
    /**
     * 用户标识
     */
    private Long personId;

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

    /******************* 搜索相关********************/

    /**
     * 关键字检索
     */
    private String search;


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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
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
}
