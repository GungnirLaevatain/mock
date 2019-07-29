package com.github.gungnirlaevatain.mock.sample.cls;

import com.github.gungnirlaevatain.mock.sample.TestResult;

/**
 * The class Class test util.
 * 测试CLASS模式的mock类
 *
 * @author GungnirLaevatain
 * @version 2019 -07-29 23:43:48
 * @since 1.0
 */
public class ClassTestUtil {

    /**
     * Test static object by default.
     * 测试静态方法返回mock的默认值
     *
     * @return the test result
     * @author GungnirLaevatain
     */
    public static TestResult testStaticObjectByDefault() {
        return null;
    }

    /**
     * Test return object by default.
     * 测试返回默认值
     *
     * @return the proxy test result
     * @author GungnirLaevatain
     */
    public TestResult testReturnObjectByDefault() {
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
    public TestResult testReturnObjectByParam(Integer a, String b, TestResult result) {
        return null;
    }

    /**
     * Test void.
     * 测试无返回值mock
     *
     * @author GungnirLaevatain
     */
    public void testVoid() {

    }

    /**
     * Test return string.
     * 测试返回字符串
     *
     * @return the string
     * @author GungnirLaevatain
     */
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
    public int testReturnInt(String a, TestResult result) {
        return 0;
    }
}
