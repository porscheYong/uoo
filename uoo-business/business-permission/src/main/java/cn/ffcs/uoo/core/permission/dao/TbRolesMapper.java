package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.TbRoles;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-24
 */
public interface TbRolesMapper extends BaseMapper<TbRoles> {
    /**
     *  失效角色
     * @param tbRoles
     */
    void remove(TbRoles tbRoles);

    /**
     * 新增角色
     * @param record
     * @return
     */
    int insertSelective(TbRoles record);
}
