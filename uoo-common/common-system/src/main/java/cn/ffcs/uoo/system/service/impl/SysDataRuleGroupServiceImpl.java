package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysDataRuleGroup;
import cn.ffcs.uoo.system.dao.SysDataRuleGroupMapper;
import cn.ffcs.uoo.system.service.ISysDataRuleGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-28
 */
@Service
public class SysDataRuleGroupServiceImpl extends ServiceImpl<SysDataRuleGroupMapper, SysDataRuleGroup> implements ISysDataRuleGroupService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public List<SysDataRuleGroup> listByAccout(String accout, Long treeId, List<String> tableNames) {
        return baseMapper.listByAccout(accout, treeId, tableNames);
    }

    @Override
    public List<SysDataRuleGroup> listByPermCode(String permCode) {
        return baseMapper.listByPermCode(permCode);
    }
}
