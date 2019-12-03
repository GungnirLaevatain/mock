package com.github.gungnirlaevatain.mock.sample.handler;

import com.github.gungnirlaevatain.mock.handler.MockHandler;

import java.lang.reflect.Method;

public class MockHandlerBaseOnInterface implements MockHandler {
    @Override
    public Object mock(Class<?> targetClass, Method targetMethod, Object[] params) {
        if ("testInt".equals(targetMethod.getName())) {
            return 2222;
        } else {
            return "1111";
        }
    }
}
