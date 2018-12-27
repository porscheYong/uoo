package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.Api.service.SystemService;
import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.service.CommonSystemService;
import cn.ffcs.uoo.core.organization.service.OrgTreeService;
import cn.ffcs.uoo.core.organization.util.EnumRuleOperator;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.DataRuleRequestVO;
import cn.ffcs.uoo.core.organization.vo.SysDataRule;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import net.sf.jsqlparser.statement.replace.Replace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/12/26
 */
@Service("commonSystemService")
public class CommonSystemServiceImpl implements CommonSystemService {

    @Autowired
    private OrgTreeService orgTreeService;
    @Autowired
    private SystemService systemService;

    /**
     * 获取单表权限Wrapper
     * @param rulelist
     * @return
     */
    @Override
    public Wrapper getConfWrapper(List<SysDataRule> rulelist,String tabName){
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
    public String getSysDataRuleSql(String tabName, String accout){
        List<String> listNames = new ArrayList<>();
        DataRuleRequestVO requestVo = new DataRuleRequestVO();
        requestVo.setAccout(accout);
        listNames.add(tabName);
        requestVo.setTableNames(listNames);
        ResponseResult<List<SysDataRule>> sysDataRulelist = systemService.getDataRuleByAccout(requestVo);
        List<SysDataRule> sdrList = new ArrayList<>();
        String params = "";
        if(sdrList!=null && sdrList.size()>0){
            for(SysDataRule sysDataRule : sdrList){
                String oper = EnumRuleOperator.getSqlRuleOper(sysDataRule.getRuleOperator());
                if(sysDataRule.getRuleOperator().equals("eq") ||
                        sysDataRule.getRuleOperator().equals("gt") ||
                        sysDataRule.getRuleOperator().equals("lt") ||
                        sysDataRule.getRuleOperator().equals("lte") ||
                        sysDataRule.getRuleOperator().equals("gte") ||
                        sysDataRule.getRuleOperator().equals("ne")
                        ){
                    params+=" "+sysDataRule.getColName()+" "+oper+" "+sysDataRule.getColValue()+" ";
                }
                if(sysDataRule.getRuleOperator().equals("in") ||
                        sysDataRule.getRuleOperator().equals("notin")){
                    params+=" "+sysDataRule.getColName()+" "+oper+" ("+sysDataRule.getColValue()+") ";
                }
                if(sysDataRule.getRuleOperator().equals("isnull") ||
                        sysDataRule.getRuleOperator().equals("isnotnull")){
                    params+=" "+sysDataRule.getColName()+" "+oper+" ";
                }
                if(sysDataRule.getRuleOperator().equals("like")){
                    params+=" "+sysDataRule.getColName()+" "+oper+" '%"+sysDataRule.getColValue()+"%' ";
                }
                params+="AND";
            }
            params = params.substring(0,params.length()-3);
        }
        return params;
    }


    /**
     * 获取指定表权限sql
     * @param tabName
     * @param sysDataRuleList
     * @return
     */
    @Override
    public String getSysDataRuleSql(String tabName, List<SysDataRule> sysDataRuleList){
        String params = "";
        if(sysDataRuleList!=null && sysDataRuleList.size()>0){
            for(SysDataRule sysDataRule : sysDataRuleList){
                String oper = EnumRuleOperator.getSqlRuleOper(sysDataRule.getRuleOperator());
                if(sysDataRule.getRuleOperator().equals("eq") ||
                        sysDataRule.getRuleOperator().equals("gt") ||
                        sysDataRule.getRuleOperator().equals("lt") ||
                        sysDataRule.getRuleOperator().equals("lte") ||
                        sysDataRule.getRuleOperator().equals("gte") ||
                        sysDataRule.getRuleOperator().equals("ne")
                        ){
                    params+=" "+sysDataRule.getColName()+" "+oper+" "+sysDataRule.getColValue()+" ";
                }
                if(sysDataRule.getRuleOperator().equals("in") ||
                        sysDataRule.getRuleOperator().equals("notin")){
                    params+=" "+sysDataRule.getColName()+" "+oper+" ("+sysDataRule.getColValue()+") ";
                }
                if(sysDataRule.getRuleOperator().equals("isnull") ||
                        sysDataRule.getRuleOperator().equals("isnotnull")){
                    params+=" "+sysDataRule.getColName()+" "+oper+" ";
                }
                if(sysDataRule.getRuleOperator().equals("like")){
                    params+=" "+sysDataRule.getColName()+" "+oper+" '%"+sysDataRule.getColValue()+"%' ";
                }
                params+="AND";
            }
            params = params.substring(0,params.length()-3);
        }
        return params;
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
        ResponseResult<List<SysDataRule>> sysDataRulelist = systemService.getDataRuleByAccout(requestVo);
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
        ResponseResult<List<SysDataRule>> sysDataRulelist = systemService.getDataRuleByAccout(requestVo);
        List<SysDataRule> sdrList = new ArrayList<>();
        sdrList = sysDataRulelist.getData();
        return sdrList;
    }

    /**
     * 是否有查询树的权限
     * @param orgTreeId
     * @param rulelist
     * @return
     */
    @Override
    public boolean isOrgTreeAutho(String orgTreeId,List<SysDataRule> rulelist){
        boolean isRet = false;
        boolean isExits = false;
        for(SysDataRule sdr : rulelist){
            if(sdr.getTabName().equals("TB_ORG_TREE")){
                isExits = true;
                break;
            }
        }
        if(isExits){
            Wrapper wrapper = getConfWrapper(rulelist,"TB_ORG_TREE");
            List<OrgTree> orgTreeList = orgTreeService.selectObjs(wrapper);
            if(orgTreeList!=null && orgTreeList.size()>0){
                for(OrgTree ot : orgTreeList){
                    if(ot.getOrgTreeId().toString().equals(orgTreeId)){
                        return true;
                    }
                }
            }
        }else{
            return true;
        }
        return isRet;
    }

}
