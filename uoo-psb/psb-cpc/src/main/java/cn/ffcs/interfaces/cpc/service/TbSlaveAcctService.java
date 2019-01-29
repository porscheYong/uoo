package cn.ffcs.interfaces.cpc.service;

import cn.ffcs.interfaces.cpc.pojo.TbSlaveAcct;
import com.baomidou.mybatisplus.service.IService;

/**
 * Created by liuxiaodong on 2019/1/11.
 */
public interface TbSlaveAcctService extends IService<TbSlaveAcct> {
    TbSlaveAcct selectBySlaveAcctAndAcctId(String account, Long acctId,Long resourceObjId);

    boolean deleteByAcctId(Long acctId,Long resourceObjId);
}
