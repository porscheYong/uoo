package cn.ffcs.uoo.organization.in.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * uoo-organization-in-action入口
 *
 * @author Lin
 * @date 2018/08/23
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrixDashboard
@EnableCircuitBreaker
public class UooOrganizationInActionApplication {

    public static void main(String[] args) {
        SpringApplication.run(UooOrganizationInActionApplication.class, args);
    }

}
