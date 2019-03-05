package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import lombok.Data;

import java.util.List;

@Data
public class FormSlaveAcctVo {
    /**
     * 用户类型（从账号）
     */
    private String userType;
    /**
     * 账号类型
     */
    private String slaveAcctType;

    private Long personnelId;

    private String psnName;

    private String psnNbr;

    private String psnCode;

    private String mobilePhone;

    private String email;

    private String certType;

    private String certNo;

    private String crossTran;

    private Long acctId;

    private String acct;

    /**
     * 从账号
     */
    private TbSlaveAcct tbSlaveAcct;

    /**
     * 角色
     */
    private List<TbRoles> tbRolesList;

    /**
     * 扩展信息
     */
    private TbAcctExt tbAcctExt;

    /**
     * 归属组织信息
     */
    private List<ListAcctOrgVo> acctOrgVoList;
}
