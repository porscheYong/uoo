package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.organization.dto.OrgType;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgTypeServiceHystrix;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@FeignClient(name = "business-organization",url="http://134.96.253.222:11100",configuration = {PersonnelServiceConfiguration.class},fallback = OrgTypeServiceHystrix.class)
public interface OrgTypeService {


    @RequestMapping(value = "/orgType/getOrgTypeList", method = RequestMethod.GET, headers = {"Content-Type=application/json"})
    public ResponseResult<List<OrgType>> getOrgTypeList(@RequestParam(value = "orgTypeCode",required = false)String orgTypeCode);

    @RequestMapping(value = "/orgType/getOrgTypeTree", method = RequestMethod.GET, headers = {"Content-Type=application/json"})
    public ResponseResult<List<TreeNodeVo>> getOrgTypeTree(@RequestParam(value = "id",required = false)String id,
                                                           @RequestParam(value = "orgTypeCode",required = false)String orgTypeCode);

    @RequestMapping(value = "/orgType/getFullOrgTypeTree", method = RequestMethod.GET, headers = {"Content-Type=application/json"})
    public ResponseResult<List<TreeNodeVo>> getFullOrgTypeTree(@RequestParam(value = "id")String id,
                                                               @RequestParam(value = "orgTypeCode")String orgTypeCode,
                                                               @RequestParam(value = "orgId")String orgId);
}