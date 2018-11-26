package cn.ffcs.uoo.web.maindata.permission.service;

import cn.ffcs.uoo.core.permission.entity.PrivGrant;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 描述将权限授权给不同的对象，包括系统用户、系统岗位、角色；
一个系统用户也可以拥有多个私有的权限，一个权限可以分配给多个系统用户。
一个系统岗位除了拥有角色所带的权限，也可以拥有私有的多个权限，一个权限可以分配给多个系统岗位。
一个角色可以包含多个权限，一个权限也可以分配给多个角色。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
public interface IPrivGrantService extends IService<PrivGrant> {
    Long getId();
    List<Map> selectPrivGrantByGranObj(HashMap<String, Object> params);
}
