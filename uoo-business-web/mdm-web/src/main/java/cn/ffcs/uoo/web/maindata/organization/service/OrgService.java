package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.organization.dto.Org;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgRelTypeServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgServiceHystrix;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
//@FeignClient(name = "business-organization", url = "http://134.96.253.222:11100",configuration = {PersonnelServiceConfiguration.class},fallback = OrgServiceHystrix.class)
@FeignClient(value = "business-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgServiceHystrix.class)
public interface OrgService{

    @RequestMapping(value="/org/addOrg",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<TreeNodeVo> addOrg(@RequestBody OrgVo org);

    @RequestMapping(value="/org/updateOrg",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> updateOrg(@RequestBody OrgVo org);

    @RequestMapping(value="/org/getOrg",method = RequestMethod.GET)
    public ResponseResult<OrgVo> getOrg(@RequestParam(value = "orgId",required = false) String orgId,
                                        @RequestParam(value = "orgTreeId",required = false) String orgTreeId);



    @RequestMapping(value="/org/getOrgRelPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<Page<OrgVo>> getOrgRelPage(@RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                     @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                     @RequestParam(value = "orgId",required = false)String orgId,
                                                     @RequestParam(value = "sortField",required = false)String sortField,
                                                     @RequestParam(value = "sortOrder",required = false)String sortOrder,
                                                     @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                     @RequestParam(value = "pageNo",required = false)Integer pageNo);

    @RequestMapping(value="/org/getOrgPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<Page<OrgVo>> getOrgPage(@RequestParam(value = "search",required = false)String search,
                                                  @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                  @RequestParam(value = "pageNo",required = false)Integer pageNo);

    @RequestMapping(value = "/org/getOrgExtByOrgId", method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<HashMap<String,String>> getOrgExtByOrgId(@RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                                   @RequestParam(value = "orgRootId",required = false)String orgRootId ,
                                                                   @RequestParam(value = "orgId",required = false) String orgId);

    @RequestMapping(value = "/org/deleteOrg", method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<String> deleteOrg(@RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                            @RequestParam(value = "orgId",required = false)String orgId,
                                            @RequestParam(value = "supOrgId",required = false)String supOrgId,
                                            @RequestParam(value = "userId",required = false)Long userId);
}
