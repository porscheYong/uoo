package cn.ffcs.common.gol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
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
@ServletComponentScan(basePackages = {"cn.ffcs.common"}) //Scan servlets using annotations
@EnableJpaRepositories(basePackages = {"cn.ffcs.common"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class//Specify your own factory class
)
public class LogApplication {


    /**Get the log entity from the message middleware, store the database after deserialization
     * @author WCNGS@QQ.COM
     * @See
     * @date 2019/1/23 16:09
     * @param args
     * @return void
     * @throws
     * @since
     */
    public static void main(String[] args) {

        SpringApplication.run(LogApplication.class, args);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//    }
}
