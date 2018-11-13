package cn.ffcs.uoo.web.maindata.organization.service;

import cn.ffcs.uoo.web.maindata.organization.dto.*;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgServiceHystrix;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@FeignClient(value = "uoo-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgRelServiceHystrix.class)
public interface OrgRelService {

   @RequestMapping(value="/orgRel/getOrgRelTree",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<List<TreeNodeVo>> getOrgRelTree(String id, String orgRootId, String relCode, boolean isOpen,
                                                         boolean isAsync, boolean isRoot);

   @RequestMapping(value="/orgRel/getRestructOrgRelTree",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(String id,String orgRootId,boolean isFull);


   @RequestMapping(value="/orgRel/getTarOrgRelTreeAndLv",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<List<TreeNodeVo>> getTarOrgRelTreeAndLv(String orgRootId,String lv,String curOrgid,boolean isFull);

   @RequestMapping(value="/orgRel/addOrgRel",method = RequestMethod.POST,headers={"Content-Type=application/json"})
   public ResponseResult<String> addOrgRel(Org org);

   @RequestMapping(value="/orgRel/getFuzzyOrgRelPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<Page<OrgVo>> getFuzzyOrgRelPage(OrgVo orgVo);


   @RequestMapping(value="/orgRel/getOrgRelTypePage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<Page<OrgRefTypeVo>> getOrgRelTypePage(OrgRefTypeVo orgRefTypeVo);
}
