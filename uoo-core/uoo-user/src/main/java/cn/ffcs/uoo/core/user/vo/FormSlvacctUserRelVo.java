package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import cn.ffcs.uoo.core.user.entity.TbUser;
import lombok.Data;

import java.util.List;

@Data
public class FormSlvacctUserRelVo {

    private List<TbUser> tbUserList;

    private TbSlaveAcct tbSlaveAcct;
}
