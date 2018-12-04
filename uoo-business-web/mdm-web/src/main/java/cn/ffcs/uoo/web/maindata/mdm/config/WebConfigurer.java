package cn.ffcs.uoo.web.maindata.mdm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.ffcs.uoo.web.maindata.mdm.interceptor.LoginInterceptor;
@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getLoginInterceptor());
        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login/**");
        addInterceptor.excludePathPatterns("/login");
        addInterceptor.excludePathPatterns("/system/sysUserLogin");
        addInterceptor.excludePathPatterns("/swagger-resources/**");
        addInterceptor.excludePathPatterns("/webjars/**");
        addInterceptor.excludePathPatterns("/v2/**");
        addInterceptor.excludePathPatterns("/swagger-ui.html/**");
       // addInterceptor.excludePathPatterns("/captcha");// 排除验证码
        // 拦截配置
        addInterceptor.addPathPatterns("/**");
        addInterceptor.addPathPatterns("/**/**");
    }
    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }
}
