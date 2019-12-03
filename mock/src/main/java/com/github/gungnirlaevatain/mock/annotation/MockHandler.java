package com.github.gungnirlaevatain.mock.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.TYPE)
public @interface MockHandler {
}
