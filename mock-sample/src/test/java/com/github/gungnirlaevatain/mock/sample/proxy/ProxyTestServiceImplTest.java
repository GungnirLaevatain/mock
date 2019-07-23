package test.com.github.gungnirlaevatain.mock.sample.proxy;

import com.github.gungnirlaevatain.mock.sample.MockSampleApplication;
import com.github.gungnirlaevatain.mock.sample.proxy.ProxyTestResult;
import com.github.gungnirlaevatain.mock.sample.proxy.ProxyTestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ProxyTestServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 23, 2019</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockSampleApplication.class)
public class ProxyTestServiceImplTest {

    @Autowired
    private ProxyTestService proxyTestService;

    /**
     * Method: testReturnObjectByDefault()
     */
    @Test
    public void testTestReturnObjectByDefault() throws Exception {
        ProxyTestResult proxyTestResult = proxyTestService.testReturnObjectByDefault();
        Assert.assertEquals("AAA", proxyTestResult.getA());
        Assert.assertNotNull(proxyTestResult.getC());
        Assert.assertNotNull(proxyTestResult.getD());
        Assert.assertEquals(0, 3 - proxyTestResult.getB());
        Assert.assertEquals("BBB", proxyTestResult.getD().getA());
    }

    /**
     * Method: testReturnObjectByParam(Integer a, String b, ProxyTestResult result)
     */
    @Test
    public void testTestReturnObjectByParam() throws Exception {
        ProxyTestResult proxyTestResult = new ProxyTestResult();
        proxyTestResult.setA("CCC");
        ProxyTestResult result = proxyTestService.testReturnObjectByParam(1, "a", null);
        Assert.assertEquals("AAA", result.getA());
        result = proxyTestService.testReturnObjectByParam(null, "B", proxyTestResult);
        Assert.assertEquals("BBB", result.getA());
        result = proxyTestService.testReturnObjectByParam(2, null, proxyTestResult);
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
     * Method: testReturnInt(String a, ProxyTestResult result)
     */
    @Test
    public void testTestReturnIntForAResult() throws Exception {
        int result = proxyTestService.testReturnInt("c", null);
        Assert.assertEquals(3, result);
    }


} 
