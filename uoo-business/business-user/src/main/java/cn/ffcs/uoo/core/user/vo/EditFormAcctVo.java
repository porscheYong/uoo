package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.core.user.entity.TbRoles;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EditFormAcctVo {
    /**
     * 用户类型（主账号）
     */
    private String userType;

    private Long personnelId;
    /**
     * 主账号
     */
    private String acct;

    private Long acctId;

    private String password;

    private String statusCd;

    private Date enableDate;

    private Date disableDate;

    private Long userId;

    /**
     * 角色
     */
    private List<TbRoles> tbRolesList;

    private List<ListAcctOrgVo> acctOrgVoList;
}
