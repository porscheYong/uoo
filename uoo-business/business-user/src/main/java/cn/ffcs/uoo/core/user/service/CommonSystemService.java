package cn.ffcs.uoo.core.user.service;/**
 * @description:
 * @author: wudj
 * @date: 2018-12-28
 */

import cn.ffcs.uoo.core.user.vo.SysDataRule;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.List;
import java.util.Map;

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

    public String getSysDataRuleSql(String sName, String tabName, String accout);

    public List<SysDataRule> getSysDataRuleList(String tabName, String accout);

    public List<SysDataRule> getSysDataRuleList(List<String> tabNames, String accout);

    public String getSysDataRuleSql(Map<String, String> map, List<SysDataRule> sysDataRuleList);

    public String getSqlJointList(Map<String, String> map, String account);

}
