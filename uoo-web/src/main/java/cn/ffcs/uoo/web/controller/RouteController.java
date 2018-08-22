package cn.ffcs.uoo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RouteController {

    @GetMapping("/")
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
