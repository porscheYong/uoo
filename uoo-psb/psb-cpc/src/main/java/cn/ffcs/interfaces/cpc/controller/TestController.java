package cn.ffcs.interfaces.cpc.controller;

import cn.ffcs.interfaces.cpc.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

   /* @Value("${sleuth.sampler.percentage}")
    private Long id;
*/
    @Autowired
    private SystemConstant systemConstant;
    @GetMapping("test")
    @ResponseBody
    public String test(){
        return /*"id:"+id+*/",SystemConstant:"+systemConstant.getCpcSystemId();
    }
}
