package cn.ffcs.uoo.core.user.dao;

import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.entity.TbUser;
import cn.ffcs.uoo.core.user.vo.ListSlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.ListUserOrgVo;
import cn.ffcs.uoo.core.user.vo.PsonOrgVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

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

    public List<TbRoles> getRoleByUserId(@Param("userId") String userId);

    public List<ListSlaveAcctVo> getSlaveAcctInfo(@Param("userId") Long userId,@Param("acctId") Long acctId);

    public List<ListUserOrgVo> getUserOrg(Pagination page, PsonOrgVo psonOrgVo);

}
