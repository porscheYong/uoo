package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbSystemOrgTree;
import cn.ffcs.uoo.message.server.vo.OrgTreeRuleVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbSystemOrgTreeMapper extends BaseMapper<TbSystemOrgTree> {
    //获取配置和规则
    OrgTreeRuleVo getTbSystemOrgTreeAndTbSystemIndividuationRule(@Param("systemId") Long systemId);

    //批量获取配置和规则
    List<OrgTreeRuleVo> getTbSystemOrgTreeAndTbSystemIndividuationRules(@Param("ids") List<Long> ids);

    int test();
}