package com.github.gungnirlaevatain.mock.autoconfig;

import com.github.gungnirlaevatain.mock.processor.MockBeanDefinitionRegistryPostProcessor;
import com.github.gungnirlaevatain.mock.property.EnvProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;

/**
 * The class Mock auto configuration.
 * 自动配置类
 *
 * @author gungnirlaevatain
 * @version 2019 -06-24 16:19:00
 * @since 1.0
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = EnvProperty.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = {"com.github.gungnirlaevatain.mock"})
public class MockAutoConfiguration {

    @Bean
    public static EnvProperty envProperty(@Autowired Environment environment) {
        Binder binder = Binder.get(environment);
        BindResult<EnvProperty> result = binder.bind(EnvProperty.PREFIX, EnvProperty.class);
        if (result.isBound()) {
            return result.get();
        }
        log.warn("use default mock environment");
        return new EnvProperty();
    }

    @Bean
    public static MockBeanDefinitionRegistryPostProcessor mockBeanDefinitionRegistryPostProcessor(@Autowired EnvProperty envProperty) throws IOException {
        return new MockBeanDefinitionRegistryPostProcessor(envProperty);
    }

}
