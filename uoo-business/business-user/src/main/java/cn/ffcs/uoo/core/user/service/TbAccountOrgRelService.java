package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbAccountOrgRel;
import cn.ffcs.uoo.core.user.vo.AccountOrgRelVo;
import cn.ffcs.uoo.core.user.vo.ListAcctOrgVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wudj
 * @since 2018-11-08
 */
public interface TbAccountOrgRelService extends IService<TbAccountOrgRel> {

    public Long getId();

    /**
     * 保存
     * @param acctOrgVoList
     * @param acctId
     * @param userId
     * @return
     */
    public Object saveAcctOrg(List<ListAcctOrgVo> acctOrgVoList, Long acctId, Long userId);

    /**
     * 删除 主账号和组织关系
     * @param personnelId
     * @param acctId
     * @param orgId
     * @param orgTreeId
     * @param userId
     * @return
     */
    public Object removeAcctOrg(Long personnelId, Long acctId, Long orgId, Long orgTreeId, Long userId);

    /**
     * 新增 主账号和组织关系
     * @param tbAccountOrgRel
     * @return
     */
    public Object addAcctOrg(TbAccountOrgRel tbAccountOrgRel);

    /**
     * 更新 主账号和组织关系
     * @param tbAccountOrgRel
     * @return
     */
    public Object updateAcctOrg(AccountOrgRelVo tbAccountOrgRel);

    /**
     * 判断是否新增 主账号和组织关系
     * @param tbAccountOrgRel
     * @return
     */
    public TbAccountOrgRel addOrUpdateAcctOrg(AccountOrgRelVo tbAccountOrgRel);

}
