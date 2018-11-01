package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.core.user.entity.TbRoles;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EditFormUserVo {

    private Long personnelId;

    private String userId;
    /**
     * 人员姓名
     */
    private String psnName;
    /**
     * 人员编码
     */
    private String psnCode;
    /**
     * 工号
     */
    private String acctNbr;
    /**
     * 手机号
     */
    private String acctPhone;
    /**
     * 邮箱
     */
    private String acctEmail;
    /**
     * 身份证
     */
    private String acctCert;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 角色
     */
    private List<TbRoles> tbRolesList;

    /**
     * 从账号信息
     */
    private List<ListSlaveAcctVo> slaveAcctVoList;
}
