package cn.ffcs.uoo.core.position.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.position.constant.StatusEnum;
import cn.ffcs.uoo.core.position.entity.TbOrgPostRel;
import cn.ffcs.uoo.core.position.service.TbOrgPostRelService;
import cn.ffcs.uoo.core.position.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 组织职位关系
 * 职位，不同组织树具有不同的职位 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
@Api(description = "组织职位关系", value = "OrgPostRel")
@RestController
@RequestMapping("/tbOrgPostRel")
public class TbOrgPostRelController extends BaseController {
    @Autowired
    private TbOrgPostRelService tbOrgPostRelService;

    @ApiOperation(value = "新增组织职位关系", notes = "新增组织职位关系")
    @ApiImplicitParam(name = "tbOrgPostRel", value = "组织职位关系", required = true, dataType = "TbOrgPostRel")
    @UooLog(value = "新增组织职位关系", key = "addTbOrgPostRel")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbOrgPostRel> addTbOrgPostRel(@RequestBody TbOrgPostRel tbOrgPostRel) {
        ResponseResult<TbOrgPostRel> responseResult = new ResponseResult<TbOrgPostRel>();

        // 校验必填项
        if(tbOrgPostRel.getPostId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入职位标识");
            return responseResult;
        }
        if(tbOrgPostRel.getOrgId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入组织标识");
            return responseResult;
        }

        tbOrgPostRel.setEffDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbOrgPostRel.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbOrgPostRel.setStatusCd(StatusEnum.VALID.getStatus());
        tbOrgPostRel.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbOrgPostRel.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbOrgPostRelService.save(tbOrgPostRel);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增组织职位关系成功");
        return responseResult;
    }

    @ApiOperation(value = "删除组织职位关系",notes = "删除组织职位关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgPostId", value = "组织职位关系标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @UooLog(value = "删除组织职位关系", key = "removeTbOrgPostRel")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbOrgPostRel> removeTbOrgPostRel(@RequestBody Long orgPostId, @RequestBody Long updateUser) {
        ResponseResult<TbOrgPostRel> responseResult = new ResponseResult<TbOrgPostRel>();

        // 校验必填项
        if(orgPostId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入组织职位关系标识");
            return responseResult;
        }
        if(updateUser == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入修改人");
            return responseResult;
        }

        tbOrgPostRelService.remove(orgPostId, updateUser);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除组织职位关系成功");
        return responseResult;
    }
}

