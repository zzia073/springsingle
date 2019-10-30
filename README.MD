####Spring学习

BeanFactory spring容器顶层接口
spring容器基本构造方式
资源 = new 资源(路径); ClassPathResource resource = new ClassPathResource("beans.xml");
容器 = new 容器(); DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
为给定的容器创建一个资源解析器 
资源解析器 = new 资源解析器(容器); XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
reader.loadBeanDefinition(resource);

将容器的构建和资源解析解耦合
####Spring 加载并装配对象的过程
  1. 定义好Spring的配置文件
  2. 通过Resource对象将Spring配置文件进行抽象，抽象成一个Resource对象
  3. 定义好Bean工厂(各种BeanFactory)
  4. 定义好XMLBeanDefinitionReader对象，并将工厂作为参数传递进去供后续回调使用。
  5. 通过reader对象读取之前抽象出来的Resource对象（包含了XML文件的解析过程）。
  6. Ioc容器创建完毕，用户可以通过容器获取到所需的对象信息。
  
spring设计原理

1. Ioc XML形式
   1. 资源加载 
      - spring配置文件的抽象 Resource 
      - spring只能读配置文件不能修改因此只有对资源输入流的抽象BeanDefinitionReader
   2. 工厂创建 
      - Ioc容器的抽象 BeanFactory
   3. 资源解析 
      - 解析器的抽象 BeanDefinitionParser(BeanDefinitionParserDelegate)


BeanDefinitionParserDelegate 该类中定义了所有spring配置文件的标签和标签中属性的值用来解析spring的配置文件
在BeanDefinitionReader注册BeanDefinition时会设置用BeanDefinitionParserDelegate去代理解析xml获取BeanDefinition
DefaultBeanDefinitionDocumentReader#doRegisterBeanDefinitions 这个方法是注册bean定义的由于beans中可以引入其他配置文件
因此会使beans标签嵌套，在调用注册bean定义的方法时会造成递归循环，这个delegate在注册bean定义时会先将当前配置文件上下文的代理parent
设置为null，创建一个解析代理对象设置为当前bean注册代理，当解析过程中遇到beans标签时会将this.delegate设置为parent代理对象
再去创建一个新的代理对象解析当前beans中的元素，当解析完成后会将parent赋值给this.delegate

ProxyFactoryBean 工厂bean的一个实现用于构建aop代理类基于spring bean工厂中的beans
代理类都是从ProxyFactoryBean中的getObject中获取的



Advisor是advice和pointCut的集合advice是用什么切入一般指的切入要执行的方法，pointcut是在哪切入，前后环绕之类






spring AOP依赖于Ioc，在创建Bean实例的时候拦截实例化过程
1. getBean-->createBean 创建bean实例
AbstractAutowireCapableBeanFactory#createBean
   1. // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
   2. Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
2. 如果bean实现了InstantiationAwareBeanPostProcessor接口则会调用postProcessBeforeInstantiation
执行实例化前置处理，如果调用该方法返回了bean实例则该实例就会作为bean实例返回
3. 

FactoryBean implementation that builds an AOP proxy based on beans in Spring BeanFactory


The full set of initialization methods and their standard order is:
BeanNameAware's {@code setBeanName}
BeanClassLoaderAware's {@code setBeanClassLoader}
BeanFactoryAware's {@code setBeanFactory}
EnvironmentAware's {@code setEnvironment}
EmbeddedValueResolverAware's {@code setEmbeddedValueResolver}
ResourceLoaderAware's {@code setResourceLoader}
(only applicable when running in an application context)
ApplicationEventPublisherAware's {@code setApplicationEventPublisher}
(only applicable when running in an application context)
MessageSourceAware's {@code setMessageSource}
(only applicable when running in an application context)
ApplicationContextAware's {@code setApplicationContext}
(only applicable when running in an application context)
ServletContextAware's {@code setServletContext}
(only applicable when running in a web application context)
{@code postProcessBeforeInitialization} methods of BeanPostProcessors
InitializingBean's {@code afterPropertiesSet}
a custom init-method definition
{@code postProcessAfterInitialization} methods of BeanPostProcessors
On shutdown of a bean factory, the following lifecycle methods apply:
{@code postProcessBeforeDestruction} methods of DestructionAwareBeanPostProcessors
DisposableBean's {@code destroy}
a custom destroy-method definition