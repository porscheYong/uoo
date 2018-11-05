package cn.ffcs.uoo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName App
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/19 18:14
 * @Version 1.0.0
*/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages={"cn.ffcs.uoo.**.dao"})
public class UooActivitiApplication {
    public static void main(String[] args) {
        SpringApplication.run(UooActivitiApplication.class,args);
    }
}
