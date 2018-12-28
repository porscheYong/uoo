package cn.ffcs.uoo.web.maindata.personnel.controller;

import cn.ffcs.uoo.web.maindata.personnel.dto.TbFamily;
import cn.ffcs.uoo.web.maindata.personnel.service.FamilyService;
import cn.ffcs.uoo.web.maindata.personnel.utils.SysUserInfo;
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
 * @ClassName FamilyController
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@RestController
@RequestMapping(value = "/family", produces = {"application/json;charset=UTF-8"})
@Api(value = "/family", description = "家庭成员相关操作")
public class FamilyController {
    @Resource
    private FamilyService familyService;

    @ApiOperation(value = "新增家庭成员信息", notes = "新增家庭成员信息")
    @ApiImplicitParam(name = "tbFamily", value = "家庭成员信息", required = true, dataType = "TbFamily")
    @RequestMapping(value = "/saveTbFamily", method = RequestMethod.POST)
    public Object saveTbFamily(@RequestBody TbFamily tbFamily) {
        return familyService.saveTbFamily(tbFamily);
    }

    @ApiOperation(value = "更新家庭成员信息", notes = "更新家庭成员信息")
    @ApiImplicitParam(name = "tbFamily", value = "家庭成员信息", required = true, dataType = "TbFamily")
    @RequestMapping(value = "/updateTbFamily", method = RequestMethod.POST)
    public Object updateTbFamily(@RequestBody TbFamily tbFamily){
        return familyService.updateTbFamily(tbFamily);
    }

    @ApiOperation(value="删除家庭成员信息",notes="删除家庭成员信息")
    @ApiImplicitParam(name = "familyId", value = "家庭成员信息标识", required = true, dataType = "Long",paramType="path")
    @RequestMapping(value="/delTbFamily",method = RequestMethod.DELETE)
    public Object delTbFamily(Long familyId ){
        return  familyService.delTbFamily(familyId, SysUserInfo.getUserId());
    }

    @ApiOperation(value="查看家庭成员信息",notes="查看家庭成员信息")
    @ApiImplicitParam(name = "familyId", value = "家庭成员信息标识", required = true, dataType = "Long",paramType="path")
    @RequestMapping(value="/getTbFamily",method = RequestMethod.GET)
    public Object getTbFamily(Long familyId ){
        return  familyService.getTbFamily(familyId);
    }

    @ApiOperation(value="家庭成员信息分页查询",notes="家庭成员信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path"),
    })
    @RequestMapping(value="/getTbFamilyPage",method = RequestMethod.GET)
    public Object getTbFamilyPage( Long personnelId, Integer pageNo, Integer pageSize){
        return familyService.getTbFamilyPage(personnelId, pageNo, pageSize);
    }
}
