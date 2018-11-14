package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.Roles;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
public interface RolesMapper extends BaseMapper<Roles> {
    Long getId();
}
