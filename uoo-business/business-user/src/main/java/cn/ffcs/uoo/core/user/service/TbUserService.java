package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.entity.TbUser;
import cn.ffcs.uoo.core.user.vo.ListSlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.ListUserOrgVo;
import cn.ffcs.uoo.core.user.vo.PsonOrgVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
public interface TbUserService extends IService<TbUser> {

    /**
     * 获取用户标识
     * @return
     */
    public Long getId();

    /**
     * 根据userId获取角色信息
     * @param userId
     * @return
     */
    public List<TbRoles> getRoleByUserId(Long userId);

    /**
     * 获取从账号信息
     * @param userId
     * @param acctId
     * @return
     */
    public List<ListSlaveAcctVo> getSlaveAcctInfo(Long userId, Long acctId);

    /**
     * 组织下用户查看
     * @return
     */
    public Page<ListUserOrgVo> selectUserOrgPage(PsonOrgVo psonOrgVo);

    }
