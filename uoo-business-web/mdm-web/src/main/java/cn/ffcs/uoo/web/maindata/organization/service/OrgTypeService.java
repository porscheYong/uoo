package cn.ffcs.uoo.web.maindata.organization.service;

import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgType;
import cn.ffcs.uoo.web.maindata.organization.dto.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgTypeServiceHystrix;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@FeignClient(value = "uoo-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgTypeServiceHystrix.class)
public interface OrgTypeService {


    @RequestMapping(value = "/orgType/getOrgTypeList", method = RequestMethod.GET, headers = {"Content-Type=application/json"})
    public ResponseResult<List<OrgType>> getOrgTypeList(String orgTypeCode);

    @RequestMapping(value = "/orgType/getOrgTypeTree", method = RequestMethod.GET, headers = {"Content-Type=application/json"})
    public ResponseResult<List<TreeNodeVo>> getOrgTypeTree(String id, String orgTypeCode);

    @RequestMapping(value = "/orgType/getFullOrgTypeTree", method = RequestMethod.GET, headers = {"Content-Type=application/json"})
    public ResponseResult<List<TreeNodeVo>> getFullOrgTypeTree(String id,String orgTypeCode,String orgId);
}