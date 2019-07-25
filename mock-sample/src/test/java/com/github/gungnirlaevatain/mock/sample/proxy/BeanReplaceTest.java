package com.github.gungnirlaevatain.mock.sample.proxy;

import com.github.gungnirlaevatain.mock.sample.MockSampleApplication;
import com.github.gungnirlaevatain.mock.sample.TestResult;
import com.github.gungnirlaevatain.mock.sample.replace.ReplaceTestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The class Bean replace test.
 *
 * @author GungnirLaevatain
 * @version 2019 -07-25 23:25:06
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockSampleApplication.class)
public class BeanReplaceTest {

    @Autowired
    private ReplaceTestService replaceTestService;

    /**
     * Method: testReturnObjectByDefault()
     */
    @Test
    public void testTestReturnObjectByDefault() throws Exception {
        TestResult testResult = replaceTestService.testReturnObjectByDefault();
        Assert.assertEquals("AAA", testResult.getA());
        Assert.assertNotNull(testResult.getC());
        Assert.assertNotNull(testResult.getD());
        Assert.assertEquals(0, 3 - testResult.getB());
        Assert.assertEquals("BBB", testResult.getD().getA());
    }

    /**
     * Method: testReturnObjectByParam(Integer a, String b, TestResult result)
     */
    @Test
    public void testTestReturnObjectByParam() throws Exception {
        TestResult testResult = new TestResult();
        testResult.setA("CCC");
        TestResult result = replaceTestService.testReturnObjectByParam(1, "a", null);
        Assert.assertEquals("AAA", result.getA());
        result = replaceTestService.testReturnObjectByParam(null, "B", testResult);
        Assert.assertEquals("BBB", result.getA());
        result = replaceTestService.testReturnObjectByParam(2, null, testResult);
        Assert.assertEquals("CCC", result.getA());
    }

    /**
     * Method: testVoid()
     */
    @Test
    public void testTestVoid() throws Exception {
        replaceTestService.testVoid();
    }

    /**
     * Method: testReturnString()
     */
    @Test
    public void testTestReturnString() throws Exception {
        String result = replaceTestService.testReturnString();
        Assert.assertEquals("true", result);
    }

    /**
     * Method: testReturnInt()
     */
    @Test
    public void testTestReturnInt() throws Exception {
        int result = replaceTestService.testReturnInt();
        Assert.assertEquals(1, result);
    }

    /**
     * Method: testReturnInt(String a)
     */
    @Test
    public void testTestReturnIntA() throws Exception {
        int result = replaceTestService.testReturnInt("a");
        Assert.assertEquals(2, result);
    }

    /**
     * Method: testReturnInt(String a, TestResult result)
     */
    @Test
    public void testTestReturnIntForAResult() throws Exception {
        int result = replaceTestService.testReturnInt("c", null);
        Assert.assertEquals(3, result);
    }


} 
