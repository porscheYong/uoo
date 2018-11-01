package cn.ffcs.uoo.base.auth.mapper;

import cn.ffcs.uoo.base.auth.entity.LoginConfig;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 系统用户登录配置表 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
public interface LoginConfigMapper extends BaseMapper<LoginConfig> {
    public Long getId();
}
