package cn.ffcs.uoo.system.util;

import java.io.Serializable;

/**
 * 封装json格式类
 * Created by liuxiaodong on 2018/9/17.
 */
public class ResponseResult<T> implements Serializable {

    public static final int STATE_OK = 1000;

    public static final int PARAMETER_ERROR = 1100;

    public static final int STATE_ERROR = 1200;


    private int state;
    private String message;
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
