package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysPrivFileRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 定义权限关联的文件，一个权限可包含多个文件。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface ISysPrivFileRelService extends IService<SysPrivFileRel> {
    Long getId();
    
    void updatePermissionCode(String oldCode, String newCode);
}
