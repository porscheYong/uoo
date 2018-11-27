package cn.ffcs.uoo.base.common.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

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
 * @ClassName MybatisPlusConfig
 * @Description MybatisPlus配置
 * @author WCNGS@QQ.COM
 * @date 2018/8/29 20:22
 * @Version 1.0.0
 */
@Component
public class MybatisPlusConfig {


    /**
     * @author WCNGS@QQ.COM
     * @See plus 的性能优化
     * @date 2018/8/29 20:18
     * @param
     * @return com.baomidou.mybatisplus.plugins.PerformanceInterceptor
     * @throws
     * @since
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {

        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
        performanceInterceptor.setMaxTime(1000);
        /*<!--SQL是否格式化 默认false-->*/
//        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }


    /**mybatis-plus分页插件
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/8/29 20:19
     * @param
     * @return com.baomidou.mybatisplus.plugins.PaginationInterceptor
     * @throws
     * @since
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {

        return new PaginationInterceptor();
    }



    /**druid注入
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/8/29 20:19
     * @param
     * @return
     * @throws
     * @since
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid." )
    public DataSource dataSource() {
        return DruidDataSourceBuilder
                .create()
                .build();
    }
}
