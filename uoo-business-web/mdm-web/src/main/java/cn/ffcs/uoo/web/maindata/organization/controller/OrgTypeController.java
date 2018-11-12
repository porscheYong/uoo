package cn.ffcs.uoo.web.maindata.organization.controller;



import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgType;
import cn.ffcs.uoo.web.maindata.organization.dto.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.organization.service.OrgTypeService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@RestController
@RequestMapping("/orgType")
@Api(value = "/orgType", description = "组织类别相关操作")
public class OrgTypeController {

    @Autowired
    private OrgTypeService orgTypeService;


    @ApiOperation(value = "获取组织类别", notes = "获取组织类别")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgTypeList", method = RequestMethod.POST)
    public ResponseResult<List<OrgType>> getOrgTypeList(String orgTypeCode){
        return orgTypeService.getOrgTypeList(orgTypeCode);
    }

    @ApiOperation(value = "获取组织类别树", notes = "获取组织类别树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgTypeTree", method = RequestMethod.POST)
    public ResponseResult<List<TreeNodeVo>> getOrgTypeTree(String id, String orgTypeCode){
        return orgTypeService.getOrgTypeTree(id,orgTypeCode);
    }

    @ApiOperation(value = "获取完整组织类别树", notes = "获取完整组织类别树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getFullOrgTypeTree", method = RequestMethod.POST)
    public ResponseResult<List<TreeNodeVo>> getFullOrgTypeTree(String id, String orgTypeCode, String orgId){
        return orgTypeService.getFullOrgTypeTree(id,orgTypeCode,orgId);
    }


}

