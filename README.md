# MOCK
[**中文说明**](https://github.com/GungnirLaevatain/mock/blob/master/README_cn.md)  
Developers often mock external interfaces for testing, so there's a lot of test code in the business code.This reduces readability and maintainability.  
To solve this problem, we plan to move the test code to the configuration file, and then use **javaassist** or **spring aop** to process the target object or class.  
Based on this plan, we have developed this tool.
## Getting Started  
### Adding Maven Dependencies  
```xml

<dependency>
  <groupId>com.github.gungnirlaevatain</groupId>
  <artifactId>mock</artifactId>
  <version>1.0.0</version>
</dependency>

```
### Create a new configuration file  
1. create a new folder named **mock** in classpath
2. create a new text file in **YAML** format in **mock**,such as class.yml 
### Write configuration information
Write configuration information to the file  
### Run
Run your project  
## Configuration
### Configuration example
```
entities:
  # specify the mock mode to use
  - type: BEAN_REPLACE
    # specify target class
    className: com.github.gungnirlaevatain.mock.sample.replace.ReplaceTestServiceImpl
    methods:
      # specify target method
      - method: testReturnObjectByDefault
        # default value
        defaultResult: '{"a":"AAA","b":3,"c":"2019-07-23 00:32:00","d":{"a":"BBB"}}'
      - method: testReturnObjectByParam
        # specify the return value under different conditions
        results:
            # Get the input parameter based on the syntax of jsonpath. 
            # The root node is an array of input parameters
          - path: $.0
            # Expected value
            expected: 1
            # When the input parameter value is equal to the expected value, return the defined result
            result: '{"a":"AAA"}'
          - path: $.1
            expected: 'B'
            result: '{"a":"BBB"}'
          - path: $.2.a
            expected: 'CCC'
            result: '{"a":"CCC"}'
      - method: testVoid
        defaultResult: '{"a":"AAA","b":3,"c":"2019-07-23 00:32:00","d":{"a":"BBB"}}'
      - method: testReturnString
        defaultResult: 'true'
      - method: testReturnInt
        defaultResult: '1'
      - method: testReturnInt
        # For overloaded methods, need to specify the type of the input parameter
        paramClass:
          - java.lang.String
        defaultResult: 2
      - method: testReturnInt
        paramClass:
          - java.lang.String
          - com.github.gungnirlaevatain.mock.sample.TestResult
        defaultResult: "3"

```
### Configuration information  
<table>
  <thead>
    <tr>
      <th>key</th><th>type</th><th>default value</th><th>description</th>
    </tr>  
   </thead>
   <tbody>
    <tr> <td>entities</td><td>List</td> <td>null</td> <td>root key</td> </tr> 
    <tr> <td>&nbsp;&nbsp;├─type</td> <td>String</td><td>BEAN_PROXY</td> <td>mock mode</td> </tr> 
    <tr> <td>&nbsp;&nbsp;├─className</td> <td>String</td><td>null</td> <td>target class name</td> </tr> 
    <tr> <td>&nbsp;&nbsp;├─methods</td><td>List</td> <td>null</td> <td>target methods</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;├─method</td><td>String</td> <td>null</td> <td>target method name</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;├─defaultResult</td><td>String</td> <td>null</td> <td>default return value. If it is an object, it is a string in JSON format</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;├─paramClass</td><td>List</td> <td>null</td> <td>the list of the input parameter class name</td> </tr>
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;├─results</td><td>List</td> <td>null</td> <td>rules for returning results</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─path</td><td>String</td> <td>null</td> <td>get the input parameter based on the syntax of jsonpath,the root node is an array of input parameters</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─expected</td><td>String</td> <td>null</td> <td>expected value</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─result</td><td>String</td> <td>null</td> <td>when the input parameter value is equal to the expected value, return the defined result</td> </tr> 
   </tbody>
</table>  

### Mock Mode   
#### 1.BEAN_PROXY  
Based on **BeanPostProcessor**, proxy target bean 
#### 2.BEAN_REPLACE 
Based on **InstantiationAwareBeanPostProcessor**, replace the target bean with a proxy instance when the target bean cannot be generated
#### 3.CLASS  
Based on **Javaassist**, modify target class
## Sample Code
### Example   
- Write the target class
```
@Service
@Slf4j
public class ProxyTestServiceImpl implements ProxyTestService {
    /**
     * Test return object by default.
     *
     * @return the proxy test result
     * @author GungnirLaevatain
     */
    @Override
    public TestResult testReturnObjectByDefault() {
        return null;
    }
}
@Data
public class TestResult {

    private String a;
    private Integer b;
    private Date c;
    private TestResult d;
}
```
- Define configuration
```
entities:
  - type: BEAN_PROXY
    className: com.github.gungnirlaevatain.mock.sample.proxy.ProxyTestService
    methods:
      - method: testReturnObjectByDefault
        defaultResult: '{"a":"AAA","b":3,"c":"2019-07-23 00:32:00","d":{"a":"BBB"}}'
```
- Write test class
```
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
        System.out.println(testResult);
    }
}
```
- Output
```
TestResult(a=AAA, b=3, c=Tue Jul 23 00:32:00 CST 2019, d=TestResult(a=BBB, b=null, c=null, d=null))
```
### Details
[See **mock-sample** module](https://github.com/GungnirLaevatain/mock/tree/master/mock-sample)
