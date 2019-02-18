package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.Api.service.SystemService;
import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.service.CommonSystemService;
import cn.ffcs.uoo.core.organization.service.OrgService;
import cn.ffcs.uoo.core.organization.service.OrgTreeService;
import cn.ffcs.uoo.core.organization.util.EnumRuleOperator;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.DataRuleGroupVO;
import cn.ffcs.uoo.core.organization.vo.DataRuleRequestVO;
import cn.ffcs.uoo.core.organization.vo.DataRuleResponseVO;
import cn.ffcs.uoo.core.organization.vo.SysDataRule;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import net.sf.jsqlparser.statement.replace.Replace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
    @Autowired
    private OrgService orgService;

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
        sdrList = sysDataRulelist.getData();
        String params = "";
        if(sdrList!=null && sdrList.size()>0){
            for(SysDataRule sysDataRule : sdrList){
                if(sysDataRule.getTabName().equals(tabName)){
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
            }
            if(!StrUtil.isNullOrEmpty(params)){
                params = params.substring(0,params.length()-3);
            }
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
                if(sysDataRule.getTabName().equals(tabName)){
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
            }
            if(!StrUtil.isNullOrEmpty(params)){
                params = params.substring(0,params.length()-3);
            }
        }
        return params;
    }


    /**
     * 获取指定表权限参数
     * @param tabName
     * @param sysDataRuleList
     * @return
     */
    @Override
    public String getSysDataRuleParams(String tabName,String tabColName,List<SysDataRule> sysDataRuleList){
        String params = "";
        if(sysDataRuleList!=null && sysDataRuleList.size()>0){
            for(SysDataRule sysDataRule : sysDataRuleList){
                if(sysDataRule.getTabName().equals(tabName) && sysDataRule.getColName().equals(tabColName)){
                    return sysDataRule.getColValue();
                }
            }
        }
        return params;
    }

    /**
     * 获取指定表权限sql（表别名）
     * @param tabAliasName
     * @param tabName
     * @param sysDataRuleList
     * @return
     */
    @Override
    public String getSysDataRuleSql(String tabAliasName,String tabName, List<SysDataRule> sysDataRuleList){
        String params = "";
        if(sysDataRuleList!=null && sysDataRuleList.size()>0){
            for(SysDataRule sysDataRule : sysDataRuleList){
                if(sysDataRule.getTabName().equals(tabName)){
                    String oper = EnumRuleOperator.getSqlRuleOper(sysDataRule.getRuleOperator());
                    if(sysDataRule.getRuleOperator().equals("eq") ||
                            sysDataRule.getRuleOperator().equals("gt") ||
                            sysDataRule.getRuleOperator().equals("lt") ||
                            sysDataRule.getRuleOperator().equals("lte") ||
                            sysDataRule.getRuleOperator().equals("gte") ||
                            sysDataRule.getRuleOperator().equals("ne")
                            ){
                        params+=" "+tabAliasName+"."+sysDataRule.getColName()+" "+oper+" "+sysDataRule.getColValue()+" ";
                    }
                    if(sysDataRule.getRuleOperator().equals("in") ||
                            sysDataRule.getRuleOperator().equals("notin")){
                        params+=" "+tabAliasName+"."+sysDataRule.getColName()+" "+oper+" ("+sysDataRule.getColValue()+") ";
                    }
                    if(sysDataRule.getRuleOperator().equals("isnull") ||
                            sysDataRule.getRuleOperator().equals("isnotnull")){
                        params+=" "+tabAliasName+"."+sysDataRule.getColName()+" "+oper+" ";
                    }
                    if(sysDataRule.getRuleOperator().equals("like")){
                        params+=" "+tabAliasName+"."+sysDataRule.getColName()+" "+oper+" '%"+sysDataRule.getColValue()+"%' ";
                    }
                    params+="AND";
                }
            }
            if(!StrUtil.isNullOrEmpty(params)){
                params = params.substring(0,params.length()-3);
            }
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
     * 获取系统权限SysDataRule 根据组织系统
     * @param tabNames
     * @param accout
     * @param orgTreeId
     * @return
     */
    @Override
    public List<SysDataRule> getSysDataRuleList(List<String> tabNames,String accout,String orgTreeId){
        DataRuleRequestVO requestVo = new DataRuleRequestVO();
        requestVo.setAccout(accout);
        requestVo.setTableNames(tabNames);
        requestVo.setTreeId(new Long(orgTreeId));
        ResponseResult<DataRuleResponseVO> sysDataRulelist = systemService.getDataRuleByAccout2(requestVo);
        List<SysDataRule> sysList = new ArrayList<>();
        if(sysDataRulelist.getData()!=null && sysDataRulelist.getData().getGroups()!=null){
            for(DataRuleGroupVO vo : sysDataRulelist.getData().getGroups()){
                for(SysDataRule svo : vo.getDataRules()){
                    sysList.add(svo);
                }

            }
        }
        return sysList;
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
            List<OrgTree> orgTreeList = orgTreeService.selectList(wrapper);
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

    /**
     *
     * @param orgIds
     * @return
     */
    @Override
    public String getOrgOrgTreeRelSql(String orgIds,String orgTreeId){

        String orgGroup[] = orgIds.split("\\|");
        List<String> OrgIdlist = new ArrayList();
        for(String orgG : orgGroup){
            String[] orgIdsz = orgG.substring(1,orgG.length()-1).split(",");
            for(String orgId:orgIdsz){
                OrgIdlist.add(orgId);
            }
        }
        HashSet h = new HashSet(OrgIdlist);
        OrgIdlist.clear();
        OrgIdlist.addAll(h);
        String orgOrgTreeRelParams = "(orgOrgTreeRel.ORG_ID in (";
        for(String orgId : OrgIdlist){
            orgOrgTreeRelParams+=orgId+",";
        }
        orgOrgTreeRelParams = orgOrgTreeRelParams.substring(0,orgOrgTreeRelParams.length()-1);
        orgOrgTreeRelParams+=")";
        for(String og : orgGroup){
            // TODO: 2019/2/14 截取最新的数据
            String[] orgIdsz = og.substring(1,og.length()-1).split(",");
            String odd = orgIdsz[orgIdsz.length-1];
            odd = orgService.getFullOrgIdList(orgTreeId,odd,",");
            orgOrgTreeRelParams+=" or orgOrgTreeRel.ORG_BIZ_FULL_ID like ',"+odd+",%'";

            //orgOrgTreeRelParams+=" or orgOrgTreeRel.ORG_BIZ_FULL_ID like '"+og+"%'";
        }
        orgOrgTreeRelParams+=")";
        return orgOrgTreeRelParams;
    }

    /**
     *
     * @param orgId
     * @param orgOrgTreeRelParams
     * @return
     */
    @Override
    public String getQueryPerPath(String orgId,String orgOrgTreeRelParams,String orgTreeId){
        String orgOrgTreeRelSql = "";
        if(StrUtil.isNullOrEmpty(orgOrgTreeRelParams)){
            String orgdd = orgService.getFullOrgIdList(orgTreeId,orgId,",");
            orgOrgTreeRelSql = " tbOrgOrgTreeRel.ORG_BIZ_FULL_ID like ',"+orgdd+",%'";
            return orgOrgTreeRelSql;
        }
        String orgGroup[] = orgOrgTreeRelParams.split("\\|");

        List<String> likeSql = new ArrayList<>();
        for(String orgG : orgGroup){
            // TODO: 2019/2/14
            String[] orgIdsz = orgG.substring(1,orgG.length()-1).split(",");
            String odd = orgIdsz[orgIdsz.length-1];
            odd = orgService.getFullOrgIdList(orgTreeId,odd,",");
            if(!StrUtil.isNullOrEmpty(odd)){
                odd = ","+odd+",";
            }
            if(!StrUtil.isNullOrEmpty(odd) && (odd.contains(","+orgId+",") || StrUtil.isNullOrEmpty(orgId))){
                likeSql.add(odd);
            }

//            if(orgG.contains(","+orgId+",")){
//                likeSql.add(orgG);
//            }
        }
        if(likeSql!=null && likeSql.size()>0){
            for(String s1 : likeSql){
                orgOrgTreeRelSql=" tbOrgOrgTreeRel.ORG_BIZ_FULL_ID like '"+s1+"%' " + "or";
            }
            orgOrgTreeRelSql = orgOrgTreeRelSql.substring(0,orgOrgTreeRelSql.length()-2);
            orgOrgTreeRelSql = "("+orgOrgTreeRelSql+")";
        }else{
            orgOrgTreeRelSql = orgService.getFullOrgIdList(orgTreeId,orgId,",");
            orgOrgTreeRelSql=" tbOrgOrgTreeRel.ORG_BIZ_FULL_ID like ',"+orgOrgTreeRelSql+",%'";
        }
        return orgOrgTreeRelSql;
    }

    /**
     * 查询组织是否有权限
     * @param orgId
     * @param orgOrgTreeRelParams
     * @return
     */
    @Override
    public boolean isOrgQueryAuth(String orgId,String orgOrgTreeRelParams){
        String orgGroup[] = orgOrgTreeRelParams.split("\\|");
        for(String og : orgGroup){
            String[] orgIdsz = og.substring(1,og.length()-1).split(",");
            for(int i=0;i<orgIdsz.length-1;i++){
                if(orgId.equals(orgIdsz[i])){
                    return false;
                }
            }
        }
        return true;
    }

}
