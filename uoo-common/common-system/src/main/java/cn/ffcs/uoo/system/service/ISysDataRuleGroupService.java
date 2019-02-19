package cn.ffcs.uoo.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.system.entity.SysDataRuleGroup;

/**
 * <p>

 * </p>
 *
 * @author zengxsh
 * @since 2019-01-28
 */
public interface ISysDataRuleGroupService extends IService<SysDataRuleGroup> {
    public Long getId();
    List<SysDataRuleGroup> listByPermCode(String permCode);
    List<SysDataRuleGroup> listByAccout(String accout,Long treeId,List<String> tableNames);
}
