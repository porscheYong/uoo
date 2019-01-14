package cn.ffcs.uoo.web.maindata.position.controller;

import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.position.dto.TbOrgPostRel;
import cn.ffcs.uoo.web.maindata.position.service.TbOrgPostRelClient;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
public class TbOrgPostRelController {
    @Resource
    private TbOrgPostRelClient tbOrgPostRelClient;

    @ApiOperation(value = "新增组织职位关系", notes = "新增组织职位关系")
    @ApiImplicitParam(name = "tbOrgPostRel", value = "组织职位关系", required = true, dataType = "TbOrgPostRel")
    @OperateLog(type= OperateType.ADD,module="岗位职位模块",methods="新增组织职位关系",desc="新增组织职位关系")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbOrgPostRel> addTbOrgPostRel(@RequestBody TbOrgPostRel tbOrgPostRel) {
        return tbOrgPostRelClient.addTbOrgPostRel(tbOrgPostRel);
    }

    @ApiOperation(value = "删除组织职位关系",notes = "删除组织职位关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgPostId", value = "组织职位关系标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @OperateLog(type= OperateType.DELETE,module="岗位职位模块",methods="删除组织职位关系",desc="删除组织职位关系")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbOrgPostRel> removeTbOrgPostRel(Long orgPostId, Long updateUser) {
        return tbOrgPostRelClient.removeTbOrgPostRel(orgPostId, updateUser);
    }
}

