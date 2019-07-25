package com.github.gungnirlaevatain.mock.proxy;

import com.github.gungnirlaevatain.mock.entity.MockEntity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * The class Mock jdk proxy.
 * 基于jdk代理实现的代理类
 *
 * @author gungnirlaevatain
 * @version 2019 -06-25 16:31:15
 * @since 1.0
 */
public class MockJdkProxy implements InvocationHandler {

    private MockProxy mockProxy;

    public MockJdkProxy(List<MockEntity> classEntities) {
        mockProxy = new MockProxy(classEntities);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return mockProxy.invoke(proxy, method, null, args);
    }
}
