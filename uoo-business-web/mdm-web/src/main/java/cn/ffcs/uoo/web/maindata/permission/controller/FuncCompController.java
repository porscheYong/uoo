package cn.ffcs.uoo.web.maindata.permission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.permission.dto.FuncComp;
import cn.ffcs.uoo.web.maindata.permission.service.FuncCompService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 指系统内的系统功能菜单的最小功能单元及组件。 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@RestController
@RequestMapping("/permission/funcComp")
public class FuncCompController {
    @Autowired
    private FuncCompService funcCompService;

    @ApiOperation(value = "获取单个功能组件", notes = "获取单个功能组件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path"), })
    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable(value = "id", required = true) Long id) {
        return funcCompService.get(id);
    }

    @ApiOperation(value = "获取功能组件列表", notes = "获取功能组件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "Long", paramType = "path"), })
    @GetMapping("/listFuncComp/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listFuncComp(@PathVariable(value = "pageNo") Integer pageNo,
            @PathVariable(value = "pageSize", required = false) Integer pageSize) {
        return funcCompService.listFuncComp(pageNo, pageSize);
    }

    @ApiOperation(value = "添加功能组件列表", notes = "添加功能组件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "funcComp", value = "funcComp", required = true, dataType = "FuncComp"), })
    @RequestMapping(value = "/addFuncComp", method = RequestMethod.POST)
    public ResponseResult addFuncComp(@RequestBody FuncComp funcComp) {

        return funcCompService.addFuncComp(funcComp);
    }

    @ApiOperation(value = "修改功能组件列表", notes = "修改功能组件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "funcComp", value = "funcComp", required = true, dataType = "FuncComp"), })
    @RequestMapping(value = "/updateFuncComp", method = RequestMethod.POST)
    public ResponseResult updateFuncComp(@RequestBody FuncComp funcComp) {

        return funcCompService.updateFuncComp(funcComp);
    }

    @ApiOperation(value = "删除功能组件列表", notes = "删除功能组件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "funcComp", value = "funcComp", required = true, dataType = "FuncComp"), })
    @RequestMapping(value = "/deleteFuncComp", method = RequestMethod.POST)
    public ResponseResult deleteFuncComp(@RequestBody FuncComp funcComp) {

        return funcCompService.deleteFuncComp(funcComp);
    }
}
