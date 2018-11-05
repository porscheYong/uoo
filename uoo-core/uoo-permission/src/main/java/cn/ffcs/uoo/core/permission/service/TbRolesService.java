package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.TbRoles;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-24
 */
public interface TbRolesService extends IService<TbRoles> {
    /**
     *  失效该角色
     * @param roleId
     * @param updateUser
     */
    void remove(Long roleId, Long updateUser);

    /**
     * 新增角色
     * @param record
     * @return
     */
    int insertSelective(TbRoles record);
}
