package cn.ffcs.uoo.web.maindata.realm;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ShiroRunner implements ApplicationRunner{
    private static Logger log=LoggerFactory.getLogger(ShiroRunner.class);
    @Autowired
    LoadUrlPermissionService loadSvc;
    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        reloadSystemPermission();
       // loadSvc.updateUrlPermission();
        log.info("加载系统权限完成");
    }
    //暂时先加一个定时器  反正卡住  10分钟检查一下
    public void reloadSystemPermission(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(shiroFilterFactoryBean.getFilterChainDefinitionMap().isEmpty()){
                    log.info("系统权限为空，重新加载中");
                    loadSvc.updateUrlPermission();
                }
            }
        }, 0,60000*10);
    }

}
