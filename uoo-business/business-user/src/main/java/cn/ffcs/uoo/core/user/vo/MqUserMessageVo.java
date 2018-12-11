package cn.ffcs.uoo.core.user.vo;

import lombok.Data;

@Data
public class MqUserMessageVo {

    private String type;

    private String handle;

    private MqUserContextVo context;
}
