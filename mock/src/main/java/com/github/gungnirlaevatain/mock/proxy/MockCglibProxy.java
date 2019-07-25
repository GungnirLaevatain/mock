package com.github.gungnirlaevatain.mock.proxy;


import com.github.gungnirlaevatain.mock.entity.MockEntity;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

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

    public MockCglibProxy(List<MockEntity> classEntities) {
        mockProxy = new MockProxy(classEntities);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return mockProxy.invoke(o, method, methodProxy, objects);
    }
}
