package cn.ffcs.uoo.core.resource.dao;

import cn.ffcs.uoo.core.resource.entity.File;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
public interface FileMapper extends BaseMapper<File> {
    Integer getId();
}
