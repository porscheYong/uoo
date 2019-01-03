package cn.ffcs.uoo.web.maindata.realm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cn.ffcs.uoo.web.maindata.common.system.client.SysFunctionClient;
import cn.ffcs.uoo.web.maindata.common.system.client.SysMenuClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysFunction;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysMenu;
import cn.ffcs.uoo.web.maindata.permission.dto.FuncComp;
import cn.ffcs.uoo.web.maindata.permission.dto.FuncMenu;
import cn.ffcs.uoo.web.maindata.permission.service.FuncCompService;
import cn.ffcs.uoo.web.maindata.permission.service.FuncMenuService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

import javax.annotation.Resource;

@Service
public class LoadUrlPermissionService {
    private static Logger log = LoggerFactory.getLogger(LoadUrlPermissionService.class);
    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    /*@Autowired
    FuncCompService funcCompSvc;
    @Autowired
    FuncMenuService funcMenuSvc;*/
    @Autowired
    private DiscoveryClient discoveryClient;
    @Resource
    private SysMenuClient sysMenuClient;
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    RestTemplate restTemplate;
    @Resource
    SysFunctionClient sysFunctionClient;

    private ConcurrentLinkedQueue<String> retryUrls = new ConcurrentLinkedQueue<>();

    @Async
    public ResponseResult<Void> notifyAllWebNodeUpdateUrlPermission() {
        retryUrls.clear();
        List<ServiceInstance> instances = discoveryClient.getInstances(appName);
        log.info("所有服务：");
        log.info(instances.toString());
        for (ServiceInstance ins : instances) {
            String url = "http://" + ins.getHost() + ":" + ins.getPort() + "/reloadUrlPermission";
            ResponseResult<Void> rr = ResponseResult.createErrorResult("");
            try {
                rr = restTemplateRequest(url);
            } catch (RestClientException e1) {
                //e1.printStackTrace();
            }
            if (rr.getState() != ResponseResult.STATE_OK) {
                log.info("服务地址调用失败，放入重试列表：{}", url);
                retryUrls.add(url);
            }
        }
        for (String url : retryUrls) {
            try {
                restTemplateRequest(url);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return null;
    }

    //@HystrixCommand(fallbackMethod = "restTemplateRequestError")
    public ResponseResult<Void> restTemplateRequest(String url) {
        return restTemplate.getForObject(url, ResponseResult.class);
    }

    public ResponseResult<Void> restTemplateRequestError(String url) {
        return ResponseResult.createErrorResult("");
    }

    public void updateUrlPermission() {
        log.info("开始重新更新URL权限信息");
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
            filterChainDefinitionMap.put("/reloadUrlPermission", "anon"); //
            filterChainDefinitionMap.put("/swagger-ui.html", "anon");
            filterChainDefinitionMap.put("/swagger-resources", "anon");
            filterChainDefinitionMap.put("/v2/api-docs", "anon");
            filterChainDefinitionMap.put("/login", "anon");
            filterChainDefinitionMap.put("/loginPage.html", "anon");
            // filterChainDefinitionMap.put("/**", "anon");
            filterChainDefinitionMap.put("/aa/aa", "perms[asasd]");
            // filterChainDefinitionMap.put("/inaction/**", "perms[inaction]");

            /*ResponseResult<List<FuncComp>> listFuncComp = funcCompSvc.listFuncComp(1, Integer.MAX_VALUE);
            ResponseResult<List<FuncMenu>> listMenuComp = funcMenuSvc.getFuncMenuPage();

            if (listFuncComp.getData() != null && !listFuncComp.getData().isEmpty()) {
                List<FuncComp> comps = listFuncComp.getData();
                for (FuncComp funcComp : comps) {
                    filterChainDefinitionMap.put(funcComp.getUrlAddr(), "perms[C" + funcComp.getCompId() + "]");
                }
            }
            if (listMenuComp.getData() != null && !listMenuComp.getData().isEmpty()) {
                List<FuncMenu> comps = listMenuComp.getData();
                for (FuncMenu funcComp : comps) {
                    filterChainDefinitionMap.put(funcComp.getUrlAddr(), "perms[M" + funcComp.getMenuId() + "]");
                }
            }*/



//            filterChainDefinitionMap.put("/ff*/ff", "perms[sad]");//
//            filterChainDefinitionMap.put("/bb/ff=*", "perms[sad]");//
//            filterChainDefinitionMap.put("/cc/*/ff", "perms[sad]");//

            cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult<List<SysMenu>> listPage = sysMenuClient.listPage(1, Integer.MAX_VALUE,null);
            if(listPage.getState()==cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult.STATE_OK){
                List<SysMenu> data = listPage.getData();
                if(data!=null){
                    log.info("菜单权限数量："+data.size());
                    for (SysMenu sysMenu : data) {
                        filterChainDefinitionMap.put(sysMenu.getMenuUrl(), "perms[M" + sysMenu.getMenuId() + "]");
                    }
                }
            }
            cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult<List<SysFunction>> fs = sysFunctionClient.list(1, Integer.MAX_VALUE,"");
            if(fs.getState()==cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult.STATE_OK){
                List<SysFunction> data = fs.getData();
                if(data!=null){
                    log.info("功能权限数量："+data.size());
                    for (SysFunction sysMenu : data) {
                        String funcApi = sysMenu.getFuncApi();
                        int indexOf = funcApi.indexOf("{");
                        while(indexOf>=0){
                            int end=funcApi.indexOf("}");
                            String tmp=funcApi.substring(indexOf, end+1);
                            funcApi=funcApi.replace(tmp, "*");
                            indexOf= funcApi.indexOf("{");
                        }
                        filterChainDefinitionMap.put(funcApi, "perms[F" + sysMenu.getFuncId() + "]");
                    }
                }
            }

            // 表示需要认证才可以访问
            filterChainDefinitionMap.put("/**", "authc");// 表示需要认证才可以访问

            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
            log.info("重新更新URL权限信息完成");
        }

    }
    public static void main(String[] args) {
        String str="asd {fff}asdas{fffsss}asd{}";
        int indexOf = str.indexOf("{");
        while(indexOf>=0){
            int end=str.indexOf("}");
            String tmp=str.substring(indexOf, end+1);
            str=str.replace(tmp, "*");
            indexOf= str.indexOf("{");
        }
        System.out.println(str);
    }
}
