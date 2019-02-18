package xyz.wongs.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
 * @ClassName DemoInitApplication
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/31 11:00
 * @Version 1.0.0
*/
@ServletComponentScan(basePackages = {"xyz.wongs"}) //Scan servlets using annotations
@EnableJpaRepositories(basePackages = {"xyz.wongs.workflow"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class//Specify your own factory class
)
@SpringBootApplication
public class DemoInitApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoInitApplication.class, args);
    }
}
