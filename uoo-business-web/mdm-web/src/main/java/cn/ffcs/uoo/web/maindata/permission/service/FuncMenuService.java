package cn.ffcs.uoo.web.maindata.permission.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.permission.dto.FuncMenu;
import cn.ffcs.uoo.web.maindata.permission.service.fallback.FuncMenuHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

/**
 * <p>
 * 功能模块和功能模块之间存在上下级关系，一个功能模块可以有多个下级功能模块，一个功能模块只能属于一个上级功能模块。例如：一级菜单功能“订单管理”下挂二级菜单功能“订单受理”、“订单查询”、“订单历史”。
 * 前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@FeignClient(value = "business-permission", fallback = FuncMenuHystrix.class)
public interface FuncMenuService {

    @RequestMapping(value = "/permission/funcMenu/getFuncMenuPage", method = RequestMethod.GET)
    public ResponseResult getFuncMenuPage();

    @RequestMapping(value = "/permission/funcMenu/addFuncMenu", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addFuncMenu(@RequestBody FuncMenu funcMenu);

    @RequestMapping(value = "/permission/funcMenu/updateFuncMenu", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult updateFuncMenu(@RequestBody FuncMenu funcMenu);

    @RequestMapping(value = "/permission/funcMenu/deleteFuncMenu", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult deleteFuncMenu(@RequestBody FuncMenu funcMenu);
}
