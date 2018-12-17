package cn.ffcs.uoo.core.permission.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.core.permission.entity.BusinessSystem;

/**
 * <p>
 * 接入系统表 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
public interface BusinessSystemMapper extends BaseMapper<BusinessSystem> {
    public List<BusinessSystem> listBusinessSystemByOrgTree(Long treeId);
}
