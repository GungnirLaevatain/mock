package com.github.gungnirlaevatain.mock.proxy;


import com.github.gungnirlaevatain.mock.annotation.MockPoint;
import com.github.gungnirlaevatain.mock.entity.MockEntity;
import com.github.gungnirlaevatain.mock.handler.MockHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * The class Mock cglib proxy.
 * 基于cglib代理的代理类
 *
 * @author gungnirlaevatain
 * @version 2019 -06-25 10:58:54
 * @since 1.0
 */
@Slf4j
public class MockCglibProxy implements MethodInterceptor {
    private MockProxy mockProxy;
    private BeanFactory beanFactory;

    public MockCglibProxy(List<MockEntity> classEntities, BeanFactory beanFactory) {
        mockProxy = new MockProxy(classEntities);
        this.beanFactory = beanFactory;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        MockPoint mockPoint = method.getAnnotation(MockPoint.class);
        if (mockPoint != null) {
            Class<?> handlerClass = mockPoint.hanlder();
            try {
                Object bean = beanFactory.getBean(handlerClass);
                String methodName = mockPoint.methodName();
                if (StringUtils.isEmpty(methodName)) {
                    methodName = method.getName();
                }

                Method handlerMethod = ReflectionUtils.findMethod(handlerClass, methodName,
                        method.getParameterTypes());

                if (handlerMethod != null) {
                    return handlerMethod.invoke(bean, objects);
                }

                log.debug("can not found mock method that has same name and params");

                if (bean instanceof MockHandler) {
                    return ((MockHandler) bean).mock(o.getClass().getSuperclass(), method, objects);
                }

                log.error("target class should has mock method or implement MockHandler");
            } catch (NoSuchBeanDefinitionException e) {
                log.warn("can not found bean definition that class is [{}]", handlerClass);
                // 若没有声明对应的处理类则尝试基于外部配置文件的方式进行打桩操作
            }
        }
        return mockProxy.invoke(o, method, methodProxy, objects);
    }
}
