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
    public ResponseResult<Void> addOrg(@RequestBody Org org);

    @RequestMapping(value="/org/updateOrg",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> updateOrg(@RequestBody Org org);

    @RequestMapping(value="/org/getOrg",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<Org> getOrg(@RequestParam(value = "orgId",required = false) String orgId);



    @RequestMapping(value="/org/getOrgRelPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<Page<OrgVo>> getOrgRelPage(@RequestBody OrgVo orgVo);

    @RequestMapping(value="/org/getOrgPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<Page<OrgVo>> getOrgPage(@RequestBody OrgVo orgVo);

    @RequestMapping(value = "/getOrgExtByOrgId", method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<HashMap<String,String>> getOrgExtByOrgId(@RequestParam(value = "orgRootId",required = false)String orgRootId ,
                                                                   @RequestParam(value = "orgId",required = false) String orgId);

}
