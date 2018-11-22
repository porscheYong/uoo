package cn.ffcs.uoo.core.position.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.position.constant.StatusEnum;
import cn.ffcs.uoo.core.position.entity.TbOrgPositionRel;
import cn.ffcs.uoo.core.position.entity.TbPosition;
import cn.ffcs.uoo.core.position.service.TbOrgPositionRelService;
import cn.ffcs.uoo.core.position.service.TbPositionService;
import cn.ffcs.uoo.core.position.util.TreeUtil;
import cn.ffcs.uoo.core.position.vo.OrgPositionInfoVo;
import cn.ffcs.uoo.core.position.vo.PositionNodeVo;
import cn.ffcs.uoo.core.position.vo.ResponseResult;
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
 * 岗位，不同组织树具有不同的岗位 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-31
 */
@Api(description = "岗位", value = "Position")
@RestController
@RequestMapping("/tbPosition")
public class TbPositionController extends BaseController {
    @Autowired
    private TbPositionService tbPositionService;
    @Autowired
    private TbOrgPositionRelService tbOrgPositionRelService;

    @ApiOperation(value = "新增岗位", notes = "新增岗位")
    @ApiImplicitParam(name = "tbPosition", value = "岗位", required = true, dataType = "TbPosition")
    @UooLog(value = "新增岗位", key = "addTbPosition")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbPosition> addTbPosition(@RequestBody TbPosition tbPosition) {
        ResponseResult<TbPosition> responseResult = new ResponseResult<TbPosition>();

        tbPosition.setEffDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPosition.setStatusCd(StatusEnum.VALID.getStatus());
        tbPosition.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPosition.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));

        tbPositionService.save(tbPosition);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增岗位成功");
        return responseResult;
    }

    @ApiOperation(value = "修改岗位", notes = "修改岗位")
    @ApiImplicitParam(name = "tbPosition", value = "岗位", required = true, dataType = "TbPosition")
    @UooLog(value = "修改岗位", key = "updateTbPosition")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbPosition> updateTbPosition(@RequestBody TbPosition tbPosition) {
        ResponseResult<TbPosition> responseResult = new ResponseResult<TbPosition>();
        if (tbPosition.getPositionId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入岗位标识");
            return responseResult;
        }

        tbPosition.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPosition.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPosition.setEffDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbPositionService.updateById(tbPosition);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("更新岗位信息成功");
        return responseResult;
    }

    @ApiOperation(value = "删除岗位", notes = "删除岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "positionId", value = "岗位标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @UooLog(value = "删除岗位", key = "removeTbPosition")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbPosition> removeTbPosition(@RequestBody Long positionId, @RequestBody Long updateUser) {
        ResponseResult<TbPosition> responseResult = new ResponseResult<TbPosition>();

        // 校验必填项
        if (positionId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("岗位标识");
            return responseResult;
        }
        if (updateUser == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("修改人");
            return responseResult;
        }

        // 是否存在组织岗位关系
        Wrapper<TbOrgPositionRel> orgPositionRelWrapper = new EntityWrapper<TbOrgPositionRel>();
        orgPositionRelWrapper.eq("POSITION_ID", positionId);
        orgPositionRelWrapper.eq("STATUS_CD", "1000");
        List<TbOrgPositionRel> tbOrgPositionRelList = tbOrgPositionRelService.selectList(orgPositionRelWrapper);

        if (tbOrgPositionRelList != null && tbOrgPositionRelList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("该岗位存在组织岗位关系，不允许删除");
            return responseResult;
        }

        // 是否存在下级节点
        Wrapper<TbPosition> positionWrapper = new EntityWrapper<TbPosition>();
        positionWrapper.eq("PARENT_POSITION_ID", positionId);
        positionWrapper.eq("STATUS_CD", "1000");
        List<TbPosition> tbPositionList = tbPositionService.selectList(positionWrapper);

        if (tbPositionList != null && tbPositionList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("该岗位存在下级节点，不允许删除");
            return responseResult;
        }

        tbPositionService.remove(positionId, updateUser);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除岗位成功");

        return responseResult;
    }

    @ApiOperation(value = "查询组织岗位信息列表", notes = "查询组织岗位信息列表")
    @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long")
    @UooLog(value = "查询组织岗位信息列表", key = "queryOrgPositionInfoList")
    @RequestMapping(value = "/getOrgPositionInfoList/{orgId}", method = RequestMethod.GET)
    public List<OrgPositionInfoVo> queryOrgPositionInfoList(@PathVariable Long orgId) {
        // 校验必填项
        if (orgId == null) {
            return null;
        }

        return tbPositionService.queryOrgPositionInfoList(orgId);
    }

    @ApiOperation(value = "查询岗位", notes = "查询岗位")
    @ApiImplicitParam(name = "positionName", value = "岗位名称", required = true, dataType = "String")
    @UooLog(value = "查询岗位", key = "queryPositionList")
    @RequestMapping(value = "/get/{positionName}", method = RequestMethod.GET)
    public List<TbPosition> queryPositionList(@PathVariable String positionName) {
        // 校验必填项
        if (StringUtils.isEmpty(positionName)) {
            return null;
        }

        Wrapper<TbPosition> positionWrapper = new EntityWrapper<TbPosition>();
        positionWrapper.like("POSITION_NAME", positionName);
        positionWrapper.eq("STATUS_CD", "1000");
        return tbPositionService.selectList(positionWrapper);
    }

    @ApiOperation(value = "查询父级岗位", notes = "查询父级岗位")
    @UooLog(value = "查询父级岗位", key = "queryParentPositionList")
    @RequestMapping(value = "/getParent", method = RequestMethod.GET)
    public List<TbPosition> queryParentPositionList() {
        Wrapper<TbPosition> positionWrapper = new EntityWrapper<TbPosition>();
        positionWrapper.isNull("PARENT_POSITION_ID");
        positionWrapper.eq("STATUS_CD", "1000");
        return tbPositionService.selectList(positionWrapper);
    }

    @ApiOperation(value = "查询下级岗位", notes = "查询下级岗位")
    @ApiImplicitParam(name = "parentPositionId", value = "上级岗位标识", required = true, dataType = "Long")
    @UooLog(value = "查询下级岗位", key = "queryChildPositionList")
    @RequestMapping(value = "/getChildren/{parentPositionId}", method = RequestMethod.GET)
    public List<TbPosition> queryChildPositionList(@PathVariable Long parentPositionId) {
        // 校验必填项
        if (parentPositionId == null) {
            return null;
        }

        Wrapper<TbPosition> positionWrapper = new EntityWrapper<TbPosition>();
        positionWrapper.eq("PARENT_POSITION_ID", parentPositionId);
        positionWrapper.eq("STATUS_CD", "1000");
        return tbPositionService.selectList(positionWrapper);
    }

    @ApiOperation(value = "查询岗位树", notes = "查询岗位树")
    @UooLog(value = "查询岗位树", key = "getPositionTree")
    @RequestMapping(value = "/getPositionTree", method = RequestMethod.GET)
    public ResponseResult<List<PositionNodeVo>> getPositionTree() {
        ResponseResult<List<PositionNodeVo>> responseResult = new ResponseResult<List<PositionNodeVo>>();
        List<PositionNodeVo> positionNodeVoList = tbPositionService.getAllPositionNodeVo();
        List<PositionNodeVo> positionTree = TreeUtil.createPositionTree(positionNodeVoList);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("成功");
        responseResult.setData(positionTree);
        return responseResult;
    }
}

