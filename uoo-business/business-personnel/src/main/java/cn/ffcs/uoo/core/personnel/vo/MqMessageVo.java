package cn.ffcs.uoo.core.personnel.vo;

import lombok.Data;

@Data
public class MqMessageVo {

    private String type;

    private String handle;

    private MqContextVo context;
}
