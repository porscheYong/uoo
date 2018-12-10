package cn.ffcs.uoo.core.user.util;

import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;

public class ResultUtils {

    public static  Object success(Object object){

        ResponseResult result = new ResponseResult();
        result.setData(object);
        result.setState(EumUserResponeCode.USER_RESPONSE_SUCCESS.getState());
        result.setMessage(EumUserResponeCode.USER_RESPONSE_SUCCESS.getMessage());

        return result;
    }

    public static Object error(EumUserResponeCode eumUserResponeCode){
        ResponseResult result = new ResponseResult();
        result.setState(eumUserResponeCode.getState());
        result.setMessage(eumUserResponeCode.getMessage());
        return result;
    }

    public static Object error(int code, String msg){
        ResponseResult result = new ResponseResult();
        result.setState(code);
        result.setMessage(msg);
        return result;
    }

    public static Object successfulTip(EumUserResponeCode eumUserResponeCode){
        ResponseResult result = new ResponseResult();
        result.setState(eumUserResponeCode.getState());
        result.setMessage(eumUserResponeCode.getMessage());
        return result;
    }
}
