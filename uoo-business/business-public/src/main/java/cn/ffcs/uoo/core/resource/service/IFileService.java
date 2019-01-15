package cn.ffcs.uoo.core.resource.service;

import cn.ffcs.uoo.core.resource.entity.File;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>

 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface IFileService extends IService<File> {
    public Integer getId();
}
