package cn.ffcs.uoo.base.auth.service;

import cn.ffcs.uoo.base.auth.entity.UserLoginHis;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 系统用户登录历史 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
public interface IUserLoginHisService extends IService<UserLoginHis> {
    public Long getId();
}
