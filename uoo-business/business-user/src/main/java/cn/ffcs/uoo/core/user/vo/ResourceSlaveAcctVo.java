package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.core.user.entity.ListUser;
import lombok.Data;

import java.util.List;

@Data
public class ResourceSlaveAcctVo {

    private Long slaveAcctId;

    private String slaveAcct;

    private String symmetryPassword;

    private String slaveAcctType;

    private String statusCd;

    private String systemName;

    private List<ListUser> userList;
}
