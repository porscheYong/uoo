package cn.ffcs.uoo.core.personnel.vo;

import lombok.Data;

@Data
public class PsnByUserVo {
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
     * 联系电话
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
     * 证件号码
     */
    private String certNo;
}
