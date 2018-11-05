package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import lombok.Data;

@Data
public class FormAcctVo {

    private Long personnelId;

    private Long userId;

    private TbAcct tbAcct;

}
