package com.study.bean;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author ：fei
 * @date ：Created in 2019/10/24 0024 09:43
 */
@Aspect
public class AOPBeanAspect {
    @Around("execution(public void com.study.bean.AOPBean.*(..))")
    public Object cut1(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println(joinPoint.getSignature().getName() + "proceed before");
        Object proceed = joinPoint.proceed();
        System.out.println(joinPoint.getSignature().getName() + "proceed after");
        return proceed;
    }
}
