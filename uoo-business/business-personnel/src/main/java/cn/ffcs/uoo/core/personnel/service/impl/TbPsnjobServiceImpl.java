package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.entity.TbPsnjob;
import cn.ffcs.uoo.core.personnel.dao.TbPsnjobMapper;
import cn.ffcs.uoo.core.personnel.service.TbPsnjobService;
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
public class TbPsnjobServiceImpl extends ServiceImpl<TbPsnjobMapper, TbPsnjob> implements TbPsnjobService {

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public void delete(TbPsnjob tbPsnjob){
        baseMapper.delete(tbPsnjob);
    }
}
