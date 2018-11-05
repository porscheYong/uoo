package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.dao.TbSlvacctAcctRelMapper;
import cn.ffcs.uoo.core.user.entity.TbSlvacctAcctRel;
import cn.ffcs.uoo.core.user.service.TbSlvacctAcctRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 从账号主账号关系 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service
public class TbSlvacctAcctRelServiceImpl extends ServiceImpl<TbSlvacctAcctRelMapper, TbSlvacctAcctRel> implements TbSlvacctAcctRelService {

    @Override
    public Long getId(){
        return baseMapper.getId();
    }
}
