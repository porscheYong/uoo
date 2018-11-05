package cn.ffcs.uoo.core.personnel.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonnelOrgVo extends BaseVo {

    /**
     * 人员姓名
     */
    private String psnName;

    /**
     * 重名称谓
     */
    private String doubleName;

    /**
     * 员工工号
     */
    private String acct;

    /**
     * 职位
     */
    private String postName;

    /**
     * 归属组织
     */
    private String orgFullName;

}
