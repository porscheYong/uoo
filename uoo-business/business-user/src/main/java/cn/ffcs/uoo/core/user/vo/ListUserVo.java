package cn.ffcs.uoo.core.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ListUserVo {

    /**
     * 序号
     */
    private Long id;
    /**
     * 账号标识
     */
    private Long acctId;
    /**
     * 账号类型
     */
    private String type;
    /**
     * 主账号/从账号 名称
     */
    private String name;
    /**
     * 账号
     */
    private String acct;

    private String statusCd;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private String createDate;

    private Long curSlaveOrgTreeId;

    private String curSlaveOrgTreeName;


}
