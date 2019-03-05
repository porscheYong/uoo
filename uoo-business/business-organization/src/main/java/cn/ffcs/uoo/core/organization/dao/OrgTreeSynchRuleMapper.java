package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.entity.OrgTreeSynchRule;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
* 目标组织树A‘上部分组织是另一颗业务树A的组织镜像，那么A’上镜像部分组织基本信息和上下级关系、是否添加下级组织理论上由A业务树进行维护。
该表规则仅为此场景下使用 2019-3-4 约定，详见需求描述 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2019-03-04
 */
public interface OrgTreeSynchRuleMapper extends BaseMapper<OrgTreeSynchRule> {
    Long getId();
    /**
     * 当前组织树引用了哪些树
     * @param orgTreeId
     * @return
     */
    List<OrgTree> getRelTree(Long orgTreeId);
    /**
     * 当前组织树被哪些树引用了
     * @param orgTreeId
     * @return
     */
    List<OrgTree> getBeRelTree(Long orgTreeId);
}
