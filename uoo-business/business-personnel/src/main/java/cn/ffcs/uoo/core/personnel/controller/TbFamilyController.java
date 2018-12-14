package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.personnel.annotion.SendMqMsg;
import cn.ffcs.uoo.core.personnel.entity.TbFamily;
import cn.ffcs.uoo.core.personnel.service.TbFamilyService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.ffcs.uoo.base.controller.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@RestController
@RequestMapping("/family")
public class TbFamilyController extends BaseController {

    @Autowired
    private TbFamilyService tbFamilyService;

    @ApiOperation(value = "新增家庭成员信息", notes = "新增家庭成员信息")
    @ApiImplicitParam(name = "tbFamily", value = "家庭成员信息", required = true, dataType = "TbFamily")
    @UooLog(value = "新增家庭成员信息", key = "saveTbFamily")
    @SendMqMsg(type = "person", handle ="update", column ="personnelId")
    @RequestMapping(value = "/saveTbFamily", method = RequestMethod.POST)
    public Object saveTbFamily(@RequestBody TbFamily tbFamily) {
        return tbFamilyService.saveTbFamily(tbFamily);
    }

    @ApiOperation(value = "更新家庭成员信息", notes = "更新家庭成员信息")
    @ApiImplicitParam(name = "tbFamily", value = "家庭成员信息", required = true, dataType = "TbFamily")
    @UooLog(value = "更新家庭成员信息", key = "updateTbFamily")
    @SendMqMsg(type = "person", handle ="update", column ="personnelId")
    @RequestMapping(value = "/updateTbFamily", method = RequestMethod.POST)
    public Object updateTbFamily(@RequestBody TbFamily tbFamily){
        return tbFamilyService.updateTbFamily(tbFamily);
    }

    @ApiOperation(value="删除家庭成员信息",notes="删除家庭成员信息")
    @ApiImplicitParam(name = "familyId", value = "家庭成员信息标识", required = true, dataType = "Long",paramType="path")
    @UooLog(value = "删除教育信息标识",key = "delTbFamily")
    @SendMqMsg(type = "person", handle ="update", column ="personnelId")
    @RequestMapping(value="/delTbFamily",method = RequestMethod.DELETE)
    public Object delTbFamily(Long familyId ){
        return  tbFamilyService.delTbFamily(familyId);
    }

    @ApiOperation(value="查看家庭成员信息",notes="查看家庭成员信息")
    @ApiImplicitParam(name = "familyId", value = "家庭成员信息标识", required = true, dataType = "Long",paramType="path")
    @UooLog(value = "查看教育信息标识",key = "getTbFamily")
    @RequestMapping(value="/getTbFamily",method = RequestMethod.GET)
    public Object getTbFamily(Long familyId ){
        return  tbFamilyService.getTbFamilyById(familyId);
    }

    @ApiOperation(value="家庭成员信息分页查询",notes="家庭成员信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path"),
    })
    @UooLog(value = "家庭成员信息分页查询",key = "getTbFamilyPage")
    @RequestMapping(value="/getTbFamilyPage",method = RequestMethod.GET)
    public Object getTbFamilyPage( Long personnelId, Integer pageNo, Integer pageSize){

        return tbFamilyService.getTbFamilyPage(personnelId, pageNo, pageSize);
    }

}

