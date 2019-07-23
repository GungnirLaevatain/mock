package com.github.gungnirlaevatain.mock.entity;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class Mock entity.
 *
 * @author GungnirLaevatain
 * @version 2019 -07-14 20:47:06
 * @since JDK 1.8
 */
@Data
public class MockEntity {
    /**
     * The Type.
     * mock的方式
     */
    private MockType type = MockType.BEAN_PROXY;
    /**
     * The Class name.
     * 类名称
     */
    private String className;
    private transient Class mockClass;
    /**
     * The Methods.
     * 类方法配置
     */
    private List<MockMethod> methods;

    /**
     * Merge.
     * 进行配置的合并
     *
     * @param mockEntity the mock entity
     * @author gungnirlaevatain
     */
    public void merge(MockEntity mockEntity) {
        List<MockMethod> extraMethods = mockEntity.getMethods();
        Map<String, MockMethod> entityMap = new HashMap<>(methods.size());
        methods.forEach(method -> entityMap.put(method.uniqueName(), method));
        extraMethods.stream()
                .filter(method -> !StringUtils.isEmpty(method.getMethod()))
                .forEach(method -> {
                    String uniqueName = method.uniqueName();
                    if (entityMap.containsKey(uniqueName)) {
                        entityMap.get(uniqueName).merge(method);
                    } else {
                        entityMap.put(uniqueName, method);
                        methods.add(method);
                    }
                });
    }

    /**
     * The class Mock type.
     *
     * @author GungnirLaevatain
     * @version 2019 -07-14 20:49:01
     * @since JDK 11
     */
    public enum MockType {
        /**
         * Bean proxy type.
         * 代理 Spring Bean的方法
         */
        BEAN_PROXY,
        /**
         * Bean replace mock type.
         * 替换Spring Bean的方式
         */
        BEAN_REPLACE,
        /**
         * Class type.
         * 直接修改类方法的方法
         */
        CLASS
    }
}
