package cn.ffcs.uoo.base.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.base.auth.entity.UserPasswdHis;
import cn.ffcs.uoo.base.auth.mapper.UserPasswdHisMapper;
import cn.ffcs.uoo.base.auth.service.IUserPasswdHisService;

/**
 * <p>
 * 系统用户登录配置表 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@Service
public class UserPasswdHisServiceImpl extends ServiceImpl<UserPasswdHisMapper, UserPasswdHis> implements IUserPasswdHisService {
    @Autowired
    private UserPasswdHisMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
}
