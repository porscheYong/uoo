package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.personnel.entity.TbEdu;
import cn.ffcs.uoo.core.personnel.service.TbEduService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/tbEdu")
public class TbEduController extends BaseController {

    @Autowired
    private TbEduService tbEduService;

    @ApiOperation(value = "新增教育信息", notes = "新增教育信息")
    @ApiImplicitParam(name = "tbEdu", value = "教育信息", required = true, dataType = "TbEdu")
    @UooLog(value = "新增教育信息", key = "saveTbEdu")
    @RequestMapping(value = "/saveTbEdu", method = RequestMethod.POST)
    public Object saveTbEdu(@RequestBody TbEdu tbEdu) {
        return tbEduService.saveTbEdu(tbEdu);
    }

    @ApiOperation(value="更新教育信息",notes="更新教育信息")
    @ApiImplicitParam(name = "tbEdu", value = "教育信息", required = true, dataType = "TbEdu")
    @UooLog(value = "更新教育信息",key = "updateTbEdu")
    @RequestMapping(value="/updateTbEdu",method = RequestMethod.PUT)
    public Object updateTbEdu(@RequestBody TbEdu tbEdu){
        return  tbEduService.updateTbEdu(tbEdu);
    }

    @ApiOperation(value="删除教育信息",notes="删除教育信息")
    @ApiImplicitParam(name = "eduId", value = "教育信息标识", required = true, dataType = "Long",paramType="path")
    @UooLog(value = "删除教育信息标识",key = "delTbEdu")
    @RequestMapping(value="/delTbEdu",method = RequestMethod.DELETE)
    public Object delTbEdu(Long eduId ){
        return  tbEduService.delTbEdu(eduId);
    }

    @ApiOperation(value="教育信息查看",notes="教育信息")
    @ApiImplicitParam(name = "eduId", value = "教育信息标识", required = true, dataType = "Long",paramType="path")
    @UooLog(value = "教育信息查看",key = "getTbEdu")
    @RequestMapping(value="/getTbEdu",method = RequestMethod.GET)
    public Object getTbEdu(Long eduId){
        return tbEduService.getTbEduById(eduId);
    }

    @ApiOperation(value="教育信息分页查询",notes="教育信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "Long",paramType="path"),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "Integer",paramType="path"),
    })
    @UooLog(value = "教育信息查看",key = "getTbEduPage")
    @RequestMapping(value="/getTbEduPage",method = RequestMethod.GET)
    public Object getTbEduPage( Long personnelId, Integer pageNo, Integer pageSize){

        return tbEduService.getTbEduPage(personnelId, pageNo, pageSize);
    }

}

