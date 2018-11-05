package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.entity.TbSlvacctUserRel;
import cn.ffcs.uoo.core.user.dao.TbSlvacctUserRelMapper;
import cn.ffcs.uoo.core.user.service.TbSlvacctUserRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@Service
public class TbSlvacctUserRelServiceImpl extends ServiceImpl<TbSlvacctUserRelMapper, TbSlvacctUserRel> implements TbSlvacctUserRelService {

    @Override
    public Long getId(){
        return  baseMapper.getId();
    }
}
