package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.PrivElementRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 定义权限关联的元素，一个权限可包含多个元素。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface PrivElementRelMapper extends BaseMapper<PrivElementRel> {
    Long getId();
}
