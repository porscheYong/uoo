package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.entity.TbUserRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wudj
 * @since 2018-11-13
 */
public interface TbUserRoleService extends IService<TbUserRole> {

    public Long getId();

    /**
     * 保存 主账号 和 从账号角色
     * @param tbRolesList
     * @param acctId
     * @param acctType
     * @param userId
     * @return
     */
    public Object saveUserRole(List<TbRoles> tbRolesList, Long acctId, Long acctType, Long userId);

    /**
     * 删除 主账号 和 从账号角色
     * @param acctId
     * @param acctType
     * @param userId
     * @return
     */
    public Object removeUserRole(Long acctId, Long acctType, Long userId);

    /**
     * 更新 主账号 和 从账号角色
     * @param tbRolesList
     * @param acctId
     * @param acctType
     * @param userId
     * @return
     */
    public Object updateUserRole(List<TbRoles> tbRolesList,List<TbRoles> oldTbRolesList,Long acctId, Long acctType, Long userId);

}
