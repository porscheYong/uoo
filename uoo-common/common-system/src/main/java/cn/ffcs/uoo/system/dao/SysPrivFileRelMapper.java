package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysPrivFileRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 定义权限关联的文件，一个权限可包含多个文件。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface SysPrivFileRelMapper extends BaseMapper<SysPrivFileRel> {
    Long getId();

    void updatePermissionCode(String oldCode, String newCode);
}
