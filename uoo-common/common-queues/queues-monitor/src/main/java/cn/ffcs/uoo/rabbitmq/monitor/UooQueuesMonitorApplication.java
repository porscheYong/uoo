package cn.ffcs.uoo.rabbitmq.monitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrixDashboard
@EnableCircuitBreaker
@SpringBootApplication
@MapperScan(basePackages= {"cn.ffcs.uoo.rabbitmq.monitor.dao"})
public class UooQueuesMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(UooQueuesMonitorApplication.class,args);
    }
}
