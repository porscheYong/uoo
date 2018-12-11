package cn.ffcs.uoo.core.permission.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.core.permission.entity.Resource;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
public interface ResourceMapper extends BaseMapper<Resource> {
    Long getId();
}
