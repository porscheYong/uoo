package cn.ffcs.uoo.web.maindata.position.controller;

import cn.ffcs.uoo.web.maindata.position.dto.TbPostLocation;
import cn.ffcs.uoo.web.maindata.position.service.TbPostLocationClient;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
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
 * <p>
 * 职位行政区域 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
@Api(description = "职位行政区域",value = "PostLocation")
@RestController
@RequestMapping("/tbPostLocation")
public class TbPostLocationController {
    @Resource
    TbPostLocationClient tbPostLocationClient;

    @ApiOperation(value = "新增职位行政区域", notes = "新增职位行政区域")
    @ApiImplicitParam(name = "tbPostLocation", value = "职位行政区域", required = true, dataType = "TbPostLocation")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbPostLocation> addTbPostLocation(@RequestBody TbPostLocation tbPostLocation) {
        return tbPostLocationClient.addTbPostLocation(tbPostLocation);
    }

    @ApiOperation(value = "删除职位行政区域",notes = "删除职位行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postLocationId", value = "职位行政区域标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbPostLocation> removeTbPostLocation(Long postLocationId, Long updateUser) {
        return tbPostLocationClient.removeTbPostLocation(postLocationId, updateUser);
    }
}

