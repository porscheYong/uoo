package cn.ffcs.uoo.core.position.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.position.constant.StatusEnum;
import cn.ffcs.uoo.core.position.entity.TbOrgPostRel;
import cn.ffcs.uoo.core.position.entity.TbPost;
import cn.ffcs.uoo.core.position.entity.TbPostLocation;
import cn.ffcs.uoo.core.position.service.TbOrgPostRelService;
import cn.ffcs.uoo.core.position.service.TbPostLocationService;
import cn.ffcs.uoo.core.position.service.TbPostService;
import cn.ffcs.uoo.core.position.vo.OrgPostInfoVo;
import cn.ffcs.uoo.core.position.vo.ResponseResult;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class TbPostController extends BaseController {
    @Autowired
    private TbPostService tbPostService;
    @Autowired
    private TbOrgPostRelService tbOrgPostRelService;
    @Autowired
    private TbPostLocationService tbPostLocationService;

    @ApiOperation(value = "新增职位", notes = "新增职位")
    @ApiImplicitParam(name = "tbPost", value = "职位", required = true, dataType = "TbPost")
    @UooLog(value = "新增职位", key = "addTbPost")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbPost> addTbPost(@RequestBody TbPost tbPost) {
        ResponseResult<TbPost> responseResult = new ResponseResult<TbPost>();

        tbPost.setStatusCd(StatusEnum.VALID.getStatus());
        tbPost.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPost.setEffDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPost.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPost.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPostService.insert(tbPost);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增职位成功");
        return responseResult;
    }

    @ApiOperation(value = "删除职位",notes = "删除职位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "职位标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @UooLog(value = "删除职位", key = "removeTbPost")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbPost> removeTbPost(@RequestBody Long postId, @RequestBody Long updateUser) {
        ResponseResult<TbPost> responseResult = new ResponseResult<TbPost>();

        // 校验必填项
        if (postId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入职位标识");
            return responseResult;
        }
        if(updateUser == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入修改人");
            return responseResult;
        }

        // 是否存在组织职位关系
        Wrapper<TbOrgPostRel> orgPostRelWrapper = new EntityWrapper<TbOrgPostRel>();
        orgPostRelWrapper.eq("POST_ID", postId);
        orgPostRelWrapper.eq("STATUS_CD", "1000");
        List<TbOrgPostRel> tbOrgPostRelList = tbOrgPostRelService.selectList(orgPostRelWrapper);

        if(tbOrgPostRelList != null && tbOrgPostRelList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("存在组织职位关系，不允许删除该职位");
            return responseResult;
        }

        // 是否存在职位行政区域
        Wrapper<TbPostLocation> postLocationWrapper = new EntityWrapper<TbPostLocation>();
        postLocationWrapper.eq("POST_ID", postId);
        postLocationWrapper.eq("STATUS_CD", "1000");
        List<TbPostLocation> tbPostLocationList = tbPostLocationService.selectList(postLocationWrapper);

        if(tbPostLocationList != null && tbPostLocationList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("存在职位行政区域，不允许删除该职位");
            return responseResult;
        }

        // 是否存在下级职位
        Wrapper<TbPost> postWrapper = new EntityWrapper<TbPost>();
        postWrapper.eq("PARENT_POST_ID", postId);
        postWrapper.eq("STATUS_CD", "1000");
        List<TbPost> tbPostList = tbPostService.selectList(postWrapper);

        if(tbPostList != null && tbPostList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("存在下级职位，不允许删除该职位");
            return responseResult;
        }

        TbPost tbPost = new TbPost();
        tbPost.setPostId(postId);
        // 失效状态
        tbPost.setStatusCd(StatusEnum.INVALID.getStatus());
        tbPost.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPost.setUpdateUser(updateUser);
        tbPost.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPost.setExpDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPostService.updateById(tbPost);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除职位成功");
        return responseResult;
    }

    @ApiOperation(value = "修改职位",notes = "修改职位")
    @ApiImplicitParam(name = "tbPost", value = "职位", required = true, dataType = "TbPost")
    @UooLog(value = "修改职位", key = "updateTbPost")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbPost> updateTbPost(@RequestBody TbPost tbPost) {
        ResponseResult<TbPost> responseResult = new ResponseResult<TbPost>();
        // 校验必填项
        if(tbPost.getPostId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入职位标识");
            return responseResult;
        }

        tbPost.setEffDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPost.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPost.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        boolean isSuccess = tbPostService.updateById(tbPost);
        if(isSuccess) {
            responseResult.setState(ResponseResult.STATE_OK);
            responseResult.setMessage("修改职位成功");
            return responseResult;
        } else {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("修改职位失败");
            return responseResult;
        }
    }

    @ApiOperation(value = "查询职位",notes = "查询职位")
    @ApiImplicitParam(name = "postName", value = "职位名称", required = true, dataType = "String")
    @UooLog(value = "查询职位", key = "queryPostList")
    @RequestMapping(value = "/get/{postName}", method = RequestMethod.GET)
    public List<TbPost> queryPostList(@PathVariable  String postName) {
        // 校验必填项
        if(StringUtils.isEmpty(postName)) {
            return null;
        }

        Wrapper<TbPost> wrapper = new EntityWrapper<TbPost>();
        wrapper.like("POST_NAME", postName);
        wrapper.eq("STATUS_CD", "1000");
        return tbPostService.selectList(wrapper);
    }

    @ApiOperation(value = "查询下级职位",notes = "查询下级职位")
    @ApiImplicitParam(name = "parentPostId", value = "上级职位关系标识", required = true, dataType = "Long")
    @UooLog(value = "查询下级职位", key = "queryChildrenPostList")
    @RequestMapping(value = "/getChildren/{parentPostId}", method = RequestMethod.GET)
    public List<TbPost> queryChildPostList(@PathVariable Long parentPostId) {
        // 校验必填项
        if(parentPostId == null) {
            return null;
        }

        Wrapper<TbPost> wrapper = new EntityWrapper<TbPost>();
        wrapper.eq("PARENT_POST_ID", parentPostId);
        wrapper.eq("STATUS_CD", "1000");
        return tbPostService.selectList(wrapper);
    }

    @ApiOperation(value = "查询父级职位",notes = "查询父级职位")
    @UooLog(value = "查询父级职位", key = "queryParentPostList")
    @RequestMapping(value = "/getParent", method = RequestMethod.GET)
    public List<TbPost> queryParentPostList() {
        Wrapper<TbPost> wrapper = new EntityWrapper<TbPost>();
        wrapper.isNull("PARENT_POST_ID");
        wrapper.eq("STATUS_CD", "1000");
        return tbPostService.selectList(wrapper);
    }

    @ApiOperation(value = "根据组织标识查询职位",notes = "根据组织标识查询职位")
    @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long")
    @UooLog(value = "根据组织标识查询职位", key = "queryPostListByOrgId")
    @RequestMapping(value = "/getPostList/{orgId}", method = RequestMethod.GET)
    public List<OrgPostInfoVo> queryPostListByOrgId(@PathVariable Long orgId) {
        // 校验必填项
        if(orgId == null) {
            return null;
        }

        return tbPostService.queryPostListByOrgId(orgId);
    }
}

