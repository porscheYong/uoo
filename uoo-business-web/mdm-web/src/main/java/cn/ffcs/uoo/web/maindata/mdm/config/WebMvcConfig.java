package cn.ffcs.uoo.web.maindata.mdm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.ffcs.uoo.web.maindata.mdm.interceptor.SystemLogHandlerInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSystemLogHandlerInterceptor());
        addInterceptor.addPathPatterns("/**");
        super.addInterceptors(registry);
    }
    @Bean
    public SystemLogHandlerInterceptor getSystemLogHandlerInterceptor(){
        return new SystemLogHandlerInterceptor();
    }
}
