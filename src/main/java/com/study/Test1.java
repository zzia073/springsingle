package com.study;

import com.study.bean.AOPBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ：fei
 * @date ：Created in 2019/10/24 0024 09:55
 * Spring xml配置时aop流程
 * 1.aop的准备流程
 *  a.spring在解析xml配置文件时用到DefaultNamespaceHandlerResolver名称空间解析处理器此解析器实现NamespaceHandlerResolver接口
 * 默认从spring的jar包META-INF/spring.handlers下加载对应的NamespaceHandler(例如aop包下的AopNamespaceHandler)
 * 该处理器会调用NamespaceHandler的init方法，一般所有的NamespaceHandler会继承NamespaceHandlerSupport类，在init方法中会调用
 * NamespaceHandlerSupport的registerBeanDefinitionParser解析对应的组件，比如<aop:aspectj-autoproxy/>对应的解析器
 * registerBeanDefinitionParser("aspectj-autoproxy", new AspectJAutoProxyBeanDefinitionParser());
 * 该解析器会给spring容器注入一个AnnotationAwareAspectJAutoProxyCreator，注解感知的AspectJ自动代理创建器
 *  b.spring如果用注解方式实现aop同样是通过注解给spring容器中注入 AnnotationAwareAspectJAutoProxyCreator 这个类
 * 2.AnnotationAwareAspectJAutoProxyCreator extends AspectJAwareAdvisorAutoProxyCreator
 *      extends AbstractAdvisorAutoProxyCreator extends AbstractAutoProxyCreator
 *          implements InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation 实例化之前执行
 *                      BeanPostProcessor#postProcessAfterInitialization 初始化之后执行
 * 以上是类关系
 * 2.bean创建流程
 * DefaultListableBeanFactory#getBean
 *  -->AbstractBeanFactory#doGetBean-->getSingleton
 *      -->DefaultSingletonBeanRegistry#getSingleton-->singletonFactory#getObject
 *      ====>>此时会根据lambda表达式判定AbstractBeanFactory的实现里有createBean方法的类调用
 *          -->AbstractAutowireCapableBeanFactory#createBean
 *          // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
 * 			Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
 * 		   以上方法是用户给定TargetSource
 *              -->AbstractAutowireCapableBeanFactory#doCreateBean
 *                  AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsAfterInitialization
 *                      AbstractAutoProxyCreator#wrapIfNecessary
 *              spring只会为Ioc容器管理的类创建SingletonTargetSource，该类会在执行获取代理目标对象时提供Ioc容器中的目标对象
 *	            Object proxy = createProxy(
 * 					bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
 *
 * TransactionDefinition  定义了事务的传播属性和隔离级别  子类 TransactionAttribute 事务属性的定义
 * TransactionInfo 持有事务管理器和事务属性、事务状态的信息 内部类不可见子类对事务的操作必须通过该类去执行
 * TransactionStatus
 * PlatformTransactionManager
 *      AbstractPlatformTransactionManager 抽象了各个事务管理器的模板方法对应子类去实现这些方法管理事务
 *          ...
 *          doBegin(transaction, def);开启事务
 *          ...
 *          doCommit(status);提交事务
 *          ...
 *          doRollback(status);回滚事务
 *
 *    a.getBean-->a.createBean-->singletonFactories.add("a",ObjectFactory(a))-->populate(b)
 * -->b.getBean-->b.createBean-->singletonFactories.add("b",ObjectFactory(b))-->populate(a)
 * -->a.getBean-->getSingleton(ObjectFactory(a).getObject())-->b.initial()-->b.addSingleton()
 * -->b.getBean返回-->populate(b)返回-->a.initial()-->a.addSingleton()
 * 当a.create()用构造方法注入在singletonFactories.add之前无法解决该类循环依赖
 * 当a和b都是prototype时无法解决循环依赖
 */
public class Test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        AOPBean bean = ac.getBean(AOPBean.class);
        bean.print();
    }
}
