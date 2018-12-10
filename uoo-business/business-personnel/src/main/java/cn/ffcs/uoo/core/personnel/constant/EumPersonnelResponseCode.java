package cn.ffcs.uoo.core.personnel.constant;


import lombok.Getter;
import lombok.Setter;

public enum  EumPersonnelResponseCode {

    PERSONNEL_RESPONSE_SUCCESS(1000, "操作成功"),
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
    CONTACT_IS_NULL(13, "手机、邮箱、QQ号、微信号、固话不能为空"),
    CERT_IS_EXIST(14, "身份证已被占用"),
    PSN_NOT_EXIST(15, "人员不存在"),
    MOBILE_IS_EXIST(16, "手机号已被占用"),
    MOBILE_REPEAT(17, "手机号不得重复添加"),
    EMAIL_REPEAT(18, "邮箱不得重复添加"),
    ;

    @Getter
    @Setter
    private int state;

    @Getter
    @Setter
    private String message;

    private EumPersonnelResponseCode(int state, String message) {
        this.state = state;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\"state\":\"" + this.state + "\",\"message\":\"" + this.message + "\"}";
    }
}
