package cn.ffcs.uoo.web.maindata.organization.service;

import cn.ffcs.uoo.web.maindata.organization.dto.*;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgPersonRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgServiceHystrix;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
//@FeignClient(name = "business-organization", url = "http://134.96.253.222:11100",configuration = {PersonnelServiceConfiguration.class},fallback = OrgRelServiceHystrix.class)
@FeignClient(value = "business-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgRelServiceHystrix.class)
public interface OrgRelService {

   @RequestMapping(value="/orgRel/getOrgRelTree",method = RequestMethod.GET)
   public ResponseResult<List<TreeNodeVo>> getOrgRelTree(@RequestParam(value = "id",required = false)String id,
                                                         @RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                         @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                         @RequestParam(value = "refCode",required = false)String refCode,
                                                         @RequestParam(value = "isOpen",required = false)boolean isOpen,
                                                         @RequestParam(value = "isAsync",required = false)boolean isAsync,
                                                         @RequestParam(value = "isRoot",required = false)boolean isRoot,
                                                         @RequestParam(value = "userId",required = false)Long userId,
                                                         @RequestParam(value = "accout",required = false)String accout);

   @RequestMapping(value="/orgRel/getRestructOrgRelTree",method = RequestMethod.GET)
   public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(@RequestParam(value = "orgId",required = false)String orgId,
                                                                 @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                                 @RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                                 @RequestParam(value = "isFull",required = false)boolean isFull);


   @RequestMapping(value="/orgRel/getTarOrgRelTreeAndLv",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<List<TreeNodeVo>> getTarOrgRelTreeAndLv(@RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                                 @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                                 @RequestParam(value = "lv",required = false)String lv,
                                                                 @RequestParam(value = "curOrgid",required = false)String curOrgid,
                                                                 @RequestParam(value = "isFull",required = false)boolean isFull);

   @RequestMapping(value="/orgRel/addOrgRel",method = RequestMethod.POST,headers={"Content-Type=application/json"})
   public ResponseResult<TreeNodeVo> addOrgRel(@RequestBody OrgVo org);

   @RequestMapping(value="/orgRel/getFuzzyOrgRelPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<Page<OrgVo>> getFuzzyOrgRelPage(@RequestParam(value = "search",required = false)String search,
                                                         @RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                         @RequestParam(value = "orgTreeId",required = false)String orgTreeId,
                                                         @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                         @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                         @RequestParam(value = "userId",required = false)Long userId,
                                                         @RequestParam(value = "accout",required = false)String accout);


   @RequestMapping(value="/orgRel/getOrgRelTypePage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<Page<OrgRefTypeVo>> getOrgRelTypePage(@RequestParam(value = "orgId",required = false)String orgId,
                                                               @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                               @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                               @RequestParam(value = "userId",required = false)Long userId,
                                                               @RequestParam(value = "accout",required = false)String accout);
}
