package cn.ffcs.uoo.core.organization.service;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-12-26
 */

import cn.ffcs.uoo.core.organization.vo.SysDataRule;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/12/26
 */
public interface CommonSystemService {

    public Wrapper getConfWrapper(List<SysDataRule> rulelist, String tabName);

    public String getSysDataRuleSql(String tabName,String accout);

    public List<SysDataRule> getSysDataRuleList(String tabName,String accout);

    public List<SysDataRule> getSysDataRuleList(List<String> tabNames,String accout);

    public boolean isOrgTreeAutho(String orgTreeId,List<SysDataRule> rulelist);

    public String getSysDataRuleParams(String tabName,String tabColName,List<SysDataRule> sysDataRuleList);

    public String getSysDataRuleSql(String tabName, List<SysDataRule> sysDataRuleList);

    public String getSysDataRuleSql(String tabAliasName,String tabName, List<SysDataRule> sysDataRuleList);

}
