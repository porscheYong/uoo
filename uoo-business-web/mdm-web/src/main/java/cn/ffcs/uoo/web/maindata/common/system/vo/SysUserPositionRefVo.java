package cn.ffcs.uoo.web.maindata.common.system.vo;

import lombok.Data;

@Data
public class SysUserPositionRefVo {

    /**
     * 系统用户职位关系标识
     */
    private Long userPositionRefId;
    /**
     * 人员编码
     */
    private String userCode;
    /**
     * 职位标识
     */
    private Long positionId;
    /**
     * 职位编码
     */
    private String positionCode;
    /**
     * 职位名称
     */
    private String positionName;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 状态
     */
    private String statusCd;

    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 备注
     */
    private String notes;
}
