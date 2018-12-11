package cn.ffcs.uoo.core.personnel.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import lombok.Data;

import java.util.Date;

@Data
public class TbPsnjobVo extends BaseVo {

    /**
     * 任职履历标识
     */
    private Long psnjobId;
    /**
     * 人员标识
     */
    private Long personnelId;
    /**
     * 组织标识 单位类
     */
    private Long orgId;
    /**
     * 任职开始时间
     */
    private Date beginTime;
    /**
     * 任职结束时间
     */
    private Date endTime;
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
