package cn.ffcs.uoo.rabbitmq.monitor.constant;

public enum  RabbitmqIndexConstant {

    collection("1"),used("2");

    private String value;

    private RabbitmqIndexConstant(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
