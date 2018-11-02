package cn.ffcs.uoo.base.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.base.auth.entity.UserLoginHis;
import cn.ffcs.uoo.base.auth.mapper.UserLoginHisMapper;
import cn.ffcs.uoo.base.auth.service.IUserLoginHisService;

/**
 * <p>
 * 系统用户登录历史 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@Service
public class UserLoginHisServiceImpl extends ServiceImpl<UserLoginHisMapper, UserLoginHis> implements IUserLoginHisService {
    @Autowired
    private UserLoginHisMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
}
