package cn.ffcs.uoo.core.user.vo;

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


}
