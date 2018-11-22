package cn.ffcs.uoo.core.position.vo;

import java.util.List;

public class PositionNodeVo {
    /**
     * 岗位标识
     */
    private Long positionId;
    /**
     * 岗位编码
     */
    private String positionCode;
    /**
     * 岗位类型
     */
    private String positionType;
    /**
     * 岗位名称
     */
    private String positionName;
    /**
     * 上级岗位标识
     */
    private Long parentPositionId;

    private List<PositionNodeVo> children;

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
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

    public List<PositionNodeVo> getChildren() {
        return children;
    }

    public void setChildren(List<PositionNodeVo> children) {
        this.children = children;
    }
}
