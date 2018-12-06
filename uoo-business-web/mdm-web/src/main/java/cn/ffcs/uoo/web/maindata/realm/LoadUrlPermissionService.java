package cn.ffcs.uoo.web.maindata.realm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.permission.dto.FuncComp;
import cn.ffcs.uoo.web.maindata.permission.dto.FuncMenu;
import cn.ffcs.uoo.web.maindata.permission.service.FuncCompService;
import cn.ffcs.uoo.web.maindata.permission.service.FuncMenuService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

@Service
public class LoadUrlPermissionService {
    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    @Autowired
    FuncCompService funcCompSvc;
    @Autowired
    FuncMenuService funcMenuSvc;

    public void updateUrlPermission() {
        AbstractShiroFilter shiroFilter = null;
        synchronized (shiroFilterFactoryBean) {
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            manager.getFilterChains().clear();

            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
            filterChainDefinitionMap.put("/logout", "logout");
            filterChainDefinitionMap.put("/css/**", "anon"); // 静态资源必须开放
            filterChainDefinitionMap.put("/contents/**", "anon"); //
            filterChainDefinitionMap.put("/fonts/**", "anon"); //
            filterChainDefinitionMap.put("/images/**", "anon"); //
            filterChainDefinitionMap.put("/js/**", "anon"); //
            filterChainDefinitionMap.put("/vendors/**", "anon"); //
            filterChainDefinitionMap.put("/system/sysUserLogin", "anon"); // 登陆接口必须开放
            // filterChainDefinitionMap.put("/index", "perms[index]");
            // filterChainDefinitionMap.put("/inaction/**", "perms[inaction]");

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
            }

            //filterChainDefinitionMap.put("/ff/ff", "perms[sad]");// 表示需要认证才可以访问
            filterChainDefinitionMap.put("/**", "authc");// 表示需要认证才可以访问

            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        }

    }

}
