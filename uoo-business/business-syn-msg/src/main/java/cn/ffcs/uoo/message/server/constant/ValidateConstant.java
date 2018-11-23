package cn.ffcs.uoo.message.server.constant;

public enum ValidateConstant {
    success("1000"), fail("1100");

    private String value;

    private ValidateConstant(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
