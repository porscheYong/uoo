package cn.ffcs.uoo.core.user.constant;

import lombok.Getter;
import lombok.Setter;

public enum EumUserResponeCode {
    USER_RESPONSE_SUCCESS(1000, "操作成功"),
    USER_RESPONSE_ERROR(1, "操作失败"),
    ACCT_IS_EXIST(2, "主账号已存在"),
    ACCT_NO_EXIST(2, "主账号不存在"),
    ACCT_IS_DELETE(4, "主账号已删除"),
    PWD_COMPARE_ERROR(5, "新密码与原密码不能相同！"),
    ACCT_UPDATE_SUCCESS(6, "主账号密码已更新！"),
    ACCT_EXT_UPDATE_SUCCESS(7, "主账号扩展信息更新！"),
    SLAVE_ACCT_IS_EXIST(8, "从账号已存在"),
    SLAVE_ACCT_ADD_SUCCESS(9, "从账号新增成功"),
    SLAVE_ACCT_NO_EXITST(10, "从账号不存在"),
    USER_NOT_EXIST(11, "用户不存在"),
    ACCT_NO_EXIST_RE(12, "主账号不存在,请新建主账号"),
    ACCT_ORG_REL_IS_EXIST(13, "主账号组织关系已存在"),
    PERSONNEL_NO_CHOOSE(14, "请选择人员"),
    SLAVE_ACCT_NULL(15, "用户名不能为空"),
    EFF_DATE_NULL(16, "生效时间不能为空"),
    EXP_DATE_NULL(17, "失效时间不能为空"),
    STATUS_CD_NULL(18, "状态不能为空"),
    ROLES_NULL(19, "请选择角色"),
    RESOURCE_OBJ_NULL(20, "应用系统不能为空"),
    ACCT_HOST_NULL(21, "归属组织不能为空"),
    EMAIL_ERROR(22, "邮箱格式有误"),
    MOBILE_ERROR(23, "手机号有误"),
    CERT_NO_ERROR(24, "证件号有误"),
    SLAVE_ACCT_ORGREL_EXIST(25, "主账号组织关系已被占用"),
    ACCT_NOT_NULL(26, "账号不能为空"),
    ;

    @Getter
    @Setter
    private int state;

    @Getter
    @Setter
    private String message;

    private EumUserResponeCode(int state, String message) {
        this.state = state;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\"state\":\"" + this.state + "\",\"message\":\"" + this.message + "\"}";
    }

}
