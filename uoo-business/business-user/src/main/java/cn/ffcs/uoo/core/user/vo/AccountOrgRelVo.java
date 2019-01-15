package cn.ffcs.uoo.core.user.vo;

import lombok.Data;

@Data
public class AccountOrgRelVo {

    private Long acctOrgRelId;

    private Long orgId;

    private Long acctId;

    private Long slaveAcctId;

    private Long orgTreeId;

    private Long userId;

    private String relType;

}
