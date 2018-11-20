package cn.ffcs.uoo.core.constant;

public enum StatusEnum {
    VALID("1000"),INVALID("1100");

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
