package cn.ffcs.uoo.web.maindata.user.vo;


import cn.ffcs.uoo.web.maindata.user.dto.BaseVo;
import lombok.Data;

@Data
public class ListAcctOrgVo extends BaseVo {

    /**
     * 序号
     */
    private Long id;
    /**
     * 主账号组织关系标识
     */
    private Long acctHostId;
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
    private String fullName;
}
