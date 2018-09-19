package cn.ffcs.uoo.core.personnel.vo;

public class PersonnelRelationInfoVo {
    /**************人员信息**************/
    /**
     *  人员姓名
     */
    private String psnName;

    /**
     *  人员编码
     */
    private String psnCode;

    /**
     *  人力编码 集团-NC人力编码
     */
    private String ncCode;

    /**
     *  员工工号
     */
    private String psnNbr;

    /**
     *  国籍
     */
    private String nationality;

    /**
     *  性别
     */
    private String gender;

    /**
     *  民族
     */
    private String nation;

    /**
     *  婚姻状况
     */
    private String marriage;

    /**
     *  政治面貌
     */
    private String pliticalStatus;

    /**
     *  变更原因
     */
    private String reason;

    /******************联系方式**************/
    /**
     *  联系类型
     */
    private String contactType;

    /**
     *  联系内容
     */
    private String content;

    /*****************证件*****************/
    /**
     *  证件姓名
     */
    private String certName;

    /**
     *  证件类型
     */
    private String certType;

    /**
     *  证件号码
     */
    private String certNo;

    /**
     *  证件地址
     */
    private String address;

    /**
     *  颁证机关
     */
    private String issuing;

    /**
     *  是否实名
     */
    private String isReal;

    /**
     *  来源
     */
    private String resource;

    /****************getter and setter**********/
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

    public String getNcCode() {
        return ncCode;
    }

    public void setNcCode(String ncCode) {
        this.ncCode = ncCode;
    }

    public String getPsnNbr() {
        return psnNbr;
    }

    public void setPsnNbr(String psnNbr) {
        this.psnNbr = psnNbr;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getPliticalStatus() {
        return pliticalStatus;
    }

    public void setPliticalStatus(String pliticalStatus) {
        this.pliticalStatus = pliticalStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIssuing() {
        return issuing;
    }

    public void setIssuing(String issuing) {
        this.issuing = issuing;
    }

    public String getIsReal() {
        return isReal;
    }

    public void setIsReal(String isReal) {
        this.isReal = isReal;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
