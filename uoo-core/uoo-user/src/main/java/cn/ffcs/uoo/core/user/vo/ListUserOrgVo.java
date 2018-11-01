package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import lombok.Data;

@Data
public class ListUserOrgVo extends BaseVo {
    /**
     * 用户编码
     */
    private String userId;
    /**
     * 人员姓名
     */
    private String psnName;
    /**
     * 归属组织
     */
    private String fullName;
    /**
     * 主账号
     */
    private String acct;
    /**
     * 从账号
     */
    private String slaveAcct;
    /**
     * 手机号
     */
    private String content;
    /**
     * 证件号
     */
    private String certNo;
    /**
     * 用户状态
     */
    private String statusCd;
}
