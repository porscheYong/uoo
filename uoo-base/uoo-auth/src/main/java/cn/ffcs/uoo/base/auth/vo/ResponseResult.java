package cn.ffcs.uoo.base.auth.vo;

import java.util.List;

import cn.ffcs.uoo.base.common.vo.BaseVo;

public class ResponseResult extends BaseVo{
    public static final int STATE_OK = 1;
    public static final int STATE_ERROR = -1;

    private int state = STATE_OK;
    private String message;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseResult createErrorResult(List<? extends Object> datas, String message) {
        ResponseResult result = new ResponseResult();
        result.setData(datas);
        result.setMessage(message);
        result.setState(STATE_ERROR);
        return result;
    }

    public static ResponseResult createErrorResult(Object data, String message) {
        ResponseResult result = new ResponseResult();
        result.setData(data);
        result.setMessage(message);
        result.setState(STATE_ERROR);
        return result;
    }

    public static ResponseResult createErrorResult(String message) {
        ResponseResult result = new ResponseResult();
        result.setMessage(message);
        result.setState(STATE_ERROR);
        return result;
    }

    public static ResponseResult createSuccessResult(String message) {
        ResponseResult result = new ResponseResult();
        result.setMessage(message);
        return result;
    }

    public static ResponseResult createSuccessResult(Object data, String message) {
        ResponseResult result = new ResponseResult();
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    public static ResponseResult createSuccessResult(List<? extends Object> datas, String message) {
        ResponseResult result = new ResponseResult();
        result.setData(datas);
        result.setMessage(message);
        return result;
    }
    
    public static ResponseResult createSuccessResult(List<? extends Object> datas, String message,int pageNo,int pageSize) {
        ResponseResult result = new ResponseResult();
        result.setData(datas);
        result.setMessage(message);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        return result;
    }

}
