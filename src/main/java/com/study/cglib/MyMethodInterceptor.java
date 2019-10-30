package com.study.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author ：fei
 * @date ：Created in 2019/10/25 0025 10:24
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(o.getClass().getName() +"." + method.getName());
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("执行结束");
        return result;
    }
}
