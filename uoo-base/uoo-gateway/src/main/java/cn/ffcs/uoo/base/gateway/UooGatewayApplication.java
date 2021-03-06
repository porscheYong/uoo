package cn.ffcs.uoo.base.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class UooGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(UooGatewayApplication.class, args);
    }

}
