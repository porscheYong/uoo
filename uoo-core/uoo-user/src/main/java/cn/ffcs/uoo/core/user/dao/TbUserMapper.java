package cn.ffcs.uoo.core.user.dao;

import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.entity.TbUser;
import cn.ffcs.uoo.core.user.vo.ListSlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.ListUserOrgVo;
import cn.ffcs.uoo.core.user.vo.PsonOrgVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
public interface TbUserMapper extends BaseMapper<TbUser> {

    public Long getId();

    public List<TbRoles> getRoleByUserId(Long userId);

    public List<ListSlaveAcctVo> getSlaveAcctInfo(Long userId, Long acctId);

    public List<ListUserOrgVo> getUserOrg(Pagination page, PsonOrgVo psonOrgVo);

}
