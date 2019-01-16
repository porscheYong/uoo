package cn.ffcs.uoo.core.resource.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.resource.entity.Func;
import cn.ffcs.uoo.core.resource.service.IFuncService;
import cn.ffcs.uoo.core.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@RestController
@RequestMapping("/resource/func")
public class FuncController {
    @Autowired
    IFuncService iFuncService;
    @ApiOperation(value = "GET_ID", notes = "GET_ID")
    @ApiImplicitParams({
    })
    @UooLog(key="get",value="get")
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public ResponseResult<Func> get(@RequestParam("id")Long id){
        return ResponseResult.createSuccessResult(iFuncService.selectById(id),"");
    }
    
    @ApiOperation(value = "LIST_PAGE", notes = "LIST_PAGE")
    @ApiImplicitParams({
    })
    @UooLog(key="LIST_PAGE",value="LIST_PAGE")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ResponseResult<Page<Func>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Wrapper<Func> wrapper = Condition.create().eq("STATUS_CD","1000").orderBy("UPDATE_DATE", false);
        Page<Func> page = iFuncService.selectPage(new Page<Func>(pageNo, pageSize), wrapper);
        return ResponseResult.createSuccessResult(page ,"");
    }
    @ApiOperation(value = "add", notes = "add")
    @ApiImplicitParams({
    })
    @UooLog(key="add",value="add")
    @Transactional
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody Func func){
        func.setCreateDate(new Date());
        func.setFuncId(iFuncService.getId());
        func.setStatusCd("1000");
        func.setStatusDate(new Date());
        iFuncService.insert(func);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "update", notes = "update")
    @ApiImplicitParams({
    })
    @UooLog(key="update",value="update")
    @Transactional
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody Func func){
        func.setUpdateDate(new Date());
        iFuncService.updateById(func);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "delete", notes = "delete")
    @ApiImplicitParams({
    })
    @UooLog(key="delete",value="delete")
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @Transactional
    public ResponseResult<Void> delete(@RequestBody Func func){
        func.setStatusCd("1100");
        func.setStatusDate(new Date());
        iFuncService.updateById(func);
        return ResponseResult.createSuccessResult("");
    }
}
