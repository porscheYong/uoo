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
import cn.ffcs.uoo.core.resource.entity.BusinessFuncMap;
import cn.ffcs.uoo.core.resource.service.IBusinessFuncMapService;
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
@RequestMapping("/resource/businessFuncMap")
public class BusinessFuncMapController {
    @Autowired
    IBusinessFuncMapService iBusinessFuncMapService;
    @ApiOperation(value = "GET_ID", notes = "GET_ID")
    @ApiImplicitParams({
    })
    @UooLog(key="get",value="get")
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public ResponseResult<BusinessFuncMap> get(@RequestParam("id")Long id){
        return ResponseResult.createSuccessResult(iBusinessFuncMapService.selectById(id),"");
    }
    
    @ApiOperation(value = "LIST_PAGE", notes = "LIST_PAGE")
    @ApiImplicitParams({
    })
    @UooLog(key="LIST_PAGE",value="LIST_PAGE")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ResponseResult<Page<BusinessFuncMap>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Wrapper<BusinessFuncMap> wrapper = Condition.create().eq("STATUS_CD","1000").orderBy("UPDATE_DATE", false);
        Page<BusinessFuncMap> page = iBusinessFuncMapService.selectPage(new Page<BusinessFuncMap>(pageNo, pageSize), wrapper);
        return ResponseResult.createSuccessResult(page ,"");
    }
    @ApiOperation(value = "add", notes = "add")
    @ApiImplicitParams({
    })
    @UooLog(key="add",value="add")
    @Transactional
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody BusinessFuncMap businessFuncMap){
        businessFuncMap.setCreateDate(new Date());
        businessFuncMap.setMapId(iBusinessFuncMapService.getId());
        businessFuncMap.setStatusCd("1000");
        businessFuncMap.setStatusDate(new Date());
        iBusinessFuncMapService.insert(businessFuncMap);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "update", notes = "update")
    @ApiImplicitParams({
    })
    @UooLog(key="update",value="update")
    @Transactional
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody BusinessFuncMap businessFuncMap){
        businessFuncMap.setUpdateDate(new Date());
        iBusinessFuncMapService.updateById(businessFuncMap);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "delete", notes = "delete")
    @ApiImplicitParams({
    })
    @UooLog(key="delete",value="delete")
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @Transactional
    public ResponseResult<Void> delete(@RequestBody BusinessFuncMap businessFuncMap){
        businessFuncMap.setStatusCd("1100");
        businessFuncMap.setStatusDate(new Date());
        iBusinessFuncMapService.updateById(businessFuncMap);
        return ResponseResult.createSuccessResult("");
    }
}
