package cn.ffcs.uoo.core.user.constant;

import lombok.Getter;
import lombok.Setter;

public enum EumUserResponeCode {
    USER_RESPONSE_SUCCESS(0, "操作成功"),
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
    ;
    ;
    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    private EumUserResponeCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{\"code\":\"" + this.code + "\",\"msg\":\"" + this.msg + "\"}";
    }

}
