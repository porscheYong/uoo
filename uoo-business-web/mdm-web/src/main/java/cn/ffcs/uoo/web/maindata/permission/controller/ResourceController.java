package cn.ffcs.uoo.web.maindata.permission.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.entity.Resource;
import cn.ffcs.uoo.core.permission.service.ResourceService;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    @ApiOperation(value = "获取单个资源", notes = "获取单个资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="getResource",value="获取单个资源")
    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable(value="id" ,required=true) Long id){
        Resource resource = resourceService.selectById(id);
        if(resource== null){
            return ResponseResult.createErrorResult("无效数据");
        }
        return ResponseResult.createSuccessResult(resource, "success");
    }

    @ApiOperation(value = "获取资源列表", notes = "获取资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long" ,paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long" ,paramType="path"),
    })
    @UooLog(key="listResource",value="获取资源列表")
    @GetMapping("/listResource/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listRoles(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;

        Wrapper<Resource> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<Resource> page = resourceService.selectPage(new Page<Resource>(pageNo, pageSize), wrapper);

        return ResponseResult.createSuccessResult(page.getRecords(), "", page);
    }

    @ApiOperation(value = "删除资源",notes = "删除资源(只需要resourceId)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "资源 ", required = true, dataType = "Roles"),
    })
    @UooLog(value = "删除资源", key = "removeTbResource")
    @Transactional
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult removeTbRoles(@RequestBody Resource resource ) {
        ResponseResult responseResult = new ResponseResult();
        Long resourceId = resource.getPkResource();
        // 校验必填项
        if(resourceId == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入资源id");
            return responseResult;
        }

        resourceService.deleteById(resourceId);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除成功");
        return responseResult;
    }

    @ApiOperation(value = "修改资源",notes = "修改资源")
    @ApiImplicitParam(name = "tbRoles", value = "资源", required = true, dataType = "Roles")
    @UooLog(value = "修改资源", key = "updateTbRoles")
    @Transactional
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult updateTbRoles(@RequestBody Resource resource) {
        ResponseResult responseResult = new ResponseResult();
        // 校验必填项
        if(resource.getPkResource() == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("请输入资源id");
            return responseResult;
        }

        resourceService.updateById(resource);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("修改资源成功");
        return responseResult;
    }

    @ApiOperation(value = "新增资源",notes = "新增资源")
    @ApiImplicitParam(name = "Resource", value = "资源", required = true, dataType = "Resource")
    @UooLog(value = "新增资源", key = "addTbResource")
    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult addResource(@RequestBody Resource resource) {
        ResponseResult responseResult = new ResponseResult();

        resource.setPkResource(resourceService.getId());
        resourceService.insert(resource);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("新增角色成功");
        return responseResult;
    }
}

