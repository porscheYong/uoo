package cn.ffcs.uoo.core.permission.controller;


import cn.ffcs.uoo.core.permission.entity.PrivMenuRel;
import cn.ffcs.uoo.core.permission.service.IPrivMenuRelService;
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
 * 定义权限关联的菜单，一个权限可包含多个菜单。 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@RestController
@RequestMapping("/permission/privMenuRel")
public class PrivMenuRelController {
    @Autowired
    IPrivMenuRelService iPrivMenuRelService;
    @ApiOperation(value = "GET_ID", notes = "GET_ID")
    @ApiImplicitParams({
    })
    @UooLog(key="get",value="get")
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public ResponseResult<PrivMenuRel> get(@RequestParam("id")Long id){
        return ResponseResult.createSuccessResult(iPrivMenuRelService.selectById(id),"");
    }
    
    @ApiOperation(value = "LIST_PAGE", notes = "LIST_PAGE")
    @ApiImplicitParams({
    })
    @UooLog(key="LIST_PAGE",value="LIST_PAGE")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ResponseResult<Page<PrivMenuRel>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Wrapper<PrivMenuRel> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<PrivMenuRel> page = iPrivMenuRelService.selectPage(new Page<PrivMenuRel>(pageNo, pageSize), wrapper);
        return ResponseResult.createSuccessResult(page ,"");
    }
    @ApiOperation(value = "add", notes = "add")
    @ApiImplicitParams({
    })
    @UooLog(key="add",value="add")
    @Transactional
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody PrivMenuRel privMenuRel){
        privMenuRel.setCreateDate(new Date());
        privMenuRel.setPrivMenuId(iPrivMenuRelService.getId());
        privMenuRel.setStatusCd(StatusCD.VALID);
        privMenuRel.setStatusDate(new Date());
        iPrivMenuRelService.insert(privMenuRel);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "update", notes = "update")
    @ApiImplicitParams({
    })
    @UooLog(key="update",value="update")
    @Transactional
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody PrivMenuRel privMenuRel){
        privMenuRel.setUpdateDate(new Date());
        iPrivMenuRelService.updateById(privMenuRel);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "delete", notes = "delete")
    @ApiImplicitParams({
    })
    @UooLog(key="delete",value="delete")
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @Transactional
    public ResponseResult<Void> delete(@RequestBody PrivMenuRel privMenuRel){
        privMenuRel.setStatusCd(StatusCD.INVALID);
        privMenuRel.setStatusDate(new Date());
        iPrivMenuRelService.updateById(privMenuRel);
        return ResponseResult.createSuccessResult("");
    }
}
