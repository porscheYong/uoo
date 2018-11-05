package cn.ffcs.uoo.core.personnel.constant;

import lombok.Getter;

/**
 * Created by 廖师兄
 * 2017-12-10 17:32
 */
@Getter
public enum ResultEum {
    PARAM_ERROR(1, "参数错误"),
    CART_EMPTY(2, "不能为空"),

    ;

    private Integer code;

    private String message;

    ResultEum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
