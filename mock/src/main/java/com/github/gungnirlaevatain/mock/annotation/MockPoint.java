package com.github.gungnirlaevatain.mock.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.METHOD)
public @interface MockPoint {
    Class<?> hanlder();

    String methodName() default "";
}
