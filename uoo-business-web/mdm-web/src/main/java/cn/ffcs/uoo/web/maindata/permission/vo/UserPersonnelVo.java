package cn.ffcs.uoo.web.maindata.permission.vo;

/**
 *  用户人员信息
 *  @author zhanglu
 *  @date 20181025
 */
public class UserPersonnelVo  {
    /**
     * 序号
     */
    private Long num;

    /**
     * 用户编码
     */
    private String psnCode;

    /**
     * 人员姓名
     */
    private String psnName;

    /**
     * 归属组织
     */
    private String orgName;

    /**
     * 联系电话
     */
    private String content;

    /**
     * 证件号码
     */
    private String certNo;

    /**
     * 主账号
     */
    private String acct;

    /**
     * 从账号
     */
    private String slaveAcct;

    /**
     * 用户状态
     */
    private String statusCd;
    /**
     * 分页的序号
     */
    private int pageNo;

    /**
     * 每页的大小
     */
    private int pageSize;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getPsnCode() {
        return psnCode;
    }

    public void setPsnCode(String psnCode) {
        this.psnCode = psnCode;
    }

    public String getPsnName() {
        return psnName;
    }

    public void setPsnName(String psnName) {
        this.psnName = psnName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public String getSlaveAcct() {
        return slaveAcct;
    }

    public void setSlaveAcct(String slaveAcct) {
        this.slaveAcct = slaveAcct;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }
}
