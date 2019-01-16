package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.PrivElementRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 定义权限关联的元素，一个权限可包含多个元素。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface IPrivElementRelService extends IService<PrivElementRel> {
    public Long getId();
}
