package cn.ffcs.uoo.core.personnel.constant;


import lombok.Getter;
import lombok.Setter;

public enum  EumPersonnelResponseCode {

    PERSONNEL_RESPONSE_SUCCESS(0, "操作成功"),
    PERSONNEL_RESPONSE_ERROR(1, "操作失败"),
    PSN_NAME_IS_NULL(2, "人员姓名不能为空"),
    CERT_TYPE_IS_NULL(3, "证件类型不能为空"),
    CERT_NO_IS_NULL(4, "身份证号不能为空"),
    MOBILE_IS_NULL(5, "手机号不能为空"),
    EMAIL_IS_NULL(6, "邮箱不能为空"),
    MOBILE_ERROR(7, "手机号格式不对"),
    EMAIL_ERROR(8 ,"邮箱格式不对"),
    CERT_ERROR(9, "身份证格式有误"),
    IMG_NOT_EMPTY(10, "图片不能空"),
    IMG_FORMAT_ERROR(11, "图片上传失败"),
    SAVE_IMG_ERROE(12, "图片保存失败"),
    ;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;

    private EumPersonnelResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{\"code\":\"" + this.code + "\",\"msg\":\"" + this.msg + "\"}";
    }
}
