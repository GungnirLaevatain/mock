package com.github.gungnirlaevatain.mock.sample.proxy;

import com.github.gungnirlaevatain.mock.sample.MockSampleApplication;
import com.github.gungnirlaevatain.mock.sample.TestResult;
import com.github.gungnirlaevatain.mock.sample.cls.ClassTestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The class Class test.
 *
 * @author GungnirLaevatain
 * @version 2019 -07-29 23:46:55
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockSampleApplication.class)
public class ClassTest {

    private ClassTestUtil classTestUtil = new ClassTestUtil();

    @Test
    public void testTestStaticObjectByDefault() throws Exception {
        TestResult testResult = ClassTestUtil.testStaticObjectByDefault();
        Assert.assertEquals("CCC", testResult.getA());
        Assert.assertNotNull(testResult.getC());
        Assert.assertNotNull(testResult.getD());
        Assert.assertEquals(0, 3 - testResult.getB());
        Assert.assertEquals("BBB", testResult.getD().getA());
    }

    /**
     * Method: testReturnObjectByDefault()
     */
    @Test
    public void testTestReturnObjectByDefault() throws Exception {
        TestResult testResult = classTestUtil.testReturnObjectByDefault();
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
        TestResult result = classTestUtil.testReturnObjectByParam(1, "a", null);
        Assert.assertEquals("AAA", result.getA());
        result = classTestUtil.testReturnObjectByParam(null, "B", testResult);
        Assert.assertEquals("BBB", result.getA());
        result = classTestUtil.testReturnObjectByParam(2, null, testResult);
        Assert.assertEquals("CCC", result.getA());
    }

    /**
     * Method: testVoid()
     */
    @Test
    public void testTestVoid() throws Exception {
        classTestUtil.testVoid();
    }

    /**
     * Method: testReturnString()
     */
    @Test
    public void testTestReturnString() throws Exception {
        String result = classTestUtil.testReturnString();
        Assert.assertEquals("true", result);
    }

    /**
     * Method: testReturnInt()
     */
    @Test
    public void testTestReturnInt() throws Exception {
        int result = classTestUtil.testReturnInt();
        Assert.assertEquals(1, result);
    }

    /**
     * Method: testReturnInt(String a)
     */
    @Test
    public void testTestReturnIntA() throws Exception {
        int result = classTestUtil.testReturnInt("a");
        Assert.assertEquals(2, result);
    }

    /**
     * Method: testReturnInt(String a, TestResult result)
     */
    @Test
    public void testTestReturnIntForAResult() throws Exception {
        int result = classTestUtil.testReturnInt("c", null);
        Assert.assertEquals(3, result);
    }


} 
