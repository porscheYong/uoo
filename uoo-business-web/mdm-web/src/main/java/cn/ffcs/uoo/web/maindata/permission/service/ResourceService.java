package cn.ffcs.uoo.web.maindata.permission.service;

import cn.ffcs.uoo.core.permission.entity.Resource;
import com.baomidou.mybatisplus.service.IService;

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
