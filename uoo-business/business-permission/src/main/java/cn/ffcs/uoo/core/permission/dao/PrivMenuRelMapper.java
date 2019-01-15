package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.PrivMenuRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 定义权限关联的菜单，一个权限可包含多个菜单。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface PrivMenuRelMapper extends BaseMapper<PrivMenuRel> {
    Long getId();
}
