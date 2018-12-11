package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.organization.dto.OrgTree;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgTreeServiceHystrix;
import common.config.PersonnelServiceConfiguration;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/21
 */
//@FeignClient(name = "business-organization", url = "http://134.96.253.222:11100",configuration = {PersonnelServiceConfiguration.class},fallback = OrgTreeServiceHystrix.class)
@FeignClient(value = "business-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgTreeServiceHystrix.class)
public interface OrgTreeService {

    @RequestMapping(value="/orgTree/addOrgTree",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> addOrgTree(@RequestBody OrgTree orgTree);


    @RequestMapping(value="/orgTree/updateOrgTree",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> updateOrgTree(@RequestBody OrgTree orgTree);

    @RequestMapping(value="/orgTree/getOrgTreeList",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<List<OrgTree>> getOrgTreeList(@RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                        @RequestParam(value = "orgRootId",required = false)String orgRootId);

    @RequestMapping(value="/orgTree/getOrgTree",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<OrgTree> getOrgTree(@RequestParam(value = "orgTreeId",required = false)String orgTreeId);

}
