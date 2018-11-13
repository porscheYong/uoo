package cn.ffcs.uoo.web.maindata.position.controller;

import cn.ffcs.uoo.web.maindata.position.dto.TbOrgPositionRel;
import cn.ffcs.uoo.web.maindata.position.service.TbOrgPositionRelClient;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
public class TbOrgPositionRelController {
    @Resource
    private TbOrgPositionRelClient tbOrgPositionRelClient;

    @ApiOperation(value = "新增岗位组织关系", notes = "新增岗位组织关系")
    @ApiImplicitParam(name = "tbOrgPositionRel", value = "岗位组织关系", required = true, dataType = "TbOrgPositionRel")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbOrgPositionRel> addTbOrgPositionRel(@RequestBody TbOrgPositionRel tbOrgPositionRel) {
        return tbOrgPositionRelClient.addTbOrgPositionRel(tbOrgPositionRel);
    }

    @ApiOperation(value = "删除岗位组织关系",notes = "删除岗位组织关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgPositionId", value = "职位标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbOrgPositionRel> removeTbOrgPositionRel(@RequestBody Long orgPositionId, @RequestBody Long updateUser) {
        return tbOrgPositionRelClient.removeTbOrgPositionRel(orgPositionId, updateUser);
    }
}

