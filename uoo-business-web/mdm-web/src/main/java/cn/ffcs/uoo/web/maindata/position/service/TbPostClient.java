package cn.ffcs.uoo.web.maindata.position.service;

import cn.ffcs.uoo.web.maindata.position.dto.TbPost;
import cn.ffcs.uoo.web.maindata.position.service.fallback.TbPostClientHystrix;
import cn.ffcs.uoo.web.maindata.position.vo.OrgPostInfoVo;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "business-position",configuration = {PersonnelServiceConfiguration.class},fallback = TbPostClientHystrix.class)
public interface TbPostClient {
    @RequestMapping(value = "/tbPost/add", method = RequestMethod.POST)
    ResponseResult<TbPost> addTbPost(@RequestBody TbPost tbPost);

    @RequestMapping(value = "/tbPost/del", method = RequestMethod.POST)
    ResponseResult<TbPost> removeTbPost(@RequestParam("postId") Long postId, @RequestParam("updateUser") Long updateUser);

    @RequestMapping(value = "/tbPost/update", method = RequestMethod.POST)
    ResponseResult<TbPost> updateTbPost(@RequestBody TbPost tbPost);

    @RequestMapping(value = "/tbPost/get/{postName}", method = RequestMethod.GET)
    List<TbPost> queryPostList(@PathVariable("postName") String postName);

    @RequestMapping(value = "/tbPost/getChildren/{parentPostId}", method = RequestMethod.GET)
    ResponseResult<List<TbPost>> queryChildPostList(@PathVariable("parentPostId") Long parentPostId);

    @RequestMapping(value = "/tbPost/getParent", method = RequestMethod.GET)
    List<TbPost> queryParentPostList();

    @RequestMapping(value = "/tbPost/getPostList/{orgId}", method = RequestMethod.GET)
    List<OrgPostInfoVo> queryPostListByOrgId(@PathVariable("orgId") Long orgId);
     
    @RequestMapping(value = "/tbPost/postTree", method = RequestMethod.GET)
    public ResponseResult<List<TbPost>> postTree();
}
