package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbAccountOrgRel;
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
     * @return
     */
    public Object saveAcctOrg(List<ListAcctOrgVo> acctOrgVoList, Long acctId);

    /**
     * 删除 主账号和组织关系
     * @param personnelId
     * @param acctId
     * @param orgId
     * @param orgTreeId
     * @return
     */
    public Object removeAcctOrg(Long personnelId, Long acctId, Long orgId, Long orgTreeId);

    /**
     * 新增 主账号和组织关系
     * @param tbAccountOrgRel
     * @return
     */
    public Object addAcctOrg(TbAccountOrgRel tbAccountOrgRel);

}
