package cn.ffcs.uoo.web.maindata.personnel.controller;


import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.personnel.service.PersonnelService;
import cn.ffcs.uoo.web.maindata.personnel.utils.SysUserInfo;
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
            @ApiImplicitParam(name = "orgTreeId", value = "业务树标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "orgId", value = "组织标识", required = true, dataType = "Long",paramType="path")
    })
    @RequestMapping(value = "/getFormPersonnel",method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="人员管理",methods="getFormPersonnel",desc="查看人员信息")
    public Object getFormPersonnel( Long personnelId,
                                    Long orgTreeId,
                                    Long orgId){
        return  personnelService.getFormPersonnel(personnelId, orgTreeId, orgId, SysUserInfo.getAccount());
    }

    @ApiOperation(value = "新增人员信息",notes = "人员信息新增")
    @ApiImplicitParam(name = "editFormPersonnelVo",value = "人员信息",required = true,dataType = "EditFormPersonnelVo")
    @RequestMapping(value = "/savePersonnel",method = RequestMethod.POST)
    @OperateLog(type= OperateType.ADD, module="人员管理",methods="savePersonnel",desc="新增人员信息")
    public Object savePersonnel(@RequestBody EditFormPersonnelVo editFormPersonnelVo) {
        editFormPersonnelVo.setUserId(SysUserInfo.getUserId());
        return personnelService.savePersonnel(editFormPersonnelVo);
    }

    @ApiOperation(value="删除人员信息",notes="人员信息删除")
    @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path")
    @RequestMapping(value="/deletePersonnel",method = RequestMethod.DELETE)
    @OperateLog(type= OperateType.DELETE, module="人员管理",methods="deletePersonnel",desc="删除人员信息")
    public Object deletePersonnel(Long personnelId) {
        return personnelService.deletePersonnel(personnelId, SysUserInfo.getUserId());
    }

    @ApiOperation(value = "修改人员基本信息",notes = "人员基本信息修改")
    @ApiImplicitParam(name = "personnelVo",value = "人员基本信息",required = true,dataType = "PersonnelVo")
    @RequestMapping(value = "/updatePersonnel",method = RequestMethod.PUT)
    @OperateLog(type= OperateType.UPDATE, module="人员管理",methods="updatePersonnel",desc="修改人员基本信息")
    public Object upPersonnel(@RequestBody PersonnelVo personnelVo){
        personnelVo.setUserId(SysUserInfo.getUserId());
        return personnelService.upPersonnel(personnelVo);
    }

    @ApiOperation(value="人员选择查询",notes="人员选择查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键字", required = true, dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path")
    })
    @RequestMapping(value="/getPsnBasicInfo",method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="人员管理",methods="getPsnBasicInfo",desc="人员选择查询")
    public Object getPsnBasicInfo(String keyWord, Integer pageNo, Integer pageSize){
        return personnelService.getPsnBasicInfo(keyWord, pageNo, pageSize);
    }

    @ApiOperation(value = "身份证对应信息", notes = "身份证对应信息")
    @ApiImplicitParam(name = "certNo", value = "身份证号", required = true, dataType = "String",paramType="path")
    @RequestMapping(value = "/getIdCardInfo",method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="人员管理",methods="getIdCardInfo",desc="身份证对应信息")
    public Object getIdCardInfo(String certNo){
        return personnelService.getIdCardInfo(certNo);
    }

    @ApiOperation(value="游离人员选择查询",notes="游离人员选择查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyWord", value = "关键字", required = true, dataType = "String",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path")
    })
    @RequestMapping(value="/getFreePsnInfo",method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="人员管理",methods="getFreePsnInfo",desc="游离人员选择查询")
    public Object getFreePsnInfo(String keyWord, Integer pageNo, Integer pageSize){
        return personnelService.getFreePsnInfo(keyWord, pageNo, pageSize);
    }

    @ApiOperation(value = "身份证对应人力编码", notes = "身份证对应人力编码")
    @ApiImplicitParam(name = "certNo", value = "身份证号", required = true, dataType = "String",paramType="path")
    @RequestMapping(value = "/getIdCardNcCode",method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="人员管理",methods="getIdCardNcCode",desc="身份证对应人力编码")
    public Object getIdCardNcCode(String certNo){
        return personnelService.getIdCardNcCode(certNo);
    }

}
