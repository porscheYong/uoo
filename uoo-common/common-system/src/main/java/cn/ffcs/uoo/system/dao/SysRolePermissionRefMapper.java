package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysRolePermissionRef;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-20
 */
public interface SysRolePermissionRefMapper extends BaseMapper<SysRolePermissionRef> {

    Long getId();

    void updatePermissionCode(String oldCode, String newCode);

}
