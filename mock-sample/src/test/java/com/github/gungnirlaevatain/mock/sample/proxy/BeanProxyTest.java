package test.com.github.gungnirlaevatain.mock.sample.proxy;

import com.github.gungnirlaevatain.mock.sample.MockSampleApplication;
import com.github.gungnirlaevatain.mock.sample.TestResult;
import com.github.gungnirlaevatain.mock.sample.proxy.ProxyTestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The class Proxy test service impl test.
 *
 * @author GungnirLaevatain
 * @version 2019 -07-25 23:24:19
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockSampleApplication.class)
public class BeanProxyTest {

    @Autowired
    private ProxyTestService proxyTestService;

    /**
     * Method: testReturnObjectByDefault()
     */
    @Test
    public void testTestReturnObjectByDefault() throws Exception {
        TestResult testResult = proxyTestService.testReturnObjectByDefault();
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
        TestResult result = proxyTestService.testReturnObjectByParam(1, "a", null);
        Assert.assertEquals("AAA", result.getA());
        result = proxyTestService.testReturnObjectByParam(null, "B", testResult);
        Assert.assertEquals("BBB", result.getA());
        result = proxyTestService.testReturnObjectByParam(2, null, testResult);
        Assert.assertEquals("CCC", result.getA());
    }

    /**
     * Method: testVoid()
     */
    @Test
    public void testTestVoid() throws Exception {
        proxyTestService.testVoid();
    }

    /**
     * Method: testReturnString()
     */
    @Test
    public void testTestReturnString() throws Exception {
        String result = proxyTestService.testReturnString();
        Assert.assertEquals("true", result);
    }

    /**
     * Method: testReturnInt()
     */
    @Test
    public void testTestReturnInt() throws Exception {
        int result = proxyTestService.testReturnInt();
        Assert.assertEquals(1, result);
    }

    /**
     * Method: testReturnInt(String a)
     */
    @Test
    public void testTestReturnIntA() throws Exception {
        int result = proxyTestService.testReturnInt("a");
        Assert.assertEquals(2, result);
    }

    /**
     * Method: testReturnInt(String a, TestResult result)
     */
    @Test
    public void testTestReturnIntForAResult() throws Exception {
        int result = proxyTestService.testReturnInt("c", null);
        Assert.assertEquals(3, result);
    }


} 
