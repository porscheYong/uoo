package cn.ffcs.uoo.core.personnel.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import lombok.Data;

import java.util.Date;

@Data
public class TbFamilyVo extends BaseVo {

    /**
     * 家庭信息标识
     */
    private Long familyId;
    /**
     * 人员标识
     */
    private Long personnelId;
    /**
     * 与本人关系
     */
    private String memRelation;
    /**
     * 家庭成员姓名
     */
    private String memName;
    /**
     * 联系邮箱
     */
    private String relaEmail;
    /**
     * 联系电话
     */
    private String relaPhone;
    /**
     * 联系地址
     */
    private String relaAddr;
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
