package cn.ffcs.uoo.core.user.vo;

import lombok.Data;

@Data
public class PersonnelInfoVo {

    /**
     * 人员标识
     */
    private Long personnelId;
    /**
     * 人员姓名
     */
    private String psnName;
    /**
     * 人员编码
     */
    private String psnCode;
    /**
     * 员工工号
     */
    private String psnNbr;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 证件类型
     */
    private String certType;
    /**
     * 证件号
     */
    private String certNo;

}
