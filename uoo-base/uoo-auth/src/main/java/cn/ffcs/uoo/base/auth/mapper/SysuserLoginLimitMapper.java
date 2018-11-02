package cn.ffcs.uoo.base.auth.mapper;

import cn.ffcs.uoo.base.auth.entity.SysuserLoginLimit;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 系统用户登录设置。同一系统用户可以有多种登录设置信息。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
public interface SysuserLoginLimitMapper extends BaseMapper<SysuserLoginLimit> {
    public Long getId();
}
