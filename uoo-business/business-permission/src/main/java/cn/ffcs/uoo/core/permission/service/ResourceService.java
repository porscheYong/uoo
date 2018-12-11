package cn.ffcs.uoo.core.permission.service;

import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.core.permission.entity.Resource;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
public interface ResourceService extends IService<Resource> {
    Long getId();
}
