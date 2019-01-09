package cn.ffcs.uoo.web.maindata.permission.vo;

import com.baomidou.mybatisplus.plugins.Page;

public class ResponseResult<T>  {
    public static final int STATE_OK = 1000;//返回结果正常
    public static final int STATE_ERROR = 1100;//返回结果异常

    private int state = STATE_OK;
    private String message;
    private T data;
    
    //补充分页数据
    private long totalRecords;
    
    /**
     * 分页的序号
     */
    private int pageNo;

    /**
     * 每页的大小
     */
    private int pageSize;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
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

    
    public static <T> ResponseResult<Page<T>> createSuccessResult(Page<T> page, String message) {
        ResponseResult<Page<T>> result = new ResponseResult<>();
        result.setData(page);
        result.setMessage(message);
        return result;
    }
}
