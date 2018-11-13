package cn.ffcs.uoo.core.permission.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.core.permission.entity.PrivGrant;

/**
 * <p>
 * 描述将权限授权给不同的对象，包括系统用户、系统岗位、角色；
一个系统用户也可以拥有多个私有的权限，一个权限可以分配给多个系统用户。
一个系统岗位除了拥有角色所带的权限，也可以拥有私有的多个权限，一个权限可以分配给多个系统岗位。
一个角色可以包含多个权限，一个权限也可以分配给多个角色。 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
public interface PrivGrantMapper extends BaseMapper<PrivGrant> {
    Long getId();
    List<Map> selectPrivGrantByGranObj(HashMap<String, Object> params);
}
