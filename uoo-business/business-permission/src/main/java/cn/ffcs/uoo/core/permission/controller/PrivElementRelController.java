package cn.ffcs.uoo.core.permission.controller;


import cn.ffcs.uoo.core.permission.entity.PrivElementRel;
import cn.ffcs.uoo.core.permission.service.IPrivElementRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 定义权限关联的元素，一个权限可包含多个元素。 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@RestController
@RequestMapping("/permission/privElementRel")
public class PrivElementRelController {
    @Autowired
    IPrivElementRelService iPrivElementRelService;
    @ApiOperation(value = "GET_ID", notes = "GET_ID")
    @ApiImplicitParams({
    })
    @UooLog(key="get",value="get")
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public ResponseResult<PrivElementRel> get(@RequestParam("id")Long id){
        return ResponseResult.createSuccessResult(iPrivElementRelService.selectById(id),"");
    }
    
    @ApiOperation(value = "LIST_PAGE", notes = "LIST_PAGE")
    @ApiImplicitParams({
    })
    @UooLog(key="LIST_PAGE",value="LIST_PAGE")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ResponseResult<Page<PrivElementRel>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Wrapper<PrivElementRel> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<PrivElementRel> page = iPrivElementRelService.selectPage(new Page<PrivElementRel>(pageNo, pageSize), wrapper);
        return ResponseResult.createSuccessResult(page ,"");
    }
    @ApiOperation(value = "add", notes = "add")
    @ApiImplicitParams({
    })
    @UooLog(key="add",value="add")
    @Transactional
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody PrivElementRel privElementRel){
        privElementRel.setCreateDate(new Date());
        privElementRel.setPrivElementId(iPrivElementRelService.getId());
        privElementRel.setStatusCd(StatusCD.VALID);
        privElementRel.setStatusDate(new Date());
        iPrivElementRelService.insert(privElementRel);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "update", notes = "update")
    @ApiImplicitParams({
    })
    @UooLog(key="update",value="update")
    @Transactional
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody PrivElementRel privElementRel){
        privElementRel.setUpdateDate(new Date());
        iPrivElementRelService.updateById(privElementRel);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "delete", notes = "delete")
    @ApiImplicitParams({
    })
    @UooLog(key="delete",value="delete")
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @Transactional
    public ResponseResult<Void> delete(@RequestBody PrivElementRel privElementRel){
        privElementRel.setStatusCd(StatusCD.INVALID);
        privElementRel.setStatusDate(new Date());
        iPrivElementRelService.updateById(privElementRel);
        return ResponseResult.createSuccessResult("");
    }
}
