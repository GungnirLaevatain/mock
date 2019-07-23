package com.github.gungnirlaevatain.mock.proxy;

import com.github.gungnirlaevatain.mock.entity.MockEntity;
import com.github.gungnirlaevatain.mock.entity.MockMethod;
import com.github.gungnirlaevatain.mock.util.MockUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The class Mock util.
 * 具体的mock实现类
 *
 * @author gungnirlaevatain
 * @version 2019 -06-25 13:22:36
 * @since JDK 1.8
 */
@Slf4j
public class MockProxy {


    /**
     * The Method map.
     * 保存方法映射,key为方法名,value为对应的方法配置
     */
    private Map<String, List<MockMethod>> methodMap = new HashMap<>();


    public MockProxy(List<MockEntity> classEntities) {
        collectMethods(classEntities);
    }

    /**
     * Invoke.
     * 代理方法
     *
     * @param proxy       the proxy
     *                    被代理的对象
     * @param method      the method
     *                    被代理的方法(jdk proxy)
     * @param methodProxy the method proxy
     *                    被代理的方法(cglib)
     * @param args        the args
     *                    入参
     * @return the object
     * @throws Throwable the throwable
     * @author gungnirlaevatain
     */
    public Object invoke(Object proxy, Method method, MethodProxy methodProxy, Object[] args) throws Throwable {

        // 判断是否是需要mock的方法
        MockMethod mock = findMockMethod(method, args);
        if (mock == null) {
            // 如果不是则调用原来的方法
            if (methodProxy != null) {
                return methodProxy.invoke(proxy, args);
            }
            return method.invoke(proxy, args);
        }
        Class<?> returnType = method.getReturnType();
        // 根据入参获取需要返回的值
        String result = MockUtil.findResultFromMockMethod(mock, args);

        // 根据返回类型和选取的返回值构建返回的对象
        return MockUtil.createResult(result, returnType);

    }

    /**
     * Collect methods.
     * 收集需要mock的类方法
     *
     * @param entities the entity
     *                 需要mock的类的配置
     * @author gungnirlaevatain
     */
    private void collectMethods(List<MockEntity> entities) {
        if (entities == null) {
            return;
        }
        entities.stream()
                .flatMap(entity -> entity.getMethods().stream())
                .forEach(mockMethod -> {
                    List<MockMethod> list = methodMap.computeIfAbsent(mockMethod.getMethod(),
                            key -> new LinkedList<>());
                    list.add(mockMethod);
                });

    }

    /**
     * Find mock method.
     * 查找该方法的mock方法
     *
     * @param method  the method
     *                需要mock的方法
     * @param objects the objects
     *                入参
     * @return the mock method
     * 如果该方法不存在mock方法,则返回null
     * @author gungnirlaevatain
     */
    private MockMethod findMockMethod(Method method, Object[] objects) {
        String name = method.getName();
        List<MockMethod> mockMethods = methodMap.get(name);
        if (mockMethods == null) {
            return null;
        }
        MockMethod mock = null;
        if (mockMethods.size() == 1) {
            return mockMethods.get(0);
        }
        for (MockMethod mockMethod : mockMethods) {
            Class<?>[] paramClasses = method.getParameterTypes();
            List<String> paramList = mockMethod.getParamClass();
            if (paramList.size() != paramClasses.length) {
                continue;
            }
            boolean eq = true;
            for (int i = 0; i < paramList.size(); i++) {
                if (!paramList.get(i).equals(paramClasses[i].getName())) {
                    eq = false;
                    break;
                }
            }
            if (eq) {
                mock = mockMethod;
                break;
            }
        }
        return mock;
    }

}
