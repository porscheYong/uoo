package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.core.user.entity.ListUser;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import lombok.Data;

@Data
public class ApplySlaveAcctVo {

    private Long slaveAcctId;

    private String slaveAcct;

    private String symmetryPassword;

    private String slaveAcctType;

    private String statusCd;

    private String systemName;

    private TbAcctExt tbAcctExt;

    private ListUser user;

    private TbAcct tbAcct;




}
