package cn.ffcs.interfaces.cpc.service;

import cn.ffcs.interfaces.cpc.pojo.TbSlaveAcct;
import com.baomidou.mybatisplus.service.IService;

/**
 * Created by liuxiaodong on 2019/1/11.
 */
public interface TbSlaveAcctService extends IService<TbSlaveAcct> {
    int selectBySlaveAcctAndAcctId(String account, Long acctId);

    boolean deleteByAcctId(Long acctId);
}
