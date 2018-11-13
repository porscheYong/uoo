package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgTree;
import cn.ffcs.uoo.web.maindata.organization.dto.SolrReqVo;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgTreeServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgTypeServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.SolrServiceHystrix;
import common.config.PersonnelServiceConfiguration;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

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
    public ResponseResult<String> addOrgTree(OrgTree orgTree);


    @RequestMapping(value="/orgTree/updateOrgTree",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> updateOrgTree(OrgTree orgTree);

    @RequestMapping(value="/orgTree/getOrgTreeList",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<List<OrgTree>> getOrgTreeList(OrgTree orgTree);


}
