package cn.ffcs.uoo.base.auth.mapper;

import cn.ffcs.uoo.base.auth.entity.LoginAccount;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 记录员工登录系统使用的系统帐户，不同的系统可有不同的系统用户。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
public interface LoginAccountMapper extends BaseMapper<LoginAccount> {
    public Long getId();
}
