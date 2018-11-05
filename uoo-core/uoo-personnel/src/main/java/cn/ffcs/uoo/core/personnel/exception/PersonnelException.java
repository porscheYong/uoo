package cn.ffcs.uoo.core.personnel.exception;

import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;

public class PersonnelException extends RuntimeException {

    private Integer code;

    public PersonnelException (Integer code, String message){
        super(message);
        this.code = code;
    }

    public  PersonnelException(EumPersonnelResponseCode responseCode){
        super(responseCode.getMsg());
        this.code = responseCode.getCode();
    }
}
