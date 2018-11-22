package cn.ffcs.uoo.core.personnel.util;

import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;

public class ResultUtils {

    public static  Object success(Object object){

        ResponseResult result = new ResponseResult();
        result.setData(object);
        result.setState(EumPersonnelResponseCode.PERSONNEL_RESPONSE_SUCCESS.getState());
        result.setMessage(EumPersonnelResponseCode.PERSONNEL_RESPONSE_SUCCESS.getMessage());

        return result;
    }

    public static  Object certError(){
        ResponseResult result = new ResponseResult();
        result.setState(EumPersonnelResponseCode.CERT_ERROR.getState());
        result.setMessage(EumPersonnelResponseCode.CERT_ERROR.getMessage());
        return result;
    }

    public static Object moblieError(){
        ResponseResult result = new ResponseResult();
        result.setState(EumPersonnelResponseCode.MOBILE_ERROR.getState());
        result.setMessage(EumPersonnelResponseCode.MOBILE_ERROR.getMessage());
        return result;
    }

    public static Object emailError(){
        ResponseResult result = new ResponseResult();
        result.setState(EumPersonnelResponseCode.EMAIL_ERROR.getState());
        result.setMessage(EumPersonnelResponseCode.EMAIL_ERROR.getMessage());
        return result;
    }

    public static Object error(EumPersonnelResponseCode eumPersonnelResponseCode){
        ResponseResult result = new ResponseResult();
        result.setState(eumPersonnelResponseCode.getState());
        result.setMessage(eumPersonnelResponseCode.getMessage());
        return result;
    }

    public static Object error(int code, String msg){
        ResponseResult result = new ResponseResult();
        result.setState(code);
        result.setMessage(msg);
        return result;
    }
}
