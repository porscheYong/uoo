package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.client.SystemClient;
import cn.ffcs.uoo.core.user.service.CommonSystemService;
import cn.ffcs.uoo.core.user.util.EnumRuleOperator;
import cn.ffcs.uoo.core.user.util.ResponseResult;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.DataRuleRequestVO;
import cn.ffcs.uoo.core.user.vo.SysDataRule;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-wudj
 * @since 2018/12/28
 */
@Service("commonSystemService")
public class CommonSystemServiceImpl implements CommonSystemService {

    @Autowired
    private SystemClient systemClient;

    /**
     * 获取单表权限Wrapper
     * @param rulelist
     * @return
     */
    @Override
    public Wrapper getConfWrapper(List<SysDataRule> rulelist, String tabName){
        Condition condition = Condition.create();
        for(SysDataRule sdr : rulelist){
            if(!StrUtil.isNullOrEmpty(tabName)){
                if(sdr.getTabName().equals(tabName)){
                    if(sdr.getRuleOperator().equals("eq")){
                        condition.eq(sdr.getColName(),sdr.getColValue());
                    }
                    if(sdr.getRuleOperator().equals("gt")){
                        condition.gt(sdr.getColName(),sdr.getColValue());
                    }
                    if(sdr.getRuleOperator().equals("lt")){
                        condition.lt(sdr.getColName(),sdr.getColValue());
                    }
                    if(sdr.getRuleOperator().equals("lte")){
                        condition.le(sdr.getColName(),sdr.getColValue());
                    }
                    if(sdr.getRuleOperator().equals("gte")){
                        condition.ge(sdr.getColName(),sdr.getColValue());
                    }
                    if(sdr.getRuleOperator().equals("ne")){
                        condition.ne(sdr.getColName(),sdr.getColValue());
                    }
                    if(sdr.getRuleOperator().equals("in")){
                        condition.in(sdr.getColName(),sdr.getColValue());
                    }
                    if(sdr.getRuleOperator().equals("notin")){
                        condition.notIn(sdr.getColName(),sdr.getColValue());
                    }
                    if(sdr.getRuleOperator().equals("isnull")){
                        condition.isNull(sdr.getColName());
                    }
                    if(sdr.getRuleOperator().equals("isnotnull")){
                        condition.isNotNull(sdr.getColName());
                    }
                    if(sdr.getRuleOperator().equals("like")){
                        condition.like(sdr.getColName(),sdr.getColValue());
                    }
                }
            }
        }
        return condition;
    }

    /**
     * 获取多表权限sql(接口)
     * @param tabName
     * @param accout
     * @return
     */
    @Override
    public String getSysDataRuleSql(String sName, String tabName, String accout){
        List<String> listNames = new ArrayList<>();
        DataRuleRequestVO requestVo = new DataRuleRequestVO();
        requestVo.setAccout(accout);
        listNames.add(tabName);
        requestVo.setTableNames(listNames);
        ResponseResult<List<SysDataRule>> sysDataRulelist = systemClient.getDataRuleByAccout(requestVo);
        List<SysDataRule> sdrList = new ArrayList<>();
        sdrList = sysDataRulelist.getData();
        String params = "";
        if(sdrList!=null && sdrList.size()>0){
            for(SysDataRule sysDataRule : sdrList){
                params = getCommonJointSql(sName, sysDataRule);
            }
            if(!StrUtil.isNullOrEmpty(params)){
                params = params.substring(0,params.length()-3);
            }
        }
        return params;
    }


    /**
     * 获取指定表权限sql
     * @param map
     * @param sysDataRuleList
     * @return
     */
    @Override
    public String getSysDataRuleSql(Map<String, String> map, List<SysDataRule> sysDataRuleList){
        String params = "";
        if(sysDataRuleList!=null && sysDataRuleList.size()>0){
            for(SysDataRule sysDataRule : sysDataRuleList){
                if(!StrUtil.isNullOrEmpty(map.get(sysDataRule.getTabName()))){
                    params = params + getCommonJointSql(map.get(sysDataRule.getTabName()), sysDataRule);
                }
            }
            if(!StrUtil.isNullOrEmpty(params)){
                params = params.substring(0,params.length()-3);
            }
        }
        return params;
    }

    public String getCommonJointSql(String sName, SysDataRule sysDataRule){
        StringBuffer params = new StringBuffer();
        String oper = EnumRuleOperator.getSqlRuleOper(sysDataRule.getRuleOperator());
        if(sysDataRule.getRuleOperator().equals("eq") ||
                sysDataRule.getRuleOperator().equals("gt") ||
                sysDataRule.getRuleOperator().equals("lt") ||
                sysDataRule.getRuleOperator().equals("lte") ||
                sysDataRule.getRuleOperator().equals("gte") ||
                sysDataRule.getRuleOperator().equals("ne")
        ){
            params.append(" ").append(sName).append(".").append(sysDataRule.getColName()).append(" ").append(oper).append(" ").append(sysDataRule.getColValue()).append(" ");
        }
        if(sysDataRule.getRuleOperator().equals("in") ||
                sysDataRule.getRuleOperator().equals("notin")){
            params.append(" ").append(sName).append(".").append(sysDataRule.getColName()).append(" ").append(oper).append(" (").append(sysDataRule.getColValue()).append(") ");
        }
        if(sysDataRule.getRuleOperator().equals("isnull") ||
                sysDataRule.getRuleOperator().equals("isnotnull")){
            params.append(" ").append(sName).append(".").append(sysDataRule.getColName()).append(" ").append(oper).append(" ");
        }
        if(sysDataRule.getRuleOperator().equals("like")){
            params.append(" ").append(sName).append(".").append(sysDataRule.getColName()).append(" ").append(oper).append(" ").append("'%").append(sysDataRule.getColValue()).append("'% ");
        }
        params.append("AND");
        return params.toString();
    }

    /**
     * 获取系统权限SysDataRule
     * @param tabName
     * @param accout
     * @return
     */
    @Override
    public List<SysDataRule> getSysDataRuleList(String tabName,String accout){
        List<String> listNames = new ArrayList<>();
        DataRuleRequestVO requestVo = new DataRuleRequestVO();
        requestVo.setAccout(accout);
        listNames.add(tabName);
        requestVo.setTableNames(listNames);
        ResponseResult<List<SysDataRule>> sysDataRulelist = systemClient.getDataRuleByAccout(requestVo);
        List<SysDataRule> sdrList = new ArrayList<>();
        sdrList = sysDataRulelist.getData();
        return sdrList;
    }
    /**
     * 获取系统权限SysDataRule
     * @param tabNames
     * @param accout
     * @return
     */
    @Override
    public List<SysDataRule> getSysDataRuleList(List<String> tabNames,String accout){
        DataRuleRequestVO requestVo = new DataRuleRequestVO();
        requestVo.setAccout(accout);
        requestVo.setTableNames(tabNames);
        ResponseResult<List<SysDataRule>> sysDataRulelist = systemClient.getDataRuleByAccout(requestVo);
        List<SysDataRule> sdrList = new ArrayList<>();
        sdrList = sysDataRulelist.getData();
        return sdrList;
    }
    /**
     * 获取拼接后sql
     * @param map
     * @param account
     * @return
     */
    @Override
    public String getSqlJointList(Map<String, String> map, String account){
        List<String> tabNames = new ArrayList<>();
        for (String key : map.keySet()) {
            tabNames.add(key);
        }
        List<SysDataRule> sdrList = getSysDataRuleList(tabNames, account);
        return getSysDataRuleSql(map, sdrList);
    }



}
