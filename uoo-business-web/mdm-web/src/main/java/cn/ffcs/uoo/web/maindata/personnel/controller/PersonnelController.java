package cn.ffcs.uoo.web.maindata.personnel.controller;


import cn.ffcs.uoo.web.maindata.personnel.service.PersonnelService;
import cn.ffcs.uoo.web.maindata.personnel.vo.EditFormPersonnelVo;
import cn.ffcs.uoo.web.maindata.personnel.vo.PersonnelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName PersonnelController
 * @Description 
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
*/
@RestController
@RequestMapping(value = "/personnel", produces = {"application/json;charset=UTF-8"})
@Api(value = "/personnel", description = "人员相关操作")
public class PersonnelController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PersonnelService personnelService;

    @ApiOperation(value = "人员信息", notes = "查看人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "orgRootId", value = "业务树标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long",paramType="path"),
    })
    @RequestMapping(value = "/getFormPersonnel",method = RequestMethod.GET)
    public Object getFormPersonnel( Long personnelId,
                                    Long orgRootId,
                                    Long orgId){
        return  personnelService.getFormPersonnel(personnelId, orgRootId, orgId);
    }

    @ApiOperation(value = "新增人员信息",notes = "人员信息新增")
    @ApiImplicitParam(name = "editFormPersonnelVo",value = "人员信息",required = true,dataType = "EditFormPersonnelVo")
    @RequestMapping(value = "/savePersonnel",method = RequestMethod.POST)
    public Object savePersonnel(@RequestBody EditFormPersonnelVo editFormPersonnelVo) {
        return personnelService.savePersonnel(editFormPersonnelVo);
    }

    @ApiOperation(value="删除人员信息",notes="人员信息删除")
    @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path")
    @RequestMapping(value="/deletePersonnel",method = RequestMethod.DELETE)
    public Object deletePersonnel(Long personnelId) {
        return personnelService.deletePersonnel(personnelId);
    }

    @ApiOperation(value = "修改人员基本信息",notes = "人员基本信息修改")
    @ApiImplicitParam(name = "personnelVo",value = "人员基本信息",required = true,dataType = "PersonnelVo")
    @RequestMapping(value = "/updatePersonnel",method = RequestMethod.PUT)
    public Object upPersonnel(@RequestBody PersonnelVo personnelVo){
        return personnelService.upPersonnel(personnelVo);
    }


}
