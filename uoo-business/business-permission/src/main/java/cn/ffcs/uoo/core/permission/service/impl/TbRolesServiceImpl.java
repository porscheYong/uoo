package cn.ffcs.uoo.core.permission.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.permission.entity.TbRoles;
import cn.ffcs.uoo.core.permission.dao.TbRolesMapper;
import cn.ffcs.uoo.core.permission.service.TbRolesService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 描述员工的系统权限集合，是用以定义系统使用人员操作权限的实体。 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-24
 */
@Service
public class TbRolesServiceImpl extends ServiceImpl<TbRolesMapper, TbRoles> implements TbRolesService {
    @Override
    public void remove(Long roleId, Long updateStaff) {
        TbRoles tbRoles = new TbRoles();
        tbRoles.setRoleId(roleId);
        tbRoles.setUpdateStaff(updateStaff);
        tbRoles.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbRoles.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        // 失效状态
        tbRoles.setStatusCd("1100");

        baseMapper.remove(tbRoles);
    }

    @Override
    public int insertSelective(TbRoles record) {
        return baseMapper.insertSelective(record);
    }
}
