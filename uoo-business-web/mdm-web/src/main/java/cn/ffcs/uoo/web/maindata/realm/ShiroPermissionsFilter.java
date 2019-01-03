package cn.ffcs.uoo.web.maindata.realm;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;

public class ShiroPermissionsFilter extends PermissionsAuthorizationFilter{
    private static final Logger logger = LoggerFactory.getLogger(ShiroPermissionsFilter.class);
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        //logger.info("----------权限控制-------------");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String header = httpServletRequest.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest" .equals(header) ;
        if (isAjax) {//如果是ajax返回指定格式数据
            logger.info("----------AJAX请求拒绝-------------");
            logger.info("url:{}",httpServletRequest.getRequestURI());
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            //返回禁止访问json字符串
            
            httpServletResponse.getWriter().write(JSON.toJSONString(ResponseResult.createErrorResult("您没有足够的权限操作此功能")));
        } else {//如果是普通请求进行重定向
            logger.info("----------普通请求拒绝-------------");
            logger.info("url:{}",httpServletRequest.getRequestURI());
            httpServletResponse.sendRedirect("/403");
        }
        return false;
    }
 
}
