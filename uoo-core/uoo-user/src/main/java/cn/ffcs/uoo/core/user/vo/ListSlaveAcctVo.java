package cn.ffcs.uoo.core.user.vo;

import lombok.Data;

@Data
public class ListSlaveAcctVo {

    /**
     * 从账号标识
     */
    private Long slaveAcctId;
    /**
     * 账号名
     */
    private String slaveAcct;
    /**
     * 账号类型
     */
    private String slaveAcctType;
    /**
     * 组织树
     */
    private String orgTreeName;
    /**
     * 归属组织Id
     */
    private String hostObjId;
    /**
     * 归属组织
     */
    private String hostObjName;

    private String statusCd;
    /**
     * 关联用户
     */
    private String userId;
    /**
     * 关联主账号
     */
    private String acct;


}
