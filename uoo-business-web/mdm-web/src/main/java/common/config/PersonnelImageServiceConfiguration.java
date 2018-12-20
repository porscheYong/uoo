package common.config;

import feign.Logger;
import feign.Retryer;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;

import static java.util.concurrent.TimeUnit.SECONDS;

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
 * @ClassName PersonnelImageServiceConfiguration
 * @Description
 * @author WCNGS@QQ.COM
 * @date 2018/9/8 21:52
 * @Version 1.0.0
 */
public class PersonnelImageServiceConfiguration {

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(200, SECONDS.toMillis(2), 10);
    }
//
//    @Bean
//    public Contract feignContract() {
//        return new feign.Contract.Default();
//    }
}