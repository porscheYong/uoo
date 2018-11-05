package cn.ffcs.uoo.core.region.service.impl;

import cn.ffcs.uoo.core.region.dao.TbAreaCodeMapper;
import cn.ffcs.uoo.core.region.entity.TbAreaCode;
import cn.ffcs.uoo.core.region.service.ITbAreaCodeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 区号 服务实现类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@Service
public class TbAreaCodeServiceImpl extends ServiceImpl<TbAreaCodeMapper, TbAreaCode> implements ITbAreaCodeService {
    @Autowired
    private TbAreaCodeMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }

}
