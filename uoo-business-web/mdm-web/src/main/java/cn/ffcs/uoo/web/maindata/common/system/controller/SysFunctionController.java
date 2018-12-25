package cn.ffcs.uoo.web.maindata.common.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.common.system.client.SysFunctionClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysFunction;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
@RestController
@RequestMapping("/system/sysFunction")
public class SysFunctionController {
    @Autowired
    SysFunctionClient funcClient;
    @RequestMapping("/listAll")
    public ResponseResult<List<SysFunction>> list(Integer pageNo,Integer pageSize,String keyWord){
        return funcClient.list(pageNo, pageSize, keyWord);
    }
    @RequestMapping("/getFunctionByAccout")
    public ResponseResult<List<SysFunction>> getFunctionByAccout(String accout){
        return funcClient.getFunctionByAccout(accout);
    }
}
