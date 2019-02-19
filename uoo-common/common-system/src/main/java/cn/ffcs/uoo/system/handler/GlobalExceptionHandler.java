package cn.ffcs.uoo.system.handler;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.ffcs.uoo.system.vo.ResponseResult;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Object> validationErrorHandler(MethodArgumentNotValidException ex) {
        // 同样是获取BindingResult对象，然后获取其中的错误信息
        // 如果前面开启了fail_fast，事实上这里只会有一个信息
        //如果没有，则可能又多个
        ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
        return ResponseResult.createErrorResult(objectError.getDefaultMessage());
    }
 
}
