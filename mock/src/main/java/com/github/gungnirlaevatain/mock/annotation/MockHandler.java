package com.github.gungnirlaevatain.mock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class Mock handler.
 * mock处理器的标记注解
 *
 * @author GungnirLaevatain
 * @version 2019 -12-04 22:56:59
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.TYPE)
public @interface MockHandler {
    /**
     * Name.
     *
     * @return the string
     * @author GungnirLaevatain
     */
    String name() default "";
}
