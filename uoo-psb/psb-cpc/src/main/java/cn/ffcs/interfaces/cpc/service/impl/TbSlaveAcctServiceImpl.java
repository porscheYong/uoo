package cn.ffcs.interfaces.cpc.service.impl;

import cn.ffcs.interfaces.cpc.dao.TbSlaveAcctMapper;
import cn.ffcs.interfaces.cpc.pojo.TbSlaveAcct;
import cn.ffcs.interfaces.cpc.service.TbSlaveAcctService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by liuxiaodong on 2019/1/11.
 */
@Service
public class TbSlaveAcctServiceImpl extends ServiceImpl<TbSlaveAcctMapper, TbSlaveAcct> implements TbSlaveAcctService {
    @Override
    public int selectBySlaveAcctAndAcctId(String account, Long acctId) {
        return baseMapper.selectBySlaveAcctAndAcctId(account,acctId);
    }

    @Override
    public boolean deleteByAcctId(Long acctId) {
        return baseMapper.deleteByAcctId(acctId);
    }
}
