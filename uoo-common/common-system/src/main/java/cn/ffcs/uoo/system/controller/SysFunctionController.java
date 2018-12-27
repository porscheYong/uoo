package cn.ffcs.uoo.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.system.consts.StatusCD;
import cn.ffcs.uoo.system.entity.SysFunction;
import cn.ffcs.uoo.system.service.ISysFunctionService;
import cn.ffcs.uoo.system.vo.ResponseResult;
@RestController
@RequestMapping("/system/sysFunction")
public class SysFunctionController {
    @Autowired
    ISysFunctionService funcSvc;
    @SuppressWarnings("unchecked")
    @RequestMapping("/listAll")
    public ResponseResult<List<SysFunction>> list(Integer pageNo,Integer pageSize,String keyWord){
        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        Wrapper<SysFunction> wrapper = Condition.create().eq("STATUS_CD", StatusCD.VALID);
        Page<SysFunction> page = funcSvc.selectPage(new Page<SysFunction>(pageNo, pageSize), wrapper);
        return ResponseResult.createSuccessResult(page , "");
    }
    @RequestMapping("/getFunctionByAccout")
    public ResponseResult<List<SysFunction>> getFunctionByAccout(String accout){
        List<SysFunction> selectList = funcSvc.getFunctionByAccout(accout);
        return ResponseResult.createSuccessResult(selectList, "");
    }
}
