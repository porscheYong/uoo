package cn.ffcs.uoo.core.resource.service;

import cn.ffcs.uoo.core.resource.entity.TbSystemOrgTree;
import cn.ffcs.uoo.core.resource.vo.SystemOrgTreeRuleVo;
import cn.ffcs.uoo.core.vo.ResponseResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 接入系统组织树引用配置 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-11-16
 */
public interface TbSystemOrgTreeService extends IService<TbSystemOrgTree> {

    public Long getId();

    public void delete(TbSystemOrgTree tbSystemOrgTree);

    public void add(TbSystemOrgTree tbSystemOrgTree);

    public void update(TbSystemOrgTree tbSystemOrgTree);

    public Page<SystemOrgTreeRuleVo> selectSystemOrgTreeRulePage(SystemOrgTreeRuleVo systemOrgTreeRuleVo);

    public List<SystemOrgTreeRuleVo> getIndividuationRule(SystemOrgTreeRuleVo systemOrgTreeRuleVo);
}
