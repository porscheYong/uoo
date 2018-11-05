package cn.ffcs.uoo.core.personnel.util;

import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;

public class ResultUtils {

    public static  Object success(Object object){

        ResponseResult result = new ResponseResult();
        result.setData(object);
        result.setCode(EumPersonnelResponseCode.PERSONNEL_RESPONSE_SUCCESS.getCode());
        result.setMessage(EumPersonnelResponseCode.PERSONNEL_RESPONSE_SUCCESS.getMsg());

        return result;
    }

    public static  Object certError(){
        ResponseResult result = new ResponseResult();
        result.setCode(EumPersonnelResponseCode.CERT_ERROR.getCode());
        result.setMessage(EumPersonnelResponseCode.CERT_ERROR.getMsg());
        return result;
    }

    public static Object moblieError(){
        ResponseResult result = new ResponseResult();
        result.setCode(EumPersonnelResponseCode.MOBILE_ERROR.getCode());
        result.setMessage(EumPersonnelResponseCode.MOBILE_ERROR.getMsg());
        return result;
    }

    public static Object emailError(){
        ResponseResult result = new ResponseResult();
        result.setCode(EumPersonnelResponseCode.EMAIL_ERROR.getCode());
        result.setMessage(EumPersonnelResponseCode.EMAIL_ERROR.getMsg());
        return result;
    }

    public static Object error(EumPersonnelResponseCode eumPersonnelResponseCode){
        ResponseResult result = new ResponseResult();
        result.setCode(eumPersonnelResponseCode.getCode());
        result.setMessage(eumPersonnelResponseCode.getMsg());
        return result;
    }

    public static Object error(int code, String msg){
        ResponseResult result = new ResponseResult();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }
}
