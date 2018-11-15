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

    /**
     * 保存
     * @param acctOrgVoList
     * @return
     */
    public Object saveAcctOrg(List<ListAcctOrgVo> acctOrgVoList);

    /**
     * 删除 主账号和组织关系
     * @param acctId
     * @return
     */
    public Object removeAcctOrg(Long acctId, Long orgId);

}
