package cn.ffcs.uoo.base.auth.service;

import cn.ffcs.uoo.base.auth.entity.UserPasswdHis;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 系统用户登录配置表 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
public interface IUserPasswdHisService extends IService<UserPasswdHis> {
    public Long getId();
}
