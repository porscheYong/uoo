package cn.ffcs.uoo.system.vo;

import lombok.Data;

@Data
public class SysUserDeptVo {
    private Long deptPositionRefId;
    /**
     * 人员编码
     */
    private String userCode;
    /**
     * 组织编码
     */
    private String orgCode;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 修改人
     */
    private Long updateUser;
}
