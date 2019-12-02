package com.github.gungnirlaevatain.mock.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
public @interface MockPoint {
    String name() default "";
}
