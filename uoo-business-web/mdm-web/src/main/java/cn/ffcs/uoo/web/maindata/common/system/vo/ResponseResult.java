package cn.ffcs.uoo.web.maindata.common.system.vo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 封装json格式类
 * Created by liuxiaodong on 2018/9/17.
 */
public class ResponseResult<T> implements Serializable {

    public static final int STATE_OK = 1000;

    public static final int PARAMETER_ERROR = 1100;

    public static final int STATE_ERROR = 1200;
    public static final int STATE_SERVICE_ERROR = 1300;


    private int state;
    private String message;
    private T data;
    /**
     * 分页的序号
     */
    private int pageNo;

    /**
     * 每页的大小
     */
    private int pageSize;
  //补充分页数据
    private long totalRecords;
    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
