package cn.ffcs.uoo.core.personnel.util;

import cn.ffcs.uoo.base.common.vo.ResponseResultVo;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;

public class ResultUtils {

    public static  Object success(Object object){

        ResponseResultVo result = new ResponseResultVo();
        result.setData(object);
        result.setState(EumPersonnelResponseCode.PERSONNEL_RESPONSE_SUCCESS.getState());
        result.setMessage(EumPersonnelResponseCode.PERSONNEL_RESPONSE_SUCCESS.getMessage());

        return result;
    }

    public static  Object certError(){
        ResponseResultVo result = new ResponseResultVo();
        result.setState(EumPersonnelResponseCode.CERT_ERROR.getState());
        result.setMessage(EumPersonnelResponseCode.CERT_ERROR.getMessage());
        return result;
    }

    public static Object moblieError(){
        ResponseResultVo result = new ResponseResultVo();
        result.setState(EumPersonnelResponseCode.MOBILE_ERROR.getState());
        result.setMessage(EumPersonnelResponseCode.MOBILE_ERROR.getMessage());
        return result;
    }

    public static Object emailError(){
        ResponseResultVo result = new ResponseResultVo();
        result.setState(EumPersonnelResponseCode.EMAIL_ERROR.getState());
        result.setMessage(EumPersonnelResponseCode.EMAIL_ERROR.getMessage());
        return result;
    }

    public static Object error(EumPersonnelResponseCode eumPersonnelResponseCode){
        ResponseResultVo result = new ResponseResultVo();
        result.setState(eumPersonnelResponseCode.getState());
        result.setMessage(eumPersonnelResponseCode.getMessage());
        return result;
    }

    public static Object error(int code, String msg){
        ResponseResultVo result = new ResponseResultVo();
        result.setState(code);
        result.setMessage(msg);
        return result;
    }
}
