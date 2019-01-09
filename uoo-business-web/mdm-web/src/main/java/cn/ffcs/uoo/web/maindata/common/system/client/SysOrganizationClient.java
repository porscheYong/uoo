package cn.ffcs.uoo.web.maindata.common.system.client;



import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysOperationLogClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysOrganizationClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.vo.*;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@FeignClient(value = "common-system",configuration = {FeignClientProperties.FeignClientConfiguration.class},fallback = SysOrganizationClientHystrix.class)
public interface SysOrganizationClient {

    @RequestMapping(value="/sysOrganization/getOrgRelTree",method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getOrgRelTree(@RequestParam(value = "id",required = false)String id,
                                                          @RequestParam(value = "isSync",required = false)boolean isSync,
                                                          @RequestParam(value = "userId",required = false)Long userId,
                                                          @RequestParam(value = "accout",required = false)String accout);


    @RequestMapping(value="/sysOrganization/getFuzzyOrgRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysOrganizationVo>> getFuzzyOrgRelPage(@RequestParam(value = "search",required = false)String search,
                                                                      @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                                      @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                                      @RequestParam(value = "userId",required = false)Long userId,
                                                                      @RequestParam(value = "accout",required = false)String accout);



    @RequestMapping(value = "/sysOrganization/getRestructOrgRelTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(@RequestParam(value = "id",required = false)String id,
                                                                  @RequestParam(value = "userId",required = false)Long userId,
                                                                  @RequestParam(value = "accout",required = false)String accout);


    @RequestMapping(value = "/sysOrganization/getOrgRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysOrganizationVo>> getOrgRelPage(@RequestParam(value = "id",required = false)String id,
                                                                 @RequestParam(value = "search",required = false)String search,
                                                                 @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                                 @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                                 @RequestParam(value = "isSearchlower",required = false)String isSearchlower,
                                                                 @RequestParam(value = "userId",required = false)Long userId,
                                                                 @RequestParam(value = "accout",required = false)String accout);



    @RequestMapping(value = "/sysOrganization/addOrg", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<TreeNodeVo> addOrg(@RequestBody SysOrganizationVo vo);



    @RequestMapping(value = "/sysOrganization/updateOrg", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> updateOrg(@RequestBody SysOrganizationVo vo);


    @RequestMapping(value = "/sysOrganization/getOrg", method = RequestMethod.GET)
    public ResponseResult<SysOrganizationVo> getOrg(@RequestParam(value = "id",required = false)String id,
                                                    @RequestParam(value = "userId",required = false)Long userId,
                                                    @RequestParam(value = "accout",required = false)String accout);

    @RequestMapping(value = "/sysOrganization/deleteOrg", method = RequestMethod.GET)
    public ResponseResult<String> deleteOrg(@RequestParam(value = "id",required = false)String id,
                                            @RequestParam(value = "userId",required = false)Long userId,
                                            @RequestParam(value = "accout",required = false)String accout);

    @RequestMapping(value = "/sysOrganization/getOrgPositionList", method = RequestMethod.GET)
    public ResponseResult<List<SysPositionVo>> getOrgPositionList(@RequestParam(value = "id",required = false)String id,
                                                                  @RequestParam(value = "userId",required = false)Long userId,
                                                                  @RequestParam(value = "accout",required = false)String accout);
    @RequestMapping(value = "/sysOrganization/getOrgUserPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysUserVo>> getOrgUserPage(@RequestParam(value = "id",required = false)String id,
                                                          @RequestParam(value = "search",required = false)String search,
                                                          @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                          @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                          @RequestParam(value = "isSearchlower",required = false)String isSearchlower,
                                                          @RequestParam(value = "userId",required = false)Long userId,
                                                          @RequestParam(value = "accout",required = false)String accout);
}

