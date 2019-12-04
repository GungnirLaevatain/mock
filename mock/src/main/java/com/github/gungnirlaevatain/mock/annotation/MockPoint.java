package com.github.gungnirlaevatain.mock.annotation;

import com.github.gungnirlaevatain.mock.handler.MockHandler;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.METHOD)
public @interface MockPoint {
    Class<?> handler() default MockHandler.class;

    String handlerName() default "";

    String methodName() default "";
}
