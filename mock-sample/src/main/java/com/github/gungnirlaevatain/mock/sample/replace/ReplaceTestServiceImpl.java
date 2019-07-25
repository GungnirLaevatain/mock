package com.github.gungnirlaevatain.mock.sample.replace;

import com.github.gungnirlaevatain.mock.sample.TestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The class Replace test service.
 * 当某些bean无法在本地生成时，利用bean replace模式进行mock
 *
 * @author GungnirLaevatain
 * @version 2019 -07-25 23:19:42
 * @since 1.0
 */
@Service
@Slf4j
public class ReplaceTestServiceImpl implements ReplaceTestService {

    public ReplaceTestServiceImpl() {
        throw new RuntimeException();
    }

    /**
     * Test return object by default.
     * 测试返回默认值
     *
     * @return the test result
     * @author GungnirLaevatain
     */
    @Override
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
     * @return the test result
     * @author GungnirLaevatain
     */
    @Override
    public TestResult testReturnObjectByParam(Integer a, String b, TestResult result) {
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
    public int testReturnInt(String a, TestResult result) {
        return 0;
    }
}
