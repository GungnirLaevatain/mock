package com.github.gungnirlaevatain.mock.processor;

import com.github.gungnirlaevatain.mock.entity.MockEntity;
import com.github.gungnirlaevatain.mock.property.MockProperty;
import com.github.gungnirlaevatain.mock.proxy.MockCglibProxy;
import com.github.gungnirlaevatain.mock.proxy.MockJdkProxy;
import com.github.gungnirlaevatain.mock.util.MockUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

/**
 * The class Mock bean post processor.
 * 处理BEAN_PROXY和BEAN_REPLACE方式的处理器
 *
 * @author gungnirlaevatain
 * @version 2019 -06-25 16:37:39
 * @since 1.0
 */
@Slf4j
public class MockBeanPostProcessor implements InstantiationAwareBeanPostProcessor, PriorityOrdered {
    private Unsafe unsafe;
    private List<MockEntity> proxyMockEntities;
    private List<MockEntity> replaceMockEntities;

    {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception ignored) {
        }
    }

    public MockBeanPostProcessor(MockProperty mockProperty) {

        List<MockEntity> entities = mockProperty.getEntities();
        proxyMockEntities = new LinkedList<>();
        replaceMockEntities = new LinkedList<>();
        for (MockEntity entity : entities) {
            if (entity.getType() == MockEntity.MockType.BEAN_PROXY) {
                MockUtil.registerMockEntity(entity, proxyMockEntities);
            }
            if (entity.getType() == MockEntity.MockType.BEAN_REPLACE) {
                MockUtil.registerMockEntity(entity, replaceMockEntities);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        List<MockEntity> classes = new LinkedList<>();
        replaceMockEntities.forEach(entity -> {
            if (entity.getMockClass().isAssignableFrom(beanClass)) {
                // 一个类可能会实现多个接口
                classes.add(entity);
            }

        });
        if (classes.size() == 0) {
            return null;
        }
        Object mock;
        if (beanClass.isInterface()) {
            // 如果接口则用jdk代理
            mock = Proxy.newProxyInstance(beanClass.getClassLoader(), new Class[]{beanClass},
                    new MockJdkProxy(classes));
        } else {
            // 否则使用cglib代理
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(beanClass);
            enhancer.setCallbackType(MockCglibProxy.class);
            Class proxyClass = enhancer.createClass();
            Enhancer.registerStaticCallbacks(proxyClass, new Callback[]{new MockCglibProxy(classes)});
            try {
                // 防止因构造器异常导致无法产生对应对象
                return unsafe.allocateInstance(proxyClass);
            } catch (InstantiationException e) {
                throw new BeanInitializationException("create bean failed", e);
            }
        }
        log.info("bean named[{}], class is [{}] has been mocked by mock create spring bean type",
                beanName, beanClass);
        return mock;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        List<MockEntity> classes = new LinkedList<>();
        proxyMockEntities.forEach(entity -> {
                    if (entity.getMockClass().isAssignableFrom(bean.getClass())) {
                        classes.add(entity);
                    }
                }
        );

        if (classes.size() == 0) {
            return bean;
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback(new MockCglibProxy(classes));
        Object mock = enhancer.create();
        log.info("bean named[{}], class is [{}] has been mocked by mock spring bean type",
                bean.getClass(), beanName);
        return mock;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
