package cn.ffcs.uoo.core.personnel.util;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by wudj on 2018/10/15.
 */
@Data
public class ResponseResult<T> implements Serializable {

    public static final int STATE_OK = 1;

    public static final int STATE_ERROR = -1;

    /**
     * 提示编码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 具体内容
     */
    private T data;

}
