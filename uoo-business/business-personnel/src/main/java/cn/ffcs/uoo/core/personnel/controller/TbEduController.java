package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.core.personnel.vo.TbPersonnelVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import cn.ffcs.uoo.base.controller.BaseController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@RestController
@RequestMapping("/tbEdu")
public class TbEduController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getMsg")
    public Object getMsg(){
        return restTemplate.getForObject("http://uoo-user/tbAcctExt/msg", Object.class);
    }

}

