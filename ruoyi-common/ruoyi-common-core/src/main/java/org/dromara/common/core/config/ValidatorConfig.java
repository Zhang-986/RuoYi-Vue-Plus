package org.dromara.common.core.config;

import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Properties;

/**
 * 校验框架配置类
 *
 * @author Lion Li
 */
@AutoConfiguration(before = ValidationAutoConfiguration.class)
public class ValidatorConfig {

    /**
     * 配置校验框架 快速失败模式
     */
    @Bean
    public Validator validator(MessageSource messageSource) {
        // LocalVaildatorFactoryBean 是 Spring 提供的一个用于创建校验器的工厂类，它实现了 javax.validation.Validator 接口。
        // 1. 快速失败模式：LocalVaildatorFactoryBean 可以设置快速失败模式，即校验过程中一旦遇到失败，立即停止并返回错误。
        try (LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean()) {
            // 国际化
            factoryBean.setValidationMessageSource(messageSource);
            // 设置使用 HibernateValidator 校验器,比默认的 javax.validation.Validator有些扩展功能，如快速失败模式等
            factoryBean.setProviderClass(HibernateValidator.class);
            Properties properties = new Properties();
            // 设置快速失败模式（fail-fast），即校验过程中一旦遇到失败，立即停止并返回错误
            properties.setProperty("hibernate.validator.fail_fast", "true");
            factoryBean.setValidationProperties(properties);
            // 加载配置
            factoryBean.afterPropertiesSet();
            return factoryBean.getValidator();
        }
    }

}
