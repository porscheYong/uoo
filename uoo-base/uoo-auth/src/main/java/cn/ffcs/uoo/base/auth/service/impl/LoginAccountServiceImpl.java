package cn.ffcs.uoo.base.auth.service.impl;

import cn.ffcs.uoo.base.auth.entity.LoginAccount;
import cn.ffcs.uoo.base.auth.mapper.LoginAccountMapper;
import cn.ffcs.uoo.base.auth.service.ILoginAccountService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 记录员工登录系统使用的系统帐户，不同的系统可有不同的系统用户。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
@Service
public class LoginAccountServiceImpl extends ServiceImpl<LoginAccountMapper, LoginAccount> implements ILoginAccountService {
    @Autowired
    private LoginAccountMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }

}
