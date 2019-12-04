package com.github.gungnirlaevatain.mock.annotation;

import com.github.gungnirlaevatain.mock.handler.MockHandler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class Mock point.
 * 需要进行mock的方法标记注解
 *
 * @author GungnirLaevatain
 * @version 2019 -12-04 22:57:42
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.METHOD)
public @interface MockPoint {
    /**
     * Handler.
     * 指定用于mock该方法的处理类
     *
     * @return the class
     * @author GungnirLaevatain
     */
    Class<?> handler() default MockHandler.class;

    /**
     * Handler name.
     * 指定用于mock该方法的处理器bean的名称
     *
     * @return the string
     * @author GungnirLaevatain
     */
    String handlerName() default "";

    /**
     * Method name.
     * 指定使用的处理器对应的方法名称
     *
     * @return the string
     * @author GungnirLaevatain
     */
    String methodName() default "";
}
