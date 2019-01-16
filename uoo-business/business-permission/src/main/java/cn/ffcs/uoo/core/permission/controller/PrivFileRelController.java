package cn.ffcs.uoo.core.permission.controller;


import cn.ffcs.uoo.core.permission.entity.PrivFileRel;
import cn.ffcs.uoo.core.permission.service.IPrivFileRelService;
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
 * 定义权限关联的文件，一个权限可包含多个文件。 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@RestController
@RequestMapping("/permission/privFileRel")
public class PrivFileRelController {
    @Autowired
    IPrivFileRelService iPrivFileRelService;
    @ApiOperation(value = "GET_ID", notes = "GET_ID")
    @ApiImplicitParams({
    })
    @UooLog(key="get",value="get")
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public ResponseResult<PrivFileRel> get(@RequestParam("id")Long id){
        return ResponseResult.createSuccessResult(iPrivFileRelService.selectById(id),"");
    }
    
    @ApiOperation(value = "LIST_PAGE", notes = "LIST_PAGE")
    @ApiImplicitParams({
    })
    @UooLog(key="LIST_PAGE",value="LIST_PAGE")
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public ResponseResult<Page<PrivFileRel>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Wrapper<PrivFileRel> wrapper = Condition.create().eq("STATUS_CD",StatusCD.VALID).orderBy("UPDATE_DATE", false);
        Page<PrivFileRel> page = iPrivFileRelService.selectPage(new Page<PrivFileRel>(pageNo, pageSize), wrapper);
        return ResponseResult.createSuccessResult(page ,"");
    }
    @ApiOperation(value = "add", notes = "add")
    @ApiImplicitParams({
    })
    @UooLog(key="add",value="add")
    @Transactional
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody PrivFileRel privFileRel){
        privFileRel.setCreateDate(new Date());
        privFileRel.setPrivFileId(iPrivFileRelService.getId());
        privFileRel.setStatusCd(StatusCD.VALID);
        privFileRel.setStatusDate(new Date());
        iPrivFileRelService.insert(privFileRel);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "update", notes = "update")
    @ApiImplicitParams({
    })
    @UooLog(key="update",value="update")
    @Transactional
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody PrivFileRel privFileRel){
        privFileRel.setUpdateDate(new Date());
        iPrivFileRelService.updateById(privFileRel);
        return ResponseResult.createSuccessResult("");
    }
    @ApiOperation(value = "delete", notes = "delete")
    @ApiImplicitParams({
    })
    @UooLog(key="delete",value="delete")
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @Transactional
    public ResponseResult<Void> delete(@RequestBody PrivFileRel privFileRel){
        privFileRel.setStatusCd(StatusCD.INVALID);
        privFileRel.setStatusDate(new Date());
        iPrivFileRelService.updateById(privFileRel);
        return ResponseResult.createSuccessResult("");
    }
}
