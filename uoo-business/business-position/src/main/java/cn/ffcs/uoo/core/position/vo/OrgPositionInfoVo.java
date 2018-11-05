package cn.ffcs.uoo.core.position.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;

/**
 *  组织岗位值信息
 *  @author zhanglu
 *  @since 2018-11-01
 */
public class OrgPositionInfoVo extends BaseVo {
    /**
     * 组织岗位关系标识
     */
    private Long orgPositionId;
    /**
     * 组织标识
     */
    private Long orgId;
    /**
     * 岗位标识
     */
    private Long positionId;
    /**
     * 组织树标识
     */
    private Long orgTreeId;
    /**
     * 岗位编码
     */
    private String positionCode;
    /**
     * 岗位类型
     */
    private String positionType;
    /**
     * 岗位描述
     */
    private String positionDesc;
    /**
     * 岗位名称
     */
    private String positionName;
    /**
     * 上级岗位标识
     */
    private Long parentPositionId;

    public Long getOrgPositionId() {
        return orgPositionId;
    }

    public void setOrgPositionId(Long orgPositionId) {
        this.orgPositionId = orgPositionId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getOrgTreeId() {
        return orgTreeId;
    }

    public void setOrgTreeId(Long orgTreeId) {
        this.orgTreeId = orgTreeId;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Long getParentPositionId() {
        return parentPositionId;
    }

    public void setParentPositionId(Long parentPositionId) {
        this.parentPositionId = parentPositionId;
    }
}
