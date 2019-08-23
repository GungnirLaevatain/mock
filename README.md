# MOCK
无侵入性的，多模式的，基于配置的，对原始代码进行MOCK处理的工具  
## Getting started  
### Maven依赖  
### 新建配置文件  
于classpath下新建mock文件夹,并于此文件夹中新建yml格式的文本文件  
### 添加配置项
于文本文件内填入配置项  
### 运行
启动项目  
## 配置说明
### 配置项说明  
<table>
  <thead class="ant-table-thead">
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
基于Spring的AOP,根据配置对容器内的对应bean进行代理拦截
#### 2.BEAN_REPLACE方式  
基于Spring的BeanPostProcessor扩展点,用动态生成的代理类实例替换准备实例化的原始bean,用于当前环境无法实例化原始bean时使用
#### 3.CLASS方式  
基于Javaassist,对非容器管理的类进行基于字节码的修改
## 样例代码   
请见mock-sample模块