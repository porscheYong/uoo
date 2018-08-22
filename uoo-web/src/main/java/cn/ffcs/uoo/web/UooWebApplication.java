package cn.ffcs.uoo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class UooWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(UooWebApplication.class, args);
    }

}
