package cn.ffcs.uoo.core.region.vo;

import java.util.Date;

import lombok.Data;
@Data
public class AreaCodeVO {
    private Long areaCodeId;

    /**
     * 公共管理区域标识
     */
    /*@TableField("COMMON_REGION_ID")
    private Long commonRegionId;
*/
    /**
     * 区号编码
     */
    private String areaNbr;

    /**
     * 区号
     */
    private String areaCode;

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
    private String regionNames;
}
