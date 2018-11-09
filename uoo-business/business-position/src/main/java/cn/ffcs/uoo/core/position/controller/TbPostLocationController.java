package cn.ffcs.uoo.core.position.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.position.entity.TbPostLocation;
import cn.ffcs.uoo.core.position.service.TbPostLocationService;
import cn.ffcs.uoo.core.position.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class TbPostLocationController extends BaseController {
    @Autowired
    private TbPostLocationService tbPostLocationService;

    @ApiOperation(value = "新增职位行政区域", notes = "新增职位行政区域")
    @ApiImplicitParam(name = "tbPostLocation", value = "职位行政区域", required = true, dataType = "TbPostLocation")
    @UooLog(value = "新增职位行政区域", key = "addTbPostLocation")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbPostLocation> addTbPostLocation(@RequestBody TbPostLocation tbPostLocation) {
        ResponseResult<TbPostLocation> responseResult = new ResponseResult<TbPostLocation>();
        // 校验必填项
        if(tbPostLocation.getRegionId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入区域标识");
            return responseResult;
        }
        if(tbPostLocation.getPostId() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入职位标识");
            return responseResult;
        }

        tbPostLocationService.save(tbPostLocation);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增职位行政区域成功");
        return responseResult;
    }

    @ApiOperation(value = "删除职位行政区域",notes = "删除职位行政区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postLocationId", value = "职位行政区域标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @UooLog(value = "删除职位行政区域", key = "removeTbPostLocation")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbPostLocation> removeTbPostLocation(@RequestBody Long postLocationId, @RequestBody Long updateUser) {
        ResponseResult<TbPostLocation> responseResult = new ResponseResult<TbPostLocation>();

        // 校验必填项
        if(postLocationId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入职位行政区域标识");
            return responseResult;
        }
        if(updateUser == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入修改人");
            return responseResult;
        }

        tbPostLocationService.remove(postLocationId, updateUser);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除职位行政区域成功");
        return responseResult;
    }
}

