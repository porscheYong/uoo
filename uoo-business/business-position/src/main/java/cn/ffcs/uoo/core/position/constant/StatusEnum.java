package cn.ffcs.uoo.core.position.constant;

/**
 *  状态枚举
 * @author zhanglu
 */
public enum StatusEnum {
    VALID("1000"), INVALID("1100");

    private String status;

    private StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
