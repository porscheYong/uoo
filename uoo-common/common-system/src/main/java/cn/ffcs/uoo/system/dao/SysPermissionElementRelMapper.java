package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysPermissionElementRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 定义权限关联的元素，一个权限可包含多个元素。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface SysPermissionElementRelMapper extends BaseMapper<SysPermissionElementRel> {
    Long getId();

    void updatePermissionCode(String oldCode, String newCode);
}
