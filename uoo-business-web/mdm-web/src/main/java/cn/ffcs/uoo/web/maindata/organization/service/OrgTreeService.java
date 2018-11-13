package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.organization.dto.OrgTree;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgTreeServiceHystrix;
import common.config.PersonnelServiceConfiguration;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/21
 */
@FeignClient(value = "uoo-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgTreeServiceHystrix.class)
public interface OrgTreeService {

    @RequestMapping(value="/orgTree/addOrgTree",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> addOrgTree(@RequestBody OrgTree orgTree);


    @RequestMapping(value="/orgTree/updateOrgTree",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> updateOrgTree(@RequestBody OrgTree orgTree);

    @RequestMapping(value="/orgTree/getOrgTreeList",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<List<OrgTree>> getOrgTreeList(@RequestBody OrgTree orgTree);


}
