package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.MemuMapper;
import cn.ffcs.uoo.core.permission.entity.Memu;
import cn.ffcs.uoo.core.permission.service.MemuService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@Service
public class MemuServiceImpl extends ServiceImpl<MemuMapper, Memu> implements MemuService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }
    @Override
    public void delete(Memu memu){
        memu.setStatus("1100");
        updateById(memu);
    }
    @Override
    public void add(Memu memu){
        memu.setStatus("1000");
        insert(memu);
    }

    @Override
    public void update(Memu memu){
        updateById(memu);
    }

}
