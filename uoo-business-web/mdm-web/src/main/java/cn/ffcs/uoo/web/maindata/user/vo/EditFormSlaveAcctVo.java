package cn.ffcs.uoo.web.maindata.user.vo;

import cn.ffcs.uoo.web.maindata.user.dto.TbAcctExt;
import cn.ffcs.uoo.web.maindata.user.dto.TbRoles;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EditFormSlaveAcctVo {

    /**
     * 用户类型（从账号）
     */
    private String userType;
    /**
     * 账号类型
     */
    private String slaveAcctType;

    private Long personnelId;

    private Long slaveAcctId;
    /**
     * 主账号组织关系标识
     */
    private Long acctOrgRelId;

    private Long orgId;

    private Long acctId;

    private Long orgTreeId;

    private String slaveAcct;

    private String relType;

    private String password;
    /**
     * 资源标识
     */
    private Long resourceObjId;

    private String statusCd;

    private Date enableDate;

    private Date disableDate;

    private Long userId;

    private List<TbRoles> rolesList;

    private TbAcctExt tbAcctExt;



}
