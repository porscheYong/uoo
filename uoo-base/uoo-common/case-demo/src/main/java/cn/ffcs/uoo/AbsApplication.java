package cn.ffcs.uoo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
 * @ClassName AbsApplication
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/8/29 20:26
 * @Version 1.0.0
*/
@SpringBootApplication
@MapperScan(basePackages={"cn.ffcs.uoo.**.dao"})
public class AbsApplication {
    private final static Logger logger = LoggerFactory.getLogger(AbsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AbsApplication.class,args);

        logger.info("AbsApplication is success!");
    }
}
