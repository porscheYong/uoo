package cn.ffcs.uoo.web.maindata.realm;

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
    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadSvc.updateUrlPermission();
        log.info("加载权限完成");
    }

}
