package com.github.gungnirlaevatain.mock.sample.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The class Proxy test service.
 *
 * @author GungnirLaevatain
 * @version 2019 -07-22 23:51:00
 * @since JDK 11
 */
@Service
@Slf4j
public class ProxyTestServiceImpl implements ProxyTestService {
    /**
     * Test return object by default.
     * 测试返回默认值
     *
     * @return the proxy test result
     * @author GungnirLaevatain
     */
    @Override
    public ProxyTestResult testReturnObjectByDefault() {
        return null;
    }

    /**
     * Test return object by param.
     * 测试根据入参值返回对应结果
     *
     * @param a      the a
     * @param b      the b
     * @param result the result
     * @return the proxy test result
     * @author GungnirLaevatain
     */
    @Override
    public ProxyTestResult testReturnObjectByParam(Integer a, String b, ProxyTestResult result) {
        return null;
    }

    /**
     * Test void.
     * 测试无返回值mock
     *
     * @author GungnirLaevatain
     */
    @Override
    public void testVoid() {

    }

    /**
     * Test return string.
     * 测试返回字符串
     *
     * @return the string
     * @author GungnirLaevatain
     */
    @Override
    public String testReturnString() {
        return null;
    }

    /**
     * Test return int.
     * 测试返回基本类型
     *
     * @return the int
     * @author GungnirLaevatain
     */
    @Override
    public int testReturnInt() {
        return 0;
    }

    /**
     * Test return int.
     * 测试重载方法
     *
     * @param a the a
     * @return the int
     * @author GungnirLaevatain
     */
    @Override
    public int testReturnInt(String a) {
        return 0;
    }

    /**
     * Test return int.
     * 测试重载方法
     *
     * @param a      the a
     * @param result the result
     * @return the int
     * @author GungnirLaevatain
     */
    @Override
    public int testReturnInt(String a, ProxyTestResult result) {
        return 0;
    }
}
