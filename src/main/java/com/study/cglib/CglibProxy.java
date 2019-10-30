package com.study.cglib;

import com.study.bean.TestBean;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author ：fei
 * @date ：Created in 2019/10/25 0025 10:31
 * cglib代理的步骤
 * 1.创建一个回调Callback，测试用的是cglib的MethodInterceptor
 *      a.设置父类方法执行前的一些动作preRequest()
 *      b.执行父类的方法invokeSuper
 *      c.设置父类方法执行后的一些动作postRequest()
 * 2.用cglib的增强器获得代理类
 *      a.Enhancer设置子类
 *      b.设置回调方法
 *      c.create创建代理类
 * 3.代理类是被代理类的子类，直接可以调用被代理类的方法
 *
 */
public class CglibProxy {
    private Enhancer enhancer = new Enhancer();
    public Object getProxy(Class<?> clazz){
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MyMethodInterceptor());
        return enhancer.create();
    }

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        TestBean proxy = (TestBean) cglibProxy.getProxy(TestBean.class);
        System.out.println("==============");
        System.out.println(proxy.getClass().getName());
        System.out.println("==============");
        System.out.println(proxy.getClass().getSuperclass());
        System.out.println("==============");
        proxy.getBean();
    }
}
