package cn.ffcs.uoo.web.maindata.permission.service;

import cn.ffcs.uoo.core.permission.entity.PostRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 记录系统职位授予的角色关系，一个系统职位可以包含多个角色，一个角色可以分配给多个系统职位。 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-13
 */
public interface IPostRoleService extends IService<PostRole> {
    Long getId();

    List<PostRole> getPostRole();
}
