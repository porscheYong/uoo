package cn.ffcs.uoo.base.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class UooSleuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(UooSleuthApplication.class, args);
    }

}
