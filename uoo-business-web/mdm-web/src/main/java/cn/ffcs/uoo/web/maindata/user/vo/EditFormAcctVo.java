package cn.ffcs.uoo.web.maindata.user.vo;

import cn.ffcs.uoo.web.maindata.user.dto.TbRoles;
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

    /**
     * 角色
     */
    private List<TbRoles> tbRolesList;

    private List<ListAcctOrgVo> acctOrgVoList;
}
