package cn.ffcs.uoo.web.maindata.organization.service;



import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.ffcs.uoo.web.maindata.organization.dto.OrgTree;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgTreeSynchRule;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgTreeSynchRuleVO;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgTreeSynchRuleServiceHystrix;
import common.config.PersonnelServiceConfiguration;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2019-01-25
 */
@FeignClient(value = "business-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgTreeSynchRuleServiceHystrix.class)
public interface OrgTreeSynchRuleService { 
    
    @RequestMapping(value="/org/orgTreeSynchRule/get",method = RequestMethod.GET)
    public ResponseResult<OrgTreeSynchRule> get(@RequestParam("id")Long id);
     
    @RequestMapping(value="/org/orgTreeSynchRule/getTree",method = RequestMethod.GET)
    public ResponseResult<List<OrgTree>> getTree(@RequestParam("id")Long id);
    
    @RequestMapping(value="/org/orgTreeSynchRule/list",method = RequestMethod.GET)
    public ResponseResult<List<OrgTreeSynchRuleVO>> list(@RequestParam("orgTreeId")Long orgTreeId);
     
    @RequestMapping(value="/org/orgTreeSynchRule/add",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> add(@RequestBody OrgTreeSynchRule orgTreeSynchRule);
     
    @RequestMapping(value="/org/orgTreeSynchRule/update",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> update(@RequestBody OrgTreeSynchRule orgTreeSynchRule);
    @RequestMapping(value="/org/orgTreeSynchRule/update",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> delete(@RequestBody OrgTreeSynchRule orgTreeSynchRule);
}

