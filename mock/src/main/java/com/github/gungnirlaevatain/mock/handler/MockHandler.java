package com.github.gungnirlaevatain.mock.handler;

import java.lang.reflect.Method;

/**
 * The class Mock handler.
 * 对目标对象的目标方法返回打桩数据的处理者
 *
 * @author gungnirlaevatain
 * @version 2019 -12-03 11:00:15
 * @since 1.0
 */
public interface MockHandler {

    /**
     * Mock.
     * 返回打桩数据
     *
     * @param targetClass  the target class
     *                     需要mock的类
     * @param targetMethod the target method
     *                     需要mock的方法
     * @param params       the params
     *                     方法的入参,若无参,则为空数组
     * @return the object
     * 返回的打桩数据
     * @author gungnirlaevatain
     */
    Object mock(Class<?> targetClass, Method targetMethod, Object[] params);
}
