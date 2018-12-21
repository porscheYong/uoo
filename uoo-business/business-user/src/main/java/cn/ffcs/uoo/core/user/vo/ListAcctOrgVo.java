package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import lombok.Data;

@Data
public class ListAcctOrgVo {

    /**
     * 序号
     */
    private Long id;
    /**
     * 主账号组织关系标识
     */
    private Long acctOrgRelId;
    /**
     * 组织ID
     */
    private Long orgId;
    /**
     * 主账号标识
     */
    private Long acctId;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 组织名称
     */
    private String fullName;
    /**
     * 组织树ID
     */
    private Long orgTreeId;
    /**
     * 组织树名称
     */
    private String orgTreeName;
}
