package cn.ffcs.uoo.core.region.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.region.dao.TbExchMapper;
import cn.ffcs.uoo.core.region.entity.TbExch;
import cn.ffcs.uoo.core.region.service.ITbExchService;

/**
 * <p>
 * 描述信令所指的方向,如到某个局(每局对应一个DPC)的信令,可称到某局的局向。 服务实现类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@Service
public class TbExchServiceImpl extends ServiceImpl<TbExchMapper, TbExch> implements ITbExchService {
    @Autowired
    private TbExchMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
}
