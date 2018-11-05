package cn.ffcs.uoo.base.auth.service;

import cn.ffcs.uoo.base.auth.entity.LoginAccount;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 记录员工登录系统使用的系统帐户，不同的系统可有不同的系统用户。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
public interface ILoginAccountService extends IService<LoginAccount> {
    public Long getId();
}
