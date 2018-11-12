package cn.ffcs.uoo.web.maindata.region.vo;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;

public class ResponseResult {
    public static final int STATE_OK = 1;//返回结果正常
    public static final int STATE_ERROR = -1;//返回结果异常

    private int state = STATE_OK;
    private String message;
    private Object data;
    
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
    
    public static ResponseResult createSuccessResult(List<? extends Object> datas, String message,Page<? extends Object> page) {
        ResponseResult result = new ResponseResult();
        result.setData(datas);
        result.setMessage(message);
        result.setPageNo(page.getCurrent());
        result.setPageSize(page.getSize());
        result.setTotalRecords(page.getTotal());
        return result;
    }

}
