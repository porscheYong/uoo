package cn.ffcs.uoo.base.auth.mapper;

import cn.ffcs.uoo.base.auth.entity.UserLoginHis;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 系统用户登录历史 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-01
 */
public interface UserLoginHisMapper extends BaseMapper<UserLoginHis> {
    public Long getId();
}
