package cn.ffcs.uoo.base.common.vo;


import java.io.Serializable;

/**
 * Created by wudj on 2018/10/15.
 */
public class ResponseResultVo<T> implements Serializable {

    public static final int STATE_OK = 1;

    public static final int STATE_ERROR = -1;

    /**
     * 提示编码
     */
    private int state;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 具体内容
     */
    private T data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
