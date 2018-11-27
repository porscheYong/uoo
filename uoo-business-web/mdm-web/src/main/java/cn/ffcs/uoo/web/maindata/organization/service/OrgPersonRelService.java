package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.organization.dto.PsonOrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgContactRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgPersonRelServiceHystrix;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @since 2018-10-21
 */
@FeignClient(value = "business-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgPersonRelServiceHystrix.class)
public interface OrgPersonRelService{

    @RequestMapping(value="/orgPersonRel/addOrgPsn",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> addOrgPsn(@RequestBody List<PsonOrgVo> psonOrgVo);

    @RequestMapping(value="/orgPersonRel/updateOrgPsn",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> updateOrgPsn(@RequestBody PsonOrgVo psonOrgVo);

    @RequestMapping(value="/orgPersonRel/deleteOrgPsn",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> deleteOrgPsn(@RequestBody PsonOrgVo psonOrgVo);

    @RequestMapping(value="/orgPersonRel/getPerOrgRelList",method = RequestMethod.GET)
    public ResponseResult<List<PsonOrgVo>> getPerOrgRelList(@RequestParam(value = "personnelId",required = false)String personnelId);

    @RequestMapping(value="/orgPersonRel/getPerOrgRelPage",method = RequestMethod.GET)
    public ResponseResult<Page<PsonOrgVo>> getPerOrgRelPage(@RequestParam(value = "orgId",required = false)String orgId,
                                                            @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                            @RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                            @RequestParam(value = "personId",required = false)String personId,
                                                            @RequestParam(value = "search",required = false)String search,
                                                            @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                            @RequestParam(value = "pageNo",required = false)Integer pageNo);

    @RequestMapping(value="/orgPersonRel/getUserOrgRelPage",method = RequestMethod.GET)
    public ResponseResult<Page<PsonOrgVo>> getUserOrgRelPage(@RequestParam(value = "orgId",required = false)String orgId,
                                                             @RequestParam(value = "search",required = false)String search,
                                                             @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                             @RequestParam(value = "pageNo",required = false)Integer pageNo);


}
