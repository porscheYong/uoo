package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.organization.dto.Org;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
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
@FeignClient(value = "business-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgServiceHystrix.class)
public interface OrgService{

    @RequestMapping(value="/org/addOrg",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> addOrg(@RequestBody OrgVo org);

    @RequestMapping(value="/org/updateOrg",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> updateOrg(@RequestBody OrgVo org);

    @RequestMapping(value="/org/getOrg",method = RequestMethod.GET)
    public ResponseResult<OrgVo> getOrg(@RequestParam(value = "orgId",required = false) String orgId);



    @RequestMapping(value="/org/getOrgRelPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<Page<OrgVo>> getOrgRelPage(@RequestParam(value = "orgRootId",required = false)Integer orgRootId,
                                                     @RequestParam(value = "orgId",required = false)Integer orgId,
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

}
