package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.entity.TbUser;
import cn.ffcs.uoo.core.user.vo.*;
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


    /**
     * -todo----新版本---------------------------------------------------------
     */

    /**
     * 选择用户
     * @param personnelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<ListUserVo> getUserList( Long personnelId, Integer pageNo, Integer pageSize);

    /**
     * 人员信息
     * @param personnelId
     * @return
     */
    public PersonnelInfoVo getPersonnelInfo( Long personnelId);

    /**
     * 主账号 关联 组织
     * @param acctId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<ListAcctOrgVo> getAcctOrg(Long acctId, Integer pageNo, Integer pageSize);

    /**
     * 从账号 关联 主账号组织
     * @param acctOrgVo
     * @return
     */
    public List<ListAcctOrgVo> getSlaveAcctOrg(ListAcctOrgVo acctOrgVo);

    /**
     *  主账号 组织关系
     * @param personnelId
     * @param resourceObjId
     * @return
     */
    public List<ListAcctOrgVo> getAcctOrgByPsnId(Long personnelId, Long resourceObjId);

}
