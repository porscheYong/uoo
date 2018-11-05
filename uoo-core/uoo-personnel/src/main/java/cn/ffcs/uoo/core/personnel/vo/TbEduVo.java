package cn.ffcs.uoo.core.personnel.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import lombok.Data;

import java.util.Date;

@Data
public class TbEduVo extends BaseVo {

    /**
     * 教育经历标识
     */
    private Long eduId;
    /**
     * 人员标识
     */
    private Long personnelId;
    /**
     * 学校
     */
    private String school;
    /**
     * 学校类别
     */
    private String schoolType;
    /**
     * 专业
     */
    private String major;
    /**
     * 学历专业类别
     */
    private String majortype;
    /**
     * 学历
     */
    private String education;
    /**
     * 学制
     */
    private Integer edusystem;
    /**
     * 学位
     */
    private String degree;
    /**
     * 是否第一学历
     */
    private String firstEducation;
    /**
     * 是否最高学历
     */
    private String lastEducation;
    /**
     * 是否最高学位
     */
    private String lastDegree;
    /**
     * 入学日期
     */
    private Date begindate;
    /**
     * 毕业日期
     */
    private Date enddate;
    /**
     * 学位证书编号
     */
    private String certifcode;
    /**
     * 是否全日制最高学历
     */
    private String isFullTimeHighEdu;
    /**
     * 是否在职最高学历
     */
    private String isInServiceHighEdu;
    /**
     * 状态
     */
    private String statusCd;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    private Date statusDate;
}
