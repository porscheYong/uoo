package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import lombok.Data;

/**
 * 从账号 对应 主账号组织关系
 */
@Data
public class ListSlaveAcctOrgVo extends BaseVo {

    /**
     * 序号
     */
    private Long id;
    /**
     * 从账号标识
     */
    private Long slaveAcctId;
    /**
     * 主账号
     */
    private Long acctId;
    /**
     * 从账号
     */
    private String slaveAcct;
    /**
     * 账号类型
     */
    private String slaveAcctType;
    /**
     * 组织树ID
     */
    private Long orgTreeId;
    /**
     * 组织树
     */
    private String orgTreeName;
    /**
     * 组织标识
     */
    private Long orgId;
    /**
     *应用系统
     */
    private String systemName;
    /**
     * 归属组织
     */
    private String fullName;
    /**
     * 状态
     */
    private String statusCd;
    /**
     * 组织人员关系类型
     */
    private String relType;



}
