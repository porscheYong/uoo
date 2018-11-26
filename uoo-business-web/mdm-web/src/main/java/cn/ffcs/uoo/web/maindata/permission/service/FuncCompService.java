package cn.ffcs.uoo.web.maindata.permission.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.permission.dto.FuncComp;
import cn.ffcs.uoo.web.maindata.permission.service.fallback.FuncCompHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

/**
 * <p>
 * 指系统内的系统功能菜单的最小功能单元及组件。 前端控制器
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@FeignClient(value = "uoo-permission-provider", fallback = FuncCompHystrix.class)
public interface FuncCompService {
     
    @GetMapping("/permission/funcComp/get/{id}")
    public ResponseResult get(@PathVariable(value="id" ,required=true) Long id);
     
    @GetMapping("/permission/funcComp/listFuncComp/pageNo={pageNo}&pageSize={pageSize}")
    public ResponseResult listFuncComp(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);
     
    @RequestMapping(value="/permission/funcComp/addFuncComp",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addFuncComp(@RequestBody FuncComp funcComp);
     
    @RequestMapping(value="/permission/funcComp/updateFuncComp",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult updateFuncComp(@RequestBody FuncComp funcComp);
     
    @RequestMapping(value="/permission/funcComp/deleteFuncComp",method=RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult deleteFuncComp(@RequestBody FuncComp funcComp);
}

