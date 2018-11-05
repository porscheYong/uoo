package cn.ffcs.uoo.core.permission.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.permission.entity.TbUserRole;
import cn.ffcs.uoo.core.permission.dao.TbUserRoleMapper;
import cn.ffcs.uoo.core.permission.service.TbUserRoleService;
import cn.ffcs.uoo.core.permission.vo.UserPersonnelVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 描述系统用户与角色之间的对应关系，是多对多关系。一个系统用户除了拥有系统岗位所带的角色和权限，也可以拥有多个私有的角色，一个角色可以分配给多个系统用户。 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-24
 */
@Service
public class TbUserRoleServiceImpl extends ServiceImpl<TbUserRoleMapper, TbUserRole> implements TbUserRoleService {

    @Override
    public void remove(Long userRoleId, Long updateStaff) {
        TbUserRole tbUserRole = new TbUserRole();
        tbUserRole.setUserRoleId(userRoleId);
        // 失效状态
        tbUserRole.setStatusCd("1100");
        tbUserRole.setUpdateStaff(updateStaff);
        tbUserRole.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbUserRole.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));

        baseMapper.remove(tbUserRole);
    }

    @Override
    public void addTbUserRole(TbUserRole tbUserRole) {
        baseMapper.insertSelective(tbUserRole);
    }

    @Override
    public Page<UserPersonnelVo> selectUserPersonnelPage(Page<UserPersonnelVo> page, Long roleId) {
        return page.setRecords(baseMapper.selectUserPersonnelList(page, roleId));
    }
}
