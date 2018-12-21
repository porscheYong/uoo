package cn.ffcs.uoo.web.maindata.personnel.controller;

import cn.ffcs.uoo.web.maindata.personnel.service.HomeStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
 * @ClassName HomeStatisticsController
 * @Description
 * @author wudj
 * @date 2018/12/20
 * @Version 1.0.0
 */
@RestController
@RequestMapping(value = "/homeStatistics", produces = {"application/json;charset=UTF-8"})
@Api(value = "/homeStatistics", description = "人员相关操作")
public class HomeStatisticsController {

    @Resource
    private HomeStatisticsService homeStatisticsService;

    @ApiOperation(value = "首页统计", notes = "首页统计")
    @ApiImplicitParam(name = "labelType", value = "标签类型", required = true, dataType = "String",paramType="path")
    @RequestMapping(value = "/getHomeStatistics",method = RequestMethod.GET)
    public Object getHomeStatistics(String labelType){
        return homeStatisticsService.getHomeStatistics(labelType);
    }
}
