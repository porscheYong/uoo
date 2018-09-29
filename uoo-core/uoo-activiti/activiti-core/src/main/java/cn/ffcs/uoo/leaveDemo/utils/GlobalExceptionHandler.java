package cn.ffcs.uoo.leaveDemo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局controller 层异常处理
 * Created by liuxiaodong on 2018/9/28.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有接口数据验证异常
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseResult<Void> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        LOGGER.error(e.getMessage(), e);
        return setJsonObject();
    }

    /**
     * 处理参数映射到对象数据异常
     * @param exception 参数映射异常
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseResult<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return setJsonObject();
    }

    private ResponseResult<Void> setJsonObject() {
        ResponseResult<Void> result = new ResponseResult<>();
        result.setState(ResponseResult.PARAMETER_ERROR);
        result.setMessage("参数无效或格式不正确！");
        return result;
    }


}
