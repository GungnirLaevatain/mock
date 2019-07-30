package com.github.gungnirlaevatain.mock.autoconfig;

import com.alibaba.fastjson.JSON;
import com.github.gungnirlaevatain.mock.entity.MockEntity;
import com.github.gungnirlaevatain.mock.entity.MockMethod;
import com.github.gungnirlaevatain.mock.processor.MockBeanDefinitionRegistryPostProcessor;
import com.github.gungnirlaevatain.mock.processor.MockBeanPostProcessor;
import com.github.gungnirlaevatain.mock.property.MockProperty;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.CollectionUtils;
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
@ComponentScan(basePackages = {"com.github.gungnirlaevatain.mock"})
public class MockAutoConfiguration {

    @Bean
    public static MockBeanDefinitionRegistryPostProcessor mockBeanDefinitionRegistryPostProcessor() throws IOException {

        MockBeanPostProcessor mockBeanPostProcessor = new MockBeanPostProcessor(readProperty());
        return new MockBeanDefinitionRegistryPostProcessor(mockBeanPostProcessor);
    }

    private static MockProperty readProperty() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(MockProperty.RESOURCE);
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
        List<MockEntity> mockEntities = mockProperty.getEntities();
        for (int i = 0; i < mockEntities.size(); i++) {
            MockEntity entity = mockEntities.get(i);
            if (StringUtils.isEmpty(entity.getClassName())) {
                log.error("mock class is required for [{}] entity", i);
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
                if (CollectionUtils.isEmpty(paramClasses)) {
                    ctMethod = ctClass.getDeclaredMethod(mockMethod.getMethod());
                } else {
                    CtClass[] ctClasses = new CtClass[paramClasses.size()];
                    for (int i = 0; i < paramClasses.size(); i++) {
                        CtClass param = pool.get(paramClasses.get(i));
                        ctClasses[i] = param;
                    }
                    ctMethod = ctClass.getDeclaredMethod(mockMethod.getMethod(), ctClasses);

                }
                MethodInfo methodInfo = ctMethod.getMethodInfo();
                CtClass returnType = ctMethod.getReturnType();
                CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
                LocalVariableAttribute attr =
                        (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
                String body = mockBody(attr, returnType.getName(), mockMethod);
                ctMethod.insertBefore(body);
                log.info("class [{}] has been mocked by mock class type", ctClass.getName());
            }
            pool.toClass(ctClass);
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
     * @param attr       the attr
     * @param returnType the return type
     * @param mockMethod the mock method
     * @return the string
     * @author gungnirlaevatain
     */
    private static String mockBody(LocalVariableAttribute attr, String returnType, MockMethod mockMethod) {
        if (attr == null) {
            return "return com.github.gungnirlaevatain.mock.util.MockUtil.createResult(\"" +
                    mockMethod.getDefaultResult().replace("\"", "\\\"") +
                    "\", " + returnType + ".class);";
        }
        String methodJson = JSON.toJSONString(mockMethod);
        int length = attr.tableLength();
        StringBuilder sb = new StringBuilder("Object[] mockArgs=new Object[]{");
        for (int i = 0; i < length - 1; i++) {
            sb.append(attr.variableName(i)).append(",");
        }
        sb.append(attr.variableName(length)).append("};")
                .append("com.github.gungnirlaevatain.mock.entity.MockMethod mockMethod=com.alibaba.fastjson.JSON.parseObject(\"")
                .append(methodJson)
                .append("\",com.github.gungnirlaevatain.mock.entity.MockMethod.class);");

        sb.append("String mockResult = com.github.gungnirlaevatain.mock.util.MockUtil.findResultFromMockMethod(mockMethod, mockArgs);");

        sb.append("return com.github.gungnirlaevatain.mock.util.MockUtil.createResult(result,")
                .append(returnType)
                .append(".class);");
        return sb.toString();
    }
}
