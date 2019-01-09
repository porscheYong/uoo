package cn.ffcs.uoo.system.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysFunction;
import cn.ffcs.uoo.system.service.ISysFunctionService;
import cn.ffcs.uoo.system.service.ISysPermissionFuncRelService;
import cn.ffcs.uoo.system.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/system/sysFunction")
public class SysFunctionController {
    @Autowired
    ISysFunctionService funcSvc;
    @Autowired
    ISysPermissionFuncRelService permFuncSvc;
    @ApiOperation(value = "分页查询api", notes = "分页查询api")
    @ApiImplicitParams({
    })
    @UooLog(key="listAll",value="分页查询api")
    @SuppressWarnings("unchecked")
    @RequestMapping("/listAll")
    public ResponseResult<Page<SysFunction>> list(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize,@RequestParam("keyWord")String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Wrapper<SysFunction> wrapper = Condition.create().eq("STATUS_CD", StatusCD.VALID);
        if(StringUtils.isNotBlank(keyWord)){
            wrapper.andNew("FUNC_NAME like {0}", "'%"+keyWord+"%'").or("FUNC_CODE like {0}","'%"+keyWord+"%'");
        }
        Page<SysFunction> page = funcSvc.selectPage(new Page<SysFunction>(pageNo, pageSize), wrapper);
        ResponseResult<Page<SysFunction>> rr = ResponseResult.createSuccessResult("");
        rr.setData(page);
        return rr;
    }
    @ApiOperation(value = "查询账号下的api", notes = "查询账号下的api")
    @ApiImplicitParams({
    })
    @UooLog(key="getFunctionByAccout",value="查询账号下的api")
    @RequestMapping("/getFunctionByAccout")
    public ResponseResult<List<SysFunction>> getFunctionByAccout(String accout){
        List<SysFunction> selectList = funcSvc.getFunctionByAccout(accout);
        return ResponseResult.createSuccessResult(selectList, "");
    }
    @ApiOperation(value = "get", notes = "get")
    @ApiImplicitParams({
    })
    @UooLog(key="get",value="get")
    @RequestMapping("/get")
    public ResponseResult<SysFunction> get(@RequestParam("id")Long id){
        return ResponseResult.createSuccessResult(funcSvc.selectById(id), "");
    }
    @Transactional
    @ApiOperation(value = "新增", notes = "新增")
    @ApiImplicitParams({
    })
    @UooLog(key="add",value="新增")
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public ResponseResult<Void> add(@RequestBody SysFunction fun){
        String funcCode = fun.getFuncCode();
        long size=funcSvc.selectCount(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("FUNC_CODE", funcCode));
        if(size>0){
            return ResponseResult.createErrorResult("编码已存在");
        }
        fun.setFuncId(funcSvc.getId());
        fun.setCreateDate(new Date());
        funcSvc.insert(fun);
        return ResponseResult.createSuccessResult("新增成功");
    }
    @Transactional
    @ApiOperation(value = "更新", notes = "更新")
    @ApiImplicitParams({
    })
    @UooLog(key="update",value="更新")
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public ResponseResult<Void> update(@RequestBody SysFunction fun){
        String funcCode = fun.getFuncCode();
        SysFunction one = funcSvc.selectById(fun.getFuncId());
        if(one==null){
            return ResponseResult.createErrorResult("ID不存在");
        }
        List<SysFunction> tmp = funcSvc.selectList(Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("FUNC_CODE", funcCode));
        if(tmp!=null&&!tmp.isEmpty()){
            if(tmp.size()>1){
                return ResponseResult.createErrorResult("编码已存在");
            }else{
                SysFunction obj = tmp.get(0);
                if(!obj.getFuncId().equals(fun.getFuncId())){
                    return ResponseResult.createErrorResult("编码已存在");
                }
            }
        }
        if(!fun.getFuncCode().equals(one.getFuncCode())){
            permFuncSvc.updateForSet("FUNC_CODE='"+funcCode+"'", Condition.create().eq("STATUS_CD", StatusCD.VALID).eq("FUNC_CODE", one.getFuncCode()));
        }
        fun.setUpdateDate(new Date());
        funcSvc.updateById(fun);
        return ResponseResult.createSuccessResult("修改成功");
    }
    @Transactional
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
    })
    @UooLog(key="delete",value="删除")
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public ResponseResult<Void> delete(@RequestBody SysFunction fun){
        SysFunction df=new SysFunction();
        df.setFuncId(fun.getFuncId());
        df.setStatusCd(StatusCD.INVALID);
        df.setStatusDate(new Date());
        funcSvc.updateById(df);
        return ResponseResult.createSuccessResult("修改成功");
    }
}
