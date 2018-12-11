package cn.ffcs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 系统域启动类
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages={"cn.ffcs.uoo.**.dao"})
public class UooSystemApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(UooSystemApplication.class,args);
    }
}
