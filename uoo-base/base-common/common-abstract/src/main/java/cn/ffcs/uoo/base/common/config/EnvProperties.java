package cn.ffcs.uoo.base.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
 * @ClassName EnvProperties
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/6 14:41
 * @Version 1.0.0
*/
@Component
@ConfigurationProperties(prefix = EnvProperties.PREFIX)
public class EnvProperties {

    public static final String PREFIX = "uoo-config";

    /**
     * 环境类型：分为测试环境 test，开发环境 dev,生产环境 pro
     */
    private String envType;

    /**
     *
     */
    private String jdbcType;


    public String getEnvType() {
        return envType;
    }

    public void setEnvType(String envType) {
        this.envType = envType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }
}
