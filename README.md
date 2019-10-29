# MOCK
在平常开发中,经常要对外部接口进行mock处理以进行测试,从而导致代码内存在大量打桩数据,降低了代码的可读性。  
此包基于Javaassist字节码处理和在Spring切点进行动态代理,从而将配置文件内定义的mock数据注入代码中以分离mock流程和正常流程，增加代码可读性
## Getting started  
### Maven依赖  
```xml

<dependency>
  <groupId>com.github.gungnirlaevatain</groupId>
  <artifactId>mock</artifactId>
  <version>1.0.0</version>
</dependency>

```
### 新建配置文件  
于classpath下新建mock文件夹,并于此文件夹中新建yml格式的文本文件,例如class.yml等  
### 添加配置项
于文本文件内填入配置项  
### 运行
启动项目  
## 配置说明
### 例子
```
entities:
  # 指定使用的mock模式
  - type: BEAN_REPLACE
    # 指定mock的目标类
    className: com.github.gungnirlaevatain.mock.sample.replace.ReplaceTestServiceImpl
    # 指定需要mock的方法
    methods:
      # 需要mock的目标方法
      - method: testReturnObjectByDefault
        # 默认返回值,对象类型则为json字符串
        defaultResult: '{"a":"AAA","b":3,"c":"2019-07-23 00:32:00","d":{"a":"BBB"}}'
      - method: testReturnObjectByParam
        # 设定不同条件下的返回值
        results:
            # 所取的入参,基于JsonPath的语法,根节点为入参组成的数组
          - path: $.0
            # 该入参的期望值
            expected: 1
            # 当所取的入参值等同于期望值时,返回定义的结果
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
        # 若存在重载,则需要指定入参的类型
        paramClass:
          - java.lang.String
        defaultResult: 2
      - method: testReturnInt
        paramClass:
          - java.lang.String
          - com.github.gungnirlaevatain.mock.sample.TestResult
        defaultResult: "3"

```
### 配置项说明  
<table>
  <thead>
    <tr>
      <th>键名</th><th>数据结构</th><th>默认值</th><th>说明</th>
    </tr>  
   </thead>
   <tbody>
    <tr> <td>entities</td><td>List</td> <td>null</td> <td>配置文件的起始键名</td> </tr> 
    <tr> <td>&nbsp;&nbsp;├─type</td> <td>String</td><td>BEAN_PROXY</td> <td>采用的mock方式,分为BEAN_PROXY,BEAN_REPLACE,CLASS三种方式</td> </tr> 
    <tr> <td>&nbsp;&nbsp;├─className</td> <td>String</td><td>null</td> <td>mock的目标类名</td> </tr> 
    <tr> <td>&nbsp;&nbsp;├─methods</td><td>List</td> <td>null</td> <td>mock的目标类中的方法列表</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;├─method</td><td>String</td> <td>null</td> <td>mock的目标类中的方法名称</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;├─defaultResult</td><td>String</td> <td>null</td> <td>默认的返回值,若为对象,则为json格式的字符串</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;├─paramClass</td><td>List</td> <td>null</td> <td>方法的入参参数的类名称集合,次序和入参顺序一致</td> </tr>
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;├─results</td><td>List</td> <td>null</td> <td>定义的结果返回规则</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─path</td><td>String</td> <td>null</td> <td>对入参集合(args[])进行取值的基于JsonPath规则的路径信息</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─expected</td><td>String</td> <td>null</td> <td>期望值</td> </tr> 
    <tr> <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├─result</td><td>String</td> <td>null</td> <td>当与期望值一致时,返回此处定义的结果</td> </tr> 
   </tbody>
</table>  

### Mock方式说明  
#### 1.BEAN_PROXY方式  
基于Spring的BeanPostProcessor扩展点,根据配置对容器内的对应的bean进行代理拦截
#### 2.BEAN_REPLACE方式  
基于Spring的BeanPostProcessor扩展点,用动态生成的代理类实例替换准备实例化的原始bean,用于当前环境无法实例化原始bean时使用
#### 3.CLASS方式  
基于Javaassist的字节码处理技术,对非容器管理的类进行基于字节码的修改
## 样例代码
### 例子   
- 编写原始类
```
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
- 配置定义
```
entities:
  - type: BEAN_PROXY
    className: com.github.gungnirlaevatain.mock.sample.proxy.ProxyTestService
    methods:
      - method: testReturnObjectByDefault
        defaultResult: '{"a":"AAA","b":3,"c":"2019-07-23 00:32:00","d":{"a":"BBB"}}'
```
- 编写测试类
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
- 输出信息如下
```
TestResult(a=AAA, b=3, c=Tue Jul 23 00:32:00 CST 2019, d=TestResult(a=BBB, b=null, c=null, d=null))
```
### 详细信息
[请见mock-sample模块](https://github.com/GungnirLaevatain/mock/tree/master/mock-sample)
