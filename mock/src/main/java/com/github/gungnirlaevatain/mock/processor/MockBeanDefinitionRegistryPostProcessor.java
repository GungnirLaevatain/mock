package com.github.gungnirlaevatain.mock.processor;

import com.github.gungnirlaevatain.mock.annotation.MockAnnotationBeanNameGenerator;
import com.github.gungnirlaevatain.mock.handler.MockHandler;
import com.github.gungnirlaevatain.mock.property.EnvProperty;
import com.github.gungnirlaevatain.mock.property.MockProperty;
import com.github.gungnirlaevatain.mock.util.PropertyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * The class Mock bean definition registry post processor.
 * 注册用于mock的后置处理器以及扫描指定包下的MockHandler
 *
 * @author GungnirLaevatain
 * @version 2019 -07-14 22:18:00
 * @since 1.0
 */
@Slf4j
public class MockBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {

    private List<String> scanPackages;
    private MockProperty mockProperty;

    public MockBeanDefinitionRegistryPostProcessor(EnvProperty envProperty) throws IOException {
        scanPackages = envProperty.getHandlerScan();
        mockProperty = PropertyUtil.readProperty(envProperty);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (CollectionUtils.isEmpty(scanPackages)) {
            return;
        }
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry, false);
        scanner.setBeanNameGenerator(new MockAnnotationBeanNameGenerator());
        scanner.addIncludeFilter(new AssignableTypeFilter(MockHandler.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(com.github.gungnirlaevatain.mock.annotation.MockHandler.class));
        scanner.scan(scanPackages.toArray(new String[0]));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 提前注册beanPostProcessor避免提前初始化的bean无法被拦截
        beanFactory.addBeanPostProcessor(new MockBeanPostProcessor(mockProperty, beanFactory));

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
