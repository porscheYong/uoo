package cn.ffcs.uoo.web.maindata.organization.service;

import cn.ffcs.uoo.web.maindata.organization.dto.*;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgServiceHystrix;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

   @RequestMapping(value="/orgRel/getOrgRelTree/id={id}&orgRootId={orgRootId}&relCode={relCode}&isOpen={isOpen}&isAsync={isAsync}&isRoot={isRoot}",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<List<TreeNodeVo>> getOrgRelTree(@PathVariable(value = "id")String id, @PathVariable(value = "orgRootId")String orgRootId,
                                                         @PathVariable(value = "relCode")String relCode, @PathVariable(value = "isOpen")boolean isOpen,
                                                         @PathVariable(value = "isAsync")boolean isAsync, @PathVariable(value = "isRoot")boolean isRoot);

   @RequestMapping(value="/orgRel/getRestructOrgRelTree/id={id}&orgRootId={orgRootId}&isFull={isFull}",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(@PathVariable(value = "id")String id,
                                                                 @PathVariable(value = "orgRootId")String orgRootId,
                                                                 @PathVariable(value = "isFull")boolean isFull);


   @RequestMapping(value="/orgRel/getTarOrgRelTreeAndLv/orgRootId={orgRootId}&lv={lv}&curOrgid={curOrgid}&isFull={isFull}",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<List<TreeNodeVo>> getTarOrgRelTreeAndLv(@PathVariable(value = "orgRootId")String orgRootId,@PathVariable(value = "lv")String lv,@PathVariable(value = "curOrgid")String curOrgid,@PathVariable(value = "isFull")boolean isFull);

   @RequestMapping(value="/orgRel/addOrgRel",method = RequestMethod.POST,headers={"Content-Type=application/json"})
   public ResponseResult<String> addOrgRel(@RequestBody Org org);

   @RequestMapping(value="/orgRel/getFuzzyOrgRelPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<Page<OrgVo>> getFuzzyOrgRelPage(@RequestBody OrgVo orgVo);


   @RequestMapping(value="/orgRel/getOrgRelTypePage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
   public ResponseResult<Page<OrgRefTypeVo>> getOrgRelTypePage(@RequestBody OrgRefTypeVo orgRefTypeVo);
}
