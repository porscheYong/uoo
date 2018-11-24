package cn.ffcs.uoo.web.maindata.position.controller;

import cn.ffcs.uoo.web.maindata.position.dto.TbOrgPositionRel;
import cn.ffcs.uoo.web.maindata.position.dto.TbPosition;
import cn.ffcs.uoo.web.maindata.position.service.TbPositionClient;
import cn.ffcs.uoo.web.maindata.position.vo.OrgPositionInfoVo;
import cn.ffcs.uoo.web.maindata.position.vo.PositionNodeVo;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
public class TbPositionController {
    @Resource
    private TbPositionClient tbPositionClient;

    @ApiOperation(value = "新增岗位", notes = "新增岗位")
    @ApiImplicitParam(name = "tbPosition", value = "岗位", required = true, dataType = "TbPosition")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbPosition> addTbPosition(@RequestBody TbPosition tbPosition) {
        return tbPositionClient.addTbPosition(tbPosition);
    }

    @ApiOperation(value = "修改岗位", notes = "修改岗位")
    @ApiImplicitParam(name = "tbPosition", value = "岗位", required = true, dataType = "TbPosition")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbPosition> updateTbPosition(@RequestBody TbPosition tbPosition) {
        return tbPositionClient.updateTbPosition(tbPosition);
    }

    @ApiOperation(value = "删除岗位", notes = "删除岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "positionId", value = "岗位标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbPosition> removeTbPosition(@RequestBody Long positionId, @RequestBody Long updateUser) {
        return tbPositionClient.removeTbPosition(positionId, updateUser);
    }

    @ApiOperation(value = "查询组织岗位信息列表", notes = "查询组织岗位信息列表")
    @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long")
    @RequestMapping(value = "/getOrgPositionInfoList/{orgId}", method = RequestMethod.GET)
    public List<OrgPositionInfoVo> queryOrgPositionInfoList(@PathVariable Long orgId) {
        return tbPositionClient.queryOrgPositionInfoList(orgId);
    }

    @ApiOperation(value = "查询岗位", notes = "查询岗位")
    @ApiImplicitParam(name = "positionName", value = "岗位名称", required = true, dataType = "String")
    @RequestMapping(value = "/get/{positionName}", method = RequestMethod.GET)
    public List<TbPosition> queryPositionList(@PathVariable String positionName) {
        return tbPositionClient.queryPositionList(positionName);
    }

    @ApiOperation(value = "查询父级岗位", notes = "查询父级岗位")
    @RequestMapping(value = "/getParent", method = RequestMethod.GET)
    public List<TbPosition> queryParentPositionList() {
        return tbPositionClient.queryParentPositionList();
    }

    @ApiOperation(value = "查询下级岗位", notes = "查询下级岗位")
    @ApiImplicitParam(name = "parentPositionId", value = "上级岗位标识", required = true, dataType = "Long")
    @RequestMapping(value = "/getChildren/{parentPositionId}", method = RequestMethod.GET)
    public List<TbPosition> queryChildPositionList(@PathVariable Long parentPositionId) {
        return tbPositionClient.queryChildPositionList(parentPositionId);
    }

    @ApiOperation(value = "查询岗位树", notes = "查询岗位树")
    @RequestMapping(value = "/getPositionTree", method = RequestMethod.GET)
    public ResponseResult<List<PositionNodeVo>> getPositionTree() {
        return tbPositionClient.getPositionTree();
    }
}

