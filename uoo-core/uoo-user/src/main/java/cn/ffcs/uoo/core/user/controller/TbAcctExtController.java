package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 主账号扩展 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@RestController
@RequestMapping("/tbAcctExt")
public class TbAcctExtController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/msg")
    public String getMsg(){
        return "sdfsdfsdfsdfs";
    }

    @GetMapping("/personnel")
    public Object getPersonnel(){
        return restTemplate.getForObject(
                "http://PERSONNEL-SERVICE/personnel/getPage/pageNo=1&pageSize=20",
                Object.class
                );
    }

}

