package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.Privilege;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 权限规格表，记录权限的配置 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
public interface IPrivilegeService extends IService<Privilege> {
    Long getId();
}
