package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.PrivFileRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 定义权限关联的文件，一个权限可包含多个文件。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface IPrivFileRelService extends IService<PrivFileRel> {
    public Long getId();
}
