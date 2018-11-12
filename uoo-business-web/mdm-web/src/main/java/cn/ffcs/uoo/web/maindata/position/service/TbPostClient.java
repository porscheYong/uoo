package cn.ffcs.uoo.web.maindata.position.service;

import cn.ffcs.uoo.web.maindata.position.dto.TbPost;
import cn.ffcs.uoo.web.maindata.position.service.fallback.TbPostClientHystrix;
import cn.ffcs.uoo.web.maindata.position.vo.OrgPostInfoVo;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "uoo-position",configuration = {PersonnelServiceConfiguration.class},fallback = TbPostClientHystrix.class)
public interface TbPostClient {
    @RequestMapping(value = "/tbPost/add", method = RequestMethod.POST)
    ResponseResult<TbPost> addTbPost(@RequestBody TbPost tbPost);

    @RequestMapping(value = "/tbPost/del", method = RequestMethod.POST)
    ResponseResult<TbPost> removeTbPost(@RequestBody Long postId, @RequestBody Long updateUser);

    @RequestMapping(value = "/tbPost/update", method = RequestMethod.POST)
    ResponseResult<TbPost> updateTbPost(@RequestBody TbPost tbPost);

    @RequestMapping(value = "/tbPost/get/{postName}", method = RequestMethod.GET)
    List<TbPost> queryPostList(@PathVariable String postName);

    @RequestMapping(value = "/tbPost/getChildren/{parentPostId}", method = RequestMethod.GET)
    List<TbPost> queryChildPostList(@PathVariable Long parentPostId);

    @RequestMapping(value = "/tbPost/getParent", method = RequestMethod.GET)
    List<TbPost> queryParentPostList();

    @RequestMapping(value = "/tbPost/getPostList/{orgId}", method = RequestMethod.GET)
    List<OrgPostInfoVo> queryPostListByOrgId(@PathVariable Long orgId);
}
