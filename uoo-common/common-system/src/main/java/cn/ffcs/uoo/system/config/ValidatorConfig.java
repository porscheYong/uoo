package cn.ffcs.uoo.system.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 将fail_fast设置为true即可，如果想验证全部，则设置为false或者取消配置即可
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        return factory.getValidator();
    }
}
 
