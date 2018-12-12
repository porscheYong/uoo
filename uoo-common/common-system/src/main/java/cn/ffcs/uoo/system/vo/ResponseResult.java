package cn.ffcs.uoo.system.vo;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.vo.BaseVo;

public class ResponseResult<T> extends BaseVo{
    public static final int STATE_OK = 1000;//返回结果正常
    public static final int STATE_ERROR = 1100;//返回结果异常

    private int state = STATE_OK;
    private String message;
    private T data;
    
    //补充分页数据
    private long totalRecords;
    
    
    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

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

    
    public static <T> ResponseResult<List<T>> createSuccessResult(Page<T> page, String message) {
        ResponseResult<List<T>> result = new ResponseResult<List<T>>();
        result.setData(page.getRecords());
        result.setMessage(message);
        result.setPageNo(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setTotalRecords(page.getTotal());
        return result;
    }

}
