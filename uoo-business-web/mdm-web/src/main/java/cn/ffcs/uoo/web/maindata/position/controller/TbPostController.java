package cn.ffcs.uoo.web.maindata.position.controller;

import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.position.dto.TbPost;
import cn.ffcs.uoo.web.maindata.position.service.TbPostClient;
import cn.ffcs.uoo.web.maindata.position.vo.OrgPostInfoVo;
import cn.ffcs.uoo.web.maindata.position.vo.PostNodeVo;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 职位表 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
@Api(description = "职位",value = "Post")
@RestController
@RequestMapping("/tbPost")
public class TbPostController {
    @Resource
    private TbPostClient tbPostClient;

    @ApiOperation(value = "新增职位", notes = "新增职位")
    @ApiImplicitParam(name = "tbPost", value = "职位", required = true, dataType = "TbPost")
    @OperateLog(type= OperateType.ADD,module="岗位职位模块",methods="新增职位",desc="新增职位")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbPost> addTbPost(@RequestBody TbPost tbPost) {
        return tbPostClient.addTbPost(tbPost);
    }

    @ApiOperation(value = "删除职位",notes = "删除职位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "职位标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @OperateLog(type= OperateType.DELETE,module="岗位职位模块",methods="删除职位",desc="删除职位")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbPost> removeTbPost(Long postId, Long updateUser) {
        return tbPostClient.removeTbPost(postId, updateUser);
    }

    @ApiOperation(value = "修改职位",notes = "修改职位")
    @ApiImplicitParam(name = "tbPost", value = "职位", required = true, dataType = "TbPost")
    @OperateLog(type= OperateType.UPDATE,module="岗位职位模块",methods="修改职位",desc="修改职位")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbPost> updateTbPost(@RequestBody TbPost tbPost) {
        return tbPostClient.updateTbPost(tbPost);
    }

    @ApiOperation(value = "查询职位",notes = "查询职位")
    @ApiImplicitParam(name = "postName", value = "职位名称", required = true, dataType = "String")
    @OperateLog(type= OperateType.SELECT,module="岗位职位模块",methods="查询职位",desc="查询职位")
    @RequestMapping(value = "/get/{postName}", method = RequestMethod.GET)
    public List<TbPost> queryPostList(@PathVariable String postName) {
        return tbPostClient.queryPostList(postName);
    }

    @ApiOperation(value = "查询下级职位",notes = "查询下级职位")
    @ApiImplicitParam(name = "parentPostId", value = "上级职位关系标识", required = true, dataType = "Long")
    @OperateLog(type= OperateType.SELECT,module="岗位职位模块",methods="查询下级职位",desc="查询下级职位")
    @RequestMapping(value = "/getChildren/{parentPostId}", method = RequestMethod.GET)
    public List<TbPost> queryChildPostList(@PathVariable Long parentPostId) {
        return tbPostClient.queryChildPostList(parentPostId);
    }

    @ApiOperation(value = "查询父级职位",notes = "查询父级职位")
    @OperateLog(type= OperateType.SELECT,module="岗位职位模块",methods="查询扩展行列表",desc="查询扩展行列表")
    @RequestMapping(value = "/getParent", method = RequestMethod.GET)
    public List<TbPost> queryParentPostList() {
        return tbPostClient.queryParentPostList();
    }

    @ApiOperation(value = "根据组织标识查询职位",notes = "根据组织标识查询职位")
    @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long")
    @OperateLog(type= OperateType.SELECT,module="岗位职位模块",methods="根据组织标识查询职位",desc="根据组织标识查询职位")
    @RequestMapping(value = "/getPostList/{orgId}", method = RequestMethod.GET)
    public List<OrgPostInfoVo> queryPostListByOrgId(@PathVariable Long orgId) {
        return tbPostClient.queryPostListByOrgId(orgId);
    }

    @ApiOperation(value = "查询职位树", notes = "查询职位树")
    @OperateLog(type= OperateType.SELECT,module="岗位职位模块",methods="查询职位树",desc="查询职位树")
    @RequestMapping(value = "/getPostTree", method = RequestMethod.GET)
    public ResponseResult<List<PostNodeVo>> getPostTree() {
        return tbPostClient.getPostTree();
    }
}

