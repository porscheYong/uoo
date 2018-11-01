package cn.ffcs.uoo.core.user.util;

import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;

public class ResultUtils {

    public static  Object success(Object object){

        ResponseResult result = new ResponseResult();
        result.setData(object);
        result.setCode(EumUserResponeCode.USER_RESPONSE_SUCCESS.getCode());
        result.setMessage(EumUserResponeCode.USER_RESPONSE_SUCCESS.getMsg());

        return result;
    }

    public static Object error(EumUserResponeCode eumUserResponeCode){
        ResponseResult result = new ResponseResult();
        result.setCode(eumUserResponeCode.getCode());
        result.setMessage(eumUserResponeCode.getMsg());
        return result;
    }

    public static Object successfulTip(EumUserResponeCode eumUserResponeCode){
        ResponseResult result = new ResponseResult();
        result.setCode(eumUserResponeCode.getCode());
        result.setMessage(eumUserResponeCode.getMsg());
        return result;
    }
}
