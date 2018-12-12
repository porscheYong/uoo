package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.service.PostService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-10
 */
@RestController
@RequestMapping("/post")
@Api(value = "/post", description = "组织职位相关操作")
public class PostController extends BaseController {

    @Autowired
    private PostService postService;

    @ApiOperation(value = "查询职位树信息-web", notes = "查询职位树信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询职位树信息", key = "getPostTree")
    @RequestMapping(value = "/getPostTree", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<TreeNodeVo>> getPostTree(String id, String postRootId,String postType) throws IOException {
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        treeNodeVos = postService.selectPostTree(postType,id,false);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("职位树查询成功");
        ret.setData(treeNodeVos);
        return ret;
    }



}

