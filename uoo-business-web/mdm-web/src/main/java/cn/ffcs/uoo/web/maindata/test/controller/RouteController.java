package cn.ffcs.uoo.web.maindata.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 测试Controller
 *
 * @author Lin
 * @date 2018/08/23
 */
@Controller
public class RouteController {

    @RequestMapping("/aa")
    public ModelAndView index1() {
        return new ModelAndView("index");
    }

    @RequestMapping("/")
    public String defaultMapping() {
        return "redirect:index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/route/{url}")
    public String route(@PathVariable("url") String url) {
        return url.replaceAll("-", "/");
    }

}
