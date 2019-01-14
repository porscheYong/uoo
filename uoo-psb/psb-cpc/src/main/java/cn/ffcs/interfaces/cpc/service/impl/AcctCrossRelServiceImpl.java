package cn.ffcs.interfaces.cpc.service.impl;

import cn.ffcs.interfaces.cpc.pojo.AcctCrossRel;
import cn.ffcs.interfaces.cpc.dao.AcctCrossRelMapper;
import cn.ffcs.interfaces.cpc.service.AcctCrossRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
@Service
public class AcctCrossRelServiceImpl extends ServiceImpl<AcctCrossRelMapper, AcctCrossRel> implements AcctCrossRelService {

    @Override
    public boolean deleteByAcctIdAndRelaType(Long acctId, String relaType) {
        return baseMapper.deleteByPersonnelIdAndRelaType(acctId,relaType);
    }

    @Override
    public Long checkExistCrossRelTypeAndSalesCode(String type, String code) {
        return baseMapper.checkExistCrossRelTypeAndSalesCode(type,code);
    }
}
