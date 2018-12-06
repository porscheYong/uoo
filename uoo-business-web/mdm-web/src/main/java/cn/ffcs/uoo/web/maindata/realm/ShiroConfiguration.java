package cn.ffcs.uoo.web.maindata.realm;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import cn.ffcs.uoo.web.maindata.permission.dto.FuncComp;
import cn.ffcs.uoo.web.maindata.permission.dto.FuncMenu;
import cn.ffcs.uoo.web.maindata.permission.service.FuncCompService;
import cn.ffcs.uoo.web.maindata.permission.service.FuncMenuService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

@Configuration
public class ShiroConfiguration {
   
    @Autowired 
    FuncCompService funcCompSvc;
    @Autowired 
    FuncMenuService funcMenuSvc;
    
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403.html");
        // 配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/css/**", "anon"); //静态资源必须开放
        filterChainDefinitionMap.put("/contents/**", "anon"); //
        filterChainDefinitionMap.put("/fonts/**", "anon"); //
        filterChainDefinitionMap.put("/images/**", "anon"); //
        filterChainDefinitionMap.put("/js/**", "anon"); //
        filterChainDefinitionMap.put("/vendors/**", "anon"); //
        filterChainDefinitionMap.put("/system/sysUserLogin", "anon"); // 登陆接口必须开放
        //filterChainDefinitionMap.put("/index", "perms[index]");
        //filterChainDefinitionMap.put("/inaction/**", "perms[inaction]");
        /*
        ResponseResult<List<FuncComp>> listFuncComp = funcCompSvc.listFuncComp(1, Integer.MAX_VALUE);
        ResponseResult<List<FuncMenu>> listMenuComp = funcMenuSvc.getFuncMenuPage();

        if (listFuncComp.getData()!=null&&!listFuncComp.getData().isEmpty()) {
            List<FuncComp> comps = listFuncComp.getData();
            for (FuncComp funcComp : comps) {
                filterChainDefinitionMap.put(funcComp.getUrlAddr(), "perms[C" + funcComp.getCompId() + "]");
            }
        }
        if (listMenuComp.getData()!=null&&!listMenuComp.getData().isEmpty()) {
            List<FuncMenu> comps = listMenuComp.getData();
            for (FuncMenu funcComp : comps) {
                filterChainDefinitionMap.put(funcComp.getUrlAddr(), "perms[M" + funcComp.getMenuId() + "]");
            }
        }*/
        
        
        
        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(ucServerRealm());
        return defaultSecurityManager;
    }

    @Bean
    public UooRealm ucServerRealm() {
        UooRealm uooRealm = new UooRealm();
        uooRealm.setCacheManager(new MemoryConstrainedCacheManager());
        return uooRealm;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor
                = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /*@Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }*/
}
