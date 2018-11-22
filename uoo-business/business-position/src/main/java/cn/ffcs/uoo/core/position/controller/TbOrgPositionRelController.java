package cn.ffcs.uoo.core.position.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.position.constant.StatusEnum;
import cn.ffcs.uoo.core.position.entity.TbOrgPositionRel;
import cn.ffcs.uoo.core.position.service.TbOrgPositionRelService;
import cn.ffcs.uoo.core.position.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 岗位组织关系，不同组织树具有不同的岗位 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-11-01
 */
@Api(description = "岗位组织关系", value = "OrgPositionRel")
@RestController
@RequestMapping("/tbOrgPositionRel")
public class TbOrgPositionRelController extends BaseController {
    @Autowired
    private TbOrgPositionRelService tbOrgPositionRelService;

    @ApiOperation(value = "新增岗位组织关系", notes = "新增岗位组织关系")
    @ApiImplicitParam(name = "tbOrgPositionRel", value = "岗位组织关系", required = true, dataType = "TbOrgPositionRel")
    @UooLog(value = "新增岗位组织关系", key = "addTbOrgPositionRel")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbOrgPositionRel> addTbOrgPositionRel(@RequestBody TbOrgPositionRel tbOrgPositionRel) {
        ResponseResult<TbOrgPositionRel> responseResult = new ResponseResult<TbOrgPositionRel>();
        tbOrgPositionRel.setEffDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbOrgPositionRel.setStatusCd(StatusEnum.VALID.getStatus());
        tbOrgPositionRel.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbOrgPositionRel.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbOrgPositionRel.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbOrgPositionRelService.save(tbOrgPositionRel);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增岗位组织关系成功");
        return responseResult;
    }

    @ApiOperation(value = "删除岗位组织关系",notes = "删除岗位组织关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgPositionId", value = "职位标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @UooLog(value = "删除岗位组织关系", key = "removeTbOrgPositionRel")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbOrgPositionRel> removeTbOrgPositionRel(@RequestBody Long orgPositionId, @RequestBody Long updateUser) {
        ResponseResult<TbOrgPositionRel> responseResult = new ResponseResult<TbOrgPositionRel>();

        // 校验必填项
        if(orgPositionId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入组织岗位关系标识");
            return responseResult;
        }
        if(updateUser == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入修改人");
            return responseResult;
        }

        tbOrgPositionRelService.remove(orgPositionId, updateUser);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除岗位组织关系成功");
        return responseResult;
    }
}

