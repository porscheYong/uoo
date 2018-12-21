package cn.ffcs.uoo.core.personnel.controller;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页 前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-12-20
 */

@Api(description="首页",value="HomeStatistics")
@RestController
@RequestMapping("/homeStatistics")
public class HomeStatisticsController extends BaseController {

    @Autowired
    private TbPersonnelService tbPersonnelService;

    @ApiOperation(value = "首页统计", notes = "首页统计")
    @ApiImplicitParam(name = "labelType", value = "标签类型", required = true, dataType = "String",paramType="path")
    @UooLog(value = "首页统计", key = "getHomeStatistics")
    @RequestMapping(value = "/getHomeStatistics",method = RequestMethod.GET)
    public Object getHomeStatistics(String labelType){
        return tbPersonnelService.getHomeStatistics(labelType);
    }



}
