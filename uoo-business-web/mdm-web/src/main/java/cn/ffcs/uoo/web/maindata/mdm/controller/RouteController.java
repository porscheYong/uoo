package cn.ffcs.uoo.web.maindata.mdm.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.realm.LoadUrlPermissionService;

/**
 * 测试Controller
 *
 * @author Lin
 * @date 2018/08/23
 */
@Controller
public class RouteController {
    @Autowired
    LoadUrlPermissionService loadUrlPermissionService;
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

    @GetMapping("/login")
    public String login(HttpServletRequest request,HttpServletResponse response) {
        Subject sub=SecurityUtils.getSubject();
        if(sub!=null&&sub.getSession().getAttribute(LoginConsts.LOGIN_KEY)!=null){
            try {
                response.sendRedirect("/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "loginPage";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) {
        request.getSession().invalidate();
        System.out.println("--------------logout------------------------");
        return "loginPage";
    }
    @GetMapping("/route/{url}")
    public String route(@PathVariable("url") String url) {
        return url.replaceAll("-", "/");
    }
    /*@GetMapping("/system/getCurrentLoginUserInfo")
    @ResponseBody
    public Object getCurrentLoginUserInfo(HttpServletRequest request){
        return request.getSession().getAttribute(LoginConsts.LOGIN_KEY);
    }*/
    
    @GetMapping("/reloadUrlPermission")
    @ResponseBody
    public ResponseResult<Void> reloadUrlPermission(){
        loadUrlPermissionService.updateUrlPermission();
        return ResponseResult.createSuccessResult("");
    }
    @GetMapping("/testReloadUrlPermission")
    @ResponseBody
    public ResponseResult<Void> testReloadUrlPermission(){
        loadUrlPermissionService.notifyAllWebNodeUpdateUrlPermission();
        return ResponseResult.createSuccessResult("test success");
    }
}
