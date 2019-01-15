package cn.ffcs.uoo.web.maindata.personnel.controller;

import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.personnel.dto.TbEdu;
import cn.ffcs.uoo.web.maindata.personnel.service.EduService;
import cn.ffcs.uoo.web.maindata.personnel.utils.SysUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName EduController
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@RestController
@RequestMapping(value = "/edu", produces = {"application/json;charset=UTF-8"})
@Api(value = "/edu", description = "教育信息相关操作")
public class EduController {

    @Resource
    private EduService eduService;

    @ApiOperation(value = "新增教育信息", notes = "新增教育信息")
    @ApiImplicitParam(name = "tbEdu", value = "教育信息", required = true, dataType = "TbEdu")
    @RequestMapping(value = "/saveTbEdu", method = RequestMethod.POST)
    @OperateLog(type= OperateType.ADD, module="人员管理",methods="saveTbEdu",desc="新增教育信息")
    public Object saveTbEdu(@RequestBody TbEdu tbEdu) {
        tbEdu.setUpdateUser(SysUserInfo.getUserId());
        tbEdu.setCreateUser(SysUserInfo.getUserId());
        return eduService.saveTbEdu(tbEdu);
    }

    @ApiOperation(value="更新教育信息",notes="更新教育信息")
    @ApiImplicitParam(name = "tbEdu", value = "教育信息", required = true, dataType = "TbEdu")
    @RequestMapping(value="/updateTbEdu",method = RequestMethod.PUT)
    @OperateLog(type= OperateType.UPDATE, module="人员管理",methods="updateTbEdu",desc="更新教育信息")
    public Object updateTbEdu(@RequestBody TbEdu tbEdu){
        tbEdu.setUpdateUser(SysUserInfo.getUserId());
        return  eduService.updateTbEdu(tbEdu);
    }

    @ApiOperation(value="删除教育信息",notes="删除教育信息")
    @ApiImplicitParam(name = "eduId", value = "教育信息标识", required = true, dataType = "Long",paramType="path")
    @RequestMapping(value="/delTbEdu",method = RequestMethod.DELETE)
    @OperateLog(type= OperateType.DELETE, module="人员管理",methods="delTbEdu",desc="删除教育信息")
    public Object delTbEdu(Long eduId ){
        return  eduService.delTbEdu(eduId, SysUserInfo.getUserId());
    }

    @ApiOperation(value="教育信息查看",notes="教育信息")
    @ApiImplicitParam(name = "eduId", value = "教育信息标识", required = true, dataType = "Long",paramType="path")
    @RequestMapping(value="/getTbEdu",method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="人员管理",methods="getTbEdu",desc="教育信息查看")
    public Object getTbEdu(Long eduId){
        return eduService.getTbEdu(eduId);
    }

    @ApiOperation(value="教育信息分页查询",notes="教育信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path"),
    })
    @RequestMapping(value="/getTbEduPage",method = RequestMethod.GET)
    @OperateLog(type= OperateType.SELECT, module="人员管理",methods="getTbEduPage",desc="教育信息分页查询")
    public Object getTbEduPage( Long personnelId, Integer pageNo, Integer pageSize){
        return eduService.getTbEduPage(personnelId, pageNo, pageSize);
    }
}
