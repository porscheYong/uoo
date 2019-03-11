package cn.ffcs.uoo.core.organization.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.entity.OrgTreeSynchRule;
import cn.ffcs.uoo.core.organization.entity.OrgUpdateCheckResult;
import cn.ffcs.uoo.core.organization.entity.OrgUpdateCheckResult.OrgOperateType;
import cn.ffcs.uoo.core.organization.vo.OrgTreeSynchRuleVO;

/**
 * <p>

 * </p>
 *
 * @author zengxsh
 * @since 2019-03-04
 */
public interface IOrgTreeSynchRuleService extends IService<OrgTreeSynchRule> {
    public Long getId();
    /**
     * 根据树id查找引用的树
     * @param orgTreeId
     * @return
     */
    public List<OrgTree> getRelTree(Long orgTreeId);
    List<OrgTreeSynchRuleVO> listByToOrgTreeId(Long orgTreeId);
    public OrgUpdateCheckResult check(OrgOperateType orgOperateType,Long orgId,Long orgTreeId);
}
