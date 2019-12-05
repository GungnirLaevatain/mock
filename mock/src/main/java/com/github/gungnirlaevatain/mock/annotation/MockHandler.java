package com.github.gungnirlaevatain.mock.annotation;

import java.lang.annotation.*;

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
     * value.
     * bean名称
     *
     * @return the string
     * @author GungnirLaevatain
     */
    String value() default "";
}
