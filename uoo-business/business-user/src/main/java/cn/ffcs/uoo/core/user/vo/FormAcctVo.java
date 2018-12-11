package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import cn.ffcs.uoo.core.user.entity.TbRoles;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

import java.util.List;

/**
 *  主账号表单
 */

@Data
public class FormAcctVo {

    /**
     * 用户类型（主账号）
     */
    private String userType;

    private Long personnelId;

    private String psnName;

    private String psnNbr;

    private String psnCode;

    private String mobilePhone;

    private String eamil;

    private String certType;

    private String certNo;

    /**
     *  主账号
     */
    private TbAcct tbAcct;
    /**
     * 角色
     */
    private List<TbRoles> tbRolesList;
    /**
     * 归属组织信息
     */
    private Page<ListAcctOrgVo> acctOrgVoPage;

    /**
     * 从账号 及 组织关系
     */
    private Page<ListSlaveAcctOrgVo> slaveAcctOrgVoPage;


}
