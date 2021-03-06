package cn.ffcs.uoo.core.organization.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

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
                .apis(RequestHandlerSelectors.basePackage("cn.ffcs.uoo.core.organization"))
                .paths(PathSelectors.any()).build();
    }




    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("uoo-organization：组织机构微服务RESTful API文档")
                .description("暂无")
                .termsOfServiceUrl("#")
                .version("0.0.1")
                .build();
    }

}
