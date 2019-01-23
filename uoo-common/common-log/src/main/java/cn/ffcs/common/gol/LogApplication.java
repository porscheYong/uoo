package cn.ffcs.common.gol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import xyz.wongs.common.persistence.jpa.repository.BaseRepositoryFactoryBean;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName LogApplication
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/23 14:48
 * @Version 1.0.0
*/
@EnableEurekaClient
@SpringBootApplication
@ServletComponentScan(basePackages = {"cn.ffcs.common"}) //扫描使用注解方式的Servlet
@EnableJpaRepositories(basePackages = {"cn.ffcs.common"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class//指定自己的工厂类
)
public class LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
    }

}
