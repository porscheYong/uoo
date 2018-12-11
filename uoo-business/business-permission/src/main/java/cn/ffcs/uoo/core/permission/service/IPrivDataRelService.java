package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.PrivDataRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 记录权限与业务对象之间多对多的关系 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
public interface IPrivDataRelService extends IService<PrivDataRel> {
    Long getId();
}
