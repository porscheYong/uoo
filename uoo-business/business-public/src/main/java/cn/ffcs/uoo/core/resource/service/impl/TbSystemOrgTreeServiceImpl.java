package cn.ffcs.uoo.core.resource.service.impl;

import cn.ffcs.uoo.core.resource.entity.TbSystemOrgTree;
import cn.ffcs.uoo.core.resource.dao.TbSystemOrgTreeMapper;
import cn.ffcs.uoo.core.resource.service.TbSystemOrgTreeService;
import cn.ffcs.uoo.core.resource.vo.SystemOrgTreeRuleVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 接入系统组织树引用配置 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-11-16
 */
@Service
public class TbSystemOrgTreeServiceImpl extends ServiceImpl<TbSystemOrgTreeMapper, TbSystemOrgTree> implements TbSystemOrgTreeService {


    @Override
    public Long getId(){
        return baseMapper.getId();
    }


    @Override
    public void delete(TbSystemOrgTree tbSystemOrgTree){
        tbSystemOrgTree.setStatusCd("1100");
        tbSystemOrgTree.setStatusDate(new Date());
        tbSystemOrgTree.setUpdateDate(new Date());
        tbSystemOrgTree.setUpdateUser(0L);
        updateById(tbSystemOrgTree);
    }

    @Override
    public void add(TbSystemOrgTree tbSystemOrgTree){
        tbSystemOrgTree.setCreateDate(new Date());
        tbSystemOrgTree.setCreateUser(0L);
        tbSystemOrgTree.setStatusCd("1000");
        tbSystemOrgTree.setStatusDate(new Date());
        insert(tbSystemOrgTree);
    }


    @Override
    public void update(TbSystemOrgTree tbSystemOrgTree){
        tbSystemOrgTree.setUpdateDate(new Date());
        tbSystemOrgTree.setUpdateUser(0L);
        tbSystemOrgTree.setStatusDate(new Date());
        updateById(tbSystemOrgTree);
    }
    @Override
    public Page<SystemOrgTreeRuleVo> selectSystemOrgTreeRulePage(SystemOrgTreeRuleVo systemOrgTreeRuleVo){
        Page<SystemOrgTreeRuleVo> page = new Page<SystemOrgTreeRuleVo>(systemOrgTreeRuleVo.getPageNo()==0?1:systemOrgTreeRuleVo.getPageNo(),
                                                systemOrgTreeRuleVo.getPageSize()==0?10:systemOrgTreeRuleVo.getPageSize());
        List<SystemOrgTreeRuleVo> list = baseMapper.selectSystemOrgTreeRulePage(page,systemOrgTreeRuleVo);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<SystemOrgTreeRuleVo> getIndividuationRule(SystemOrgTreeRuleVo systemOrgTreeRuleVo){
        return baseMapper.getIndividuationRule(systemOrgTreeRuleVo);
    }



}
