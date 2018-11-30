package cn.ffcs.uoo.base.common.vo;

public class MqMessageVo {

    private String type;

    private String handle;

    private MqContextVo context;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public MqContextVo getContext() {
        return context;
    }

    public void setContext(MqContextVo context) {
        this.context = context;
    }
}
