package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.TreeStaffTypeRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-28
 */
public interface TreeStaffTypeRelMapper extends BaseMapper<TreeStaffTypeRel> {

    public Long getId();

    public void delete(TreeStaffTypeRel treeStaffTypeRel);
}
