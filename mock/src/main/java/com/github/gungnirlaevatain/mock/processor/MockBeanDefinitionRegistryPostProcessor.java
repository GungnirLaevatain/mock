package com.github.gungnirlaevatain.mock.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

/**
 * The class Mock bean definition registry post processor.
 * 处理BEAN_CREATE方式的处理器
 *
 * @author GungnirLaevatain
 * @version 2019 -07-14 22:18:00
 * @since JDK 1.8
 */
@Slf4j
public class MockBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {

    private MockBeanPostProcessor mockBeanPostProcessor;

    public MockBeanDefinitionRegistryPostProcessor(MockBeanPostProcessor mockBeanPostProcessor) {
        this.mockBeanPostProcessor = mockBeanPostProcessor;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 提前注册beanPostProcessor避免提前初始化的bean无法被拦截
        beanFactory.addBeanPostProcessor(mockBeanPostProcessor);

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
