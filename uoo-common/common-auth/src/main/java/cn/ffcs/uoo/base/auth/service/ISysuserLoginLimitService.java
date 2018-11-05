package cn.ffcs.uoo.base.auth.service;

import cn.ffcs.uoo.base.auth.entity.SysuserLoginLimit;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 系统用户登录设置。同一系统用户可以有多种登录设置信息。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
public interface ISysuserLoginLimitService extends IService<SysuserLoginLimit> {
    public Long getId();
}
