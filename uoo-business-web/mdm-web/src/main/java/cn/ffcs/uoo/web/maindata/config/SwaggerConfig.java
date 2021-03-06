package cn.ffcs.uoo.web.maindata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger的配置文件
 *
 * @author Lin
 * @date 2018/08/23
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("cn.ffcs.uoo.web.maindata"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("uoo-mdm-web：主数据中心RESTful API文档")
                .description("暂无")
                .termsOfServiceUrl("#")
                .version("0.0.1")
                .build();
    }

}
