package cn.ffcs.uoo.core.personnel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PersonnelRelationInfoVo {
    /**************人员信息**************/
    /**
     *  人员姓名
     */
    @JsonProperty("psnName")
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
    private String source;
}
