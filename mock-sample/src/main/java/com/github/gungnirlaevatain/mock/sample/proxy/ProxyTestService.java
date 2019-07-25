package com.github.gungnirlaevatain.mock.sample.proxy;

import com.github.gungnirlaevatain.mock.sample.TestResult;

/**
 * The class Proxy test service.
 * proxy模式测试接口
 *
 * @author GungnirLaevatain
 * @version 2019 -07-22 23:04:23
 * @since 1.0
 */
public interface ProxyTestService {

    /**
     * Test return object by default.
     * 测试返回默认值
     *
     * @return the proxy test result
     * @author GungnirLaevatain
     */
    TestResult testReturnObjectByDefault();

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
    TestResult testReturnObjectByParam(Integer a, String b, TestResult result);

    /**
     * Test void.
     * 测试无返回值mock
     *
     * @author GungnirLaevatain
     */
    void testVoid();

    /**
     * Test return string.
     * 测试返回字符串
     *
     * @return the string
     * @author GungnirLaevatain
     */
    String testReturnString();

    /**
     * Test return int.
     * 测试返回基本类型
     *
     * @return the int
     * @author GungnirLaevatain
     */
    int testReturnInt();

    /**
     * Test return int.
     * 测试重载方法
     *
     * @param a the a
     * @return the int
     * @author GungnirLaevatain
     */
    int testReturnInt(String a);

    /**
     * Test return int.
     * 测试重载方法
     *
     * @param a      the a
     * @param result the result
     * @return the int
     * @author GungnirLaevatain
     */
    int testReturnInt(String a, TestResult result);
}
