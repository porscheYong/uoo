package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.TreeStaffTypeRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-28
 */
public interface TreeStaffTypeRelService extends IService<TreeStaffTypeRel> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效数据
     * @param treeStaffTypeRel
     */
    public void delete(TreeStaffTypeRel treeStaffTypeRel);

    public void add(TreeStaffTypeRel treeStaffTypeRel);

    public void update(TreeStaffTypeRel treeStaffTypeRel);


}
