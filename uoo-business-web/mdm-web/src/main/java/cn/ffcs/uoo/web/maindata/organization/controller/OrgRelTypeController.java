package cn.ffcs.uoo.web.maindata.organization.controller;


import cn.ffcs.uoo.web.maindata.organization.dto.OrgRelType;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.organization.service.OrgRelTypeService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

//import com.alibaba.fastjson.JSON;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@RestController
@RequestMapping(value = "/orgRelType" ,produces = {"application/json;charset=UTF-8"})
@Api(value = "/orgRelType", description = "组织关系类型相关操作")
public class OrgRelTypeController {

    @Resource
    private OrgRelTypeService orgRefTypeService;

    @ApiOperation(value = "组织关系类型", notes = "组织关系类型")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgRelTypeList", method = RequestMethod.GET)
    public ResponseResult<List<OrgRelType>> getOrgRelTypeList(@RequestParam(value = "orgRelCode",required = false)String orgRelCode){
        return orgRefTypeService.getOrgRelTypeList(orgRelCode);
    }

    @ApiOperation(value = "查询组织关系类型树", notes = "查询组织关系类型树")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgRelTypeTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getOrgRelTypeTree(@RequestParam(value = "refCode",required = false)String refCode){
        return orgRefTypeService.getOrgRelTypeTree(refCode);
    }

}

