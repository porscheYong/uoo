package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import lombok.Data;

@Data
public class FormSlvacctAcctRelVo {

    private Long acctId;

    private TbSlaveAcct tbSlaveAcct;

    private TbAcctExt tbAcctExt;
}
