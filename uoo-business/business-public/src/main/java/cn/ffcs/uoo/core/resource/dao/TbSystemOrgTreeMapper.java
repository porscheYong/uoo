package cn.ffcs.uoo.core.resource.dao;

import cn.ffcs.uoo.core.resource.entity.TbSystemOrgTree;
import cn.ffcs.uoo.core.resource.vo.SystemOrgTreeRuleVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 接入系统组织树引用配置 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-11-16
 */
public interface TbSystemOrgTreeMapper extends BaseMapper<TbSystemOrgTree> {


    public Long getId();

    public List<SystemOrgTreeRuleVo> selectSystemOrgTreeRulePage(Pagination page, @Param("systemOrgTreeRuleVo")SystemOrgTreeRuleVo systemOrgTreeRuleVo);

    public List<SystemOrgTreeRuleVo> getIndividuationRule(@Param("systemOrgTreeRuleVo")SystemOrgTreeRuleVo systemOrgTreeRuleVo);

}
