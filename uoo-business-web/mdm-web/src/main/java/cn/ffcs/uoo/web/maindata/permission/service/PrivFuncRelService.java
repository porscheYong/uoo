package cn.ffcs.uoo.web.maindata.permission.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.permission.dto.PrivFuncRel;
import cn.ffcs.uoo.web.maindata.permission.service.fallback.PrivFuncRelHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

/**
 * <p>
 * 定义权限关联的功能菜单、功能组件，一个权限可包含多个功能菜单或功能组件。 前端控制器
 * </p>
 *
 */
@FeignClient(value = "business-permission", fallback = PrivFuncRelHystrix.class)
public interface PrivFuncRelService {
    @GetMapping("/permission/privFuncRel/getPrivFuncRel/{id}")
    public ResponseResult<PrivFuncRel> getPrivFuncRel(@PathVariable(value = "id", required = true) Long id);

    @GetMapping("/permission/privFuncRel/listPrivFuncRel/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult<List<PrivFuncRel>> listPrivFuncRel(@PathVariable(value = "pageNo") Integer pageNo,
            @PathVariable(value = "pageSize", required = false) Integer pageSize);

    @RequestMapping(value = "/permission/privFuncRel/addPrivFuncRel", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> addPrivFuncRel(@RequestBody PrivFuncRel privFuncRel);

    @RequestMapping(value = "/permission/privFuncRel/updatePrivFuncRel", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> updatePrivFuncRel(@RequestBody PrivFuncRel privFuncRel);

    @RequestMapping(value = "/permission/privFuncRel/deletePrivFuncRel", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<Void> deleteFuncComp(@RequestBody PrivFuncRel privFuncRel);
}
