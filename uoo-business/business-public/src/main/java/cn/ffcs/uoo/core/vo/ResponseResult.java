package cn.ffcs.uoo.core.vo;

import java.io.Serializable;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 封装接口返回类
 */
public class ResponseResult<T> implements Serializable {
    public static final int STATE_OK = 1000;
    public static final int STATE_ERROR = 1100;


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
    public static <T> ResponseResult<T> createErrorResult(T datas, String message) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setData(datas);
        result.setMessage(message);
        result.setState(STATE_ERROR);
        return result;
    }


    public static <T> ResponseResult<T> createErrorResult(String message) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setMessage(message);
        result.setState(STATE_ERROR);
        return result;
    }

    public static <T> ResponseResult<T> createSuccessResult(String message) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setMessage(message);
        return result;
    }

    public static <T> ResponseResult<T> createSuccessResult(T data, String message) {
        ResponseResult<T> result = new ResponseResult<T>();
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    
    public static <T> ResponseResult<Page<T>> createSuccessResult(Page<T> page, String message) {
        ResponseResult<Page<T>> result = new ResponseResult<>();
        result.setData(page);
        result.setMessage(message);
        return result;
    }
}
