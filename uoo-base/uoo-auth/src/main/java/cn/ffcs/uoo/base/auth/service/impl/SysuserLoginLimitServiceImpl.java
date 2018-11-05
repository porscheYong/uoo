package cn.ffcs.uoo.base.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.base.auth.entity.SysuserLoginLimit;
import cn.ffcs.uoo.base.auth.mapper.SysuserLoginLimitMapper;
import cn.ffcs.uoo.base.auth.service.ISysuserLoginLimitService;

/**
 * <p>
 * 系统用户登录设置。同一系统用户可以有多种登录设置信息。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@Service
public class SysuserLoginLimitServiceImpl extends ServiceImpl<SysuserLoginLimitMapper, SysuserLoginLimit> implements ISysuserLoginLimitService {
    @Autowired
    private SysuserLoginLimitMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
}
