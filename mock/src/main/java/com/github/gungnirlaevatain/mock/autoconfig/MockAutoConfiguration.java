package com.github.gungnirlaevatain.mock.autoconfig;

import com.alibaba.fastjson.JSON;
import com.github.gungnirlaevatain.mock.entity.MockEntity;
import com.github.gungnirlaevatain.mock.entity.MockMethod;
import com.github.gungnirlaevatain.mock.processor.MockBeanDefinitionRegistryPostProcessor;
import com.github.gungnirlaevatain.mock.processor.MockBeanPostProcessor;
import com.github.gungnirlaevatain.mock.property.EnvProperty;
import com.github.gungnirlaevatain.mock.property.MockProperty;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.List;

/**
 * The class Mock auto configuration.
 * 自动配置类
 *
 * @author gungnirlaevatain
 * @version 2019 -06-24 16:19:00
 * @since 1.0
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = EnvProperty.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = {"com.github.gungnirlaevatain.mock"})
public class MockAutoConfiguration {

    @Bean
    public static EnvProperty envProperty(@Autowired Environment environment) {
        Binder binder = Binder.get(environment);
        BindResult<EnvProperty> result = binder.bind(EnvProperty.PREFIX, EnvProperty.class);
        if (result.isBound()) {
            return result.get();
        }
        log.warn("use default mock environment");
        return new EnvProperty();
    }

    @Bean
    public static MockBeanDefinitionRegistryPostProcessor mockBeanDefinitionRegistryPostProcessor(@Autowired EnvProperty envProperty) throws IOException {
        MockBeanPostProcessor mockBeanPostProcessor = new MockBeanPostProcessor(readProperty(envProperty));
        return new MockBeanDefinitionRegistryPostProcessor(mockBeanPostProcessor);
    }

    private static MockProperty readProperty(EnvProperty envProperty) throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(envProperty.getConfigLocation());
        MockProperty mockProperty = new MockProperty();
        Yaml yaml = new Yaml();
        for (Resource resource : resources) {
            try {
                log.debug("now will read mock property from [{}]", resource.getURI());
                MockProperty newMockProperty = yaml.loadAs(resource.getInputStream(), MockProperty.class);
                mockProperty.merge(newMockProperty);
            } catch (Exception e) {
                log.error("can not read mock property form [{}], because ", resource.getURI(), e);
            }
        }
        refreshMockEntities(mockProperty);
        return mockProperty;
    }

    /**
     * Refresh mock entities.
     * 刷新mock的配置项
     *
     * @param mockProperty the mock property
     * @author gungnirlaevatain
     */
    private static void refreshMockEntities(MockProperty mockProperty) {
        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath(new LoaderClassPath(MockAutoConfiguration.class.getClassLoader()));
        List<MockEntity> mockEntities = mockProperty.getEntities();
        for (int i = 0; i < mockEntities.size(); i++) {
            MockEntity entity = mockEntities.get(i);
            if (StringUtils.isEmpty(entity.getClassName())) {
                log.error("class name is required for [{}] entity", i);
                // 移除不合法的配置项
                mockEntities.remove(i);
                i--;
                continue;
            }
            if (entity.getType() == MockEntity.MockType.CLASS) {
                // 如果是直接修改类,则直接进行修改
                mockClass(entity, pool);
            }
        }
    }

    /**
     * Mock class.
     * 处理需要mock的类
     *
     * @param entity the entity
     * @param pool   the pool
     * @author gungnirlaevatain
     */
    private static void mockClass(MockEntity entity, ClassPool pool) {
        try {
            CtClass ctClass = pool.get(entity.getClassName());
            List<MockMethod> methods = entity.getMethods();
            for (MockMethod mockMethod : methods) {
                List<String> paramClasses = mockMethod.getParamClass();
                CtMethod ctMethod;
                CtMethod[] ctMethods = ctClass.getDeclaredMethods(mockMethod.getMethod());
                if (ctMethods.length == 1) {
                    ctMethod = ctMethods[0];
                } else {
                    CtClass[] ctClasses = new CtClass[paramClasses.size()];
                    for (int i = 0; i < paramClasses.size(); i++) {
                        CtClass param = pool.get(paramClasses.get(i));
                        ctClasses[i] = param;
                    }
                    ctMethod = ctClass.getDeclaredMethod(mockMethod.getMethod(), ctClasses);
                }
                String body = mockBody(ctMethod, mockMethod);
                ctMethod.insertBefore(body);
            }
            pool.toClass(ctClass);
            log.info("class [{}] has been mocked by mock class type", ctClass.getName());
        } catch (NotFoundException e) {
            log.error("can not found class for class name {} from context", entity.getClassName(), e);
        } catch (CannotCompileException e) {
            log.error("can not compile class for class name {} from context", entity.getClassName(), e);
        }
    }

    /**
     * Mock body.
     * 修改方法体以完成mock
     *
     * @param ctMethod   the ctMethod
     * @param mockMethod the mock method
     * @return the string
     * @author gungnirlaevatain
     */
    private static String mockBody(CtMethod ctMethod, MockMethod mockMethod) throws NotFoundException {
        CtClass returnType = ctMethod.getReturnType();
        if (returnType == CtClass.voidType) {
            return "return;";
        }
        StringBuilder sb = new StringBuilder();
        String methodJson = JSON.toJSONString(JSON.toJSONString(mockMethod));
        sb.append("com.github.gungnirlaevatain.mock.entity.MockMethod mockMethod=(com.github.gungnirlaevatain.mock.entity.MockMethod)com.alibaba.fastjson.JSON.parseObject(")
                .append(methodJson)
                .append(",com.github.gungnirlaevatain.mock.entity.MockMethod.class);");

        sb.append("String mockResult = com.github.gungnirlaevatain.mock.util.MockUtil.findResultFromMockMethod(mockMethod, $args);");

        sb.append("return ")
                .append("(")
                .append("$r")
                .append(")")
                .append("com.github.gungnirlaevatain.mock.util.MockUtil.createResult(mockResult,")
                .append(returnType.getName())
                .append(".class)")
                .append(";");
        return sb.toString();
    }
}
