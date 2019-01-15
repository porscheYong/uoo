package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.PrivFileRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 定义权限关联的文件，一个权限可包含多个文件。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface PrivFileRelMapper extends BaseMapper<PrivFileRel> {
    Long getId();
}
