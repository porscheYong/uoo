package cn.ffcs.uoo.web.maindata.organization.service;

import cn.ffcs.uoo.web.maindata.organization.dto.*;
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
@FeignClient(value = "uoo-organization",fallback = OrgRelServiceHystrix.class)
public interface OrgRelService {

   @RequestMapping(value="/orgRel/getOrgRelTree",method = RequestMethod.GET)
   public ResponseResult<List<TreeNodeVo>> getOrgRelTree(@RequestParam(value = "id",required = false)String id,
                                                         @RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                         @RequestParam(value = "relCode",required = false)String relCode,
                                                         @RequestParam(value = "isOpen",required = false)boolean isOpen,
                                                         @RequestParam(value = "isAsync",required = false)boolean isAsync,
                                                         @RequestParam(value = "isRoot",required = false)boolean isRoot);

   @RequestMapping(value="/orgRel/getRestructOrgRelTree",method = RequestMethod.GET)
   public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(@RequestParam(value = "id",required = false)String id,
                                                                 @RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                                 @RequestParam(value = "isFull",required = false)boolean isFull);


   @RequestMapping(value="/orgRel/getTarOrgRelTreeAndLv",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<List<TreeNodeVo>> getTarOrgRelTreeAndLv(@RequestParam(value = "orgRootId",required = false)String orgRootId,
                                                                 @RequestParam(value = "lv",required = false)String lv,
                                                                 @RequestParam(value = "curOrgid",required = false)String curOrgid,
                                                                 @RequestParam(value = "isFull",required = false)boolean isFull);

   @RequestMapping(value="/orgRel/addOrgRel",method = RequestMethod.POST,headers={"Content-Type=application/json"})
   public ResponseResult<String> addOrgRel(@RequestBody Org org);

   @RequestMapping(value="/orgRel/getFuzzyOrgRelPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<Page<OrgVo>> getFuzzyOrgRelPage(@RequestBody OrgVo orgVo);


   @RequestMapping(value="/orgRel/getOrgRelTypePage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<Page<OrgRefTypeVo>> getOrgRelTypePage(@RequestBody OrgRefTypeVo orgRefTypeVo);
}
