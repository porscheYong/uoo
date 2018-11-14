package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.PostRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 记录系统职位授予的角色关系，一个系统职位可以包含多个角色，一个角色可以分配给多个系统职位。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
public interface PostRoleMapper extends BaseMapper<PostRole> {
    Long getId();
}
