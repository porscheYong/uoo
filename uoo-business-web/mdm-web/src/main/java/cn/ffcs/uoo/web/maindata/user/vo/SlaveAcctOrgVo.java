package cn.ffcs.uoo.web.maindata.user.vo;

import lombok.Data;

@Data
public class SlaveAcctOrgVo {

    private Long acctOrgRelId;

    private Long orgId;

    private Long acctId;

    private Long slaveAcctId;

    private Long orgTreeId;

    private Long userId;

    private String relType;
}
